package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicStairsPaintable;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintableStairs extends BlockLogicStairsPaintable {

    private final Block<? extends BlockLogicPaintedStairs> paintedBlock;

    public BlockLogicPaintableStairs(Block<?> block, Block<?> modelBlock, Block<? extends BlockLogicPaintedStairs> paintedBlock) {
        super(block, modelBlock);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(World world, TilePosc pos, DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeData(pos, paintedBlock, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
    }
}
