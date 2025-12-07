package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSlabPainted;
import net.minecraft.core.world.World;

public class BlockLogicPaintedSlab extends BlockLogicSlabPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedSlab(Block<?> block, Block<?> modelBlock, int unpaintedBlockID) {
        super(block, modelBlock);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, meta & 15);
    }
}
