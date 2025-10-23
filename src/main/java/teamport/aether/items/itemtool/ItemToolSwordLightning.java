package teamport.aether.items.itemtool;

import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import teamport.aether.AetherMod;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemToolSwordLightning extends ItemToolSword {
    public int weaponDamage;

    public ItemToolSwordLightning(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
        this.weaponDamage = 1;
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target != null && (target.hurtTime == 10) && target.isAlive()) {
            if (!EnvironmentHelper.isClientWorld()) {
                target.world.entityJoinedWorld(new EntityLightning(target.world, target.x, target.y + 0.5, target.z));
            }
        }

        if(target.hurtTime == 10) {
            target.hurt(attacker, 11, AetherMod.LIGHTNING);
        }
        itemstack.damageItem(1, attacker);
        return true;
    }
}
