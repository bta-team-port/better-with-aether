package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.AetherAchievements;

public class BlockLogicCloudBlue extends BlockLogicCloudBase {
    public BlockLogicCloudBlue(Block<?> block) {
        super(block);
    }

    public void jump(Entity entity) {
        entity.fallDistance = 0.0F;
        if (!entity.isSneaking()) {
            entity.fallDistance = 0.0F;
            entity.yd = 2.0f;
        }
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return AABB.getPermanentBB(x, y, z, x, y, z);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.jump(entity);
        entity.fallDistance = 0.0F;
    }

    public void handleEntityInside(World world, int x, int y, int z, Entity entity, Vec3 entityVelocity) {
        this.jump(entity);
        entity.fallDistance = 0.0F;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.fallDistance = 0.0F;

        if (entity.y > (double) y) {
            this.jump(entity);
        }
        if (entity instanceof Player) {
            ((Player) entity).addStat(AetherAchievements.BOUNCE, 1);
        }
    }

}
