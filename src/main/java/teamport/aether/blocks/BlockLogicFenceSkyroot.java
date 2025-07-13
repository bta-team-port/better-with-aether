package teamport.aether.blocks;

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
