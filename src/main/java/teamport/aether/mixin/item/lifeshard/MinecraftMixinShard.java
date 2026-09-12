package teamport.aether.mixin.item.lifeshard;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalByteRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.ILifeShard;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixinShard {

    @Shadow
    public PlayerLocal thePlayer;

    @Inject(method = "respawn", at = @At("HEAD"))
    public void saveEffectsOnDeath(
        boolean flag, int i,
        CallbackInfo ci, @Share("lifeShardUsed") LocalByteRef lifeShard
    ) {
        lifeShard.set((byte)((ILifeShard)thePlayer).better_with_aether$getLifeShardUsed());
    }


    @Inject(method = "respawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/player/PlayerLocal;animate4()V", shift = At.Shift.AFTER))
    public void restoreEffectsOnRespawn(
        boolean flag, int i,
        CallbackInfo ci, @Share("lifeShardUsed") LocalByteRef lifeShard
    ) {
        ((ILifeShard)thePlayer).better_with_aether$setLifeshardUsed(lifeShard.get());
    }
}
