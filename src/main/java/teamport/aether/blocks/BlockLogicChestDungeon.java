package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicRotatable;
import net.minecraft.core.block.material.Material;

public class BlockLogicChestDungeon extends BlockLogicRotatable {
    public BlockLogicChestDungeon(Block<BlockLogic> b) {
        super(b, Material.stone);
    }
}
