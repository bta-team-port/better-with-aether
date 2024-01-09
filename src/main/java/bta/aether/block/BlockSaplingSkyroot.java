package bta.aether.block;

import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;

import java.util.Random;

public class BlockSaplingSkyroot extends BlockSaplingBase {
    public BlockSaplingSkyroot(String key, int id) {
        super(key, id);
    }
    @Override
    public boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == AetherBlocks.grassAether.id || i == AetherBlocks.dirtAether.id;
    }
    @Override
    public void growTree(World world, int i, int j, int k, Random random) {
        WorldFeature treeSmall = new WorldFeatureTree(AetherBlocks.leavesSkyroot.id, AetherBlocks.logSkyroot.id, 4);
        world.setBlock(i, j, k, 0);
        if (!treeSmall.generate(world, random, i, j, k)){
            world.setBlock(i, j, k, this.id);
        }
    }
}
