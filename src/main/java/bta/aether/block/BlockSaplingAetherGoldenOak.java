package bta.aether.block;

import bta.aether.world.WorldFeatureGoldenOak;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class BlockSaplingAetherGoldenOak extends BlockSaplingAetherBase {
    public BlockSaplingAetherGoldenOak(String key, int id) {
        super(key, id);
    }

    @Override
    public void growTree(World world, int i, int j, int k, Random random) {
        WorldFeature treeBig = new WorldFeatureGoldenOak(AetherBlocks.leavesOakGolden.id, AetherBlocks.logOakGolden.id);
        world.setBlock(i, j, k, 0);
        if (!treeBig.generate(world, random, i, j, k)){
            world.setBlock(i, j, k, this.id);
        }

    }
}
