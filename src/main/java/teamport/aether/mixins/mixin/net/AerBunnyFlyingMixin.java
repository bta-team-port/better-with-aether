package teamport.aether.mixins.mixin.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.packet.PacketMovePlayer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.aerbunny.MobAerbunny;

@Environment(EnvType.SERVER)
@Mixin(PacketHandlerServer.class)
public abstract class AerBunnyFlyingMixin {
    @Shadow
    private PlayerServer playerEntity;
    @Shadow
    private int playerInAirTime;
    @Inject(method = "handleMovePlayer(Lnet/minecraft/core/net/packet/PacketMovePlayer;)V", at = @At("HEAD"))
    private void handleMovePlayer(PacketMovePlayer packetMovePlayer, CallbackInfo ci) {
        if (this.playerEntity.passenger instanceof MobAerbunny) {
            playerInAirTime = 0;
        }
    }
}
