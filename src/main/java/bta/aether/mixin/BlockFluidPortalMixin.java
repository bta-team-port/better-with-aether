package bta.aether.mixin;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockFluid.class, remap = false)
public class BlockFluidPortalMixin
{
    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    private void onBlockAdded(World world, int x, int y, int z, CallbackInfo info)
    {
        if (world.getBlockMaterial(x, y, z) == Material.water)
        {
            if (world.getBlockId(x, y - 1, z) == Block.glowstone.id && ((BlockPortal) AetherBlocks.portalAether).tryToCreatePortal(world, x, y, z))
            {
                info.cancel();
            }
        }
    }
}
