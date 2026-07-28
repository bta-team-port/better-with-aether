package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicPathDirt;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = BlockLogicPathDirt.class)
public abstract class BlockLogicPathDirtMixin {
    @Definition(id = "isSolid", method = "Lnet/minecraft/core/block/material/Material;isSolid()Z")
    @Expression("?.isSolid()")
    @ModifyExpressionValue(
        method = "onNeighborChanged(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)V",
        at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean addNewPathBlock(boolean original, World world, TilePosc tilePos, Block<?> block, @Local(name = "b") Block<?> aboveBlock) {
        return original &&
            aboveBlock.id() != AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id() &&
            aboveBlock.id() != AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id() &&
            aboveBlock.id() != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED.id() &&
            aboveBlock.id() != Blocks.FENCE_GATE_PLANKS_OAK_PAINTED.id() &&
            aboveBlock.id() != Blocks.SIGN_WALL_PLANKS_OAK_PAINTED.id() &&
            aboveBlock.id() != AetherBlocks.SIGN_WALL_PLANKS_SKYROOT.id();
    }
}
