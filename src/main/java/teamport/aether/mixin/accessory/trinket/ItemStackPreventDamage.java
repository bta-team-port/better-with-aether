package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;
import teamport.aether.mixin.accessors.ItemAccessor;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ItemStack.class)
public abstract class ItemStackPreventDamage {
    @WrapOperation(method = "damageItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;isItemStackDamageable()Z"))
    private boolean preventDamage(ItemStack instance, Operation<Boolean> original, int i, @Nullable Entity entity) {
        if (Boolean.FALSE.equals(original.call(instance))) return false;
        ItemStack asThis = (ItemStack) (Object) this;
        if (!(entity instanceof Player)) return true;
        Player player = (Player) entity;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id && ((ItemAccessor) trinketOne.getItem()).getItemRand().nextInt(4) == 0) {
            PlayerUtil.damageItemArmor(player, i, trinketOne, TRINKET_1_SLOT);
            return false;
        }

        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id && ((ItemAccessor) trinketTwo.getItem()).getItemRand().nextInt(4) == 0) {
            PlayerUtil.damageItemArmor(player, i, trinketTwo, TRINKET_2_SLOT);
            return false;
        }

        return true;
    }
}
