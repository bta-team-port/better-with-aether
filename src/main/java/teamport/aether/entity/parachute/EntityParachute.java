package teamport.aether.entity.parachute;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
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

    public void tick() {
        this.checkOnWater(true);
        this.pushTime *= 0.98F;
        if (this.pushTime < 0.05F || (double)this.pushTime < 0.25 && this.onGround) {
            this.pushTime = 0.0F;
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.yd -= 0.04;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98;
        this.yd *= 0.1;
        this.zd *= 0.98;

        if (this.onGround) {
            this.ejectRider();
            this.remove();
        }
        super.tick();
    }

    public boolean interact(@NotNull Player player) {
        player.startRiding(this);
        return true;
    }

    public void updateAI() {
        if (!this.world.isClientSide) {
            if (this.passenger != null && this.passenger instanceof Player) {
                this.moveSpeed = 0.0F;
                this.moveStrafing = 0.0F;
                ((EntityAccessor) this.passenger).setFallDistance(0.0F);
                Player mob = (Player) this.passenger;
                float f = 3.141593F;
                float f1 = f / 180.0F;
                float f5;
                float capSpeed = 16;
                if (((MobAccessor) mob).getForwardVelocity() > 0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (((MobAccessor) mob).getForwardVelocity() * -Math.sin(f5) * 0.17499999701976776) / capSpeed;
                    this.zd += (((MobAccessor) mob).getForwardVelocity() * Math.cos(f5) * 0.17499999701976776) / capSpeed;
                } else if (((MobAccessor) mob).getForwardVelocity() < -0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (((MobAccessor) mob).getForwardVelocity() * -Math.sin(f5) * 0.17499999701976776) / capSpeed;
                    this.zd += (((MobAccessor) mob).getForwardVelocity() * Math.cos(f5) * 0.17499999701976776) / capSpeed;
                }

                if (((MobAccessor) mob).getHorizontalVelocity() > 0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f5) * 0.17499999701976776) / capSpeed;
                    this.zd += (((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f5) * 0.17499999701976776) / capSpeed;
                } else if (((MobAccessor) mob).getHorizontalVelocity() < -0.1F) {
                    f5 = mob.yRot * f1;
                    this.xd += (((MobAccessor) mob).getHorizontalVelocity() * Math.cos(f5) * 0.17499999701976776) / capSpeed;
                    this.zd += (((MobAccessor) mob).getHorizontalVelocity() * Math.sin(f5) * 0.17499999701976776) / capSpeed;
                }

                double d = Math.abs(Math.sqrt(this.xd * this.xd + this.zd * this.zd));
                if (d > 0.375) {
                    double d1 = 0.375 / d;
                    this.xd *= d1 / capSpeed;
                    this.zd *= d1 / capSpeed;
                }

            } else {
                super.updateAI();
            }
        }
    }

    public double getRideHeight() {
        return this.bbHeight + 0.2f;
    }

    public void causeFallDamage(float distance) {
    }

    @Override
    public void defineSynchedData() {

    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {

    }
}
