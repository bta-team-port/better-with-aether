package teamport.aether.blocks.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTrapDoorPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;

public class BlockLogicPaintedTrapDoor extends BlockLogicTrapDoorPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedTrapDoor(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, meta & 15);
    }

    @Override
    public String getLanguageKey(int meta) {
        return super.getLanguageKey(meta) + "." + this.fromMetadata(meta).colorID;
    }
}
