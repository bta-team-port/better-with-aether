package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFencePainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintedFence extends BlockLogicFencePainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedFence(Block<?> block, int unpaintedBlockID) {
        super(block);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, TilePosc pos) {
        world.setBlockTypeNotify(pos, Blocks.getBlock(unpaintedBlockID));
    }
}
