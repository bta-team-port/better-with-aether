package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;

public class NetEntryParachute implements IVehicleEntry<EntityParachute>, ITrackedEntry<EntityParachute> {
    public NetEntryParachute() {
    }

    @Override
    public @NonNull Class<EntityParachute> getAppliedClass() {
        return EntityParachute.class;
    }

    @Override
    public int getTrackingDistance() {
        return 160;
    }

    @Override
    public int getMovementPacketDelay() {
        return 1;
    }

    @Override
    public boolean sendMotionUpdates() {
        return true;
    }

    @Override
    public void onEntityTracked(EntityTracker tracker, EntityTrackerEntry trackerEntry, EntityParachute trackedObject) {
    }

    @Override
    public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
        if (metadata == 1) {
            return new EntityParachuteGold(world, x, y, z);
        }
        return new EntityParachute(world, x, y, z);
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, EntityParachute trackedObject) {
        int metadata = (trackedObject instanceof EntityParachuteGold) ? 1 : 0;
        return new PacketAddEntity(trackedObject, metadata, -1, trackedObject.xd, trackedObject.yd, trackedObject.zd);
    }
}
