package cc.ghast.artemis.v4.utils.position;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 15-Mar-20
 */
@Getter
public class SimplePosition {
    public double x, y, z;
    public long timestamp = System.currentTimeMillis();

    public SimplePosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public Location toLocation(Player p) {
        return new Location(p.getWorld(), this.x, this.y, this.z);
    }

    public SimplePosition clone() {
        return new SimplePosition(x, y, z);
    }
}
