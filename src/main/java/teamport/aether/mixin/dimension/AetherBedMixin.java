package teamport.aether.mixin.dimension;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicBed.class, remap = false)
public class AetherBedMixin extends BlockLogic {

    public AetherBedMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Inject(method = "onBlockRightClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockWithNotify(IIII)Z", shift = At.Shift.AFTER), cancellable = true)
    public void onBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
        if (world.dimension.id == AetherDimension.AETHER.id) {
            world.createExplosion(null, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, 5.0F, false, false);
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
