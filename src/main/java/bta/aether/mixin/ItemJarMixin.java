package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumFireflyColor;
import net.minecraft.core.item.ItemJar;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(value = ItemJar.class, remap = false)
public class ItemJarMixin {
    @Inject(method = "onItemRightClick(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;)Lnet/minecraft/core/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemJar;fillJar(Lnet/minecraft/core/entity/player/EntityPlayer;Lnet/minecraft/core/item/ItemStack;)V",
                    shift = At.Shift.BEFORE, ordinal = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private void fillCustom(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir, List list, Iterator var5, Entity entity, EntityFireflyCluster fireflyCluster, EnumFireflyColor colour){
//        if (colour == EnumFireflyColor.SILVER){
//            ItemJar.fillJar(player, new ItemStack(AetherItems.lanternAether));
//            cir.setReturnValue(itemstack);
//        }
    }
}
