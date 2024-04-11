package bta.aether.mixin.item;

import bta.aether.entity.EntityPhyg;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemSaddle;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemSaddle.class, remap = false)
public class ItemSaddleMixin extends Item {

    public ItemSaddleMixin(String name, int id) {
        super(name, id);
    }

    @Inject(method = "useItemOnEntity", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityPlayer, CallbackInfoReturnable<Boolean> info) {
        if (entityliving instanceof EntityPhyg && itemstack.consumeItem(entityPlayer)) {
            EntityPhyg entitypig = (EntityPhyg)entityliving;
            if (!entitypig.getSaddled()) {
                entitypig.setSaddled(true);
                entityPlayer.swingItem();
                info.setReturnValue(true);
            }
        }
    }
}
