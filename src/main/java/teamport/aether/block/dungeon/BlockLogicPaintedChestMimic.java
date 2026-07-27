package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public class BlockLogicPaintedChestMimic extends BlockLogicChestMimic implements IPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedChestMimic(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(World world, TilePosc pos) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataNotify(pos, Blocks.getBlock(unpaintedBlockID), meta & -241);
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
    public void setColor(World world, TilePosc pos, DyeColor color) {
        IPainted.super.setColor(world, pos, color);
    }
}
