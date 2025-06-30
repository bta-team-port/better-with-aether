package teamport.aether.items;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemToolSwordFire extends ItemToolSword {

    public ItemToolSwordFire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive()) {
            target.maxFireTicks = 30 * 20;
            target.remainingFireTicks = 30 * 20;
        }

        itemstack.damageItem(1, attacker);
        return true;
    }
}
