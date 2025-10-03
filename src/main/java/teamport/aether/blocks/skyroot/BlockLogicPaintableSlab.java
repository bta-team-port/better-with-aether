package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.*;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableSlab extends BlockLogicSlabPaintable{
    protected final Block<? extends BlockLogicPaintedSlab> paintedSlab;

    public BlockLogicPaintableSlab(Block<?> block, Block<?> modelBlock, Block<? extends BlockLogicPaintedSlab> paintedSlab) {
        super(block, modelBlock);
        this.paintedSlab = paintedSlab;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadata(x, y, z, paintedSlab.id(), meta);
        paintedSlab.getLogic().setColor(world, x, y, z, color);
    }
}
