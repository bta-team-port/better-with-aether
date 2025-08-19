package teamport.aether.mixin.entity;

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
    public double prevY = y - 0.5;
    @Unique
    public double deltaY;

    @Inject(method = "baseTick()V", at = @At(value ="HEAD"))
    public void tick(CallbackInfo ci){
        deltaY = y - prevY;
        prevY = y - 0.5;
    }

    @Redirect(method = "move(DDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/util/helper/MathHelper;floor(D)I", ordinal = 5))
    public int extendBlockRange(double xd, double yd, double zd){
        return MathHelper.floor(((Entity)(Object)this).bb.minY + 0.001 + deltaY);
    }

}
