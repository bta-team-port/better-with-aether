package teamport.aether.entity.projectile;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;

public class ProjectileElementIce extends ProjectileElementBase {
    public ProjectileElementIce(World world) {
        super(world);
    }

    public ProjectileElementIce(World world, Mob owner) {
        super(world, owner);
        this.initProjectile();
        this.initialSpeed = 0.25F;
    }

    public ProjectileElementIce(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void onHit(HitResult hitResult) {
        if (!this.world.isClientSide) {
            if (ticksLived < 40) {
                if (hitResult.entity instanceof ProjectileElementBase) {
                } else if (hitResult.entity instanceof Mob) {
                    hitResult.entity.hurt(this.owner, this.damage, DamageType.GENERIC);
                    this.remove();
                    return;
                }
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

}
