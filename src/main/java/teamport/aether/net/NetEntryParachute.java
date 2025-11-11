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

import static teamport.aether.AetherMod.LOGGER;

public class NetEntryParachute implements IVehicleEntry<EntityParachute>, ITrackedEntry<EntityParachute> {
    @Override
    public Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {

        Class<? extends EntityParachute> parachuteClass = (meta >>> 24) > 0 ? EntityParachuteGold.class : EntityParachute.class;

        EntityParachute parachute;
        try {
            parachute = parachuteClass.getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            LOGGER.error("Failed to spawn parachute cloud!");
            throw new RuntimeException(e);
        }

        parachute.moveTo(x, y, z, 0, 0);
        return parachute;
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry entityTrackerEntry, EntityParachute tracked) {
        PacketAddEntity packet = new PacketAddEntity(tracked);
        packet.metaData = (tracked instanceof EntityParachuteGold ? 1 : 0) << 24;
        return packet;
    }

    @Override
    public @NonNull Class<? extends EntityParachute> getAppliedClass() {
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
        return false;
    }

    @Override
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, EntityParachute object) {
    }
}
