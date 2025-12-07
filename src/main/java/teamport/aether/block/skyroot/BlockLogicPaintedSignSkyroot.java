package teamport.aether.block.skyroot;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import teamport.aether.item.AetherItems;

public class BlockLogicPaintedSignSkyroot extends BlockLogicPaintableSignSkyroot implements IPainted {
    public BlockLogicPaintedSignSkyroot(Block<?> block, boolean isFreeStanding) {
        super(block, isFreeStanding);
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int meta, TileEntity tileEntity) {
        ItemStack item = new ItemStack(AetherItems.SIGN_SKYROOT_PAINTED, 1, this.fromMetadata(meta).itemMeta);
        return new ItemStack[]{item};
    }

    public DyeColor fromMetadata(int meta) {
        return DyeColor.colorFromBlockMeta((meta & 240) >> 4);
    }

    public int toMetadata(DyeColor color) {
        return color.blockMeta << 4;
    }

    public int stripColorFromMetadata(int meta) {
        return meta & -241;
    }

    public void removeDye(World world, int x, int y, int z) {
        world.setBlockMetadata(x, y, z, this.stripColorFromMetadata(world.getBlockMetadata(x, y, z)));
    }

    @Override
    public void setColor(World world, int x, int y, int z, DyeColor color) {
        world.setBlockMetadataWithNotify(x, y, z, color.blockMeta << 4 | this.stripColorFromMetadata(world.getBlockMetadata(x, y, z)));
    }
}
