package teamport.aether.net;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.net.entity.EntityTracker;
import net.minecraft.core.net.entity.EntityTrackerEntry;
import net.minecraft.core.net.entity.ITrackedEntry;
import net.minecraft.core.net.entity.IVehicleEntry;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.entity.projectile.*;

import java.util.HashMap;
import java.util.Map;

public class NetEntryAetherProjectile implements IVehicleEntry<ProjectileAether>, ITrackedEntry<ProjectileAether> {
    public static final int TYPE_BITS = 0xff00_0000;
    public static final Map<Integer, ProjectileAether.ConstructorLambda> idToConstructor = new HashMap<>();
    public static final Map<Class<?>, Integer> classToId = new HashMap<>();

    static {
        register(0, ProjectileArrowFlaming::getEntity, ProjectileArrowFlaming.class);
        register(1, ProjectileDart::getEntity, ProjectileDart.class);
        register(2, ProjectileKnifeLightning::getEntity, ProjectileKnifeLightning.class);
        register(3, ProjectileHammerHead::getEntity, ProjectileHammerHead.class);
        register(4, ProjectileWindball::getEntity, ProjectileWindball.class);
        register(5, ProjectileNeedle::getEntity, ProjectileNeedle.class);
        register(6, ProjectileElementLightning::getEntity, ProjectileElementLightning.class);
        register(7, ProjectileElementFire::getEntity, ProjectileElementFire.class);
        register(8, ProjectileElementIce::getEntity, ProjectileElementIce.class);
    }

    public static void register(int id, ProjectileAether.ConstructorLambda constructor, Class<?> clazz) {
        idToConstructor.put(id, constructor);
        classToId.put(clazz, id);
    }

    public static int getIdBits(ProjectileAether projectile) {
        return classToId.get(projectile.getClass()) << 24;
    }

    @Override
    public PacketAddEntity getSpawnPacket(EntityTrackerEntry entityTrackerEntry, ProjectileAether tracked) {
        PacketAddEntity packet = tracked.getSpawnPacket((Projectile) tracked);
        packet.metaData |= getIdBits(tracked);
        return packet;
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
    public void onEntityTracked(EntityTracker entityTracker, EntityTrackerEntry entityTrackerEntry, ProjectileAether tracked) {
    }

    @Override
    public @NonNull Class<? extends ProjectileAether> getAppliedClass() {
        return ProjectileAether.class;
    }

    @Override
    public Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileAether.ConstructorLambda projectile = idToConstructor.get(meta >>> 24);
        return projectile.getEntity(world, x, y, z, meta & ~TYPE_BITS, hasVelocity, xd, yd, zd, owner, compoundTag);
    }
}
