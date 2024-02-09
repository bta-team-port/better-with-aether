package bta.aether.block;

import bta.aether.AetherAchievements;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

public class BlockCloudBlue extends BlockCloudBase {

    public BlockCloudBlue(String key, int id, Material material) {
        super(key, id, material);
    }

    public void jump(Entity entity) {
        if (entity != null && entity.yd < 1.0D) {
            entity.yd = 0.0D;
            entity.fallDistance = 0.0F;
            entity.push(0.0D, 2.0D, 0.0D);
        }
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AABB.getBoundingBoxFromPool(0, y, 0, 0, y, 0);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.jump(entity);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity.yd < 0.0) {
            entity.yd *= 0.6;
            entity.fallDistance = 0.0F;
        }
        if (entity.y > (double) y) {
            this.jump(entity);
        }
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).addStat(AetherAchievements.BOUNCE, 1);
        }
    }
}
