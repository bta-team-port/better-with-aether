package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.effect.api.HealthHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class ExtraHealthSyncNetworkMessage implements NetworkMessage {
    private int extraHealth;

    public ExtraHealthSyncNetworkMessage() { }

    public ExtraHealthSyncNetworkMessage(Player player) {
        this.extraHealth = HealthHelper.getExtraHealth(player);
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeInt(extraHealth);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        extraHealth = packet.readInt();
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        if (context.player != null) HealthHelper.setExtraHealth(context.player, extraHealth);
    }
}
