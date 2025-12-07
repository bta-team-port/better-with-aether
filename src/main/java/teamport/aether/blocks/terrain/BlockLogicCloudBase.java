package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.entity.monster.zephyr.MobZephyr;

public class BlockLogicCloudBase extends BlockLogicTransparent {
    public BlockLogicCloudBase(Block<?> block) {
        super(block, Material.air);
    }

    @Override
    public int getPistonPushReaction(World world, int x, int y, int z) {
        return 1;
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        entity.fallDistance = 0.0F;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public boolean getIsBlockSolid(WorldSource blockAccess, int x, int y, int z, Side side) {
        return false;
    }

    @Override
    public void handleEntityInside(World world, int x, int y, int z, Entity entity, Vec3 entityVelocity) {
        entity.fallDistance = 0.0F;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean collidesWithEntity(Entity entity, World world, int x, int y, int z) {
        if (entity instanceof Projectile || entity instanceof MobZephyr) return false;
        return super.collidesWithEntity(entity, world, x, y, z);
    }

    @Override
    public HitResult collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end, boolean useSelectorBoxes) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean isProjectile = false;

        for (int idx = 0; idx < Math.min(stackTrace.length, 20); idx++) {
            if (stackTrace[idx].getClassName().equals(Projectile.class.getName())) {
                isProjectile = true;
                break;
            }
        }

        return isProjectile ? null : super.collisionRayTrace(world, x, y, z, start, end, useSelectorBoxes);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return AABB.getPermanentBB(x, y, z, x + 1.0, y + 0.00001, z + 1.0);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!(entity instanceof MobZephyr)) {
            if (!entity.isSneaking() && entity.yd < 0.0) {
                entity.yd *= 0.005;
            }
            entity.fallDistance = 0.0F;
        }
    }

}
