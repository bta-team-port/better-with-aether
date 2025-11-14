package teamport.aether.mixin.net;

import net.minecraft.core.net.packet.PacketMovePlayer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;

@Mixin(value = PacketHandlerServer.class, remap = false)
public abstract class AerBunnyFlyingMixin {
    @Shadow
    private PlayerServer playerEntity;
    @Shadow
    private int playerInAirTime;
    @Inject(method = "handleFlying", at = @At("HEAD"))
    public void handleFlying(PacketMovePlayer packet, CallbackInfo ci) {
        if (this.playerEntity.passenger instanceof MobAerbunny) {
            playerInAirTime = 0;
        }
    }
}
