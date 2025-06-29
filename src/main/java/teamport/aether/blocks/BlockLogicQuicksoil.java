package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;

public class BlockLogicQuicksoil extends BlockLogic {
    public BlockLogicQuicksoil(Block<?> block) {
        super(block, Material.dirt);
        block.friction = 1.1f;
    }
}
