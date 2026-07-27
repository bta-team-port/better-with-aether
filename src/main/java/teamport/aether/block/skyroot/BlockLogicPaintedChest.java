package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChestPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintedChest extends BlockLogicChestPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedChest(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, TilePosc pos) {
        int meta = this.stripColorFromMetadata(world.getBlockData(pos));
        world.setBlockTypeDataNotify(pos, Blocks.getBlock(unpaintedBlockID), meta);
    }
}
