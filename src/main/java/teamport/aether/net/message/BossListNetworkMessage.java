package teamport.aether.net.message;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.boss.AetherBossList;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.Optional;

import static teamport.aether.AetherGlobals.LOGGER;

public class BossListNetworkMessage implements NetworkMessage {
    private Type type;
    private int entityID;
    public enum Type {
        CLEAR,
        ADD,
        REMOVE
    }

    public static @NonNull BossListNetworkMessage clear() {
        BossListNetworkMessage e = new BossListNetworkMessage();
        e.type = Type.CLEAR;
        e.entityID = -1;

        return e;
    }

    public BossListNetworkMessage() {
    }

    public BossListNetworkMessage(Type type, @NonNull Entity entity) {
        this.type = type;
        this.entityID = entity.id;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeInt(type.ordinal());
        packet.writeInt(entityID);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        this.type = Type.values()[packet.readInt()];
        this.entityID = packet.readInt();
    }

    @Override
    public void handle(NetworkContext context) {
        if (!EnvironmentHelper.isMultiplayerClient()) return;

        LOGGER.info("Received BossList update message.");

        Player player = context.player;

        if (type != Type.CLEAR) {
            Optional<Entity> entityOption = player.world.getLoadedEntityList().stream().filter(e -> e.id == entityID).findFirst();

            if (entityOption.isEmpty()) {
                LOGGER.error("Couldn't find boss to add to list.");
                return;
            }

            Entity entity = entityOption.get();

            if (type == Type.ADD) ((AetherBossList) player).aether$TryAddBossList((Mob) entity);
            else ((AetherBossList) player).aether$removeFromBossList((Mob) entity);
        } else {
            ((AetherBossList) player).aether$clearBossList();
        }
    }
}
