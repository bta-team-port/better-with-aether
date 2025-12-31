package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemJar;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.AetherMod;
import teamport.aether.item.AetherItems;

@Mixin(value = ItemJar.class, remap = false)
public abstract class ItemJarMixin {
    @WrapOperation(method = "onUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemJar;fillJar(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/item/ItemStack;)Z", ordinal = 3))
    private boolean onGetFireflyColor(Player player, ItemStack itemToGive, Operation<Boolean> original, @Local MobFireflyCluster.FireflyColor colour) {
        if (colour == AetherMod.SILVER) return original.call(player, new ItemStack(AetherItems.LANTERN_FIREFLY_SILVER, 1));
        return original.call(player, itemToGive);
    }
}
