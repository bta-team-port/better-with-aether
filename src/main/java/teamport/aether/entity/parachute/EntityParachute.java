package teamport.aether.entity.parachute;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityParachute extends Entity {
    public EntityParachute(@Nullable World world) {
        super(world);
    }

    public boolean makeStepSound() {
        return false;
    }

    public void tick() {
        this.checkOnWater(true);
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
        this.yd *= 0.8;
        this.zd *= 0.98;
        if (this.onGround) {
            this.remove();
        }
    }

    protected void causeFallDamage(float distance) {
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
