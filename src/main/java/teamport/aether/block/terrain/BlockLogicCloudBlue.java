package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.Vector3d;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class BlockLogicCloudBlue extends BlockLogicCloudBase {
    public BlockLogicCloudBlue(Block<?> block) {
        super(block);
    }

    @Override
    public AABBdc getCollisionAABB(@NonNull WorldSource world, @NonNull TilePosc pos) {
        return null;
    }

    @Override
    public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc pos, @NonNull Entity entity) {
        this.onEntityCollision(world, pos, entity);
    }

    @Override
    public void onEntityInside(@NonNull World world, @NonNull TilePosc pos, @NonNull Entity entity, @NonNull Vector3d entityVelocity) {
        this.onEntityCollision(world, pos, entity);
    }

    @Override
    public void onEntityCollision(@NonNull World world, @NonNull TilePosc pos, @NonNull Entity entity) {
        entity.fallDistance = 0.0F;

        if (!entity.isSneaking()) {
            if (entity.yd <= 0.0) {
                this.jump(entity);
                //don't reference particles on the server. It will crash.
                if (!EnvironmentHelper.isMultiplayerServer()) {
                    if (entity instanceof Player player) {
                        player.addStat(AetherAchievements.BOUNCE, 1);
                    }

                    ParticleMaker.spawnParticle(entity.world, "splash", entity.x, entity.y, entity.z, world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat(), 0);
                }
            }
        } else {
            entity.yd *= 0.005;
        }
    }

    public void jump(@NonNull Entity entity) {
        entity.fallDistance = 0.0F;
        entity.yd = 2.0f;
    }
}
