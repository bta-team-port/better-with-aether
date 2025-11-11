//package teamport.aether.net;
//
//import com.mojang.nbt.tags.CompoundTag;
//import net.minecraft.core.entity.Entity;
//import org.jspecify.annotations.NonNull;
//import turniplabs.halplibe.helper.network.NetworkMessage;
//import turniplabs.halplibe.helper.network.UniversalPacket;
//
//public class DeathMessage implements NetworkMessage {
//    Entity killer;
//
//    @Override
//    public void encodeToUniversalPacket(@NonNull UniversalPacket universalPacket) {
//        CompoundTag tag = new CompoundTag();
//        killer.save(tag);
//        universalPacket.write;
//    }
//
//    @Override
//    public void decodeFromUniversalPacket(@NonNull UniversalPacket universalPacket) {
//
//    }
//
//    @Override
//    public void handle(NetworkContext networkContext) {
//
//    }
//}
