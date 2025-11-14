package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.ItemAccessor;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackPreventDamage {
    @WrapOperation(method = "damageItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;isItemStackDamageable()Z"))
    public boolean preventDamage(ItemStack instance, Operation<Boolean> original, int i, @Nullable Entity entity) {
        if (Boolean.FALSE.equals(original.call(instance))) return false;
        ItemStack asThis = (ItemStack) (Object) this;
        if (!(entity instanceof Player)) return false;
        Player player = (Player) entity;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id) {
            if (((ItemAccessor) trinketOne.getItem()).getItemRand().nextInt(4) == 0) {
                trinketOne.damageItem(i, player);
                return false;
            }
        }
        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id) {
            if (((ItemAccessor) trinketTwo.getItem()).getItemRand().nextInt(4) == 0) {
                trinketTwo.damageItem(i, player);
                return false;
            }
        }
        return true;
    }
}
