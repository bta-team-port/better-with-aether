package bta.aether.block;

import net.minecraft.core.world.World;

import java.util.Random;

public class BlockSaplingAetherBase extends BlockAetherFlower{
    public BlockSaplingAetherBase(String key, int id) {
        super(key, id);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void growTree(World world, int i, int j, int k, Random random) {

    }

}
