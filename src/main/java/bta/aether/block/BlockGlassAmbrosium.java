package bta.aether.block;

import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockGlassAmbrosium extends BlockTransparent {
    public BlockGlassAmbrosium(String key, int id) {
        super(key, id, Material.glass, false);
        this.movementScale = 1.05f;
    }
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    @Override
    public boolean isSolidRender() {
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
