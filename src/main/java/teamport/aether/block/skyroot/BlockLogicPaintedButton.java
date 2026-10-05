package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicButtonPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintedButton extends BlockLogicButtonPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedButton(Block<?> block, int unpaintedBlockID) {
        super(block);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public int tickDelay() {
        return 5;
    }

    @Override
    public void removeDye(@NonNull World world, @NonNull TilePosc pos) {
        int meta = stripColorFromMetadata(world.getBlockData(pos));
        Block<?> unpaintedBlock = Blocks.getBlock(unpaintedBlockID);
        world.setBlockTypeDataNotify(pos, unpaintedBlock, meta);
        if ((meta & 8) != 0) {
            world.scheduleBlockUpdate(pos, unpaintedBlock, tickDelay());
        }
    }
}
