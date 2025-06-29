package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;

import java.util.Random;

public class BlockLogicSaplingSkyroot extends BlockLogicSaplingBase {
    public BlockLogicSaplingSkyroot(Block<?> block) {
        super(block);
    }

    public boolean mayPlaceOn(int blockId) {
        return blockId == Blocks.SAND.id() || blockId == AetherBlocks.GRASS_AETHER.id() || blockId == AetherBlocks.DIRT_AETHER.id() || super.mayPlaceOn(blockId);
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random random) {
        WorldFeature treeBig = new WorldFeatureTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
        WorldFeature treeSmall = new WorldFeatureTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
        world.setBlock(x, y, z, 0);
        if (!treeSmall.place(world, random, x, y, z) && !treeBig.place(world, random, x, y, z)) {
            world.setBlock(x, y, z, this.id());
        }

    }
}
