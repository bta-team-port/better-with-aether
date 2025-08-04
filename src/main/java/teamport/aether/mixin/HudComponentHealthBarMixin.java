package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.gui.hud.component.layout.Layout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HudComponentHealthBar.class, remap = false)
public class HudComponentHealthBarMixin {

    // TODO incase we have different heart icons enable this
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void noRenderHealthBar(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci){
        ci.cancel();
    }

    // TODO incase we have different heart icons enable this
    @Inject(method = "renderPreview", at = @At("HEAD"), cancellable = true)
    public void noRenderPreviewHealthBar(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen, CallbackInfo ci){
        ci.cancel();
    }
}
