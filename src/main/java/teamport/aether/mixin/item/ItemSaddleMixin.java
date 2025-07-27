package teamport.aether.mixin.item;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemSaddle;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.moa.MobMoa;
import teamport.aether.entity.phow.MobPhow;
import teamport.aether.entity.phyg.MobPhyg;

@Mixin(value = ItemSaddle.class, remap = false)
public class ItemSaddleMixin extends Item {

    public ItemSaddleMixin(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Inject(method = "useItemOnEntity", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, Mob entityliving, Player entityPlayer, CallbackInfoReturnable<Boolean> info) {
        if (entityliving instanceof MobPhyg && itemstack.consumeItem(entityPlayer)) {
            MobPhyg entity = (MobPhyg)entityliving;
            if (!entity.getSaddled()) {
                entity.setSaddled(true);
                entityPlayer.swingItem();
                info.setReturnValue(true);
            }
        }
        if (entityliving instanceof MobPhow && itemstack.consumeItem(entityPlayer)) {
            MobPhow entity = (MobPhow)entityliving;
            if (!entity.getSaddled()) {
                entity.setSaddled(true);
                entityPlayer.swingItem();
                info.setReturnValue(true);
            }
        }
        if (entityliving instanceof MobMoa && itemstack.consumeItem(entityPlayer)) {
            MobMoa entity = (MobMoa)entityliving;
            if (!entity.getSaddled()) {
                entity.setSaddled(true);
                entityPlayer.swingItem();
                info.setReturnValue(true);
            }
        }
    }
}
