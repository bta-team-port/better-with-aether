package bta.aether.mixin;

import bta.aether.mixin.accessors.EntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.FireBarComponent;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FireBarComponent.class, remap = false)
abstract public class FireBarComponentMixinPhoenix extends MovableHudComponent {
    public FireBarComponentMixinPhoenix(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {
        if (((EntityAccessor) mc.thePlayer).isFireImmune()) {
            ci.cancel();
        }
    }
}
