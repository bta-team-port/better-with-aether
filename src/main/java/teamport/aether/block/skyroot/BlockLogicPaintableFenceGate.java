package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceGate;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableFenceGate extends BlockLogicFenceGate {
    protected final Block<? extends BlockLogicPaintedFenceGate> paintedBlock;

    public BlockLogicPaintableFenceGate(Block<?> block, Block<? extends BlockLogicPaintedFenceGate> paintedBlock) {
        super(block);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadata(x, y, z, paintedBlock.id(), meta);
        paintedBlock.getLogic().setColor(world, x, y, z, color);
    }
}
