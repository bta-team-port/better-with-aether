package bta.aether.item;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemSwordFlaming extends ItemToolSword {
    public ItemSwordFlaming(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
    }
    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        entityliving.maxFireTicks = 30 * 20;
        entityliving.remainingFireTicks = 30 * 20;
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }
}
