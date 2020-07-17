package cc.ghast.janitor.v6.utils;

import lombok.Getter;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Ghast
 * @since 11-Jan-20
 * Ghast CC © 2019
 */
@Getter
public class BoundingBox {

    public double minX, minY, minZ, maxX, maxY, maxZ;
    private final long timestamp;


    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, long timestamp) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.timestamp = timestamp;
    }

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this(minX, minY, minZ, maxX, maxY, maxZ, System.currentTimeMillis());
    }

    public BoundingBox(Vector min, Vector max, long timestamp) {
        this.minX = min.getX();
        this.minZ = min.getZ();
        this.minY = min.getY();
        this.maxX = max.getX();
        this.maxY = max.getY();
        this.maxZ = max.getZ();
        this.timestamp = timestamp;
    }

    public BoundingBox shrink(double x, double y, double z) {
        minX += x;
        minY += y;
        minZ += z;
        maxX -= x;
        maxY -= y;
        maxZ -= z;
        return this;
    }

    public BoundingBox expand(double x, double y, double z) {
        minX -= x;
        minY -= y;
        minZ -= z;
        maxX += x;
        maxY += y;
        maxZ += z;
        return this;
    }

    public BoundingBox expandMax(double x, double y, double z) {
        maxX += x;
        maxY += y;
        maxZ += z;
        return this;
    }

    public BoundingBox expandMin(double x, double y, double z) {
        minX -= x;
        minY -= y;
        minZ -= z;
        return this;
    }

    public BoundingBox add(double x, double y, double z) {
        minX += x;
        minY += y;
        minZ += z;
        maxX += x;
        maxY += y;
        maxZ += z;
        return this;
    }

    public BoundingBox subtract(double x, double y, double z) {
        minX -= x;
        minY -= y;
        minZ -= z;
        maxX -= x;
        maxY -= y;
        maxZ -= z;
        return this;
    }

    public BoundingBox subtractMin(double x, double y, double z) {
        minX -= x;
        minY -= y;
        minZ -= z;
        return this;
    }

    public BoundingBox subtractMax(double x, double y, double z) {
        maxX -= x;
        maxY -= y;
        maxZ -= z;
        return this;
    }

    public BoundingBox union(BoundingBox other) {
        double d0 = Math.min(this.minX, other.minX);
        double d1 = Math.min(this.minY, other.minY);
        double d2 = Math.min(this.minZ, other.minZ);
        double d3 = Math.max(this.maxX, other.maxX);
        double d4 = Math.max(this.maxY, other.maxY);
        double d5 = Math.max(this.maxZ, other.maxZ);
        return new BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    public BoundingBox offset(double x, double y, double z) {
        return new BoundingBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z, System.currentTimeMillis());
    }

    public BoundingBox contract(double x, double y, double z) {
        double d0 = this.minX + x;
        double d1 = this.minY + y;
        double d2 = this.minZ + z;
        double d3 = this.maxX - x;
        double d4 = this.maxY - y;
        double d5 = this.maxZ - z;
        return new BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    public double middleX() {
        return (minX + maxX) / 2.0D;
    }

    public double middleY() {
        return (minY + maxY) / 2.0D;
    }

    public double middleZ() {
        return (minZ + maxZ) / 2.0D;
    }

    public double sizeX() {
        return Math.abs(minX - maxX);
    }

    public double sizeZ() {
        return Math.abs(minZ - maxZ);
    }

    public double distance(BoundingBox box) {

        double x = Math.abs(middleX() - box.middleX());
        double y = Math.abs(middleY() - box.middleY());
        double z = Math.abs(middleZ() - box.middleZ());
        return Math.sqrt(x * x + z * z + y * y);
    }

    public double distanceXZ(BoundingBox box) {

        double x = Math.abs(middleX() - box.middleX());
        double z = Math.abs(middleZ() - box.middleZ());
        return Math.sqrt(x * x + z * z);
    }

    public double distanceXZ(Vector vec) {

        double x = Math.abs(middleX() - vec.getX());
        double z = Math.abs(middleZ() - vec.getZ());
        return Math.sqrt(x * x + z * z);
    }

    public double distanceMinMax() {

        // Pythagoras
        double minXZDiagonal = Math.sqrt((minX * minX) + (minZ * minZ));
        return Math.sqrt((minXZDiagonal * minXZDiagonal) + (maxY * maxY));
    }

    public boolean checkCollision(final World world, final Predicate<Material> predicate) {
        boolean start = false;
        for (Material material : collidingMaterials(world)) {
            if (predicate.test(material)) start = true;
        }

        return start;
    }

    public List<Material> collidingMaterials(World world) {
        List<Material> mats = new ArrayList<>();

        int x = (int) Math.floor(minX);
        int y = (int) Math.max(Math.floor(minY), 0);
        int z = (int) Math.floor(minZ);

        final int x2 = (int) Math.floor(maxX + 1.0D);
        final int y2 = (int) Math.floor(maxY + 1.0D);
        final int z2 = (int) Math.floor(maxZ + 1.0D);

        for (int ax = x; ax < x2; ax++) {
            for (int az = z; az < z2; az++) {
                if (world.isChunkLoaded(ax << 4, az << 4)) {
                    for (int ay = y; ay < y2; ay++) {
                        Block block = world.getBlockAt(ax, ay, az);
                        mats.add(block.getType());
                    }
                }
            }

        }

        /*while (x < x2) {
            while (y < y2) {
                while (z < z2) {
                    Location loc = new Location(world, x, y, z);
                    if (world.isChunkLoaded(loc.getBlockX(), loc.getBlockZ())){
                        Block block = world.getBlockAt(loc);
                        mats.add(block.getType());
                    }
                    z++;
                }
                y++;
            }
            x++;
        }*/

        return mats;
    }

    public boolean isVectorInside(Vector var1) {
        if (var1 == null) {
            return false;
        } else {
            return var1.getX() >= minX && var1.getX() <= maxX
                    && var1.getZ() >= minZ && var1.getZ() <= maxZ
                    && var1.getX() >= minY && var1.getY() <= maxY;
        }
    }


    public BoundingBox cloneBB() {
        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ, timestamp);
    }


    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(BoundingBox other, double offsetX) {
        if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            if (offsetX > 0.0D && other.maxX <= this.minX) {
                double d1 = this.minX - other.maxX;

                if (d1 < offsetX) {
                    offsetX = d1;
                }
            } else if (offsetX < 0.0D && other.minX >= this.maxX) {
                double d0 = this.maxX - other.minX;

                if (d0 > offsetX) {
                    offsetX = d0;
                }
            }

            return offsetX;
        } else {
            return offsetX;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(BoundingBox other, double offsetY) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
            if (offsetY > 0.0D && other.maxY <= this.minY) {
                double d1 = this.minY - other.maxY;

                if (d1 < offsetY) {
                    offsetY = d1;
                }
            } else if (offsetY < 0.0D && other.minY >= this.maxY) {
                double d0 = this.maxY - other.minY;

                if (d0 > offsetY) {
                    offsetY = d0;
                }
            }

            return offsetY;
        } else {
            return offsetY;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(BoundingBox other, double offsetZ) {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
            if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
                double d1 = this.minZ - other.maxZ;

                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            } else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
                double d0 = this.maxZ - other.minZ;

                if (d0 > offsetZ) {
                    offsetZ = d0;
                }
            }

            return offsetZ;
        } else {
            return offsetZ;
        }
    }

    public BoundingBox addCoord(double x, double y, double z) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D) {
            d0 += x;
        } else if (x > 0.0D) {
            d3 += x;
        }

        if (y < 0.0D) {
            d1 += y;
        } else if (y > 0.0D) {
            d4 += y;
        }

        if (z < 0.0D) {
            d2 += z;
        } else if (z > 0.0D) {
            d5 += z;
        }

        return new BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInYZ(Point vec) {
        return vec != null && (vec.getY() >= this.minY && vec.getY() <= this.maxY && vec.getZ() >= this.minZ && vec.getZ() <= this.maxZ);
    }

    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXZ(Point vec) {
        return vec != null && (vec.getX() >= this.minX && vec.getX() <= this.maxX && vec.getZ() >= this.minZ && vec.getZ() <= this.maxZ);
    }

    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXY(Point vec) {
        return vec != null && (vec.getX() >= this.minX && vec.getX() <= this.maxX && vec.getY() >= this.minY && vec.getY() <= this.maxY);
    }

    public MovingPoint calculateIntercept(Point vecA, Point vecB) {
        Point vec3 = vecA.getIntermediateWithXValue(vecB, this.minX);
        Point vec31 = vecA.getIntermediateWithXValue(vecB, this.maxX);
        Point vec32 = vecA.getIntermediateWithYValue(vecB, this.minY);
        Point vec33 = vecA.getIntermediateWithYValue(vecB, this.maxY);
        Point vec34 = vecA.getIntermediateWithZValue(vecB, this.minZ);
        Point vec35 = vecA.getIntermediateWithZValue(vecB, this.maxZ);

        if (!this.isVecInYZ(vec3)) {
            vec3 = null;
        }

        if (!this.isVecInYZ(vec31))
        {
            vec31 = null;
        }

        if (!this.isVecInXZ(vec32))
        {
            vec32 = null;
        }

        if (!this.isVecInXZ(vec33))
        {
            vec33 = null;
        }

        if (!this.isVecInXY(vec34))
        {
            vec34 = null;
        }

        if (!this.isVecInXY(vec35))
        {
            vec35 = null;
        }

        Point vec36 = null;

        if (vec3 != null)
        {
            vec36 = vec3;
        }

        if (vec31 != null && (vec36 == null || vecA.squareDistanceTo(vec31) < vecA.squareDistanceTo(vec36)))
        {
            vec36 = vec31;
        }

        if (vec32 != null && (vec36 == null || vecA.squareDistanceTo(vec32) < vecA.squareDistanceTo(vec36)))
        {
            vec36 = vec32;
        }

        if (vec33 != null && (vec36 == null || vecA.squareDistanceTo(vec33) < vecA.squareDistanceTo(vec36)))
        {
            vec36 = vec33;
        }

        if (vec34 != null && (vec36 == null || vecA.squareDistanceTo(vec34) < vecA.squareDistanceTo(vec36)))
        {
            vec36 = vec34;
        }

        if (vec35 != null && (vec36 == null || vecA.squareDistanceTo(vec35) < vecA.squareDistanceTo(vec36)))
        {
            vec36 = vec35;
        }

        if (vec36 == null)
        {
            return null;
        }
        else
        {
            EnumFacing enumfacing = null;

            if (vec36 == vec3)
            {
                enumfacing = EnumFacing.WEST;
            }
            else if (vec36 == vec31)
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (vec36 == vec32)
            {
                enumfacing = EnumFacing.DOWN;
            }
            else if (vec36 == vec33)
            {
                enumfacing = EnumFacing.UP;
            }
            else if (vec36 == vec34)
            {
                enumfacing = EnumFacing.NORTH;
            }
            else
            {
                enumfacing = EnumFacing.SOUTH;
            }

            return new MovingPoint(vec36, enumfacing);
        }
    }

    public void draw(Effect particle, List<? extends Player> players) {
        List<Float> xVars = MathUtil.skipValues(0.3, minX, maxX);
        List<Float> yVars = MathUtil.skipValues(0.3, minY, maxY);
        List<Float> zVars = MathUtil.skipValues(0.3, minZ, maxZ);

        for (float x : xVars){
            for (float y : yVars) {
                for (float z : zVars){
                    /*PacketServerWorldParticle packet = new PacketServerWorldParticle(particle, true, x, y, z, 0F, 0F, 0F, 0, 1);
                    */
                    players.forEach(p -> {
                        p.spigot().playEffect(new Location(p.getWorld(), x, y, z), particle, 0, 0,0F,0F,0F,0.0F,1, 0);
                    });
                }
            }
        }
    }
}
