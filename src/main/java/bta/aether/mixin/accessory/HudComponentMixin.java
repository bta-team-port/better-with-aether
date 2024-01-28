package bta.aether.mixin.accessory;

import bta.aether.accessory.API.HealthHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HealthBarComponent;
import net.minecraft.client.gui.hud.HudComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = HudComponent.class, remap = false)
public class HudComponentMixin {

	@Unique
	private static int CalculateHealthbarYSize(Minecraft mc) {
		// TODO: fix when more than one row of hearts is supported
		return (HealthHelper.getExtraHealth(mc.thePlayer) > 0 ? 20 : 10);
	}


	// dynamic size for hearts
	@Inject(method = "getYSize", at=@At("HEAD"), cancellable = true)
	public void getYSize(Minecraft mc, CallbackInfoReturnable<Integer> cir) {
		HudComponent thisAs = (HudComponent) (Object) this;
		if (thisAs instanceof HealthBarComponent) {
			cir.setReturnValue(CalculateHealthbarYSize(mc));
		}
	}

	@Inject(method = "getAnchorY", at = @At("HEAD"),cancellable = true)
	public void getAnchorY(ComponentAnchor anchor, CallbackInfoReturnable<Integer> cir) {
		HudComponent thisAs = (HudComponent) (Object) this;
		if (thisAs instanceof HealthBarComponent) {
			cir.setReturnValue((int) anchor.yPosition * CalculateHealthbarYSize(Minecraft.getMinecraft(this)));
		}
	}
}
