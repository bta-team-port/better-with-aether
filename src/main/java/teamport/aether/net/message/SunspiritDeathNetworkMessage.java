package teamport.aether.net.message;

import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.world.SunSpiritDeath;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class SunspiritDeathNetworkMessage implements NetworkMessage {
    private boolean isDead;
    private long timestamp;

    public SunspiritDeathNetworkMessage() {
    }

    public SunspiritDeathNetworkMessage(boolean isDead, long timestamp) {
        this.isDead = isDead;
        this.timestamp = timestamp;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeBoolean(isDead);
        packet.writeLong(timestamp);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        isDead = packet.readBoolean();
        timestamp = packet.readLong();
    }

    @Override
    public void handle(NetworkContext networkContext) {
        if (EnvironmentHelper.isClientWorld()) {
            AetherMod.LOGGER.info("Received SunspiritDeathNetworkMessage.");
            SunSpiritDeath.setDead(isDead);
            SunSpiritDeath.setDeathTime(timestamp);
        }
    }
}
