package teamport.aether.mixin.accessors;

import net.minecraft.client.render.particle.ParticleBlockBreaking;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ParticleBlockBreaking.class)
public interface ParticleBlockBreakingAccessor {
    @Mutable
    @Accessor
    void setSide(Side side);

    @Mutable
    @Accessor
    void setBlock(Block<?> block);
}
