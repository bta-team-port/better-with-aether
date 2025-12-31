package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChestPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;

public class BlockLogicPaintedChest extends BlockLogicChestPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedChest(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = this.stripColorFromMetadata(world.getBlockMetadata(x, y, z));
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, meta);
    }
}
