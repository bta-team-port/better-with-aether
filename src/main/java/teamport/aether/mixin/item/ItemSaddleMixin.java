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

@Mixin(ItemSaddle.class)
public abstract class ItemSaddleMixin {
    @ModifyReturnValue(method = "useOnEntity(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/entity/Mob;)Z", at = @At("TAIL"))
    private boolean callOnItemUse(boolean original, ItemStack selfStack, Player player, Mob mob) {
        if (mob instanceof MobPhyg entity && selfStack.consumeItem(player) && !entity.getSaddled()) {
            entity.setSaddled(true);
            entity.setSitting(true);
            return true;
        }

        if (mob instanceof MobPhow entity && selfStack.consumeItem(player) && !entity.getSaddled()) {
            entity.setSaddled(true);
            entity.setSitting(true);
            return true;
        }

        if (mob instanceof MobMoa entity && !entity.getSaddled() && entity.getTamed() && selfStack.consumeItem(player)) {
            entity.setSaddled(true);
            entity.setSitting(true);
            return true;
        }

        return original;
    }
}
