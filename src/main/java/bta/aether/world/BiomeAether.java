package bta.aether.world;

import bta.aether.Aether;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeTall;

import java.util.Random;

public class BiomeAether
    extends Biome
{
    @Override
    public WorldFeature getRandomWorldGenForTrees(Random random)
    {

        if(random.nextInt(10) == 0)
        {
            return new WorldFeatureTreeFancy(Aether.SkyrootLeavesAether.id, Aether.SkyrootLogAether.id);
        } else
            return new WorldFeatureTree(Aether.SkyrootLeavesAether.id, Aether.SkyrootLogAether.id, 5);

    }
}
