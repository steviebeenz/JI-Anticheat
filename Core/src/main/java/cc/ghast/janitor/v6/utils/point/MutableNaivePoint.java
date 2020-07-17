package cc.ghast.janitor.v6.utils.point;

/**
 * @author Ghast
 * @since 10/07/2020
 * Ghast Holdings LLC / Artemis © 2020
 */
public class MutableNaivePoint extends NaivePoint {
    public MutableNaivePoint(int x, int y, int z) {
        super(x, y, z);
    }

    public MutableNaivePoint(double x, double y, double z) {
        super(x, y, z);
    }

    public void override(double x, double y, double z){
        this.x = (int) Math.floor(x);
        this.y = (int) Math.floor(y);
        this.z = (int) Math.floor(z);
    }

    public void override(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
