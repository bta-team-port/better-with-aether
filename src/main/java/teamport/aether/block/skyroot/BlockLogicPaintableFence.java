package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFence;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintableFence extends BlockLogicFence {
    protected final Block<? extends BlockLogicPaintedFence> paintedBlock;

    public BlockLogicPaintableFence(Block<?> block, Block<? extends BlockLogicPaintedFence> paintedBlock) {
        super(block);
        this.paintedBlock = paintedBlock;
    }


    @Override
    public void setColor(World world, TilePosc pos, DyeColor color) {
        world.setBlockType(pos, paintedBlock);
        paintedBlock.getLogic().setColor(world, pos, color);
    }
}
