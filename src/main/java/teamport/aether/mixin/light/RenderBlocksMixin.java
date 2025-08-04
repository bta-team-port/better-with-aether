package teamport.aether.mixin.light;

import net.minecraft.client.render.RenderBlocks;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import teamport.aether.ducks.IBlockAether;

@Mixin(value = RenderBlocks.class, remap = false)
public abstract class RenderBlocksMixin {

    @Redirect(method = "setupLighting", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I"))
    public int fixAO(Block<?> instance) {
        return ((IBlockAether)(Object)instance).better_with_aether$getEmissionOverride();
    }
}
