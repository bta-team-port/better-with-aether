package teamport.aether.mixin.item.lifeshard;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.ILifeShard;

@Mixin(value = Player.class, remap = false)
public class PlayerMixinLifeShard implements ILifeShard {

    public byte lifeshardUsed = 0;

    @Override
    public int better_with_aether$getLifeShardUsed() {
        return this.lifeshardUsed;
    }

    @Override
    public void better_with_aether$setLifeshardUsed(byte lifeshardUsed) {
        this.lifeshardUsed = lifeshardUsed ;
    }


    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putByte("lifeShardUsed", this.lifeshardUsed);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.lifeshardUsed = tag.getByte("lifeShardUsed");
    }
}
