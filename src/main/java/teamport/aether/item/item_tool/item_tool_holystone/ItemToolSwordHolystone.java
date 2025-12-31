package teamport.aether.item.item_tool.item_tool_holystone;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import teamport.aether.item.item_tool.ItemToolSwordAether;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.item.AetherItems.AMBROSIUM;

public class ItemToolSwordHolystone extends ItemToolSwordAether {
    public ItemToolSwordHolystone(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);

    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (!EnvironmentHelper.isClientWorld() && itemRand.nextInt(8) == 0) {
            target.dropItem(AMBROSIUM.id, 1);
        }
        return super.hitEntity(itemstack, target, attacker);
    }

}
