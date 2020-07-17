package cc.ghast.artemis.v4.utils.raytrace;

import cc.ghast.artemis.deprecated.minecraft.BlockPos;
import cc.ghast.artemis.v4.nms.minecraft.entity.EnumFacing;
import org.bukkit.entity.Entity;

public class MovingPoint {

    private BlockPos blockPos;

    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public MovingPoint.MovingObjectType typeOfHit;
    public EnumFacing sideHit;

    /** The vector position of the hit */
    public Point hitVec;

    /** The hit entity */
    public Entity entityHit;

    public MovingPoint(Point hitVecIn, EnumFacing facing, BlockPos blockPosIn)
    {
        this(MovingPoint.MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
    }

    public MovingPoint(Point p_i45552_1_, EnumFacing facing)
    {
        this(MovingPoint.MovingObjectType.BLOCK, p_i45552_1_, facing, BlockPos.ORIGIN);
    }

    public MovingPoint(Entity p_i2304_1_)
    {
        this(p_i2304_1_, new Point(p_i2304_1_.getLocation().getX(), p_i2304_1_.getLocation().getY(), p_i2304_1_.getLocation().getZ()));
    }

    public MovingPoint(MovingPoint.MovingObjectType typeOfHitIn, Point hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn)
    {
        this.typeOfHit = typeOfHitIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Point(hitVecIn.getX(), hitVecIn.getY(), hitVecIn.getZ());
    }

    public MovingPoint(Entity entityHitIn, Point hitVecIn)
    {
        this.typeOfHit = MovingPoint.MovingObjectType.ENTITY;
        this.entityHit = entityHitIn;
        this.hitVec = hitVecIn;
    }

    public BlockPos getBlockPos()
    {
        return this.blockPos;
    }

    public String toString()
    {
        return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }

    public static enum MovingObjectType
    {
        MISS,
        BLOCK,
        ENTITY;
    }
}
