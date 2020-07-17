package cc.ghast.janitor.v6.utils.block;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * @author Ghast
 * @since 18/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */
public class BlockUtil {
    public static Block getBlockAsync(Location loc) {
        if (!loc.getWorld().isChunkInUse(loc.getBlockX() << 4, loc.getBlockZ() << 4)) return null;
        return loc.getWorld().getBlockAt(loc);
    }

    public static Block getBlockAsync(World world, int x, int y, int z) {
        if (!world.isChunkLoaded(x << 4, z << 4)) return null;
        return getBlockAsync(new Location(world, x, y, z));
    }

    public static float getSlipperiness(Block block) {
        switch (XMaterial.matchXMaterial(block.getType())) {
            case SLIME_BLOCK:
                return 0.8F;
            case ICE:
            case PACKED_ICE:
            case BLUE_ICE:
            case FROSTED_ICE:
                return 0.98F;
            default:
                return 0.6F;
        }
    }
}

