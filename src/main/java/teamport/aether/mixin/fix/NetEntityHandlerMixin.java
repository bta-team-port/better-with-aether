package teamport.aether.mixin.fix;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.entity.*;
import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(value = NetEntityHandler.class, remap = false)
public abstract class NetEntityHandlerMixin {

    @Shadow
    private static boolean isListDirty;

    @Shadow
    private static void sortEntries() {}

    @Shadow
    @Final
    private static List<ITrackedEntry<?>> trackedEntries;

    @Shadow
    @Final
    private static Map<IVehicleEntry<?>, Integer> providerToTypeMap;

    @Shadow
    @Final
    private static List<IPacketEntry<?>> packetEntries;

    @Inject(method = "getTrackedEntry", at=@At("HEAD"), cancellable = true)
    private static void getTrackedEntry(Object entity, CallbackInfoReturnable<ITrackedEntry<?>> cir) {
        if (isListDirty) sortEntries();

        ITrackedEntry<?> nearMatch = null;

        for (ITrackedEntry<?> tracked : trackedEntries) {

            if (tracked.getAppliedClass().equals(entity.getClass())) {
                cir.setReturnValue(tracked);
                return;
            }

            if (tracked.getAppliedClass().isAssignableFrom(entity.getClass())) {
                nearMatch = tracked;
            }
        }

        cir.setReturnValue(nearMatch);
    }


    @Inject(method = "getSpawnPacket", at=@At("HEAD"), cancellable = true)
    private static void getSpawnPacket(EntityTrackerEntry trackerEntry, CallbackInfoReturnable<Packet> cir) {
        Packet packet;

        IPacketEntry<Entity> nearMatch = null;
        IPacketEntry<Entity> match = null;

        for (IPacketEntry<?> netEnt : packetEntries) {
            Class<Entity> entityClass = (Class<Entity>) trackerEntry.getTrackedEntity().getClass();

            if (netEnt.getAppliedClass().equals(entityClass)) {
                match = (IPacketEntry<Entity>) netEnt;
                break;
            }

            if (netEnt.getAppliedClass().isAssignableFrom(entityClass)) {
                nearMatch = (IPacketEntry<Entity>) netEnt;
            }
        }

        if (match == null) {
            if (nearMatch == null) {
                throw new IllegalArgumentException("Don't know how to add " + trackerEntry.getTrackedEntity().getClass() + "!");
            }
            match = nearMatch;
        }

        if (match instanceof IVehicleEntry) {
            packet = ((IVehicleEntry)match).getSpawnPacket(trackerEntry, trackerEntry.getTrackedEntity()).setType(providerToTypeMap.get(match));
        }
        else {
            packet = match.getSpawnPacket(trackerEntry, trackerEntry.getTrackedEntity());
        }

        cir.setReturnValue(packet);
    }

}
