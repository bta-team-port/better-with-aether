package teamport.aether.items.itemtool;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.AetherMod;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemToolSwordLightning extends ItemToolSword {

    public ItemToolSwordLightning(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target != null && (target.hurtTime == 10) && target.isAlive()) {
            if (!EnvironmentHelper.isClientWorld()) {
                target.world.entityJoinedWorld(new EntityLightning(target.world, target.x, target.y + 0.5, target.z));
            }
        }

        target.hurt(attacker, getDamageVsEntity(target, itemstack), AetherMod.LIGHTNING);
        itemstack.damageItem(1, attacker);
        return true;
    }
}
