package teamport.aether.mixin;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}
