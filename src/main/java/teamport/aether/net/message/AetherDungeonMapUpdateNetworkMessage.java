package teamport.aether.net.message;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.AetherMod;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.*;

public class AetherDungeonMapUpdateNetworkMessage implements NetworkMessage {

    private UUID playerUUID;

    @Nullable
    private ListTag entriesReceived = null;

    public AetherDungeonMapUpdateNetworkMessage() {}

    public AetherDungeonMapUpdateNetworkMessage(UUID uuid) {
        playerUUID = uuid;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        if (EnvironmentHelper.isServerEnvironment()) {
            PlayerList playerList = MinecraftServer.getInstance().playerList;
            Player player = playerList.playerEntities.stream()
                .filter(p -> p.uuid.compareTo(playerUUID) == 0)
                .findFirst()
                .orElse(null);

            if (player == null) {
                packet.writeInt(0);
                return;
            }

            packet.writeInt(1);

            CompoundTag tag = new CompoundTag();
            tag.putList(AetherMod.MOD_ID+".dungeons", DungeonMap.serializeListFor(player));
            packet.writeCompoundTag(tag);
            return;
        }

        if (EnvironmentHelper.isClientWorld()) {
            packet.writeString(Minecraft.getMinecraft().thePlayer.uuid.toString());
        }
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        if (EnvironmentHelper.isServerEnvironment()) {
            this.playerUUID = UUID.fromString(packet.readString());
        }

        else {
            if (packet.readInt() == 0) return;

            CompoundTag tag = packet.readCompoundTag();
            entriesReceived = tag.getList(AetherMod.MOD_ID+".dungeons");
        }
    }

    @Override
    public void handle(NetworkContext context) {
        if (EnvironmentHelper.isServerEnvironment()) {
            if (context.player == null) return;
            NetworkHandler.sendToPlayer(context.player, new AetherDungeonMapUpdateNetworkMessage(context.player.uuid));
            return;
        }

        if (EnvironmentHelper.isClientWorld()) {
            if (entriesReceived != null) {
                DungeonMap.updateListCache(entriesReceived);
            }
        }
    }
}
