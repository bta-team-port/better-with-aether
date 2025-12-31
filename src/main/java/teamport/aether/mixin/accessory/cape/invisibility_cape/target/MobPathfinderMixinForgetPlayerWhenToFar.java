package teamport.aether.mixin.accessory.cape.invisibility_cape.target;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.PlayerUtil;

@Mixin(value = MobPathfinder.class, remap = false)
public abstract class MobPathfinderMixinForgetPlayerWhenToFar {
    @Shadow
    @Nullable
    protected Entity target;
    @Shadow
    public abstract void setTarget(@Nullable Entity target);
    @Inject(method = "updateAI", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/MobPathfinder;isMovementCeased()Z"))
    private void forgetsPlayer(CallbackInfo ci) {
        if (PlayerUtil.isInvisible(target)) {
            MobPathfinder mob = (MobPathfinder) (Object) this;
            float distanceToEntity = this.target.distanceTo(mob);
            if (distanceToEntity > 16) {
                this.setTarget(null);
            }
        }
    }
}
