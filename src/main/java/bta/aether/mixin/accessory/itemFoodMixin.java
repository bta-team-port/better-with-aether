package bta.aether.mixin.accessory;

import bta.aether.accessory.API.VariableHealthPlayer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFood;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = ItemFood.class, remap = false)
public abstract class itemFoodMixin {

	@Shadow
	protected int healAmount;

	// TODO: we should use modify-constant or something here probabably.
	@Inject(method = "onItemRightClick", at = @At("HEAD"), cancellable = true)
	public void onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir) {
		int max_health = 20 + ((VariableHealthPlayer) entityplayer).getExtraHP();

		if (entityplayer.health < max_health && itemstack.consumeItem(entityplayer)) {
			entityplayer.heal(this.healAmount);
		}
		cir.setReturnValue(itemstack);
	}
}
