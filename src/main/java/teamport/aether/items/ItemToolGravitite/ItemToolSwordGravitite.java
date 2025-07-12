package teamport.aether.items.ItemToolGravitite;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.items.ItemToolSwordAether;

public class ItemToolSwordGravitite extends ItemToolSwordAether {

    public ItemToolSwordGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob) {
            target.push(target.xd, target.yd * 3, target.zd);
        }

        itemstack.damageItem(1, attacker);
        return true;
    }

}
