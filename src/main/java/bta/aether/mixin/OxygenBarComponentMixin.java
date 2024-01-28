package bta.aether.mixin;

import bta.aether.accessory.API.AccessoryHelper;
import bta.aether.item.AetherItems;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = net.minecraft.client.gui.hud.OxygenBarComponent.class, remap = false)
public class OxygenBarComponentMixin {

	// don't draw oxygen bar when player has the iron bubble
	@Inject(method = "isVisible",at = @At("HEAD"), cancellable = true)
	void isVisible(Minecraft mc, CallbackInfoReturnable<Boolean> cir) {
		if (AccessoryHelper.hasAccessory(mc.thePlayer, AetherItems.armorTalismanIronBubble)) {
			cir.setReturnValue(false);
		}
	}
}
