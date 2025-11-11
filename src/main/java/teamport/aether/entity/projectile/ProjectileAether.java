package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.net.packet.PacketAddEntity;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;

public interface ProjectileAether {
    default PacketAddEntity getSpawnPacket(Projectile tracked) {
        return new PacketAddEntity(tracked, 0, tracked.owner == null ? -1 : tracked.owner.id, tracked.xd, tracked.yd, tracked.zd);
    }

    interface ConstructorLambda {
        Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag);
    }
}
