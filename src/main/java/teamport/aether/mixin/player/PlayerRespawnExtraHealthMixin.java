package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerLocal.class, remap = false)
public abstract class PlayerRespawnExtraHealthMixin {
    @Shadow
    protected Minecraft mc;
    @Inject(method = "respawnPlayer", at = @At("TAIL"), remap = false)
    private void restoreExtraHeartsClient(CallbackInfo ci) {
        this.mc.thePlayer.heal(1000);
    }
}
