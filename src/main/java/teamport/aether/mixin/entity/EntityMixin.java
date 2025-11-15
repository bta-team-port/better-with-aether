package teamport.aether.mixin.entity;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin {
    @Shadow
    public double y;
    @Unique
    private double prevY = y - 0.5;
    @Unique
    private double deltaY;
    @Inject(method = "baseTick()V", at = @At(value = "HEAD"))
    private void tick(CallbackInfo ci) {
        deltaY = y - prevY;
        prevY = y - 0.5;
    }
    @ModifyArg(method = "move(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/helper/MathHelper;floor(D)I", ordinal = 5), index = 0)
    private double extendBlockRange(double originalY) {
        return originalY + deltaY;
    }
}
