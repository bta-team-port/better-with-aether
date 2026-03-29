package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = BlockLogicFluid.class)
public abstract class BlockCreatePortalMixin {
    @WrapOperation(method = "onBlockPlacedByWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogicFluid;checkForHarden(Lnet/minecraft/core/world/World;III)V"))
    private void onBlockAdded(BlockLogicFluid instance, World world, int x, int y, int z, Operation<Void> original) {
        if (world.getBlockMaterial(x, y, z) == Material.water && world.getBlockId(x, y - 1, z) == Blocks.GLOWSTONE.id() && AetherBlocks.PORTAL_AETHER.getLogic().tryToCreatePortal(world, x, y, z, DyeColor.BLUE)) return;
        original.call(instance, world, x, y, z);
    }
}
