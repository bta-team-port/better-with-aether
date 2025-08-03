package teamport.aether.mixin.item;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

@Mixin(value = ItemFireStriker.class, remap = false)
public abstract class ItemFireStrikerMixin extends Item {

    public ItemFireStrikerMixin(NamespaceID namespaceId, int id) {
        super(namespaceId, id);
    }

    @Inject(method = "onUseItemOnBlock", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        if (world.dimension == AetherDimension.AETHER) {

            for (int l = 0; l < 8; ++l) {
                double angle = Math.toRadians(l * 45);
                world.spawnParticle("smoke", (double) blockX + 0.5, (double) blockY + 1, (double) blockZ + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0, 0);
            }

            world.playSoundEffect(null, SoundCategory.WORLD_SOUNDS, (double)blockX + 0.5, (double)blockY + 0.5, (double)blockZ + 0.5, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);

            itemstack.damageItem(1, entityplayer);
            entityplayer.swingItem();
            info.setReturnValue(false);
        }
    }
}