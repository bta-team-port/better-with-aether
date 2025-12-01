package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.useless.dragonfly.renderer.MobRenderer;

@Mixin(value = MobRenderer.class, remap = false)
public abstract class MobRendererMixinFireMinion {


    @WrapOperation(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V"
        )
    )
    private void onGlColor4f(float r, float g, float b, float a, Operation<Void> original, Entity entity) {
//        boolean isDamageColor = r == 1.0F && g == 0.0F && b == 0.0F && a == 0.4F;
//        if(entity instanceof MobFireMinion && isDamageColor){
//            original.call(0.0f, g, 1.0f, a);
//        }
//        original.call(r, g, b, a);
    }
}
