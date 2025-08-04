package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.entity.*;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.entity.projectile.ProjectileDart;
import teamport.aether.entity.projectile.ProjectileDartEnchanted;

public class NetEntryDart implements IVehicleEntry<ProjectileDart>, ITrackedEntry<ProjectileDart> {

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry entityTrackerEntry, ProjectileDart tracked) {
        return new PacketAddEntity(tracked, tracked.dartType, tracked.owner == null ? -1 : tracked.owner.id, tracked.xd, tracked.yd, tracked.zd);
    }

    @Override
    public int getTrackingDistance() {
        return 64;
    }

    @Override
    public int getPacketDelay() {
        return 20;
    }

    @Override
    public boolean sendMotionUpdates() {
        return false;
    }

    @Override
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, ProjectileDart tracked) {
    }

    @Override
    public @NotNull Class<? extends ProjectileDart> getAppliedClass() {
        return ProjectileDart.class;
    }

    @Override
    public Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileDart dart = meta == 2 ? new ProjectileDartEnchanted(world, x, y, z) : new ProjectileDart(world, x, y, z, meta);
        if (owner instanceof Mob) dart.owner = (Mob) owner;
        if (hasVelocity) dart.setHeading(xd, yd, zd, 1, 0);
        return dart;
    }
}
