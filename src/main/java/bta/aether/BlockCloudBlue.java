package bta.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class BlockCloudBlue extends BlockCloudBase {
    public BlockCloudBlue(String key, int id, Material material) {
        super(key, id, material);
    }

    public void jump(Entity entity) {
        if ((entity instanceof EntityLiving || entity instanceof EntityItem) && entity.yd < 1.0D) {
            entity.yd = 0.0D;
            entity.fallDistance = 0.0F;
            entity.push(0.0D, 2.0D, 0.0D);
        }
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        this.jump(entity);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity.yd < 0.0) {
            entity.yd *= 0.6;
            entity.fallDistance = 0.0F;
        }
        if (entity.y > (double) y){
            this.jump(entity);
        }
    }

}
