package teamport.aether.mixin.achievement;

import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

@Mixin(EntityItem.class)
public abstract class EntityItemPickupAchievementMixin {

    @Shadow
    public ItemStack item;

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void playerTouch(Player player, CallbackInfo ci) {
        String pickUpKey = StatList.STAT_PICKED_UP;
        if (this.item.itemID == AetherBlocks.AERCLOUD_GOLD.id()) {
            player.triggerAchievement(AetherAchievements.GOLD_CLOUD);
        }
        if ((this.item.itemID == AetherItems.RECORD_AETHER.id
            || this.item.itemID == AetherItems.RECORD_DAWN.id
            || this.item.itemID == AetherItems.RECORD_MORNING.id
            || this.item.itemID == AetherItems.RECORD_NETHER.id
            && player.getStat(AetherItems.RECORD_AETHER.getStat(pickUpKey)) > 0
            && player.getStat(AetherItems.RECORD_DAWN.getStat(pickUpKey)) > 0
            && player.getStat(AetherItems.RECORD_NETHER.getStat(pickUpKey)) > 0
            && player.getStat(AetherItems.RECORD_MORNING.getStat(pickUpKey)) > 0)) {
            player.triggerAchievement(AetherAchievements.ALL_MUSIC_DISCS);
        }
    }
}
