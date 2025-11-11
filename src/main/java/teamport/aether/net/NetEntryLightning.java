package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class NetEntryLightning implements IVehicleEntry<EntityLightning>, ITrackedEntry<EntityLightning> {
    @Override
    public Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        return new EntityLightning(world, x, y, z);
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry entityTrackerEntry, EntityLightning tracked) {
        return new PacketAddEntity(tracked);
    }

    @Override
    public @NotNull Class<? extends EntityLightning> getAppliedClass() {
        return EntityLightning.class;
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
        return false;
    }

    @Override
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, EntityLightning object) {
    }
}
