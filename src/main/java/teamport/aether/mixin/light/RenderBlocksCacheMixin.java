package teamport.aether.mixin.light;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.RenderBlockCache;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IBlockAether;

@Mixin(value = RenderBlockCache.class, remap = false)
public abstract class RenderBlocksCacheMixin {
    @Shadow
    private Block<?> block;
    @ModifyExpressionValue(method = "getLightmapCoord", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I"))
    public int overrideEmission(int original) {
        return ((IBlockAether) (Object) this.block).better_with_aether$getEmissionOverride();
    }
}
