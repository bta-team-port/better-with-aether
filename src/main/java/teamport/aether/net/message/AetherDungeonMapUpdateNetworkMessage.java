package teamport.aether.net.message;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.world.feature.util.map.DungeonMap;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class AetherDungeonMapUpdateNetworkMessage implements NetworkMessage {
    private ListTag entries = new ListTag();

    public AetherDungeonMapUpdateNetworkMessage() { }

    public AetherDungeonMapUpdateNetworkMessage(Player player) {
        this.entries = DungeonMap.serializeListFor(player);
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        CompoundTag tag = new CompoundTag();
        tag.putList(AetherMod.MOD_ID + ".dungeons", entries);
        packet.writeCompoundTag(tag);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        entries = packet.readCompoundTag().getList(AetherMod.MOD_ID + ".dungeons");
    }

    @Override
    public void handleClientEnv(NetworkContext context) {
        DungeonMap.updateListCache(entries);
    }
}
