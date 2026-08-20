package teamport.aether.mixins.mixin.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.PostProcessingManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.option.AetherGameSettingsHolder;
import teamport.aether.world.type.AetherWorldTypes;

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
        if (this.mc.currentWorld == null || this.mc.thePlayer == null) return;

        float ccValue = AetherGameSettingsHolder.COLOR_CORRECTION_AETHER.value;

        if (ccValue > 0.0F && this.mc.currentWorld.getWorldType().hasTag(AetherWorldTypes.AETHER)) {
            this.current.gMod += 0.15F * ccValue;
            this.current.bMod += 0.15F * ccValue;
            this.current.saturation += 0.125F * ccValue;
            this.current.exposure += 0.0625F * ccValue;
            this.current.contrast -= 0.05F * ccValue;
            this.current.brightness += 0.05F * ccValue;
            this.current.heatHaze = 0.0F;
        }
    }

}
