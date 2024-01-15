package bta.aether.mixin;

import bta.aether.world.AetherDimension;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = ItemBlock.class, remap = false)
public class ItemBlockMixin {
    @Shadow protected int blockID;

    @Inject(method = "onItemUse(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/world/World;IIILnet/minecraft/core/util/helper/Side;DD)Z",
            at = @At(value = "HEAD"), cancellable = true)
    private void banBlocksFromDimensions(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
        if (AetherDimension.getDimensionBlacklist(Dimension.getDimensionList().get(player.dimension)).contains(blockID)){ // if block id is in dimension's blacklist
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();
            if (Dimension.getDimensionList().get(player.dimension) == AetherDimension.dimensionAether){
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45);
                    world.spawnParticle("smoke", (double) blockX + 0.5, (double) blockY + 1, (double) blockZ + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0);
                }

                world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)blockX + 0.5, (double)blockY + 0.5, (double)blockZ + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
            } else {
                world.playSoundEffect(SoundType.WORLD_SOUNDS, (float)blockX + 0.5f, (float)blockY + 0.5f, (float)blockZ + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                for (int i = 0; i < 8; ++i) {
                    world.spawnParticle("largesmoke", (double)blockX + Math.random(), (double)blockY + .2, (double)blockZ + Math.random(), 0.0, 0.0, 0.0);
                }
            }

            cir.setReturnValue(true);
        }
    }
}
