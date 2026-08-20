package teamport.aether.mixins.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;
import teamport.aether.mixins.mixin.accessors.ItemAccessor;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(ItemStack.class)
public abstract class ItemStackPreventDamage {
    @WrapOperation(method = "damageItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;isItemStackDamageable()Z"))
    private boolean preventDamage(ItemStack instance, @NonNull Operation<Boolean> original, int i, @Nullable Entity entity) {
        if (Boolean.FALSE.equals(original.call(instance))) return false;
        ItemStack asThis = (ItemStack) (Object) this;
        if (!(entity instanceof Player player)) return true;
        ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id && ItemAccessor.getItemRand().nextInt(4) == 0) {
            PlayerUtil.damageItemArmor(player, i, trinketOne, TRINKET_1_SLOT);
            return false;
        }

        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id && ItemAccessor.getItemRand().nextInt(4) == 0) {
            PlayerUtil.damageItemArmor(player, i, trinketTwo, TRINKET_2_SLOT);
            return false;
        }

        return true;
    }
}
