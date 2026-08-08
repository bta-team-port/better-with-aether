package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import teamport.aether.entity.monster.zephyr.MobZephyr;

public class BlockLogicCloudBase extends BlockLogicTransparent {
    public BlockLogicCloudBase(Block<?> block) {
        super(block, Materials.AIR);
    }

    @Override
    public int getPistonPushReaction(World world, TilePosc pos) {
        return 1;
    }

    @Override
    public void onEntityWalkedOn(World world, TilePosc pos, Entity entity) {
        this.onEntityCollision(world, pos, entity);
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    public boolean getIsBlockSolid(WorldSource blockAccess, int x, int y, int z, Side side) {
        return false;
    }

    @Override
    public void onEntityInside(World world, TilePosc pos, Entity entity, Vector3d entityVelocity) {
        this.onEntityCollision(world, pos, entity);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean collidesWithEntity(Entity entity, World world, TilePosc pos) {
        if (entity instanceof Projectile || entity instanceof MobZephyr) return false;
        return super.collidesWithEntity(entity, world, pos);
    }

    @Override
    public HitResult collisionRayTrace(World world, TilePosc pos, Vector3dc start, Vector3dc end, boolean useSelectorBoxes) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean isProjectile = false;

        for (int idx = 0; idx < Math.min(stackTrace.length, 20); idx++) {
            if (stackTrace[idx].getClassName().equals(Projectile.class.getName())) {
                isProjectile = true;
                break;
            }
        }

        return isProjectile ? null : super.collisionRayTrace(world, pos, start, end, useSelectorBoxes);
    }

    @Override
    public AABBdc getCollisionAABB(WorldSource world, TilePosc pos) {
        int x = pos.x();
        int y = pos.y();
        int z = pos.z();
        return new AABBd(x, y, z, x + 1.0, y + 0.01, z + 1.0);
    }

    @Override
    public void onEntityCollision(World world, TilePosc pos, Entity entity) {
        if (!(entity instanceof MobZephyr)) {
            if (!entity.isSneaking() && entity.yd < 0.0) {
                entity.yd *= 0.005;
            }
            entity.fallDistance = 0.0F;
        }
    }

}
