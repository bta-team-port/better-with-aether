package teamport.aether.blocks.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

public class BlockLogicPaintedChestMimic extends BlockLogicChestMimic implements IPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedChestMimic(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockAndMetadataWithNotify(x, y, z, unpaintedBlockID, meta & -241);
    }

    @Override
    public DyeColor fromMetadata(int meta) {
        return DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }

    @Override
    public int toMetadata(DyeColor dyeColor) {
        return dyeColor.blockMeta << 4;
    }

    @Override
    public int stripColorFromMetadata(int meta) {
        return meta & -241;
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        IPainted.super.setColor(world, x, y, z, color);
    }
}
