package teamport.aether.mixin.light;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightingCache;
import net.minecraft.core.block.Block;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IBlockAether;

@Environment(EnvType.CLIENT)
@Mixin(value = LightingCache.class, remap = false)
public abstract class LightingCacheMixin {
    @Shadow
    private Block<?> block;

    @ModifyExpressionValue(
        method = "getLightIndex(III)B",
        at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I", opcode = Opcodes.GETFIELD)
    )
    private int overrideEmission(int original) {
        return IBlockAether.of(this.block).better_with_aether$getEmissionOverride();
    }

    @ModifyExpressionValue(
        method = "calcBrightness(Lnet/minecraft/core/world/pos/TilePos;)F",
        at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I", opcode = Opcodes.GETFIELD)
    )
    private int overrideBrightnessEmission(int original) {
        return IBlockAether.of(this.block).better_with_aether$getEmissionOverride();
    }
}
