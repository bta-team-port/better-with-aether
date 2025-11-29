package teamport.aether.items.item_tool.item_tool_gravitite;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.entity.MobUtil;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.item_tool.ItemToolSwordAether;

public class ItemToolSwordGravitite extends ItemToolSwordAether implements AetherHasCustomDamageType {
    private static final float KNOCKBACK_STRENGTH = 1.0F;
    private static final float LIFT = KNOCKBACK_STRENGTH;

    public ItemToolSwordGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10) {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker,KNOCKBACK_STRENGTH, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, LIFT);
            }
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FALL;
    }
}
