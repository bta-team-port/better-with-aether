package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicButtonPlanks;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintableButton extends BlockLogicButtonPlanks {
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
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataRaw(x, y, z, paintedBlock.id(), meta);
        world.setBlockMetadata(x, y, z, meta);
        paintedBlock.getLogic().setColor(world, x, y, z, color);
        if ((meta & 8) != 0) {
            world.scheduleBlockUpdate(x, y, z, paintedBlock.id(), this.tickDelay());
        }

    }
}
