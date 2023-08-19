package bta.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

public class BlockCloudBase extends BlockTransparent {
    public BlockCloudBase(String key, int id, Material material) {
        super(key, id, material, true);
    }

    public int getRenderBlockPass() {
        return 1;
    }
    public boolean renderAsNormalBlock() {
        return false;
    }
    public int getMobilityFlag() {
        return 1;
    }
    public boolean isOpaqueCube() {
        return false;
    }

    public AABB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AABB.getBoundingBoxFromPool((double)x, (double)y, (double)z, (double)(x + 1), (double)y, (double)(z + 1));
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity.yd < 0.0) {
            entity.yd *= 0.2;
            entity.fallDistance = 0.0F;
        }
    }

}
