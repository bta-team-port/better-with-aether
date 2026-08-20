package teamport.aether.mixins.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.accessory.gloves.ItemGloves;

import static teamport.aether.item.accessory.SlotAccessory.GLOVES_SLOT;

@Mixin(Player.class)
public abstract class PlayerMixinDamageTypes {

    @WrapOperation(method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/core/entity/Entity;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean handleGloveDamageAndEffects(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original) {
        if (attacker instanceof Player player) {
            ItemStack heldItem = player.getCurrentEquippedItem();

            DamageType damageTypeToUse = type;
            if (heldItem != null) {
                if (heldItem.getItem() instanceof AetherHasCustomDamageType hasCustomDamageType) {
                    damageTypeToUse = hasCustomDamageType.getDamageType();
                }
            } else {
                ItemStack gloves = PlayerUtil.getArmorOrAccessoryItem(player, GLOVES_SLOT);
                if (gloves != null && gloves.getItem() instanceof ItemGloves hasCustomDamageType) {
                    damageTypeToUse = hasCustomDamageType.getDamageType();
                }
            }

            boolean damageDealt = original.call(instance, attacker, baseDamage, damageTypeToUse);

            if (damageDealt && heldItem == null && instance instanceof Mob mobTarget) {
                ItemStack gloves = PlayerUtil.getArmorOrAccessoryItem(player, GLOVES_SLOT);
                if (gloves != null && gloves.getItem() instanceof ItemGloves) {
                    gloves.hitEntity(mobTarget, player);
                }
            }

            return damageDealt;
        }

        return original.call(instance, attacker, baseDamage, type);
    }

}
