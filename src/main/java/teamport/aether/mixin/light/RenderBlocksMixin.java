package teamport.aether.mixin.light;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.core.block.Block;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IBlockAether;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderBlocks.class)
public abstract class RenderBlocksMixin {
    @ModifyExpressionValue(method = "setupLighting", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I", opcode = Opcodes.GETFIELD))
    private int fixAO(int original, Block<?> block, int x, int y, int z, float r, float g, float b, int side, int meta, int dirX, int dirY, int dirZ, float depth, int topX, int topY, int topZ, float topP, float botP, int lefX, int lefY, int lefZ, float lefP, float rigP) {
        return ((IBlockAether) (Object) block).better_with_aether$getEmissionOverride();
    }
}
