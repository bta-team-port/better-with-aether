package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFencePainted;
import net.minecraft.core.world.World;

public class BlockLogicPaintedFence extends BlockLogicFencePainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedFence(Block<?> block, int unpaintedBlockID) {
        super(block);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        world.setBlockWithNotify(x, y, z, unpaintedBlockID);
    }
}
