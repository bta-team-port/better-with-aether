package teamport.aether.mixins.mixin.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.EnvironmentHelper;

@Mixin(BlockLogicBrazier.class)
public abstract class BlockLogicBrazierMixin extends BlockLogic {
    protected BlockLogicBrazierMixin(Block<?> block, Material material) {
        super(block, material);
    }

    @Shadow
    @Final
    private boolean burning;

    @WrapMethod(method = "onInteracted")
    private boolean callOnInteracted(@NonNull World world, TilePosc tilePos, @NonNull Player player, Side side, double xHit, double yHit, Operation<Boolean> original) {
        ItemStack heldItem = player.getHeldItem();
        if (world.dimension == AetherDimension.getAether() && heldItem != null && heldItem.getItem() instanceof ItemFireStriker && !this.burning) {
            TilePos neighborPos = new TilePos();
            Block<?> b;
            if (((b = world.getBlockType(tilePos.add(Direction.EAST, neighborPos))) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlockType(tilePos.add(Direction.WEST, neighborPos))) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlockType(tilePos.add(Direction.SOUTH, neighborPos))) == null || !(b.getLogic() instanceof BlockLogicFluid)) && ((b = world.getBlockType(tilePos.add(Direction.NORTH, neighborPos))) == null || !(b.getLogic() instanceof BlockLogicFluid))) {
                world.setBlockTypeNotify(tilePos, Blocks.BRAZIER_INACTIVE);
                heldItem.damageItem(1, player);
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45.0);
                    ParticleMaker.spawnParticle(world, "smoke", tilePos.x() + 0.5, tilePos.y(), tilePos.z() + 0.5, -Math.cos(angle) / 20.0, 0.03, -Math.sin(angle) / 20.0, 0);
                }
                if (!EnvironmentHelper.isMultiplayerClient()) {
                    world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, tilePos.x() + 0.5, tilePos.y() + 0.5, tilePos.z() + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
                }
                return true;
            } else {
                return false;
            }
        }

        return original.call(world, tilePos, player, side, xHit, yHit);
    }
}
