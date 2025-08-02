package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.sunspirit.MobBossSunspirit;

public class ProjectileElementFire extends ProjectileElementBase {

    public ProjectileElementFire(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
    }

    @Override
    public void tick() {
        super.tick();
        ticksLived++;

        int maxBounces = 10;
        int maxTicks = 600;
        if (!this.world.isClientSide && (ticksLived > maxTicks || bounceCount >= maxBounces)) {
            this.remove();
            return;
        }

        if (this.world.isClientSide) {
            this.world.spawnParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0, 0);
        }
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (hitResult.entity instanceof MobBossSunspirit) {
                this.remove();
            } else if (hitResult.entity instanceof Mob) {
                hitResult.entity.hurt(this.owner, this.damage, DamageType.FIRE);
                hitResult.entity.remainingFireTicks = 100;
                this.remove();
                return;
            }

            if (hitResult.side != null) {
                switch (hitResult.side) {
                    case BOTTOM:
                    case TOP:
                        this.yd = -this.yd * 1.0F;
                        break;
                    case NORTH:
                    case SOUTH:
                        this.zd = -this.zd * 1.0F;
                        break;
                    case WEST:
                    case EAST:
                        this.xd = -this.xd * 1.0F;
                        break;
                }
                bounceCount++;
            }
        }
    }

    public boolean hurt(Entity entity, int damage, DamageType type) {
        return false;
    }

}
