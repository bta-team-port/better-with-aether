package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlabPaintable;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintableSlab extends BlockLogicSlabPaintable {
    protected final Block<? extends BlockLogicPaintedSlab> paintedBlock;

    public BlockLogicPaintableSlab(Block<?> block, Block<?> modelBlock, Block<? extends BlockLogicPaintedSlab> paintedBlock) {
        super(block, modelBlock);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeData(pos, paintedBlock, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
    }
}
