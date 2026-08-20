package teamport.aether.mixin.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.font.FontRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.DrawMode;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.AetherItems;

@Environment(EnvType.CLIENT)
@Mixin(HudIngame.class)
public abstract class ArmorOverlayMixin extends Gui {
    @Shadow
    protected Minecraft mc;

    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/component/HudComponents;getComponents()Ljava/util/List;"))
    private void renderAetherArmour(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        Player player = this.mc.thePlayer;
        ContainerInventory inv = player.inventory;
        ItemStack[] accessorySlots = ((IContainerInventoryAether) inv).aether$getAccessoryInventory();

        int height = this.mc.resolution.getScaledHeightScreenCoords();
        int sp = (int) (GameSettings.SCREEN_PADDING.get() * height / 8.0F);

        FontRenderer font = this.mc.font;
        TessellatorGeneral tessellator = GLRenderer.getTessellator();

        for (int i = 0; i < accessorySlots.length; i++) {
            ItemStack stack = accessorySlots[accessorySlots.length - 1 - i];
            if (stack != null) {
                int x = 2 + 48 + sp;
                int y = height - sp - 16 - i * 16;

                ItemModelDispatcher.getInstance().getDispatch(stack).renderItemOverlayIntoGUI(tessellator, font, this.mc.textureManager, stack, x, y, null, 1.0F);

                if (stack.isItemStackDamageable()) {
                    float durability = (float) (stack.getMaxDamage() - stack.getMetadata()) / (float) stack.getMaxDamage();
                    int l = (int) (durability * 255.0F);
                    int color = 255 - l << 16 | l << 8;

                    boolean lightingEnabled = GLRenderer.globalGetLightEnabled();
                    GLRenderer.pushFrame();
                    try {
                        GLRenderer.disableState(State.BLEND);
                        Lighting.disable();
                        font.render(String.valueOf(stack.getMaxDamage() - stack.getMetadata()), x + 20, y + 4).setColor(color).setShadow().call();
                    } finally {
                        GLRenderer.popFrame();
                        GLRenderer.globalSetLightEnabled(lightingEnabled);
                    }
                }
            }
        }
    }

    @Inject(method = "renderGameOverlay(FZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupScaledResolution()V", shift = At.Shift.AFTER))
    private void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        int width = this.mc.resolution.getScaledWidthScreenCoords();
        int height = this.mc.resolution.getScaledHeightScreenCoords();

        ItemStack[] accessorySlots = ((IContainerInventoryAether) this.mc.thePlayer.inventory).aether$getAccessoryInventory();
        ItemStack trinketOneSlotItem = accessorySlots[2];
        ItemStack trinketTwoSlotItem = accessorySlots[3];
        double velocity = MathHelper.sqrt(this.mc.thePlayer.xd * this.mc.thePlayer.xd + this.mc.thePlayer.zd * this.mc.thePlayer.zd);

        if (GameSettings.THIRD_PERSON_VIEW.value == 0 &&
            ((trinketOneSlotItem != null && trinketOneSlotItem.itemID == AetherItems.ARMOR_SHIELD_REPULSION.id) ||
                (trinketTwoSlotItem != null && trinketTwoSlotItem.itemID == AetherItems.ARMOR_SHIELD_REPULSION.id)) &&
            (this.mc.thePlayer.isSneaking() ||
                (this.mc.thePlayer.onGround && velocity < 0.075D))) {
            GLRenderer.pushFrame();
            try {
                GLRenderer.enableState(State.BLEND);
                GLRenderer.disableState(State.DEPTH_TEST);
                GLRenderer.setDepthMask(false);
                GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
                GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GLRenderer.setAlphaTest(0.0F);
                this.mc.textureManager.loadTexture("/assets/aether/textures/other/shieldvignette.png").bind();
                TessellatorGeneral tessellator = GLRenderer.getTessellator();
                tessellator.startDrawing(DrawMode.QUADS);
                tessellator.addVertexWithUV(0.0, height, -90.0, 0.0, 1.0);
                tessellator.addVertexWithUV(width, height, -90.0, 1.0, 1.0);
                tessellator.addVertexWithUV(width, 0.0, -90.0, 1.0, 0.0);
                tessellator.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
                tessellator.draw();
            } finally {
                GLRenderer.popFrame();
            }
        }
    }
}
