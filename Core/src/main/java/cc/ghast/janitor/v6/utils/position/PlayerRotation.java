package cc.ghast.janitor.v6.utils.position;

import cc.ghast.janitor.v6.utils.math.MathUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 15-Mar-20
 */

@Getter
public class PlayerRotation extends SimpleRotation {
    private final long timestamp;
    private final Player player;

    public PlayerRotation(Player player, float yaw, float pitch, long timestamp) {
        super(yaw, pitch);
        this.timestamp = timestamp;
        this.player = player;
    }

    public double getDeltaYaw(SimpleRotation two) {
        return MathUtil.distanceBetweenAngles(yaw, two.getYaw());
    }

    public double getDeltaPitch(SimpleRotation two) {
        return MathUtil.distanceBetweenAngles(pitch, two.getPitch());
    }


}
