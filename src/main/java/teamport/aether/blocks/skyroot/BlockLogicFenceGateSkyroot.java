package teamport.aether.blocks.skyroot;

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
