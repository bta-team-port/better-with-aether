package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.BlockLogicPathDirt;
import net.minecraft.core.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = BlockLogicPathDirt.class)
public abstract class BlockLogicPathDirtMixin {
    @Definition(id = "isSolid", method = "Lnet/minecraft/core/block/material/Material;isSolid()Z")
    @Expression("?.isSolid()")
    @ModifyExpressionValue(method = "onNeighborBlockChange", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean addNewPathBlock(boolean original, @Local(name = "id") int id) {
        return original &&
            id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id() &&
            id != AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id() &&
            id != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED.id() &&
            id != Blocks.FENCE_GATE_PLANKS_OAK_PAINTED.id() &&
            id != Blocks.SIGN_WALL_PLANKS_OAK_PAINTED.id() &&
            id != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT.id();
    }
}
