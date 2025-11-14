package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.boss.slider.MobBossSlider;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderSliderBoundingBoxMixin {
    @WrapOperation(method = "drawInterpolatedEntityBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/phys/AABB;grow(DDD)Lnet/minecraft/core/util/phys/AABB;"))
    public AABB undoGrow(AABB instance, double d, double d1, double d2, Operation<AABB> original, Entity entity, ICamera camera, float partialTicks) {
        if (entity instanceof MobBossSlider) return instance;
        return original.call(instance, d, d1, d2);
    }
}
