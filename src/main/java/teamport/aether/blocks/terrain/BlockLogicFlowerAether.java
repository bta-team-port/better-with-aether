package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import teamport.aether.blocks.AetherBlockTags;

public class BlockLogicFlowerAether extends BlockLogicFlowerStackable {
    public BlockLogicFlowerAether(Block<?> block) {
        super(block);
    }

    @Override
    public boolean mayPlaceOn(int blockId) {
        Block<?> block = Blocks.blocksList[blockId];
        return block != null
            && (block.hasTag(BlockTags.GROWS_FLOWERS)
            || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS));
    }
}
