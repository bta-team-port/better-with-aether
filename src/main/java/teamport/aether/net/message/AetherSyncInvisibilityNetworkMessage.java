package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.items.accessory.AetherInvisibility;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.UUID;

public class AetherSyncInvisibilityNetworkMessage implements NetworkMessage {

    private UUID playerUUID;
    private boolean invisibility;

    public AetherSyncInvisibilityNetworkMessage() {}

    public AetherSyncInvisibilityNetworkMessage(Player player) {
        this.playerUUID = player.uuid;
        this.invisibility = ((AetherInvisibility) player).aether$isInvisible();
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeUUID(playerUUID);
        packet.writeBoolean(invisibility);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        playerUUID = packet.readUUID();
        invisibility = packet.readBoolean();
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        World world = context.player.world;

        world.players.stream()
            .filter(p -> p.uuid.equals(playerUUID))
            .forEach(p -> ((AetherInvisibility) p).aether$SyncVisibility(invisibility));
    }
}
