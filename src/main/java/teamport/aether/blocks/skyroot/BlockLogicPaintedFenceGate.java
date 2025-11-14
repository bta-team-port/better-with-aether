package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicFenceGatePainted;
import net.minecraft.core.world.World;

public class BlockLogicPaintedFenceGate extends BlockLogicFenceGatePainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedFenceGate(Block<?> block, int unpaintedBlockID) {
        super(block);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, meta & -241);
    }
}
