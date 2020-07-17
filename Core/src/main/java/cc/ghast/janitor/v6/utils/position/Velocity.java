package cc.ghast.janitor.v6.utils.position;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.util.Vector;

/**
 * @author Ghast
 * @since 24-Apr-20
 */
@Getter
@Setter
public class Velocity {
    private double x;
    private double y;
    private double z;
    private double horizontal, vertical;

    public Velocity(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.horizontal = Math.sqrt(x * x + z * z);
        this.vertical = Math.abs(y);
    }

    public Velocity(Vector v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Velocity add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public double getDistance() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public double getSquaredHorizontal() {
        return (x * x + z * z);
    }

    public double getSqr() {
        return x + y + z;
    }
}
