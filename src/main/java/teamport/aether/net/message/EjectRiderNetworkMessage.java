package teamport.aether.net.message;

import net.minecraft.core.entity.Entity;
import org.jspecify.annotations.NonNull;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class EjectRiderNetworkMessage implements NetworkMessage {
    int vehicle = 0;

    public EjectRiderNetworkMessage() {}

    public EjectRiderNetworkMessage(Entity vehicle) {
        this.vehicle = vehicle.id;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeInt(vehicle);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        vehicle = packet.readInt();
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        if (context.player == null || context.player.world == null) return;
        Entity entity = context.player.id == vehicle
            ? context.player
            : context.player.world.getEntityByID(vehicle);
        if (entity != null) entity.ejectRider();
    }
}
