package bta.aether.block;

import bta.aether.item.AetherItems;
import bta.aether.item.AetherToolMaterial;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.world.World;

public class BlockOreAmbrosium extends BlockAetherDouble {
    protected final Class<? extends ItemTool> toolClass;
    public BlockOreAmbrosium(String key, int id, Class<? extends ItemTool> toolClass) {
        super(key, id, Material.stone, toolClass);
        this.toolClass = toolClass;
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        switch (dropCause) {
            case SILK_TOUCH:
            case PICK_BLOCK:
                return new ItemStack[]{new ItemStack(this)};
            case PROPER_TOOL:
                return new ItemStack[]{new ItemStack(AetherItems.ambrosium)};
            default:
                return null;
        }
    }
}
