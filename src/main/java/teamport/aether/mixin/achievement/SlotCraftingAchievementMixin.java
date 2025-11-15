package teamport.aether.mixin.achievement;

import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

@Mixin(value = SlotResult.class, remap = false)
public abstract class SlotCraftingAchievementMixin {
    @Shadow
    private Player thePlayer;
    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;onCrafting(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;)V", shift = At.Shift.AFTER))
    private void addCraftingAchievements(ItemStack itemstack, CallbackInfo ci) {
        if (itemstack.itemID == AetherItems.TOOL_PICKAXE_SKYROOT.id) {
            this.thePlayer.addStat(AetherAchievements.SKYROOT, 1);
        }
        if (itemstack.itemID == AetherItems.TOOL_PICKAXE_GRAVITITE.id || itemstack.itemID == AetherItems.TOOL_SHOVEL_GRAVITITE.id || itemstack.itemID == AetherItems.TOOL_AXE_GRAVITITE.id || itemstack.itemID == AetherItems.TOOL_SWORD_GRAVITITE.id
            || itemstack.itemID == AetherItems.ARMOR_HELMET_GRAVITITE.id || itemstack.itemID == AetherItems.ARMOR_LEGGINGS_GRAVITITE.id || itemstack.itemID == AetherItems.ARMOR_CHESTPLATE_GRAVITITE.id || itemstack.itemID == AetherItems.ARMOR_BOOTS_GRAVITITE.id
            || itemstack.itemID == AetherItems.ARMOR_TALISMAN_GRAVITITE.id) {
            this.thePlayer.addStat(AetherAchievements.GRAVITITE, 1);
        }
        if (itemstack.itemID == AetherBlocks.ENCHANTER_IDLE.id()) {
            this.thePlayer.addStat(AetherAchievements.ENCHANTER, 1);
        }
        if (itemstack.itemID == AetherItems.TOOL_SHOOTER.id) {
            this.thePlayer.addStat(AetherAchievements.SHOOTER, 1);
        }
        if (itemstack.itemID == AetherItems.ARMOR_GLOVES_CHAIN.id || itemstack.itemID == AetherItems.ARMOR_TALISMAN_CHAIN.id) {
            this.thePlayer.addStat(Achievements.REPAIR_ARMOR, 1);
        }
    }
}
