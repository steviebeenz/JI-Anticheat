package cc.ghast.janitor.v6.utils.position;


import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 15-Mar-20
 */

@Getter
public class PlayerPosition extends SimplePosition {

    private final long timestamp;
    private final Player player;
    public World world;
    private double minX, centerX, maxX, minZ, centerZ, maxZ, minY, centerY, maxY;
    private cc.ghast.artemis.v4.utils.bounding.BoundingBox box;

    public PlayerPosition(Player player, double x, double y, double z, long timestamp) {
        super(x, y, z);
        this.timestamp = timestamp;
        this.player = player;
        this.world = player.getWorld();
        recalc();
        //box = new BoundingBox(this, timestamp);
    }

    public PlayerPosition(World player, double x, double y, double z, long timestamp) {
        super(x, y, z);
        this.timestamp = timestamp;
        this.player = null;
        this.world = player;
        recalc();
        //box = new BoundingBox(this, timestamp);
    }

    private void recalc() {
        // X value
        this.minX = x - 0.3;
        this.centerX = x;
        this.maxX = x + 0.3;

        // Y value
        this.minY = y;
        this.centerY = y + 0.925;
        this.maxY = y + 1.85;

        // Z value
        this.minZ = z - 0.3;
        this.centerZ = z;
        this.maxZ = z + 0.3;

        // Bounding box
        box = new cc.ghast.artemis.v4.utils.bounding.BoundingBox(this, timestamp);
    }

    public PlayerPosition add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        recalc();
        return this;
    }

    public PlayerPosition addCoord(double x, double y, double z) {
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

        this.minX = d0;
        this.minY = d1;
        this.minZ = d2;
        this.maxX = d3;
        this.maxY = d4;
        this.maxZ = d5;

        return this;
    }


    public PlayerPosition add(SimplePosition p) {
        this.x += p.x;
        this.y += p.y;
        this.z += p.z;
        recalc();
        return this;
    }

    public Location toBukkitLocation() {
        return new Location(world, x, y, z);
    }

    public double distanceXZ(PlayerPosition sec) {
        return Math.sqrt((sec.getX() - getX()) * (sec.getX() - getX()) + (sec.getZ() - getZ()) * (sec.getZ() - getZ()));
    }

    public double distanceY(PlayerPosition sec) {
        return Math.abs(sec.getY() - y);
    }

    public double distance(PlayerPosition sec) {
        return Math.abs(
                ((sec.getX() - getX()) * (sec.getX() - getX()))
                        + ((sec.getY() - getY()) * (sec.getY() - getY()))
                        + ((sec.getZ() - getZ()) * (sec.getZ() - getZ()))
        );
    }

    @Override
    public String toString() {
        return "PlayerPosition{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public PlayerPosition clone() {
        return new PlayerPosition(world, x, y, z, timestamp);
    }
}
