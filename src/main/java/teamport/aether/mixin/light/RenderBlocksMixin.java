package teamport.aether.mixin.light;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.RenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.ducks.IBlockAether;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderBlocks.class)
public abstract class RenderBlocksMixin {
    @ModifyExpressionValue(
        method = "setupLighting(Lnet/minecraft/core/world/WorldSource;Lnet/minecraft/core/block/Block;Lnet/minecraft/core/world/pos/TilePosc;FFFLnet/minecraft/core/util/helper/Side;IIIFIIIFFIIIFF)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;emission:I", opcode = Opcodes.GETFIELD)
    )
    private int fixAO(int original, WorldSource worldSource, Block<?> block, TilePosc tilePos, float r, float g, float b, Side side, int dirX, int dirY, int dirZ, float depth, int topX, int topY, int topZ, float topP, float botP, int lefX, int lefY, int lefZ, float lefP, float rigP) {
        return ((IBlockAether) (Object) block).better_with_aether$getEmissionOverride();
    }
}
