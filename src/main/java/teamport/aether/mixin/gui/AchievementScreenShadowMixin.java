package teamport.aether.mixin.gui;

import teamport.aether.achievements.AetherAchievementPageExtras;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.gui.achievements.ScreenAchievements;
import net.minecraft.client.gui.achievements.data.AchievementPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenAchievements.class, remap = false)
public class AchievementScreenShadowMixin {

    @Shadow
    private AchievementPage currentPage;

    @Inject(method = "drawBackgroundTiles", at = @At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D"))
    public void ChangeShadow(double shiftX, double shiftY, CallbackInfo ci, @Local LocalFloatRef shadowScaleInitial, @Local(name = "i") int index) {
        if (currentPage instanceof AetherAchievementPageExtras) {
            shadowScaleInitial.set(((AetherAchievementPageExtras) currentPage).getShadowScale(index));
        }
    }
}
