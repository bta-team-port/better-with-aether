package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.world.generate.feature.WorldFeatureAetherTree;

import java.util.Random;

public class BlockLogicSaplingSkyroot extends BlockLogicSaplingBase {
    public BlockLogicSaplingSkyroot(Block<?> block) {
        super(block);
    }

    public boolean mayPlaceOn(int blockId) {
        return Blocks.blocksList[blockId] != null && (Blocks.blocksList[blockId].hasTag(BlockTags.GROWS_FLOWERS) || Blocks.blocksList[blockId].hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS) || Blocks.blocksList[blockId].hasTag(AetherBlockTags.GROWS_AETHER_TREES));
    }


    @Override
    public void growTree(World world, int x, int y, int z, Random random) {
        world.setBlockWithNotify(x, y, z, 0);
        WorldFeature tree = new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
        if (!tree.place(world, random, x, y, z)) {
            world.setBlockWithNotify(x, y, z, this.id());
            }
    }
}
