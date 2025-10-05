package teamport.aether.net.message;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.jetbrains.annotations.NotNull;
import teamport.aether.models.DynamicTextureDungeonCompass;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMap;
import teamport.aether.world.generate.feature.dungeon.map.DungeonMapEntry;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

import java.util.*;
import java.util.stream.Collectors;

import static teamport.aether.world.generate.feature.components.WorldFeaturePoint.wfpoint;

public class AetherDungeonMapUpdateNetworkMessage implements NetworkMessage {

    private UUID playerUUID;

    private final List<DungeonMapEntry> entriesReceived = new ArrayList<>();

    public  AetherDungeonMapUpdateNetworkMessage() {}

    public  AetherDungeonMapUpdateNetworkMessage(UUID uuid) {
        playerUUID = uuid;
    }

    @Override
    public void encodeToUniversalPacket(@NotNull UniversalPacket packet) {
        if (EnvironmentHelper.isServerEnvironment()) {
            PlayerList playerList = MinecraftServer.getInstance().playerList;
            Optional<PlayerServer> player = playerList.playerEntities.stream()
                    .filter(p -> p.uuid.compareTo(playerUUID) == 0).findFirst();

            if (!player.isPresent()) {
                packet.writeInt(0);
                return;
            }

            List<DungeonMapEntry> entries = AetherDimension.dungeonMap.values().stream()
                    .filter(Objects::nonNull)
                    .filter(d -> d.getPosition() != null) // :^)
                    .filter(d -> d.getPosition().distanceTo(wfpoint(player.get())) < 300)
                    .collect(Collectors.toList());

            packet.writeInt(entries.toArray().length);

            for (DungeonMapEntry entry : entries) {
                CompoundTag tag = new CompoundTag();
                entry.writeToNBT(tag);
                packet.writeInt(entry.getId());
                packet.writeCompoundTag(tag);
            }
        }

        else {
            packet.writeString(Minecraft.getMinecraft().thePlayer.uuid.toString());
        }
    }

    @Override
    public void decodeFromUniversalPacket(@NotNull UniversalPacket packet) {
        if (EnvironmentHelper.isServerEnvironment()) {
            this.playerUUID = UUID.fromString(packet.readString());
        }

        else {
            entriesReceived.clear();

            int entriesLength = packet.readInt();
            for (int i = 0; i < entriesLength; i++) {
                int id = packet.readInt();
                CompoundTag tag = packet.readCompoundTag();
                DungeonMapEntry entry = DungeonMap.readDungeonMapEntryFromNBT(tag, id);
                entriesReceived.add(entry);
            }
        }
    }

    @Override
    public void handleServerEnv(NetworkContext context) {
        if (context.player == null) return;
        NetworkHandler.sendToPlayer(context.player, new AetherDungeonMapUpdateNetworkMessage(context.player.uuid));
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        List<DungeonMapEntry> cache = DynamicTextureDungeonCompass.entryCache;
        cache.clear();
        cache.addAll(entriesReceived);
    }
}
