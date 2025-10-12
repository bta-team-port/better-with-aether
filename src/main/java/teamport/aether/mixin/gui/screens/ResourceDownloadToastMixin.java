package teamport.aether.mixin.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ScaledResolution;
import net.minecraft.client.gui.toasts.GuiElementToastsHud;
import net.minecraft.client.render.Font;
import net.minecraft.core.Timer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherRemoteResourceDownloaderThread;

import static teamport.aether.AetherClient.resourceDownloaderThread;

@Mixin(value = Minecraft.class, remap = false)
public class ResourceDownloadToastMixin {

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

    @Unique
    int dark = DyeColor.SILVER.color.value;

    @Unique
    int light = DyeColor.WHITE.color.value;

    @Unique
    int animLength = (int) (20 * 1.5);

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/toasts/GuiElementToastsHud;render(F)V", shift = At.Shift.AFTER))
    public void renderResourceToast(CallbackInfo ci) {
        if (resourceDownloaderThread.state == AetherRemoteResourceDownloaderThread.State.DOWNLOADING) {
            int screenWidth = resolution.getScaledWidthScreenCoords();
            int screenHeight = resolution.getScaledHeightScreenCoords();
            int padding = 5;

            String message = String.format(
                    I18n.getInstance().translateKey("aether.download_resources"),
                    resourceDownloaderThread.progress.get(), resourceDownloaderThread.toDownload
            );

            float progress = (float) Math.sin(((double) ((ticksRan + timer.partialTicks) % animLength) / animLength) * Math.PI);

            guiToasts.drawString(
                    font,
                    message,
                    screenWidth - font.getStringWidth(message) - padding,
                    padding,
                    mixColor(light, dark, progress)
            );
        }
    }

    @Unique
    private static int mixColor(int colorA, int colorB, float ratio) {
        int alphaA = Color.alphaFromInt(colorA);
        int redA   = Color.redFromInt(colorA);
        int blueA  = Color.blueFromInt(colorA);
        int greenA = Color.greenFromInt(colorA);
        int alphaB = Color.alphaFromInt(colorB);
        int redB   = Color.redFromInt(colorB);
        int blueB  = Color.blueFromInt(colorB);
        int greenB = Color.greenFromInt(colorB);

        int alphaRes = (int) (alphaA * ratio + alphaB * (1 - ratio));
        int redRes = (int) (redA * ratio + redB * (1 - ratio));
        int blueRes = (int) (blueA * ratio + blueB * (1 - ratio));
        int greenRes = (int) (greenA * ratio + greenB * (1 - ratio));

        return Color.intToIntARGB(alphaRes, redRes, blueRes, greenRes);
    }
}
