package bta.aether.mixin.accessory;

import bta.aether.accessory.API.HealthHelper;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFood;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ItemFood.class, remap = false)
public abstract class itemFoodMixin {

	@ModifyExpressionValue(method = "onItemRightClick", at=@At(value = "CONSTANT", args = "intValue=20"))
	public int modifyHardcodedHealth(int health, @Local EntityPlayer player) {
		return HealthHelper.getTotalHealth(player);
	}
}
