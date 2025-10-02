package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFence;

public class BlockLogicFenceSkyroot extends BlockLogicFence {
    public BlockLogicFenceSkyroot(Block<?> block) {
        super(block);
    }

    public boolean canBePainted() {
        return false;
    }
}
