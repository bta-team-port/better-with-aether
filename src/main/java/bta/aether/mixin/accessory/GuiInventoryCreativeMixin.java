package bta.aether.mixin.accessory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiInventory;
import net.minecraft.client.gui.GuiInventoryCreative;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiInventoryCreative.class, remap = false)
public abstract class GuiInventoryCreativeMixin extends GuiInventory {

	@Unique
	private static final int CORNER_INSET = 7;

	public GuiInventoryCreativeMixin(EntityPlayer player) {
		super(player);
	}

	@Inject(method = "drawGuiContainerBackgroundLayer",at=@At(value="INVOKE",target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V",ordinal = 0))
	public void bindGuiTexture(float f, CallbackInfo ci) {
		Minecraft minecraft = Minecraft.getMinecraft(this);

		int startX = (width - xSize) / 2;
		int startY = (height - ySize) / 2;

		// draw aether gui texture
		int texture_id = minecraft.renderEngine.getTexture("assets/aether/gui/inventory.png");
		minecraft.renderEngine.bindTexture(texture_id);
		drawTexturedModalRect(startX, startY, 0, 0, 175 - CORNER_INSET, 165);
	}

	// move the player 'doll' over to the left some
	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V",ordinal = 0))
	public void translatePlayerModel(float x, float y, float z) {
		GL11.glTranslatef(x - 18, y, z);
	}

	@Redirect(method = "drawGuiContainerBackgroundLayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/player/EntityPlayerSP;yRot:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	private void fixPlayerModelYaw(EntityPlayerSP instance, float yaw) {
		int startX = (width - xSize) / 2;
		instance.yRot = (float) Math.atan((startX + 33 - this.xSize_lo) / 40.0F) * 40.0F;
	}
}
