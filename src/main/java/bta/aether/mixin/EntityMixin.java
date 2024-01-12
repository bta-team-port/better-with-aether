package bta.aether.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, remap = false)
public class EntityMixin {
    @Shadow
    public double y;
    @Unique
    public double prevY = y;
    @Unique
    public double deltaY;

    @Inject(method = "baseTick()V", at = @At(value ="HEAD"))
    private void tick(CallbackInfo ci){
        deltaY = y - prevY;
        prevY = y;
    }

    @Redirect(method = "move(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/helper/MathHelper;floor_double(D)I", ordinal = 5))
    private int extendBlockRange(double d){
        return MathHelper.floor_double(((Entity)(Object)this).bb.minY + 0.001 + deltaY);
    }
}
