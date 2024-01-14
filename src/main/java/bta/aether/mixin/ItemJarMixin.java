package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemJar;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemJar.class, remap = false)
public class ItemJarMixin {
    @Redirect(method = "onItemRightClick(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;)Lnet/minecraft/core/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemJar;fillJar(Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/item/ItemStack;)V", ordinal = 3))
    private void fillCustom(EntityPlayer player, ItemStack itemToGive){
        ItemJar.fillJar(player, new ItemStack(AetherItems.lanternAether));
    }
}
