//package teamport.aether.net;
//
//import com.mojang.nbt.tags.CompoundTag;
//import net.minecraft.core.entity.Entity;
//import org.jetbrains.annotations.NotNull;
//import turniplabs.halplibe.helper.network.NetworkMessage;
//import turniplabs.halplibe.helper.network.UniversalPacket;
//
//public class DeathMessage implements NetworkMessage {
//    Entity killer;
//
//    @Override
//    public void encodeToUniversalPacket(@NotNull UniversalPacket universalPacket) {
//        CompoundTag tag = new CompoundTag();
//        killer.save(tag);
//        universalPacket.write;
//    }
//
//    @Override
//    public void decodeFromUniversalPacket(@NotNull UniversalPacket universalPacket) {
//
//    }
//
//    @Override
//    public void handle(NetworkContext networkContext) {
//
//    }
//}
