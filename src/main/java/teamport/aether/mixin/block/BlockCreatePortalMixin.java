package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = BlockLogicFluid.class)
public abstract class BlockCreatePortalMixin {
    @WrapOperation(method = "onPlacedByWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogicFluid;checkForHarden(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;)V"))
    private void onBlockAdded(BlockLogicFluid instance, World world, TilePosc pos, Operation<Void> original) {
        if (world.getBlockMaterial(pos) == Materials.WATER && world.getBlockType(pos.down(new TilePos())) == Blocks.GLOWSTONE && AetherBlocks.PORTAL_AETHER.getLogic().tryToCreatePortal(world, pos, DyeColor.BLUE)) return;
        original.call(instance, world, pos);
    }
}
