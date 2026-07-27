package teamport.aether.mixin.player;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.effect.api.HealthHelper;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerExtraHealthMixin {
    private static final String AETHER_EXTRA_HEALTH = "AetherExtraHealth";

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    private void addExtraHealth(CallbackInfoReturnable<Integer> cir) {
        Player player = (Player) (Object) this;
        cir.setReturnValue(cir.getReturnValue() + HealthHelper.getExtraHealth(player));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void saveExtraHealth(CompoundTag tag, CallbackInfo ci) {
        tag.putInt(AETHER_EXTRA_HEALTH, HealthHelper.getExtraHealth((Player) (Object) this));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void loadExtraHealth(CompoundTag tag, CallbackInfo ci) {
        HealthHelper.setExtraHealth((Player) (Object) this, tag.getInteger(AETHER_EXTRA_HEALTH));
    }
}
