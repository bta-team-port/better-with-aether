package teamport.aether.mixin.entity;

import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.boss.slider.MobBossSlider;

@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderSliderBoundingBoxMixin {

    @Shadow
    public abstract void drawOutlinedBoundingBox(AABB aabb);

    // on today's episode of stupid mixins...
    @Inject(method = "drawInterpolatedEntityBoundingBox", at = @At("HEAD"), cancellable = true)
    public void undoGrow(Entity entity, ICamera camera, float partialTicks, CallbackInfo ci) {
        if (entity instanceof MobBossSlider) {
            double cameraX = camera.getX(partialTicks);
            double cameraY = camera.getY(partialTicks);
            double cameraZ = camera.getZ(partialTicks);

            double lerpOffsetX = entity.xo + (entity.x - entity.xo) * (double) partialTicks;
            double lerpOffsetY = entity.yo + (entity.y - entity.yo) * (double) partialTicks;
            double lerpOffsetZ = entity.zo + (entity.z - entity.zo) * (double) partialTicks;

            AABB boundingBox = entity.bb;
            boundingBox = boundingBox.cloneMove(-entity.x, -entity.y, -entity.z);
            boundingBox = boundingBox.cloneMove(-cameraX, -cameraY, -cameraZ);
            boundingBox = boundingBox.cloneMove(lerpOffsetX, lerpOffsetY, lerpOffsetZ);
            this.drawOutlinedBoundingBox(boundingBox);
            ci.cancel();
        }

    }
}
