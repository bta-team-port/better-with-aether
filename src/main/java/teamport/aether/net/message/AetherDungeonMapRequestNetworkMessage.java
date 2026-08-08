package teamport.aether.net.message;

import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class AetherDungeonMapRequestNetworkMessage implements NetworkMessage {
    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) { }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) { }

    @Override
    public void handleServerEnv(NetworkContext context) {
        if (context.player != null) {
            NetworkHandler.sendToPlayer(context.player, new AetherDungeonMapUpdateNetworkMessage(context.player));
        }
    }
}
