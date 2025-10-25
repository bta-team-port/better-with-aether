package teamport.aether.items.itemtool.ItemToolGravitite;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolSwordAether;

public class ItemToolSwordGravitite extends ItemToolSwordAether implements AetherHasCustomDamageType {

    public ItemToolSwordGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive() && target.getHealth() > 0 && target.hurtMarked && target.hurtTime > 0) {
            target.push(target.xd, target.yd * 3, target.zd);
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FALL;
    }

}
