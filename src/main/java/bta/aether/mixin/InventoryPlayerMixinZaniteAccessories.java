package bta.aether.mixin;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = InventoryPlayer.class, remap = false)
public abstract class InventoryPlayerMixinZaniteAccessories {

    @Shadow public EntityPlayer player;

    @Shadow public ItemStack[] armorInventory;

    @ModifyConstant(method = "getStrVsBlock", constant = @Constant(floatValue = 1.0f))
    public float modifyBreakSpeedModifier(float constant) {
        // cause blocks to break faster when zanite accessories are equipped

        int zanite_item_slot = AccessoryHelper.firstSlotWithAccessory(this.player, AetherItems.armorPendantZanite);

        if (zanite_item_slot == -1)
            zanite_item_slot = AccessoryHelper.firstSlotWithAccessory(this.player, AetherItems.armorRingZanite);

        if (zanite_item_slot != -1) {
            ItemStack is = this.armorInventory[zanite_item_slot];
            float durability_progress = (float) is.getMetadata() / is.getItem().getMaxDamage();

            // we will 'lerp' between the starting multiplier and the ending multiplier
            float startingMultiplier = 1.0f;
            float endingMultiplier = 2.0f;

            return (startingMultiplier * (1.0f - durability_progress) + (endingMultiplier * durability_progress));
        }
        return constant;
    }
}
