package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceGate;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintableFenceGate extends BlockLogicFenceGate {
    protected final Block<? extends BlockLogicPaintedFenceGate> paintedBlock;

    public BlockLogicPaintableFenceGate(Block<?> block, Block<? extends BlockLogicPaintedFenceGate> paintedBlock) {
        super(block);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(World world, TilePosc pos, DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeData(pos, paintedBlock, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
    }
}
