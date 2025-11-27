package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.PostProcessingManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(PostProcessingManager.class)
public abstract class AetherColorCorrectionMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    public float gMod;

    @Shadow
    public float bMod;

    @Shadow
    public float saturation;

    @Shadow
    public float contrast;

    @Shadow
    public float brightness;

    @Shadow
    public float exposure;

    @Shadow
    public float heatHaze;

    @Inject(method = "update",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/PostProcessingManager;reset()V",
            shift = At.Shift.AFTER))
    private void applyDimensionColorCorrections(CallbackInfo ci) {
        if (mc.currentWorld == null || mc.thePlayer == null) return;

        if (this.mc.gameSettings.colorCorrection.value != 0.0F && mc.currentWorld.dimension.id == AetherDimension.getAether().id) {
            this.gMod += 0.15F;
            this.bMod += 0.15F;
            this.saturation += 0.125F;
            this.exposure -= 0.125F;
            this.contrast -= 0.05F;
            this.brightness += 0.05F;
            this.heatHaze = 0.0F;
        }

    }

}
