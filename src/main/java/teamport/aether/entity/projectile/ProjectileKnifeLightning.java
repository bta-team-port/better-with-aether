package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.items.AetherItems;

public class ProjectileKnifeLightning extends Projectile implements ProjectileAether, AetherProjectileDeathMessages {

    @SuppressWarnings("unused")
    public ProjectileKnifeLightning(World world) {
        super(world);
    }

    public ProjectileKnifeLightning(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.modelItem = AetherItems.TOOL_KNIFE_LIGHTNING;
    }

    public ProjectileKnifeLightning(World world, Player owner) {
        super(world, owner);
    }

    @Override
    public void initProjectile() {
        this.damage = 6;
        this.defaultGravity = 0.03F;
        this.defaultProjectileSpeed = 0.99F;
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (this.world == null) return;
        if (hitResult.entity != null) {
            hitResult.entity.hurt(this.owner, this.damage, AetherMod.LIGHTNING);
            if (!world.isClientSide) {
                world.entityJoinedWorld(new EntityLightning(hitResult.entity.world, hitResult.entity.x, hitResult.entity.y, hitResult.entity.z));
            }

            doEffect();
            this.remove();
        }

        if (hitResult.hitType == HitResult.HitType.TILE) {
            if (!world.isClientSide) {
                world.entityJoinedWorld(
                    new EntityLightning(
                        world,
                        (double) hitResult.x + hitResult.side.getOffsetX(),
                        (double) hitResult.y + hitResult.side.getOffsetY(),
                        (double) hitResult.z + hitResult.side.getOffsetZ()
                    )
                );
            }

            doEffect();
            this.remove();
        }
    }

    public void doEffect() {
        if (this.world == null) return;
        for (int j = 0; j < 8; ++j) {
            ParticleMaker.spawnParticle(world,
                "item",
                this.x, this.y, this.z,
                world.rand.nextDouble(),
                world.rand.nextDouble(),
                world.rand.nextDouble(),
                AetherItems.TOOL_KNIFE_LIGHTNING.id
            );
        }

        for (int j = 0; j < 16; j++) {
            ParticleMaker.spawnParticle(world,
                "lightning",
                this.x, this.y, this.z,
                world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1),
                world.rand.nextDouble() * 0.25F * -1,
                world.rand.nextDouble() * 0.25F * (world.rand.nextBoolean() ? -1 : 1),
                0
            );
        }
    }

    @SuppressWarnings("unused")
    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        ProjectileKnifeLightning knife = new ProjectileKnifeLightning(world, x, y, z);
        if (hasVelocity) knife.setHeading(xd, yd, zd, 1, 0);
        if (owner instanceof Mob) knife.owner = (Mob) owner;
        return knife;
    }
}
