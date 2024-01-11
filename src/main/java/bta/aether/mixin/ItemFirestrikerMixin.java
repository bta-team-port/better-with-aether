package bta.aether.mixin;

import bta.aether.Aether;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFirestriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemFirestriker.class, remap = false)
public class ItemFirestrikerMixin extends Item {

    public ItemFirestrikerMixin(String name, int id) {
        super(name, id);
    }

    @Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
    public boolean callOnItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        if (world.dimension == Aether.dimensionAether) {

            for (int l = 0; l < 8; ++l) {
                double angle = Math.toRadians(l * 45);
                world.spawnParticle("largesmoke", (double) blockX + 0.5, (double) blockY + 1, (double) blockZ + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0);
            }

            world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)blockX + 0.5, (double)blockY + 0.5, (double)blockZ + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);

            itemstack.damageItem(2, entityplayer);
            info.setReturnValue(false);
            return false;
        } else {
            return super.onItemUse(itemstack, entityplayer, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
        }
    }

}