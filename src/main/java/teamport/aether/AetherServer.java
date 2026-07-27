package teamport.aether;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.entity.player.PlayerServer;
import teamport.aether.command.AetherCommand;
import teamport.aether.net.message.EffectSyncNetworkMessage;
import teamport.aether.net.message.ExtraHealthSyncNetworkMessage;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.SunSpiritDeath;
import turniplabs.halplibe.helper.network.NetworkHandler;

import static teamport.aether.AetherMod.LOGGER;

@Environment(EnvType.SERVER)
public class AetherServer implements DedicatedServerModInitializer {

    public static void onPlayerJoinedServer(PlayerServer player) {
        NetworkHandler.sendToPlayer(
                player,
                new SunspiritDeathNetworkMessage(SunSpiritDeath.isDead(), SunSpiritDeath.getDeathTime())
        );
        NetworkHandler.sendToPlayer(player, new EffectSyncNetworkMessage(player));
        NetworkHandler.sendToPlayer(player, new ExtraHealthSyncNetworkMessage(player));
    }

    @Override
    public void onInitializeServer() {
        AetherCommand.registerServerCommands();
        LOGGER.info("AetherMod server initialized.");
    }
}
