package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFence;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableFence extends BlockLogicFence {
    protected final Block<? extends BlockLogicPaintedFence> paintedBlock;

    public BlockLogicPaintableFence(Block<?> block, Block<? extends BlockLogicPaintedFence> paintedBlock) {
        super(block);
        this.paintedBlock = paintedBlock;
    }


    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        world.setBlock(x, y, z, paintedBlock.id());
        paintedBlock.getLogic().setColor(world, x, y, z, color);
    }
}
