package cc.ghast.artemis.v4.utils.position;

import cc.ghast.artemis.v4.utils.maths.MathUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 09-Apr-20
 */

@Getter
public class PlayerMovement extends PlayerPosition {

    public float yaw;
    public float pitch;

    public PlayerMovement(Player player, double x, double y, double z, float yaw, float pitch, long timestamp) {
        super(player, x, y, z, timestamp);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public PlayerMovement(World player, double x, double y, double z, float yaw, float pitch, long timestamp) {
        super(player, x, y, z, timestamp);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public PlayerMovement(PlayerPosition pos, PlayerRotation rot) {
        this(pos.getWorld(), pos.x, pos.y, pos.z, rot.yaw, rot.pitch, pos.getTimestamp());
    }

    @Override
    public PlayerMovement add(double x, double y, double z) {
        super.add(x, y, z);
        return this;
    }

    public float getYaw(){
        return yaw % 360;
    }

    @Override
    public Location toBukkitLocation() {
        return new Location(getWorld(), x, y, z, yaw % 360, pitch);
    }

    public int getBlockX() {
        return (int) Math.round(x);
    }

    public int getBlockY() {
        return (int) Math.round(y);
    }

    public int getBlockZ() {
        return (int) Math.round(z);
    }

    public double getDeltaYaw(PlayerMovement two) {
        return MathUtil.distanceBetweenAngles(yaw, two.getYaw());
    }

    public double getDeltaPitch(PlayerMovement two) {
        return MathUtil.distanceBetweenAngles(pitch, two.getPitch());
    }

    public PlayerMovement setWorld(World world){
        this.world = world;
        return this;
    }

    @Override
    public String toString() {
        return "PlayerMovement{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
