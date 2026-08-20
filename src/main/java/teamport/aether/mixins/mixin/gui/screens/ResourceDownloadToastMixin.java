package teamport.aether.mixins.mixin.gui.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherRemoteResourceDownloaderThread;
import teamport.aether.helper.MixinHelper;

import static teamport.aether.AetherClient.resourceDownloaderThread;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class ResourceDownloadToastMixin {
    @Shadow
    public Minecraft mc;

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/toasts/GuiElementToastsHud;render(F)V", shift = At.Shift.AFTER))
    private void renderResourceToast(float partialTick, CallbackInfo ci) {
        if (resourceDownloaderThread.getTheState() == AetherRemoteResourceDownloaderThread.State.DOWNLOADING) {
            int screenWidth = mc.resolution.getScaledWidthScreenCoords();
            int padding = 5;
            String message = String.format(
                I18n.getInstance().translateKey("aether.download_resources"),
                resourceDownloaderThread.getProgress().get(), resourceDownloaderThread.getToDownload()
            );
            float progress = (float) Math.sin(((double) ((mc.ticksRan + partialTick) % MixinHelper.ANIMATION_LENGTH) / MixinHelper.ANIMATION_LENGTH) * Math.PI);
            mc.guiToasts.drawStringShadow(
                mc.font,
                message,
                screenWidth - mc.font.stringWidth(message) - padding,
                padding,
                MixinHelper.mixColor(DyeColor.WHITE.color.value, DyeColor.SILVER.color.value, progress)
            );
        }
    }
}
