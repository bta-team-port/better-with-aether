package teamport.aether.mixins.mixin.achievement;

import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

@Mixin(SlotResult.class)
public abstract class SlotCraftingAchievementMixin {
    @Shadow
    private Player thePlayer;
    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;onCrafting(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;)V", shift = At.Shift.AFTER))
    private void addCraftingAchievements(@NonNull ItemStack itemStack, CallbackInfo ci) {
        if (itemStack.itemID == AetherItems.TOOL_PICKAXE_SKYROOT.id) {
            this.thePlayer.addStat(AetherAchievements.SKYROOT, 1);
        }
        if (itemStack.itemID == AetherItems.TOOL_PICKAXE_GRAVITITE.id || itemStack.itemID == AetherItems.TOOL_SHOVEL_GRAVITITE.id || itemStack.itemID == AetherItems.TOOL_AXE_GRAVITITE.id || itemStack.itemID == AetherItems.TOOL_SWORD_GRAVITITE.id
            || itemStack.itemID == AetherItems.ARMOR_HELMET_GRAVITITE.id || itemStack.itemID == AetherItems.ARMOR_LEGGINGS_GRAVITITE.id || itemStack.itemID == AetherItems.ARMOR_CHESTPLATE_GRAVITITE.id || itemStack.itemID == AetherItems.ARMOR_BOOTS_GRAVITITE.id
            || itemStack.itemID == AetherItems.ARMOR_TALISMAN_GRAVITITE.id) {
            this.thePlayer.addStat(AetherAchievements.GRAVITITE, 1);
        }
        if (itemStack.itemID == AetherBlocks.ENCHANTER_IDLE.id()) {
            this.thePlayer.addStat(AetherAchievements.ENCHANTER, 1);
        }
        if (itemStack.itemID == AetherItems.TOOL_SHOOTER.id) {
            this.thePlayer.addStat(AetherAchievements.SHOOTER, 1);
        }
        if (itemStack.itemID == AetherItems.ARMOR_GLOVES_CHAINMAIL.id || itemStack.itemID == AetherItems.ARMOR_TALISMAN_CHAINMAIL.id) {
            this.thePlayer.addStat(Achievements.REPAIR_ARMOR, 1);
        }
    }
}
