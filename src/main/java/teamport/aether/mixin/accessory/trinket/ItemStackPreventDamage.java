package teamport.aether.mixin.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;
import teamport.aether.mixin.accessors.ItemAccessor;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(value = ItemStack.class, remap = false)
public class ItemStackPreventDamage {

    @Inject(method = "damageItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/item/ItemStack;isItemStackDamageable()Z",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    public void preventDamage(int damage, Entity entity, CallbackInfo ci) {
        ItemStack asThis = (ItemStack) (Object) this;
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        if (trinketOne != null && trinketOne.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id) {
            if (((ItemAccessor) trinketOne.getItem()).getItemRand().nextInt(4) == 0) {
                trinketOne.damageItem(damage, player);
                ci.cancel();
            }
        }
        if (trinketTwo != null && trinketTwo.itemID == AetherItems.ARMOR_TALISMAN_STEEL.id && asThis.itemID != AetherItems.ARMOR_TALISMAN_STEEL.id) {
            if (((ItemAccessor) trinketTwo.getItem()).getItemRand().nextInt(4) == 0) {
                trinketTwo.damageItem(damage, player);
                ci.cancel();
            }
        }

    }
}
