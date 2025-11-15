package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerLocal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import teamport.aether.blocks.AetherBlocks;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerLocal.class, remap = false)
public abstract class PlayerLocalPortalNoiseMixin {
    @ModifyArg(method = "onLivingUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FF)V", ordinal = 0), index = 0)
    private String modifyPortalTriggerSound(String originalSound) {
        PlayerLocal player = (PlayerLocal) (Object) this;
        if (player.portalID == AetherBlocks.PORTAL_AETHER.id()) {
            return "aether:trigger";
        }
        return originalSound;
    }
    @ModifyArg(method = "onLivingUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;playSound(Ljava/lang/String;Lnet/minecraft/core/sound/SoundCategory;FF)V", ordinal = 1), index = 0)
    private String modifyPortalTravelSound(String originalSound) {
        PlayerLocal player = (PlayerLocal) (Object) this;
        if (player.portalID == AetherBlocks.PORTAL_AETHER.id()) {
            return "aether:travel";
        }
        return originalSound;
    }
}
