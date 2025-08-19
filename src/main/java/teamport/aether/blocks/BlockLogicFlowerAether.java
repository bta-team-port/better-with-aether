package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;

public class BlockLogicFlowerAether extends BlockLogicFlowerStackable {
    public BlockLogicFlowerAether(Block<?> block) {
        super(block);
    }

    public boolean mayPlaceOn(int blockId) {
        return Blocks.blocksList[blockId] != null && (Blocks.blocksList[blockId].hasTag(BlockTags.GROWS_FLOWERS) || Blocks.blocksList[blockId].hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS) || Blocks.blocksList[blockId].hasTag(AetherBlockTags.GROWS_AETHER_TREES));
    }
}
