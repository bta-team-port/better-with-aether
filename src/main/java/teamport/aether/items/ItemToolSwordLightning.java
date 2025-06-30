package teamport.aether.items;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;

public class ItemToolSwordLightning extends ItemToolSword {

    public ItemToolSwordLightning(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive()) {
            target.world.entityJoinedWorld(new EntityLightning(target.world, target.x, target.y + 0.5, target.z));
        }

        itemstack.damageItem(1, attacker);
        return true;
    }
}
