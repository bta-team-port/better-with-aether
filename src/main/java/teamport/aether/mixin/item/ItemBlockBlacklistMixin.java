package teamport.aether.mixin.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

import java.util.List;

@Mixin(value = ItemBlock.class, remap = false)
public class ItemBlockBlacklistMixin {

    @Shadow @NotNull protected Block<?> block;

    @Inject(method = "onUseItemOnBlock", at = @At(value = "HEAD"), cancellable = true)
    private void banBlocksFromDimensions(ItemStack stack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
        Dimension dim = Dimension.getDimensionList().get(player.dimension);
        List<Integer> BLACKLIST = AetherDimension.getDimensionBlacklist(dim);

        if (BLACKLIST.contains(block.id()))  {
            Minecraft.getMinecraft().thePlayer.sendMessage("HELLO");
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();

            if (Dimension.getDimensionList().get(player.dimension) == AetherDimension.AETHER){
                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45);
                    world.spawnParticle("smoke", (double) blockX + 0.5, (double) blockY + .2, (double) blockZ + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0, 0);
                }
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double)blockX + 0.5, (double)blockY + 0.5, (double)blockZ + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);

            } else {
                world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (float)blockX + 0.5f, (float)blockY + 0.5f, (float)blockZ + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                for (int i = 0; i < 8; ++i) {
                    world.spawnParticle("largesmoke", (double)blockX + Math.random(), (double)blockY + .2, (double)blockZ + Math.random(), 0.0, 0.0, 0.0, 0);
                }

            }

            cir.setReturnValue(true);
        }
    }
}