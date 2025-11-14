package teamport.aether.blocks.terrain;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class BlockLogicCloudBlue extends BlockLogicCloudBase {
    public BlockLogicCloudBlue(Block<?> block) {
        super(block);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        //don't reference particles on the server. It will crash.
        if (!EnvironmentHelper.isServerEnvironment()) {
            if (entity instanceof Player) {
                ((Player) entity).addStat(AetherAchievements.BOUNCE, 1);
            }

            if (!(entity instanceof Particle)) {
                ParticleMaker.spawnParticle(entity.world, "splash", entity.x, entity.y, entity.z, world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), 0);
            }
        }

        entity.fallDistance = 0.0F;
        entity.yd *= 0.005;

        if (!EnvironmentHelper.isServerEnvironment()) {
            if (entity.y > y && !entity.isSneaking() && !(entity instanceof Particle)) {
                this.jump(entity);
            }

        } else {
            if (entity.y > y && !entity.isSneaking()) {
                this.jump(entity);
            }

        }
    }

    public void jump(Entity entity) {
        entity.fallDistance = 0.0F;
        entity.yd = 2.0f;
    }
}
