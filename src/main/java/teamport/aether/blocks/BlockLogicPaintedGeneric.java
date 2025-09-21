package teamport.aether.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockLogicPaintedGeneric extends BlockLogic implements IPainted {

    private final Supplier<Block<?>> unpaintedVariant;

    public BlockLogicPaintedGeneric(Block<?> block, Material material, Supplier<Block<?>> unpaintedVariant) {
        super(block, material);
        this.unpaintedVariant = unpaintedVariant;
    }

    @Override
    public String getLanguageKey(int meta) {
        return super.getLanguageKey(meta) + "." + this.fromMetadata(meta).colorID;
    }

    public int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return stack.getMetadata();
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        return new ItemStack[]{new ItemStack(this, 1, meta)};
    }

    public DyeColor fromMetadata(int meta) {
        return DyeColor.colorFromBlockMeta(meta & 15);
    }

    public int toMetadata(DyeColor color) {
        return color.blockMeta;
    }

    public int stripColorFromMetadata(int meta) {
        return 0;
    }

    public void removeDye(World world, int x, int y, int z) {
        Block<?> block = unpaintedVariant.get();
        world.setBlockWithNotify(x, y, z, block.id());
    }

    public void setColor(World world, int x, int y, int z, DyeColor color) {
        IPainted.super.setColor(world, x, y, z, color);
    }
}
