package teamport.aether.items.itemtool.ItemToolValkyrie;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.AetherMod;
import teamport.aether.items.itemtool.ItemToolSwordAether;

public class ItemToolSwordValkyrie extends ItemToolSwordAether {

    public ItemToolSwordValkyrie(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        target.hurt(attacker, getDamageVsEntity(target, itemstack), AetherMod.HOLY);
        itemstack.damageItem(1, attacker);
        return true;
    }
}
