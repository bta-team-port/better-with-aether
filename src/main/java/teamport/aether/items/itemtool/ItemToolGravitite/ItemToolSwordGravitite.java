package teamport.aether.items.itemtool.ItemToolGravitite;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.helper.MobUtil;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolSwordAether;

public class ItemToolSwordGravitite extends ItemToolSwordAether implements AetherHasCustomDamageType {

    public ItemToolSwordGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10) {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker, 3.0f, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, 3.0f);
            }
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FALL;
    }
}
