package teamport.aether.mixin.achievement;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.achievement.Achievements;
import net.minecraft.core.achievement.stat.Stat;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.SlotArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;

@Mixin(SlotArmor.class)
public abstract class SlotArmorWearChainmailAchievementMixin {

    @Shadow
    @Final
    MenuInventory menu;

    @WrapOperation(method = "setChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;triggerAchievement(Lnet/minecraft/core/achievement/stat/Stat;)V"))
    private void checkChainmailWithAccessories(Player instance, Stat statbase, Operation<Void> original) {
        if (statbase == Achievements.GET_CHAINMAIL) {
            MixinHelper.checkChainmailAchievement(this.menu);
        } else {
            original.call(instance, statbase);
        }
    }
}
