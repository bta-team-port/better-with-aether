package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.ILifeShard;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class AetherSyncLifeShardMessage implements NetworkMessage {

    byte lifeShardUsed;

    public AetherSyncLifeShardMessage(){}

    public AetherSyncLifeShardMessage(byte lifeShardUsed){
        this.lifeShardUsed = lifeShardUsed;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeByte(this.lifeShardUsed);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        this.lifeShardUsed = packet.readByte();
    }

    @Override
    public void handle(NetworkContext context) {
        if (EnvironmentHelper.isMultiplayerServer()) return;
        ((ILifeShard)context.player).better_with_aether$setLifeshardUsed(this.lifeShardUsed);
    }
}
