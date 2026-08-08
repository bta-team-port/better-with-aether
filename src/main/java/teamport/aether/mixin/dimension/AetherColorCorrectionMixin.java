package teamport.aether.mixin.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.PostProcessingManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Environment(EnvType.CLIENT)
@Mixin(PostProcessingManager.class)
public abstract class AetherColorCorrectionMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    @Final
    private PostProcessingManager.PostProcessingConfig current;

    @Inject(method = "tick",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/PostProcessingManager;reset()V",
            shift = At.Shift.AFTER))
    private void applyDimensionColorCorrections(CallbackInfo ci) {
        if (mc.currentWorld == null || mc.thePlayer == null) return;

        if (GameSettings.COLOR_CORRECTION_OVERWORLD.get() != 0.0F && mc.currentWorld.dimension.id == AetherDimension.getAether().id) {
            this.current.gMod += 0.15F;
            this.current.bMod += 0.15F;
            this.current.saturation += 0.125F;
            this.current.exposure += 0.0625F;
            this.current.contrast -= 0.05F;
            this.current.brightness += 0.05F;
            this.current.heatHaze = 0.0F;
        }

    }

}
