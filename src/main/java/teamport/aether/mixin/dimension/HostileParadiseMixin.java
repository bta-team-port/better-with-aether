package teamport.aether.mixin.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.core.util.helper.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherAchievements;
import teamport.aether.world.AetherDimension;

@Mixin(value = Minecraft.class, remap = false)
public abstract class HostileParadiseMixin {

    @Inject(method = "usePortal", at = @At(value = "HEAD"))
    private void grantHostileParadise(int dim, DyeColor portalColor, CallbackInfo ci){
        if (dim == AetherDimension.AetherDimensionID) {
            Minecraft.getMinecraft().thePlayer.addStat(AetherAchievements.HOSTILE_PARADISE, 1);
        }
    }
}
