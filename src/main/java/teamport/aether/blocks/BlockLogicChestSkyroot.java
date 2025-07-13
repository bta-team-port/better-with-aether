package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.material.Material;

public class BlockLogicChestSkyroot extends BlockLogicChest {

    public BlockLogicChestSkyroot(Block<?> block, Material material) {
        super(block, material);
    }

    public boolean canBePainted() {
        return false;
    }
}
