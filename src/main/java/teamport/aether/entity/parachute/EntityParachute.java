package teamport.aether.entity.parachute;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.mixin.accessors.MobAccessor;

public class EntityParachute extends Mob {
    public EntityParachute(@Nullable World world) {
        super(world);
        setSize(1.0f, 1.0f);
    }

    public boolean makeStepSound() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        double x = this.x + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double y = this.bb.minY - 0.5 + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        double z = this.z + ((EntityAccessor) this).getRandom().nextDouble() * 0.75 * 2.0 - 0.75;
        world.spawnParticle("explode", x, y, z, 0.0, 0.0, 0.0, 0);

        if (this.passenger == null) {
            this.remove();
        }

        this.move(this.xd, this.yd, this.zd);
        if (this.yd < -0.2) {
            this.yd *= 0.5F;
        }

        this.xd *= 0.9F;
        this.zd *= 0.9F;

        if (this.onGround) {
            this.ejectRider();
            this.remove();
        }
    }

    public void updateAI() {
        if (!this.world.isClientSide && this.passenger != null) {
            this.moveSpeed = 0.0F;
            this.moveStrafing = 0.0F;
            this.passenger.fallDistance = 0.0F;
            Player player = (Player) this.passenger;
            float f = 3.141593F;
            float f1 = f / 180.0F;

            float forward = ((MobAccessor) player).getForwardVelocity();
            float strafe = ((MobAccessor) player).getHorizontalVelocity();

            if (Math.abs(forward) > 0.1F || Math.abs(strafe) > 0.1F) {
                float f5 = player.yRot * f1;
                float moveX = (float) ((-forward * Math.sin(f5) + strafe * Math.cos(f5)) * 0.6F / 6.0F);
                float moveZ = (float) ((forward * Math.cos(f5) + strafe * Math.sin(f5)) * 0.6F / 6.0F);

                float magnitude = (float)Math.sqrt(moveX * moveX + moveZ * moveZ);
                if (magnitude > 0.1F) {
                    moveX /= magnitude;
                    moveZ /= magnitude;
                    moveX *= 0.6F / 6.0F;
                    moveZ *= 0.6F / 6.0F;
                }

                this.xd = this.xd * 0.6F + moveX * 0.4F;
                this.zd = this.zd * 0.6F + moveZ * 0.4F;
            }

            double speed = Math.sqrt(this.xd * this.xd + this.zd * this.zd);
            if (speed > 0.65F) {
                double factor = 0.65F / speed;
                this.xd *= factor;
                this.zd *= factor;
            }
        }
    }

    public double getRideHeight() {
        return this.bbHeight + 0.2f;
    }

    public void causeFallDamage(float distance) {
    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {
        return false;
    }

}