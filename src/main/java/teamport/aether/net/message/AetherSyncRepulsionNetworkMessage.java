package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.items.AetherRepulsion;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.UUID;

public class AetherSyncRepulsionNetworkMessage implements NetworkMessage {
    private UUID playerUUID;
    private boolean repulsion;

    public AetherSyncRepulsionNetworkMessage() {}

    public AetherSyncRepulsionNetworkMessage(Player player) {
        this.playerUUID = player.uuid;
        this.repulsion = ((AetherRepulsion) player).aether$isRepulse();
    }
    @Override
    public void encodeToUniversalPacket(@NotNull UniversalPacket packet) {
        packet.writeUUID(playerUUID);
        packet.writeBoolean(repulsion);
    }

    @Override
    public void decodeFromUniversalPacket(@NotNull UniversalPacket packet) {
        playerUUID = packet.readUUID();
        repulsion = packet.readBoolean();
    }
    @Override
    public void handleClientEnv(NetworkContext context) {
        World world = context.player.world;

        if (world != null) world.players.stream()
            .filter(p -> p.uuid.equals(playerUUID))
            .forEach(p -> ((AetherRepulsion) p).aether$SyncRepulsion(repulsion));
    }
}
