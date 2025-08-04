package teamport.aether.entity.projectile;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class ProjectileHammerHead extends Projectile implements ProjectileAether {

    public ProjectileHammerHead(World world, Mob owner) {
        super(world, owner);
    }
    public void initProjectile() {
        this.damage = 10;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 0.99F;
    }

    public ProjectileHammerHead(World world, double x, double y, double z) {
        super(world);
        this.setPos(x, y, z);
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.entity != null) {
            hitResult.entity.hurt(this.owner, this.damage, DamageType.COMBAT);
            doEffect();
            this.remove();
        }

        if (hitResult.hitType == HitResult.HitType.TILE) {
            doEffect();
            this.remove();
        }

    }

    public void doEffect() {
        world.playSoundAtEntity(null, this, "random.explode", 0.5F, 0.5F / (this.world.rand.nextFloat() * 0.4F + 0.8F));
        for (int j = 0; j < 8; ++j) {
            this.world.spawnParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
            this.world.spawnParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
            this.world.spawnParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0,0);
        }
    }

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner, @Nullable CompoundTag compoundTag) {
        ProjectileHammerHead hammer = new ProjectileHammerHead(world, x, y, z);
        if (hasVelocity) hammer.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) hammer.owner = (Mob) owner;
        return hammer;
    }
}
