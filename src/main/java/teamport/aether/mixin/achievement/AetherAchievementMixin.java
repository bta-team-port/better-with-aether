package teamport.aether.mixin.achievement;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.toasts.AchievementToast;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.sound.SoundCategory;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static teamport.aether.AetherMod.MOD_ID;

@Environment(EnvType.CLIENT)
@Mixin(AchievementToast.class)
public abstract class AetherAchievementMixin {

    @Shadow
    @Final
    private Achievement achievement;

    @WrapOperation(method = "onToastStart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FF)V"))
    private void playAetherSound(SoundEngine instance, String name, SoundCategory category, float volume, float pitch, @NonNull Operation<Void> original) {
        original.call(instance, achievement.statId.namespace().equals(MOD_ID) ? "aether:achievement" : name, category, volume, pitch);
    }
}
