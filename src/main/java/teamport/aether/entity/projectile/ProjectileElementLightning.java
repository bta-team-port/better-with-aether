package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;

import java.util.List;

public class ProjectileElementLightning extends ProjectileElementBase implements AetherProjectileDeathMessages<ProjectileElementLightning> {
    private Mob target;
    private static final float homingPower = 0.1F;
    private static final float topSpeed = 0.5F;

    public ProjectileElementLightning(World world) {
        super(world);
    }

    public ProjectileElementLightning(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    public ProjectileElementLightning(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.initProjectile();
    }

    @Override
    public void initProjectile() {
        super.initProjectile();
        this.damage = 4;
        this.defaultGravity = 0.0F;
        this.defaultProjectileSpeed = 1.0F;
        this.setSize(0.5F, 1.0F);
    }

    @Override
    public void tick() {
        for (int j = 0; j < 2; j++) {
            world.spawnParticle("lightning", this.x, this.y + 0.5, this.z, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), world.rand.nextFloat() * 0.25F * -1, world.rand.nextFloat() * 0.25F * (world.rand.nextBoolean() ? -1 : 1), 0);
        }

        ++this.ticksInAir;
        if (ticksInAir > 100) {
            remove();
            this.world.spawnParticle("explode", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0,0);
            this.world.spawnParticle("smoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0,0);
            this.world.spawnParticle("largesmoke", this.x, this.y + 1, this.z, 0.0, 0.0, 0.0,0);
            world.playSoundAtEntity(null, this, "mob.ghast.fireball", 1.0F, (random.nextFloat() * 1.4F + 1.8F));

        }

        if (this.target == null || !this.target.isAlive()) {
            AABB searchBox = AABB.getPermanentBB(this.x - 16.0, this.y - 16.0, this.z - 16.0, this.x + 16.0, this.y + 16.0, this.z + 16.0);
            List<Mob> entities = this.world.getEntitiesWithinAABB(Mob.class, searchBox);
            Player closestPlayer = null;
            for (Mob entity : entities) {
                if (entity instanceof Player && entity.isAlive()) {
                    double distance = this.distanceTo(entity);
                    if (distance < 32.0f) {
                        closestPlayer = (Player) entity;
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
                double targetXd = dx / dist * topSpeed;
                double targetYd = dy / dist * topSpeed;
                double targetZd = dz / dist * topSpeed;
                this.xd += (targetXd - this.xd) * homingPower;
                this.yd += (targetYd - this.yd) * homingPower;
                this.zd += (targetZd - this.zd) * homingPower;
                double speed = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
                if (speed > topSpeed) {
                    this.xd = this.xd / speed * topSpeed;
                    this.yd = this.yd / speed * topSpeed;
                    this.zd = this.zd / speed * topSpeed;
                }
            }
        }

        super.tick();

        this.yRot = (float) (Math.atan2(this.xd, this.zd) * 180.0 / Math.PI);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (!(hitResult.entity instanceof MobBossValkyrie || hitResult.entity instanceof ProjectileElementBase)) {
                if (hitResult.entity instanceof MobCreeper || hitResult.entity instanceof MobPig) {
                    EntityLightning bolt = new EntityLightning(world, x, y, z);
                    world.entityJoinedWorld(bolt);
                    this.remove();
                    return;
                }

                if (hitResult.entity instanceof Mob) {
                    hitResult.entity.hurt(this, this.damage, DamageType.GENERIC);
                    this.remove();
                    this.world.playSoundEffect(target, SoundCategory.ENTITY_SOUNDS, this.x, this.y, this.z, "ambient.weather.thunder", 0.5F, 0.8F + this.random.nextFloat() * 0.2F);
                    return;
                }
            }
        }

        super.onHit(hitResult);
    }

    public boolean hurt(Entity entity, int damage, DamageType type) {
        return false;
    }

}
