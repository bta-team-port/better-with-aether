package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.generate.feature.WorldFeatureAetherTreeGoldenOak;

import java.util.Random;

public class BlockLogicSaplingOakGolden extends BlockLogicSaplingBase {
    public BlockLogicSaplingOakGolden(Block<?> block) {
        super(block);
    }

    public boolean mayPlaceOn(int blockId) {
        return blockId == Blocks.SAND.id() || blockId == AetherBlocks.GRASS_AETHER.id() || blockId == AetherBlocks.DIRT_AETHER.id() || super.mayPlaceOn(blockId);
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random random) {
        world.setBlockWithNotify(x, y, z, 0);
        WorldFeature tree = new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN.id(), AetherBlocks.LOG_OAK_GOLDEN.id());
        if (!tree.place(world, random, x, y, z)) {
            world.setBlockWithNotify(x, y, z, this.id());
        }
    }

}
