package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.boss.slider.MobBossSlider;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderSliderBoundingBoxMixin {
    @WrapOperation(
        method = "drawInterpolatedEntityBoundingBox(Lnet/minecraft/core/entity/Entity;Lorg/joml/primitives/AABBdc;Lnet/minecraft/client/render/camera/ICamera;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/util/helper/MathHelper;aabbGrow(Lorg/joml/primitives/AABBdc;DDDLorg/joml/primitives/AABBd;)Lorg/joml/primitives/AABBd;"
        )
    )
    private AABBd undoGrow(AABBdc instance, double d, double d1, double d2, AABBd destination, Operation<AABBd> original, Entity entity, AABBdc boundingBox, ICamera camera, float partialTicks) {
        if (entity instanceof MobBossSlider) return destination.set(instance);
        return original.call(instance, d, d1, d2, destination);
    }
}
