package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicButtonPlanksOak;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintableButton extends BlockLogicButtonPlanksOak {
    protected final Block<? extends BlockLogicPaintedButton> paintedBlock;

    public BlockLogicPaintableButton(Block<?> block, Block<? extends BlockLogicPaintedButton> paintedBlock) {
        super(block);
        this.paintedBlock = paintedBlock;
    }

    @Override
    public int tickDelay() {
        return 5;
    }

    @Override
    public boolean canBePainted() {
        return true;
    }

    @Override
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor color) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataRaw(pos, paintedBlock, meta);
        world.setBlockData(pos, meta);
        paintedBlock.getLogic().setColor(world, pos, color);
        if ((meta & 8) != 0) {
            world.scheduleBlockUpdate(pos, paintedBlock, this.tickDelay());
        }
    }
}
