package teamport.aether.mixin.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicFire;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicFire.class, remap = false)
public abstract class BlockLogicFireMixin extends BlockLogic {

    public BlockLogicFireMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Inject(method = "onBlockPlacedByWorld", at = @At("HEAD"), cancellable = true)
    public void onBlockPlacedByWorld(World world, int x, int y, int z, CallbackInfo ci) {
        if (world.dimension == AetherDimension.AETHER) {
            world.setBlock(x, y, z, 0);
            ci.cancel();
        }
    }
}
