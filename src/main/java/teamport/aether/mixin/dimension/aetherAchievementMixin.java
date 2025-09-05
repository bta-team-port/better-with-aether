package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.AchievementToast;
import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static teamport.aether.AetherMod.MOD_ID;

@Mixin(value = AchievementToast.class, remap = false)
public abstract class aetherAchievementMixin{
    @Shadow @Final private Achievement achievement;

    @Shadow private long startTime;

    @Shadow @Final private boolean informational;

    @Shadow @Final private Minecraft mc;

    @Inject(method = "onToastStart", at = @At("HEAD"), cancellable = true)
    public void playAetherSound(CallbackInfo ci) {
        if (achievement.statId.namespace().equals(MOD_ID)) {
            new Random();

            this.startTime = System.currentTimeMillis();
            if (!this.informational) {
                this.mc.sndManager.playSound("aether:achievement", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
            }
            ci.cancel();
        }
    }
}
