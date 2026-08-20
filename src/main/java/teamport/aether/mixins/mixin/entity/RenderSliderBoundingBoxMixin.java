package teamport.aether.mixins.mixin.entity;

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
@Mixin(RenderGlobal.class)
public abstract class RenderSliderBoundingBoxMixin {
    @WrapOperation(
        method = "drawInterpolatedEntityBoundingBox(Lnet/minecraft/core/entity/Entity;Lorg/joml/primitives/AABBdc;Lnet/minecraft/client/render/camera/ICamera;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/util/helper/MathHelper;aabbGrow(Lorg/joml/primitives/AABBdc;DDDLorg/joml/primitives/AABBd;)Lorg/joml/primitives/AABBd;"
        )
    )
    private AABBd undoGrow(AABBdc self, double x, double y, double z, AABBd dest, Operation<AABBd> original, Entity entity, AABBdc boundingBox, ICamera camera, float partialTicks) {
        if (entity instanceof MobBossSlider) return dest.set(self);
        return original.call(self, x, y, z, dest);
    }
}
