package bta.aether.item;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemVampireSword extends ItemToolSword {
    public ItemVampireSword(String name, int id, ToolMaterial enumtoolmaterial) {
        super(name, id, enumtoolmaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(enumtoolmaterial.getDurability());
        this.setFull3D();
    }

    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        EntityPlayer player = (EntityPlayer)entityliving1;
        player.heal(1);
        itemstack.damageItem(1, entityliving1);
        return true;
    }
}