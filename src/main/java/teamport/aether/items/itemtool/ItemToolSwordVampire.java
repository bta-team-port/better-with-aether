package teamport.aether.items.itemtool;

import net.minecraft.core.Global;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.items.AetherHasCustomDamageType;

public class ItemToolSwordVampire extends ItemToolSword implements AetherHasCustomDamageType {

    public ItemToolSwordVampire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (!(target instanceof Mob) || target.hurtTime != 10 || !hitEntity) {
            return hitEntity;
        }
        if (!(target instanceof Player)) {
            if (attacker.getHealth() < attacker.getMaxHealth() && attacker.getHealth() + attacker.getTotalHealingRemaining() < attacker.getMaxHealth()) {
                attacker.heal(3);
                AetherEffects.add(attacker, new EffectStack((IHasEffects<?>)attacker, AetherEffects.regenerationEffect, 50, 1)); //2.5 seconds
            }
            return true;
        }
        Player victim = (Player) target;
        if (victim.gamemode.isPlayerInvulnerable()) {
            return false;
        }
        if (attacker.getHealth() < attacker.getMaxHealth() && attacker.getHealth() + attacker.getTotalHealingRemaining() < attacker.getMaxHealth()) {
            float damageDone = victim.inventory.getTotalProtectionAmount(this.getDamageType());
            int overTimeHealing = (int)Math.ceil(Global.TICKS_PER_SECOND * 5.0f / 16.0f);
            attacker.heal((int) Math.ceil(damageDone * 3.0f / 8.0f));
            AetherEffects.add(attacker, new EffectStack((IHasEffects<?>)attacker, AetherEffects.regenerationEffect, overTimeHealing, 1));
        }
        return true;
    }
}
