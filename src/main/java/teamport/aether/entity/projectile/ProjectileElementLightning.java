package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import teamport.aether.AetherMod;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.helper.ParticleMaker;

import java.util.List;

public class ProjectileElementLightning extends ProjectileElementBase implements AetherProjectileDeathMessages {
    private static final String[] PARTICLES = {"explode", "lightning", "lightning"};
    private Mob target;
    private static final float HOMING_POWER = 0.15F;
    private static final float TOP_SPEED = 0.5F;

    public static Entity getEntity(World world, double x, double y, double z, int meta, boolean hasVelocity, double xd, double yd, double zd, Entity owner) {
        return getEntity(ProjectileElementLightning.class, world, x, y, z, meta, hasVelocity, xd, yd, zd, owner);
    }

    @SuppressWarnings("unused")
    public ProjectileElementLightning(World world) {
        super(world);
        this.initProjectile();
    }

    public ProjectileElementLightning(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    @Override
    public void initProjectile() {
        super.initProjectile();
        this.damage = 4;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
        this.setSize(1.0F, 1.0F);
    }

    @Override
    public void doExplosion() {
        doExplosionHelper(world, this, PARTICLES, "aether:zap", target, SoundCategory.ENTITY_SOUNDS, 0.5F);
    }

    @Override
    public void tick() {
        if (this.world == null) return;
        for (int j = 0; j < 2; j++) {
            ParticleMaker.spawnParticle(world, "lightning", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), world.rand.nextFloat() * 0.25F * -1, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
        }

        if (ticksInAir > 100) {
            remove();
            ParticleMaker.spawnParticle(this.world, "explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            ParticleMaker.spawnParticle(this.world, "smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            ParticleMaker.spawnParticle(this.world, "largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0, 0);
            world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, (random.nextFloat() * 1.4F + 1.8F));
            return;
        }

        if (this.target == null || !this.target.isAlive()) {
            AABBdc searchBox = new AABBd(this.x - 16.0, this.y - 16.0, this.z - 16.0, this.x + 16.0, this.y + 16.0, this.z + 16.0);
            List<Mob> entities = this.world.getEntitiesWithinAABB(Mob.class, searchBox);
            Player closestPlayer = null;
            for (Mob entity : entities) {
                if (entity instanceof Player && entity.isAlive()) {
                    double distance = this.distanceTo(entity);
                    if (distance < 32.0f) {
                        if (closestPlayer == null || distance < this.distanceTo(closestPlayer)) {
                            closestPlayer = (Player) entity;
                        }
                    }
                }
            }
            this.target = closestPlayer;
        }

        if (this.target != null && this.target.isAlive()) {
            double dx = this.target.x - this.x;
            double dy = this.target.y + this.target.getHeadHeight() - this.y;
            double dz = this.target.z - this.z;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (dist > 0) {
                double targetXd = dx / dist * TOP_SPEED;
                double targetYd = dy / dist * TOP_SPEED;
                double targetZd = dz / dist * TOP_SPEED;
                this.xd += (targetXd - this.xd) * HOMING_POWER;
                this.yd += (targetYd - this.yd) * HOMING_POWER;
                this.zd += (targetZd - this.zd) * HOMING_POWER;
                double speed = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                if (speed > TOP_SPEED) {
                    this.xd = this.xd / speed * TOP_SPEED;
                    this.yd = this.yd / speed * TOP_SPEED;
                    this.zd = this.zd / speed * TOP_SPEED;
                }
            }
        }

        super.tick();
    }

    @Override
    public void onHit(HitResult hitResult) {
        Entity hitEntity = hitResult instanceof HitResult.Entity ? ((HitResult.Entity) hitResult).entity : null;
        if (this.world != null && !this.world.isClientSide) {
            if (!(hitEntity instanceof MobBossValkyrie || hitEntity instanceof ProjectileElementBase)) {
                if (hitEntity instanceof MobCreeper || hitEntity instanceof MobPig) {
                    EntityLightning bolt = new EntityLightning(world, x, y, z);
                    world.entityJoinedWorld(bolt);
                    this.remove();
                    doExplosion();
                    return;
                }

                if (hitEntity instanceof Mob) {
                    hitEntity.hurt(this.owner, this.damage, AetherMod.LIGHTNING);
                    this.remove();
                    doExplosion();
                    return;
                }
            }
        }

        super.onHit(hitResult);
    }

}
