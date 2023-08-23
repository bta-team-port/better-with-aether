package bta.aether.block;

import net.minecraft.core.block.BlockGlass;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import java.util.Random;

public class BlockGlassAmbrosium extends BlockTransparent {
    public BlockGlassAmbrosium(String key, int id, Material material, boolean renderInside) {
        super(key, id, material.glass, false);
    }
    public int quantityDropped(int meta, Random rand) {
        return 0;
    }

    public int getRenderBlockPass() {
        return 1;
    }
    public boolean isOpaqueCube() {
        return false;
    }

    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case PICK_BLOCK:
            case SILK_TOUCH:
                return new ItemStack[]{new ItemStack(this)};
            default:
                return null;
        }
    }
}
