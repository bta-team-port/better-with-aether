package bta.aether.world;

import bta.aether.Aether;
import bta.aether.block.AetherBlocks;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;

import java.util.Random;

public class BiomeAether
    extends Biome
{
    @Override
    public WorldFeature getRandomWorldGenForTrees(Random random)
    {

        if(random.nextInt(10) == 0)
        {
            return new WorldFeatureTreeFancy(AetherBlocks.leavesSkyroot.id, AetherBlocks.logSkyroot.id);
        } else
            return new WorldFeatureTree(AetherBlocks.leavesSkyroot.id, AetherBlocks.logSkyroot.id, 5);

    }
}
