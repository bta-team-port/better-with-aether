package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.BlockLogicPathDirt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.blocks.AetherBlocks;

@Mixin(value = BlockLogicPathDirt.class, remap = false)
public abstract class BlockLogicPathDirtMixin {
    @Definition(id = "isSolid", method = "Lnet/minecraft/core/block/material/Material;isSolid()Z")
    @Expression("?.isSolid()")
    @ModifyExpressionValue(method = "onNeighborBlockChange", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean addNewPathBlock(boolean original, @Local(name = "id") int id) {
        return original && id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id();
    }
}
