package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceGate;

public class BlockLogicFenceGateSkyroot extends BlockLogicFenceGate {
    public BlockLogicFenceGateSkyroot(Block<?> block) {
        super(block);
    }

    public boolean canBePainted() {
        return false;
    }
}
