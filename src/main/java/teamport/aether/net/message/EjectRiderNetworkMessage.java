package teamport.aether.net.message;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClientMP;
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

    @Environment(EnvType.CLIENT)
    protected Entity getEntityByID(int i) {
        Minecraft mc = Minecraft.getMinecraft();
        return i == mc.thePlayer.id ? mc.thePlayer : ((WorldClientMP)mc.currentWorld).getEntityFromId(i);
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        getEntityByID(vehicle).ejectRider();
    }
}
