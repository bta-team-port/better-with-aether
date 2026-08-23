package teamport.aether.mixin.accessory;

import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import teamport.aether.helper.MixinHelper;

@Mixin(SlotArmor.class)
public abstract class SlotArmorMixin {

    @Shadow
    @Final
    MenuInventory menu;

    @Redirect(method = "setChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;triggerAchievement(Lnet/minecraft/core/achievement/stat/Stat;)V"))
    private void checkChainmailWithAccessories(Player instance, Stat statbase) {
        if (statbase == Achievements.GET_CHAINMAIL) {
            MixinHelper.checkChainmailAchievement(this.menu);
        } else {
            instance.triggerAchievement(statbase);
        }
    }
}
