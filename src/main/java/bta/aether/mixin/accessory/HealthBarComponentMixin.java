package bta.aether.mixin.accessory;


import bta.aether.accessory.API.HealthHelper;
import bta.aether.accessory.API.VariableHealthPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.HealthBarComponent;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = HealthBarComponent.class, remap = false)
public abstract class HealthBarComponentMixin extends MovableHudComponent {

	// TODO: just make a new fucking component, why didnt i do that instead of this hardcoded shit

	@Final
	@Shadow
	private Random random;

	public HealthBarComponentMixin(String key, int xSize, int ySize, Layout layout) {
		super(key, xSize, ySize, layout);
	}

	// draw second row of hearts
	@Inject(method = "render", at = @At("TAIL"))
	public void render(Minecraft mc, GuiIngame gui, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {

		int extra_health =  ((VariableHealthPlayer) mc.thePlayer).getExtraHP();

		int extra_heart_amount = extra_health / 2;
		if (extra_heart_amount == 0)
			return;

		int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
		int y = this.getLayout().getComponentY(mc, this, ySizeScreen);

		int health = mc.thePlayer.health;
		int prevHealth = mc.thePlayer.prevHealth;

		boolean heartsFlash = mc.thePlayer.heartsFlashTime / 3 % 2 == 1;
		if (mc.thePlayer.heartsFlashTime < 10) {
			heartsFlash = false;
		}

		for (int i = 10; i < 10 + extra_heart_amount; ++i) {
			int heartOffset = 0;
			if (heartsFlash) {
				heartOffset = 1;
			}
			int xHeart = x + (i - 10) * 8;
			int yHeart = y;
			if (health <= 4) {
				yHeart += this.random.nextInt(2);
			}
			gui.drawTexturedModalRect(xHeart, yHeart, 16 + heartOffset * 9, 0, 9, 9);
			if (heartsFlash) {
				if (i * 2 + 1 < prevHealth) {
					gui.drawTexturedModalRect(xHeart, yHeart, 70, 0, 9, 9);
				}
				if (i * 2 + 1 == prevHealth) {
					gui.drawTexturedModalRect(xHeart, yHeart, 79, 0, 9, 9);
				}
			}
			if (i * 2 + 1 < health) {
				gui.drawTexturedModalRect(xHeart, yHeart, 52, 0, 9, 9);
			}
			if (i * 2 + 1 == health) {
				gui.drawTexturedModalRect(xHeart, yHeart, 61, 0, 9, 9);
			}
			if (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) && !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) || !((Boolean) mc.gameSettings.foodHealthRegenOverlay.value).booleanValue())
				continue;

			int healing = mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood ? ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount() : ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
			if (i * 2 + 1 < health) continue;
			if (i * 2 + 1 == health) {
				gui.drawTexturedModalRect(xHeart, yHeart, 106, 0, 9, 9);
				continue;
			}
			if (i * 2 + 1 < health + healing) {
				gui.drawTexturedModalRect(xHeart, yHeart, 88, 0, 9, 9);
				continue;
			}
			if (i * 2 + 1 != health + healing) continue;
			gui.drawTexturedModalRect(xHeart, yHeart, 97, 0, 9, 9);
		}
	}

	@Redirect(method = "render", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/hud/Layout;getComponentY(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/hud/HudComponent;I)I"))
	public int healthY(Layout instance, Minecraft minecraft, HudComponent hudComponent, int i) {
		int y = instance.getComponentY(minecraft,hudComponent,i);
		if (HealthHelper.getExtraHealth(minecraft.thePlayer) > 0) {
			y += 10;
		}
		return y;
	}
}
