package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;

import java.util.Random;

public class BlockSaplingGoldenOak extends BlockSaplingBase {
    public BlockSaplingGoldenOak(String key, int id) {
        super(key, id);
    }

    public void growTree(World world, int i, int j, int k, Random random) {
        Object treeBig = new WorldFeatureTreeFancy(AetherBlocks.leavesOakGolden.id, AetherBlocks.logOakGolden.id);
        world.setBlock(i, j, k, 0);
        if (!((WorldFeature)treeBig).generate(world, random, i, j, k)){
            world.setBlock(i, j, k, this.id);
        }

    }
}
