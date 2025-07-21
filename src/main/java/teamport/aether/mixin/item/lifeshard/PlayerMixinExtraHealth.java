package teamport.aether.mixin.item.lifeshard;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.items.IVariableHealthPlayer;

import static teamport.aether.AetherConfig.EXTRA_HEALTH;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinExtraHealth
        implements IVariableHealthPlayer {
    @Unique
    public int extraHealth = EXTRA_HEALTH;

    //###############################  ItemLifeShard  ###############################

    // when respawning the player is created as a new entity, thus this is incorrect
    // TODO define in entry in entityData for extra Health
//    @Inject(method = "defineSynchedData", at = @At("TAIL"))
//    public void aether$initExtraHealth(CallbackInfo ci) {
//        this.entityData.define(31, 0, Integer.class);
//    }

    @Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
    public void aether$getMaxHealth(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(20 + this.aether$getExtraHealth());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void aether$writeExtraHealth(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("ExtraHP", this.aether$getExtraHealth());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void aether$readExtraHealth(CompoundTag tag, CallbackInfo ci) {
        if (tag.containsKey("ExtraHP")) {
            this.aether$setExtraHealth(tag.getInteger("ExtraHP"));
        } else {
            this.aether$setExtraHealth(0);
        }
    }

    // TODO restore once entry in entityData for extra Health works
    public int aether$getExtraHealth() {
        return this.extraHealth;
//        return this.entityData.getInt(31);
    }

    // TODO restore once entry in entityData for extra Health works
    public void aether$setExtraHealth(int extraHealth) {
        this.extraHealth = Math.min(extraHealth, EXTRA_HEALTH);
//        this.entityData.set(31, Math.min(extraHealth, 20));
    }

    public void aether$addExtraHealth(int extraHealth) {
        this.aether$setExtraHealth(this.aether$getExtraHealth() + extraHealth);
    }
}
