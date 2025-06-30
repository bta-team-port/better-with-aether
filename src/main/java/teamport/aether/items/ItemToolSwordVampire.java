package teamport.aether.items;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemToolSwordVampire extends ItemToolSword {

    public ItemToolSwordVampire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive()) {
            attacker.heal(8);
        }

        itemstack.damageItem(1, attacker);
        return true;
    }
}
