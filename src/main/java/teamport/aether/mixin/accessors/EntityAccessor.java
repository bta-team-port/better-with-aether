package teamport.aether.mixin.accessors;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(value = Entity.class)
public interface EntityAccessor {
    @Accessor
    void setFallDistance(float fallDistance);
    @Accessor
    Random getRandom();
}
