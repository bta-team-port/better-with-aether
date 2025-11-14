package teamport.aether.mixin.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.MixinHelper;
import teamport.aether.items.AetherItems;

@Mixin(value = HudIngame.class, remap = false)
public abstract class ArmorOverlayMixin extends Gui {
    @Shadow
    protected Minecraft mc;
    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V", ordinal = 5))
    public void renderAetherArmour(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        Player player = this.mc.thePlayer;
        ContainerInventory inv = player.inventory;

        int height = this.mc.resolution.getScaledHeightScreenCoords();
        int sp = (int) (this.mc.gameSettings.screenPadding.value * height / 8.0F);

        Font font = this.mc.font;

        for (int i = 0; i < inv.armorInventory.length - 4; i++) {
            ItemStack stack = inv.armorInventory[inv.armorInventory.length - 1 - i];
            if (stack != null) {
                int x = 2 + 48 + sp;
                int y = height - sp - 16 - i * 16;

                ItemModelDispatcher.getInstance().getDispatch(stack).renderItemIntoGui(Tessellator.instance, font, this.mc.textureManager, stack, x, y, 1.0F);

                if (stack.isItemStackDamageable()) {
                    float durability = (float) (stack.getMaxDamage() - stack.getMetadata()) / (float) stack.getMaxDamage();
                    int l = (int) (durability * 255.0F);
                    int color = 255 - l << 16 | l << 8;

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    font.drawStringWithShadow(String.valueOf(stack.getMaxDamage() - stack.getMetadata() + 1), x + 20, y + 4, color);
                }
            }
        }
    }
    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupScaledResolution()V", shift = At.Shift.AFTER))
    public void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        int width = this.mc.resolution.getScaledWidthScreenCoords();
        int height = this.mc.resolution.getScaledHeightScreenCoords();

        ItemStack trinketOneSlotItem = this.mc.thePlayer.inventory.armorItemInSlot(6);
        ItemStack trinketTwoSlotItem = this.mc.thePlayer.inventory.armorItemInSlot(7);
        double velocity = MathHelper.sqrt(this.mc.thePlayer.xd * this.mc.thePlayer.xd + this.mc.thePlayer.zd * this.mc.thePlayer.zd);

        if (this.mc.gameSettings.thirdPersonView.value == 0 &&
            ((trinketOneSlotItem != null && trinketOneSlotItem.itemID == AetherItems.ARMOR_SHIELD_REPULSION.id) ||
                (trinketTwoSlotItem != null && trinketTwoSlotItem.itemID == AetherItems.ARMOR_SHIELD_REPULSION.id))) {
            if (this.mc.thePlayer.isSneaking() || (this.mc.thePlayer.onGround && velocity < 0.075D)) {
                MixinHelper.renderShieldVignette(mc.textureManager, width, height);
            }
        }
    }
}
