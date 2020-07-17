package cc.ghast.artemis.v4.utils.raytrace;

import cc.ghast.artemis.v3.packet.wrapper.tinyprotocol.packet.types.MathHelper;
import cc.ghast.artemis.v4.utils.position.SimplePosition;
import lombok.Getter;
import org.bukkit.util.Vector;

/**
 * @author Ghast
 * @since 19-May-20
 */

@Getter
public class Point {
    private final double x, y, z;

    public Point(double x, double y, double z) {

        if (x == -0.0D) {
            x = 0.0D;
        }

        if (y == -0.0D) {
            y = 0.0D;
        }

        if (z == -0.0D) {
            z = 0.0D;
        }

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(SimplePosition pos){
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public Point(Vector vec){
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    /**
     * Returns a new vector with the result of the specified vector minus this.
     */
    public Point subtractReverse(Point vec)
    {
        return new Point(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Point normalize()
    {
        double d0 = (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d0 < 1.0E-4D ? new Point(0.0D, 0.0D, 0.0D) : new Point(this.x / d0, this.y / d0, this.z / d0);
    }

    public double dotProduct(Point vec)
    {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public Point crossProduct(Point vec)
    {
        return new Point(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Point subtract(Point vec)
    {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Point subtract(double x, double y, double z)
    {
        return this.addVector(-x, -y, -z);
    }

    public Point add(Point vec)
    {
        return this.addVector(vec.x, vec.y, vec.z);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Point addVector(double x, double y, double z)
    {
        return new Point(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Euclidean distance between this and the specified vector, returned as double.
     */
    public double distanceTo(Point vec)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public double squareDistanceTo(Point vec)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * Returns the length of the vector.
     */
    public double lengthVector()
    {
        return (double) MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Point getIntermediateWithXValue(Point vec, double x)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;

        if (d0 * d0 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d3 = (x - this.x) / d0;
            return d3 >= 0.0D && d3 <= 1.0D ? new Point(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }

    /**
     * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Point getIntermediateWithYValue(Point vec, double y)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;

        if (d1 * d1 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d3 = (y - this.y) / d1;
            return d3 >= 0.0D && d3 <= 1.0D ? new Point(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Point getIntermediateWithZValue(Point vec, double z)
    {
        double d0 = vec.x - this.x;
        double d1 = vec.y - this.y;
        double d2 = vec.z - this.z;

        if (d2 * d2 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d3 = (z - this.z) / d2;
            return d3 >= 0.0D && d3 <= 1.0D ? new Point(this.x + d0 * d3, this.y + d1 * d3, this.z + d2 * d3) : null;
        }
    }


    public String toString()
    {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Point rotatePitch(float pitch)
    {
        float f = MathHelper.cos(pitch);
        float f1 = MathHelper.sin(pitch);
        double d0 = this.x;
        double d1 = this.y * (double)f + this.z * (double)f1;
        double d2 = this.z * (double)f - this.y * (double)f1;
        return new Point(d0, d1, d2);
    }

    public Point rotateYaw(float yaw)
    {
        float f = MathHelper.cos(yaw);
        float f1 = MathHelper.sin(yaw);
        double d0 = this.x * (double)f + this.z * (double)f1;
        double d1 = this.y;
        double d2 = this.z * (double)f - this.x * (double)f1;
        return new Point(d0, d1, d2);
    }

    public Vector toVector(){
        return new Vector(x, y, z);
    }
}
