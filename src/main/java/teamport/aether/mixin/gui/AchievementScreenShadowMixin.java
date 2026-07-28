package teamport.aether.mixin.gui;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.achievements.ScreenAchievements;
import net.minecraft.client.gui.achievements.data.AchievementPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.achievements.AetherAchievementPageExtras;

@Environment(EnvType.CLIENT)
@Mixin(value = ScreenAchievements.class, remap = false)
public abstract class AchievementScreenShadowMixin {
    @Shadow
    private AchievementPage currentPage;
    @Shadow
    private int viewportLeft;
    @Shadow
    private int viewportTop;
    @Shadow
    private int viewportRight;
    @Shadow
    private int viewportBottom;

    @Inject(method = "drawBackgroundTiles", at = @At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D"))
    private void changeShadow(double shiftX, double shiftY, CallbackInfo ci, @Local LocalFloatRef shadowScaleInitial, @Local(name = "i") int index) {
        if (currentPage instanceof AetherAchievementPageExtras) {
            shadowScaleInitial.set(((AetherAchievementPageExtras) currentPage).getShadowScale(index));
        }
    }

    @Inject(method = "renderAchievementsPanel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievements/ScreenAchievements;drawRectDouble(DDDDI)V", shift = At.Shift.AFTER))
    private void drawBackground(int mouseX, int mouseY, float partialTick, CallbackInfo ci, @Local(name = "shiftX") double shiftX, @Local(name = "shiftY") double shiftY) {
        if (currentPage instanceof AetherAchievementPageExtras) {
            ((AetherAchievementPageExtras) currentPage).drawBeforeTiles((ScreenAchievements) (Object) this, shiftX, shiftY, mouseX, mouseY, viewportLeft, viewportTop, viewportRight, viewportBottom);
        }
    }
}
