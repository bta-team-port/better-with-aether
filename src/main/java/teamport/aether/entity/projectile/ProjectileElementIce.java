package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;

public class ProjectileElementIce extends ProjectileElementBase {
    public ProjectileElementIce(World world) {
        super(world);
    }

    public boolean hasBeenHitByPlayer = false;

    public ProjectileElementIce(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
        this.initialSpeed = 0.25F;
    }

    public ProjectileElementIce(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public boolean hurt(Entity entity, int damage, DamageType type) {
        if (!this.world.isClientSide) {
            if (entity instanceof Player) {
                hasBeenHitByPlayer = true;
            }
        }

        return super.hurt(entity, damage, type);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (!(hitResult.entity instanceof ProjectileElementBase)) {
                if (hitResult.entity instanceof MobBossSunspirit) {
                    if (hasBeenHitByPlayer) {
                        hitResult.entity.hurt(this, this.damage, DamageType.GENERIC);
                        this.remove();
                    } else {
                        super.onHit(hitResult);
                    }

                } else if (hitResult.entity instanceof Mob) {
                    hitResult.entity.hurt(this.owner, this.damage, DamageType.GENERIC);
                    this.remove();
                }
            }
        }

        super.onHit(hitResult);
    }

}
