package teamport.aether.mixin.fix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderGlobal.class, remap = false)
public class RenderGlobalMixin {

    @Inject(method = "playStreamingMusic", at = @At("HEAD"), cancellable = true)
    private void fixCustomRecordTranslation(String soundPath, String author, int x, int y, int z, CallbackInfo ci) {
        if (soundPath != null && soundPath.contains(":")) {
            Minecraft mc = Minecraft.getMinecraft();
            String recordName = soundPath.substring(soundPath.indexOf(":") + 1);

            String message = author != null && !author.isEmpty()
                    ? author + " - " + I18n.getInstance().translateKey(recordName)
                    : I18n.getInstance().translateKey(recordName);

            mc.hudIngame.setRecordPlayingMessage(message);
            mc.sndManager.playMusic(soundPath, (float)x, (float)y, (float)z, 1.0F, 1.0F);
            ci.cancel();
        }
    }
}
