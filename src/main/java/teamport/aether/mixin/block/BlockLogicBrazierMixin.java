package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
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
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

@Mixin(value = BlockLogicBrazier.class, remap = false)
public abstract class BlockLogicBrazierMixin extends BlockLogic {
    protected BlockLogicBrazierMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Shadow
    @Final
    private boolean burning;

    @WrapMethod(method = "onBlockRightClicked")
    private boolean callOnBlockRightClicked(World world, int x, int y, int z, Player player, Side side, double xPlaced, double yPlaced, Operation<Boolean> original) {
        ItemStack heldItem = player.getHeldItem();
        if (world.dimension == AetherDimension.getAether() && heldItem != null && heldItem.getItem() instanceof ItemFireStriker && !this.burning) {
            Block<?> b;
            if (((b = world.getBlock(x + 1, y, z)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x - 1, y, z)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x, y, z + 1)) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlock(x, y, z - 1)) == null || !(b.getLogic() instanceof BlockLogicFluid))) {
                world.setBlockAndMetadataWithNotify(x, y, z, Blocks.BRAZIER_INACTIVE.id(), 0);
                heldItem.damageItem(1, player);
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45.0);
                    ParticleMaker.spawnParticle(world, "smoke", x + 0.5, y, z + 0.5, -Math.cos(angle) / 20.0, 0.03, -Math.sin(angle) / 20.0, 0);
                }
                if (!EnvironmentHelper.isClientWorld()) {
                    world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, x + 0.5, y + 0.5, z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                }
                return true;
            } else {
                return false;
            }
        }

        return original.call(world, x, y, z, player, side, xPlaced, yPlaced);
    }
}
