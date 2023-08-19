package bta.aether;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;

import java.util.Random;

public class BlockSaplingSkyroot extends BlockSaplingBase {
    public BlockSaplingSkyroot(String key, int id) {
        super(key, id);
    }

    public void growTree(World world, int i, int j, int k, Random random) {
        Object treeSmall = new WorldFeatureTree(Aether.SkyrootLeavesAether.id, Aether.SkyrootLogAether.id, 4);
        world.setBlock(i, j, k, 0);
        if (!((WorldFeature)treeSmall).generate(world, random, i, j, k)){
            world.setBlock(i, j, k, this.id);
        }

    }
}
