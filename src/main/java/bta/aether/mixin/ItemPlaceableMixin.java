package bta.aether.mixin;

import bta.aether.world.AetherDimension;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemPlaceable.class, remap = false)
public class ItemPlaceableMixin extends Item {
    public ItemPlaceableMixin(String key, int id) {
        super(key, id);
    }
    @Unique
    int x;
    @Unique
    int y;
    @Unique
    int z;
    @Inject(
            method = "onItemUse", at = @At(value = "HEAD"), cancellable = true)

    public void onItemUse(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        if (world.dimension == AetherDimension.dimensionAether) {
            world.playSoundEffect(SoundType.WORLD_SOUNDS, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);

            for (int l = 0; l < 8; ++l) {
                double angle = Math.toRadians(l * 45);
                world.spawnParticle("smoke", (double) x + 0.5, (double) y + 0.6, (double) z + 0.5, -Math.cos(angle) / 20.0, 0.03, -Math.sin(angle) / 20.0);
            }
            player.swingItem();
            info.setReturnValue(false);
        }
    }
}
