package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.vehicle.parachute.EntityParachute;

public class NetEntryParachute implements IVehicleEntry<EntityParachute>, ITrackedEntry<EntityParachute> {
    @Override
    public Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        return new EntityParachute(world);
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry entityTrackerEntry, EntityParachute tracked) {
        return new PacketAddEntity(tracked);
    }

    @Override
    public @NotNull Class<? extends EntityParachute> getAppliedClass() {
        return EntityParachute.class;
    }

    @Override
    public int getTrackingDistance() {
        return 12 * 16;
    }

    @Override
    public int getPacketDelay() {
        return 1;
    }

    @Override
    public boolean sendMotionUpdates() {
        return true;
    }

    @Override
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, EntityParachute object) {
    }
}
