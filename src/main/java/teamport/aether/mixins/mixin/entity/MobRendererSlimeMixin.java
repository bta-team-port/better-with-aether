package teamport.aether.mixins.mixin.entity;

import net.minecraft.client.render.entity.MobRendererSlime;
import net.minecraft.core.entity.monster.MobSlime;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;

@Mixin(value = MobRendererSlime.class, remap = false)
public class MobRendererSlimeMixin {

    @Inject(method = "getAndSetupModelForLayer(Lnet/minecraft/core/entity/monster/MobSlime;FFI)Lorg/useless/dragonfly/models/entity/StaticEntityModel;", at = @At("TAIL"))
    private void fixSlimeFloorOffset(MobSlime entity, float brightness, float partialTick, int layer, @NonNull CallbackInfoReturnable<StaticEntityModel> cir) {
        StaticEntityModel model = cir.getReturnValue();
        if (model != null) {
            BoneTransform cube = model.getTransform("cube");
            cube.posY = 24.0 * (cube.scaleY - 1.0);
        }
    }
}
