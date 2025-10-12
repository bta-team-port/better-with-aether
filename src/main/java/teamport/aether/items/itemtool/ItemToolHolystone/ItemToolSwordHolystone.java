package teamport.aether.items.itemtool.ItemToolHolystone;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.items.itemtool.ItemToolSwordAether;

import static teamport.aether.items.AetherItems.AMBROSIUM;

public class ItemToolSwordHolystone extends ItemToolSwordAether {

    public ItemToolSwordHolystone(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);

    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (itemRand.nextInt(8) == 0) {
            target.dropItem(AMBROSIUM.id, 1);
        }
        return super.hitEntity(itemstack, target, attacker);
    }

}
