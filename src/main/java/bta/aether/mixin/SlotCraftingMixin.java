package bta.aether.mixin;

import bta.aether.AetherAchievements;
import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SlotCrafting.class, remap = false)
public class SlotCraftingMixin {
    @Shadow
    private EntityPlayer thePlayer;
    @Inject(method = "onPickupFromSlot(Lnet/minecraft/core/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;onCrafting(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;)V", shift = At.Shift.AFTER))
    private void addCraftingAchievements(ItemStack itemstack, CallbackInfo ci){
        if (itemstack.itemID == AetherBlocks.enchanter.id) {
            thePlayer.addStat(AetherAchievements.ENCHANTER, 1);
        }
        if (itemstack.itemID == AetherItems.toolPickaxeGravitite.id) {
            thePlayer.addStat(AetherAchievements.GRAVITITE, 1);
        }
    }
}
