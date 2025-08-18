package teamport.aether.mixin.dimension;

import net.minecraft.core.block.BlockLogicFluid;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.AetherBlocks;

@Mixin(value = BlockLogicFluid.class, remap = false)
public class BlockCreatePortalMixin {
    @Inject(method = "onBlockPlacedByWorld", at = @At("HEAD"), cancellable = true)
    public void onBlockAdded(World world, int x, int y, int z, CallbackInfo info) {
        if (world.getBlockMaterial(x, y, z) == Material.water) {
            if (world.getBlockId(x, y - 1, z) == Blocks.GLOWSTONE.id() && AetherBlocks.PORTAL_AETHER.getLogic().tryToCreatePortal(world, x, y, z, DyeColor.BLUE)) {
                info.cancel();
            }
        }
    }
}