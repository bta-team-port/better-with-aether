package teamport.aether.mixin.accessory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.GLManager;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRenderer.class)
public abstract class MobRendererGLCleanUpMixin<T extends Mob> {
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelBase;render(FFFFFF)V", shift = At.Shift.AFTER, ordinal = 2))
    private void restoreGLState2(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
        GLManager.restore();
    }
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelBase;render(FFFFFF)V", shift = At.Shift.AFTER, ordinal = 5))
    private void restoreGLState5(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
        GLManager.restore();
    }
    @Inject(method = "render(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Mob;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelBase;render(FFFFFF)V", shift = At.Shift.AFTER, ordinal = 8))
    private void restoreGLState8(Tessellator tessellator, T entity, double x, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
        GLManager.restore();
    }
}
