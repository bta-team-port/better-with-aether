package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import teamport.aether.AetherAchievements;

public class BlockLogicCloudBlue extends BlockLogicCloudBase{
    public BlockLogicCloudBlue(Block<?> block) {
        super(block);
    }

    public void jump(Entity entity) {
        if (entity != null && entity.yd < 1.0D) {
            entity.yd = 0.0D;
            entity.fallDistance = 0.0F;
            entity.push(0.0D, 2.0D, 0.0D);
        }
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        return AABB.getPermanentBB(0, y, 0, 0, y, 0);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.jump(entity);
        entity.fallDistance = 0.0F;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.fallDistance = 0.0F;

        if (entity.yd < 0.0) {
            entity.yd *= 0.1;
            entity.fallDistance = 0.0F;
        }
        if (entity.y > (double) y) {
            this.jump(entity);
        }
        if (entity instanceof Player) {
            ((Player) entity).addStat(AetherAchievements.BOUNCE, 1);
        }
    }

}
