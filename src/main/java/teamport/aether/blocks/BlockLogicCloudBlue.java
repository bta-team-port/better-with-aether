package teamport.aether.blocks;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.AetherAchievements;

public class BlockLogicCloudBlue extends BlockLogicCloudBase {
    public BlockLogicCloudBlue(Block<?> block) {
        super(block);
    }

    public void jump(Entity entity) {
        entity.fallDistance = 0.0F;
        entity.yd = 2.0f;
    }

    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!world.isClientSide) {
            entity.fallDistance = 0.0F;
            entity.yd *= 0.005;

            if (!(entity instanceof Particle)) {
                entity.world.spawnParticle("splash", entity.x, entity.y - 0.5, entity.z, world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), 0);
                if (entity.y > (double) y && !entity.isSneaking()) {
                    this.jump(entity);
                    if (entity instanceof Player) {
                        ((Player) entity).addStat(AetherAchievements.BOUNCE, 1);
                    }
                }
            }
        }
    }

}
