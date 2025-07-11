package teamport.aether.items.ItemToolHolystone;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.items.ItemToolSwordAether;

import static teamport.aether.items.AetherItems.AMBROSIUM;

public class ItemToolSwordHolystone extends ItemToolSwordAether {

    public ItemToolSwordHolystone(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);

    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (itemRand.nextInt(16) == 0) {
            target.dropItem(AMBROSIUM.id, 1);
        }
        itemstack.damageItem(1, attacker);
        return true;
    }

}
