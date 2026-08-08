package teamport.aether.block.dungeon;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

public class BlockLogicPaintedChestMimic extends BlockLogicChestMimic implements IPainted {
    protected final int unpaintedBlockID;

    public BlockLogicPaintedChestMimic(Block<?> block, Material material, int unpaintedBlockID) {
        super(block, material);
        this.unpaintedBlockID = unpaintedBlockID;
    }

    @Override
    public void removeDye(@NonNull World world, @NonNull TilePosc pos) {
        int meta = world.getBlockData(pos);
        world.setBlockTypeDataNotify(pos, Blocks.getBlock(unpaintedBlockID), meta & -241);
    }

    @Override
    public @NonNull DyeColor fromMetadata(int meta) {
        return DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }

    @Override
    public int toMetadata(@NonNull DyeColor dyeColor) {
        return dyeColor.blockMeta << 4;
    }

    @Override
    public int stripColorFromMetadata(int meta) {
        return meta & -241;
    }

    @Override
    public void setColor(@NonNull World world, @NonNull TilePosc pos, @NonNull DyeColor color) {
        IPainted.super.setColor(world, pos, color);
    }
}
