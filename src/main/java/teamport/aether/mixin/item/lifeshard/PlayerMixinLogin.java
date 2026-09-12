package teamport.aether.mixin.item.lifeshard;


import net.minecraft.core.net.packet.PacketLogin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerLogin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.ILifeShard;
import teamport.aether.net.message.AetherSyncLifeShardMessage;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = PacketHandlerLogin.class, remap = false)
public class PlayerMixinLogin {

    @Shadow
    @Final
    private MinecraftServer mcServer;

    @Inject(method = {"doLogin"}, at = {@At(value = "TAIL")})
    public void doLogin(PacketLogin loginPacket, CallbackInfo ci) {
        PlayerServer player = this.mcServer.playerList.getPlayerEntity(loginPacket.username);

        if (player != null) {
            byte lifeShardUsed = (byte)((ILifeShard)player).better_with_aether$getLifeShardUsed();
            NetworkHandler.sendToPlayer(player, new AetherSyncLifeShardMessage(lifeShardUsed));
        }
    }
}
