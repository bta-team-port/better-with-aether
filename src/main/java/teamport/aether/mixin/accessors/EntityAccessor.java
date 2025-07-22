package teamport.aether.mixin.accessors;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(value = Entity.class, remap = false)
public interface EntityAccessor {

    @Accessor("fallDistance")
    float getFallDistance();

    @Accessor("fallDistance")
    void setFallDistance(float fallDistance);

    @Accessor("random")
    Random getRandom();

    // later for to suppress onFire
    @Accessor("fireImmune")
    boolean getFireImmune();

    @Accessor("fireImmune")
    void setFireImmune(boolean fireImmune);
}
