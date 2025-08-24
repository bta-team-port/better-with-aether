package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import net.minecraft.server.entity.player.PlayerServer;
import org.jetbrains.annotations.NotNull;
import teamport.aether.helper.HealthHelper;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class CommandExtraHealthMessage implements NetworkMessage {
    int health;

    public CommandExtraHealthMessage(){}

    public CommandExtraHealthMessage(int health){
        this.health = health;
    }

    @Override
    public void encodeToUniversalPacket(@NotNull UniversalPacket packet) {
        packet.writeInt(health);
    }

    @Override
    public void decodeFromUniversalPacket(@NotNull UniversalPacket packet) {
        this.health = packet.readInt();
    }

    @Override
    public void handle(NetworkContext context) {
        if (EnvironmentHelper.isClientWorld()) {
            HealthHelper.setExtraHealth(context.player,this.health);
        }
    }
}
