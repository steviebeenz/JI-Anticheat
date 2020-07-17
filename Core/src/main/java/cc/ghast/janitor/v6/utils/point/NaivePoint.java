package cc.ghast.artemis.v4.utils.raytrace;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NaivePoint {
    protected int x, y, z;

    public NaivePoint(double x, double y, double z) {
        this.x = (int) Math.floor(x);
        this.y = (int) Math.floor(y);
        this.z = (int) Math.floor(z);
    }
}
