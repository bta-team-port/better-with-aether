package bta.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.core.enums.EnumFireflyColor;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderGlobal.class, remap = false)
public class RenderGlobalMixin {
    @Shadow private Minecraft mc;

    @Shadow private World worldObj;

    @Inject(method = "addParticle(Ljava/lang/String;DDDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void specialParticles(String particleId, double x, double y, double z, double motionX, double motionY, double motionZ, double maxDistance, CallbackInfo ci){
        if (particleId.equals("fireflySilver")){
            double d8;
            double d7;
            if (this.mc == null || this.mc.activeCamera == null || this.mc.effectRenderer == null) {
                ci.cancel();
                return;
            }
            double d6 = this.mc.activeCamera.getX() - x;
            if (d6 * d6 + (d7 = this.mc.activeCamera.getY() - y) * d7 + (d8 = this.mc.activeCamera.getZ() - z) * d8 > maxDistance * maxDistance) {
                ci.cancel();
                return;
            }
            mc.effectRenderer.addEffect(new EntityFireflyFX(this.worldObj, x, y, z, motionX, motionY, motionZ, 2.5f, EnumFireflyColor.SILVER.getId()));
        }
    }
}
