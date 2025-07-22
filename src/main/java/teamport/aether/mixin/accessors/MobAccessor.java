package teamport.aether.mixin.accessors;

import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Mob.class, remap = false)
public interface MobAccessor {

    @Accessor("moveForward")
    float getForwardVelocity();

    @Accessor("moveStrafing")
    float getHorizontalVelocity();

    @Accessor("moveForward")
    void setForwardVelocity(float v);

    @Accessor("moveStrafing")
    void setHorizontalVelocity(float v);

    @Accessor("isJumping")
    boolean getJumping();

    @Accessor("prevHealth")
    void setPrevHealth(int i);

}
