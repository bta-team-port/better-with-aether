package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlabPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintedSlab extends BlockLogicSlabPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedSlab(Block<?> block, Block<?> modelBlock, int unpaintedBlockID) {
        super(block, modelBlock);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, TilePosc pos) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataNotify(pos, Blocks.getBlock(unpaintedBlockID), meta & 15);
    }
}
