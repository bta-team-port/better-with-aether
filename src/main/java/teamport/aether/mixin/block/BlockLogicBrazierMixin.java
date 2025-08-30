package teamport.aether.mixin.block;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

@Mixin(value = BlockLogicBrazier.class, remap = false)
public abstract class BlockLogicBrazierMixin extends BlockLogic {

    @Shadow @Final private boolean burning;

    public BlockLogicBrazierMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Inject(method = "onBlockRightClicked", at = @At("HEAD"), cancellable = true)
    public void callOnBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
        ItemStack heldItem = player.getHeldItem();
        if (world.dimension == AetherDimension.AETHER) {
            if (heldItem != null && heldItem.getItem() instanceof ItemFireStriker && !this.burning) {
                Block<?> b;
                if (((b = world.getBlock(x + 1, y, z)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x - 1, y, z)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x, y, z + 1)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x, y, z - 1)) == null || !(b.getLogic() instanceof BlockLogicFluid))) {
                    world.setBlockAndMetadataWithNotify(x, y, z, Blocks.BRAZIER_INACTIVE.id(), 0);
                    heldItem.damageItem(1, player);
                    for (int l = 0; l < 8; ++l) {
                        double angle = Math.toRadians(l * 45);
                        world.spawnParticle("smoke", (double) x + 0.5, y, (double) z + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0, 0);
                    }
                    world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                    cir.setReturnValue(true);
                } else {
                    cir.setReturnValue(false);
                }
            }

        }
    }
}
