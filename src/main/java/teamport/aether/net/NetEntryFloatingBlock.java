package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.floating_block.EntityFloatingBlock;

public class NetEntryFloatingBlock implements IVehicleEntry<EntityFloatingBlock>, ITrackedEntry<EntityFloatingBlock> {
    public NetEntryFloatingBlock() {
    }

    public @NonNull Class<EntityFloatingBlock> getAppliedClass() {
        return EntityFloatingBlock.class;
    }

    public int getTrackingDistance() {
        return 160;
    }

    public int getMovementPacketDelay() {
        return 20;
    }

    public boolean sendMotionUpdates() {
        return true;
    }

    @Override
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, EntityFloatingBlock object) {
    }

    public Entity getEntity(World world, double x, double y, double z, int metadata, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag tag) {
        if (metadata < 0 || metadata > Blocks.highestBlockId) return null;
        Block<?> block = Blocks.getBlock(metadata);
        if (block == null) return null;

        EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, x, y, z, block.id(), 0, null);
        if (tag != null) floatingBlock.readAdditionalSaveData(tag);

        return floatingBlock;
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry tracker, EntityFloatingBlock trackedObject) {
        CompoundTag tag = new CompoundTag();
        trackedObject.addAdditionalSaveData(tag);
        return new PacketAddEntity(trackedObject, -1, -1, null, null, null, tag);
    }
}
