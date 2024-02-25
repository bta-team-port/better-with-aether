package bta.aether.mixin.accessory;

import bta.aether.item.Accessories.base.ItemAccessoryGloves;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiInventory;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiInventory.class, remap = false)
public abstract class GuiInventoryMixin extends GuiContainer {

	@Shadow
	private GuiButton armorButton;

	@Shadow
	protected float xSize_lo;

	@Unique
	private static final int CORNER_INSET = 7;

	public GuiInventoryMixin(Container container) {
		super(container);
	}

	// dumb hack, modify the inventory size so we check the full inventory for gloves,
	// we can't do it globally cause shit breaks then and idk why
	@WrapOperation(method = "init", at = @At(value = "INVOKE",target = "Lnet/minecraft/core/player/inventory/InventoryPlayer;getSizeInventory()I"))
	public int checkFullInventory(InventoryPlayer instance, Operation<Integer> original) {
		return original.call(instance) + 8;
	}

	// enables armor button if gloves are in INVENTORY
	@WrapOperation(method = "init", at = @At(value = "INVOKE",target = "Lnet/minecraft/core/item/ItemStack;getItem()Lnet/minecraft/core/item/Item;"))
	public Item enableArmorButtonForGloves(ItemStack stack, Operation<Item> original) {
		Item item = original.call(stack);

		// lie about gloves being armor to force armor button to appear
		if (item instanceof ItemAccessoryGloves) {
			item = Item.armorBootsIron;
		}

		return item;
	}

	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo ci) {
		if (this.armorButton != null) {
			this.armorButton.xPosition = (width - xSize) / 2 + CORNER_INSET + 2;
			this.armorButton.yPosition = (height - ySize) / 2 + CORNER_INSET + 2;
		}
	}

	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V", ordinal = 0))
	public void bindGuiTexture(float f, CallbackInfo ci) {
		Minecraft minecraft = Minecraft.getMinecraft(this);

		int startX = (width - xSize) / 2;
		int startY = (height - ySize) / 2;
		int texture_id;

		// draw aether inventory texture
		texture_id = minecraft.renderEngine.getTexture("assets/aether/gui/inventory.png");
		minecraft.renderEngine.bindTexture(texture_id);
		drawTexturedModalRect(startX, startY, 0, 0, 175, 165);
	}

	// move the player 'doll' over to the left some
	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V",ordinal = 0))
	public void translatePlayerModel(float x, float y, float z) {
		GL11.glTranslatef(x - 18, y, z);
	}

	// make the player model face correctly given the new shift
	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;yRot:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	private void fixPlayerModelYaw(EntityPlayerSP instance, float yaw) {
		int startX = (width - xSize) / 2;
		instance.yRot = (float) Math.atan((startX + 33 - this.xSize_lo) / 40.0F) * 40.0F;
	}

	// don't draw the inventory text
	@Inject(method = "drawGuiContainerForegroundLayer", at = @At("HEAD"), cancellable = true)
	public void renderForeground(CallbackInfo ci) {
		ci.cancel();
	}

	// draw overlay buttons if player has compass/clock/calendar in accessory inv
	@Inject(method = "updateOverlayButtons", at=@At(value = "INVOKE", target="Lnet/minecraft/client/entity/player/EntityPlayerSP;getGamemode()Lnet/minecraft/core/player/gamemode/Gamemode;"))
	public void updateOverlayButtons(CallbackInfo ci,
									 @Local(ordinal = 0) LocalBooleanRef clock,
									 @Local(ordinal = 1) LocalBooleanRef compass,
									 @Local(ordinal = 2) LocalBooleanRef calendar
	) {
		EntityPlayer player = mc.thePlayer;

		// technically we only have to check misc slots but :shrug:
		for (int i = 0; i < player.inventory.armorInventory.length; i++) {
			ItemStack stack = player.inventory.armorInventory[i];

			if (stack == null) continue;

			if (stack.getItem().equals(Item.toolClock)) {
				clock.set(true);
			} else if (stack.getItem().equals(Item.toolCompass)) {
				compass.set(true);
			} else if (stack.getItem().equals(Item.toolCalendar)) {
				calendar.set(true);
			}
		}
	}
}
