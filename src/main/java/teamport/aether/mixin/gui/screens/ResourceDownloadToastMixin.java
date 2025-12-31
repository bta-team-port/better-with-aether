package teamport.aether.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScaledResolution;
import net.minecraft.client.gui.toasts.GuiElementToastsHud;
import net.minecraft.client.render.Font;
import net.minecraft.core.Timer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherRemoteResourceDownloaderThread;
import teamport.aether.helper.MixinHelper;

import static teamport.aether.AetherClient.resourceDownloaderThread;

@Environment(EnvType.CLIENT)
@Mixin(value = Minecraft.class, remap = false)
public abstract class ResourceDownloadToastMixin {
    @Shadow
    public GuiElementToastsHud guiToasts;
    @Shadow
    public Font font;
    @Shadow
    public ScaledResolution resolution;
    @Shadow
    private int ticksRan;
    @Shadow
    @Final
    private Timer timer;
    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/toasts/GuiElementToastsHud;render(F)V", shift = At.Shift.AFTER))
    private void renderResourceToast(CallbackInfo ci) {
        if (resourceDownloaderThread.getTheState() == AetherRemoteResourceDownloaderThread.State.DOWNLOADING) {
            int screenWidth = resolution.getScaledWidthScreenCoords();
            int padding = 5;
            String message = String.format(
                I18n.getInstance().translateKey("aether.download_resources"),
                resourceDownloaderThread.getProgress().get(), resourceDownloaderThread.getToDownload()
            );
            float progress = (float) Math.sin(((double) ((ticksRan + timer.partialTicks) % MixinHelper.ANIMATION_LENGTH) / MixinHelper.ANIMATION_LENGTH) * Math.PI);
            guiToasts.drawString(
                font,
                message,
                screenWidth - font.getStringWidth(message) - padding,
                padding,
                MixinHelper.mixColor(DyeColor.WHITE.color.value, DyeColor.SILVER.color.value, progress)
            );
        }
    }
}
