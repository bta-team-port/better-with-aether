package teamport.aether;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.server.entity.player.PlayerServer;
import teamport.aether.command.AetherCommand;
import teamport.aether.net.message.SunspiritDeathNetworkMessage;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.network.NetworkHandler;

import static teamport.aether.AetherMod.LOGGER;

@Environment(EnvType.SERVER)
public class AetherServer implements DedicatedServerModInitializer {

    public static void onPlayerJoinedServer(PlayerServer player, NetworkManager netManager) {
        NetworkHandler.sendToPlayer(
                player,
                new SunspiritDeathNetworkMessage(AetherDimension.sunspiritIsDead, AetherDimension.sunspiritDeathTimestamp)
        );
    }

    @Override
    public void onInitializeServer() {
        AetherCommand.registerServerCommands();
        LOGGER.info("AetherMod server initialized.");
    }
}
