package teamport.aether.mixin;

import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = HudComponentHealthBar.class, remap = false)
public class HudComponentHealthBarMixin {

    // TODO incase we have different heart icons enable this
//    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
//    public void noRenderHealthBar(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci){
//        ci.cancel();
//    }

    // TODO incase we have different heart icons enable this
//    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
//    public void noRenderPreviewHealthBar(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci){
//        ci.cancel();
//    }
}
