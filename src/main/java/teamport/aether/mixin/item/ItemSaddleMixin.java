package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemSaddle;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.animal.moa.MobMoa;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;

@Mixin(value = ItemSaddle.class, remap = false)
public abstract class ItemSaddleMixin {
    @ModifyReturnValue(method = "useOnEntity(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/entity/Mob;)Z", at = @At("TAIL"))
    private boolean callOnItemUse(boolean original, ItemStack itemstack, Player player, Mob mob) {
        if (mob instanceof MobPhyg && itemstack.consumeItem(player)) {
            MobPhyg entity = (MobPhyg) mob;
            if (!entity.getSaddled()) {
                entity.setSaddled(true);
                return true;
            }
        }
        if (mob instanceof MobPhow && itemstack.consumeItem(player)) {
            MobPhow entity = (MobPhow) mob;
            if (!entity.getSaddled()) {
                entity.setSaddled(true);
                return true;
            }
        }
        if (mob instanceof MobMoa && itemstack.consumeItem(player)) {
            MobMoa entity = (MobMoa) mob;
            if (!entity.getSaddled() && entity.isTamed()) {
                entity.setSaddled(true);
                return true;
            }
        }
        return original;
    }
}
