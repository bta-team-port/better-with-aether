package bta.aether.block;

import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;

import java.util.Random;

public class BlockSaplingAetherBase extends BlockSaplingBase {
    public BlockSaplingAetherBase(String key, int id) {
        super(key, id);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == AetherBlocks.grassAether.id || i == AetherBlocks.dirtAether.id;
    }


    public void growTree(World world, int i, int j, int k, Random random) {

    }

}
