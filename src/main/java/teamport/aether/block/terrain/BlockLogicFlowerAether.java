package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFlowerStackable;
import net.minecraft.core.block.tag.BlockTags;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;

public class BlockLogicFlowerAether extends BlockLogicFlowerStackable {
    public BlockLogicFlowerAether(Block<?> block) {
        super(block);
    }

    @Override
    public boolean mayPlaceOn(@NonNull Block<?> block) {
        return block.hasTag(BlockTags.GROWS_FLOWERS) || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS);
    }
}
