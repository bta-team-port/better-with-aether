package teamport.aether.mixin.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import teamport.aether.AetherServer;

@Environment(EnvType.SERVER)
@Mixin(value = PacketHandlerLogin.class)
public abstract class PlayerLoginMixin {
    @Shadow
    @Final
    private MinecraftServer mcServer;
    @Inject(method = "doLogin(Lnet/minecraft/core/net/packet/PacketLogin;)V", at = @At("TAIL"))
    private void doLogin(PacketLogin loginPacket, CallbackInfo ci) {
        PlayerServer player = this.mcServer.playerList.getPlayerEntity(loginPacket.username);
        if (player != null) AetherServer.onPlayerJoinedServer(player);
    }
}
