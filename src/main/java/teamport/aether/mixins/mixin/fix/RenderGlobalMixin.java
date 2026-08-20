package teamport.aether.mixins.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.core.lang.I18n;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
    @WrapOperation(method = "playStreamingMusic(Ljava/lang/String;Ljava/lang/String;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/lang/I18n;translateKey(Ljava/lang/String;)Ljava/lang/String;"))
    private String fixCustomRecordTranslation(I18n instance, String s, Operation<String> original, @NonNull String soundPath, String author, int x, int y, int z) {
        if (!soundPath.contains(":")) return original.call(instance, s);
        return original.call(instance, soundPath.substring(soundPath.indexOf(":") + 1));
    }
}
