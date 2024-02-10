package bta.aether.mixin;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Item.class, remap = false)
public abstract class ItemMixinGoldAccesories {

    @Unique
    public EntityPlayer player;

    @Unique
    public ItemStack[] armorInventory;

    @ModifyConstant(method = "isSilkTouch", constant = @Constant(floatValue = 1.0f))
    public float modifySilkTouchModifier(float constant) {
        // cause blocks to break faster when zanite accessories are equipped

        int gold_item_slot = AccessoryHelper.firstSlotWithAccessory(this.player, AetherItems.armorPendantGold);

        if (gold_item_slot == -1)
            gold_item_slot = AccessoryHelper.firstSlotWithAccessory(this.player, AetherItems.armorRingGold);

        if (gold_item_slot != -1) {
            ItemStack is = this.armorInventory[gold_item_slot];
            float durability_progress = (float) is.getMetadata() / is.getItem().getMaxDamage();

            // we will 'lerp' between the starting multiplier and the ending multiplier
            float startingMultiplier = 1.0f;
            float endingMultiplier = 2.0f;

            return (startingMultiplier * (1.0f - durability_progress) + (endingMultiplier * durability_progress));
        }
        return constant;
    }
}
