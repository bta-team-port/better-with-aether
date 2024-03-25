package bta.aether.mixin.accessory;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value= GuiIngame.class, remap = false)
public class GuiIngameMixin {

    @Shadow
    protected Minecraft mc;

    @Shadow
    public static ItemEntityRenderer itemRenderer;

    @Shadow
    protected FontRenderer fontrenderer;

    @Inject(method = "renderGameOverlay", at=@At("TAIL"))
    void renderAccessoriesOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {

        int width = this.mc.resolution.scaledWidth;
        int height = this.mc.resolution.scaledHeight;
        int sp = (int)(this.mc.gameSettings.screenPadding.value * (float)height / 8.0f);

        int l;
        int i1;
        int num_drawn = 0;

        // Should accessory overlay be it's own setting? maybe
        // also, I think the spacing might be off like a pixel from the armor (but i cant tell) and maybe it will upset people
        if (this.mc.gameSettings.immersiveMode.drawOverlays() && (Boolean) this.mc.gameSettings.armorDurabilityOverlay.value) {
            for (int i = 0; i < 8; ++i) {
                ItemStack stack = this.mc.thePlayer.inventory.armorInventory[11 - i];

                if (stack == null || !stack.isItemStackDamageable()) continue;

                int x = width - sp - 19;
                int y = height - sp - ((1 + num_drawn++) * 16);

                itemRenderer.renderItemIntoGUI(fontrenderer, this.mc.renderEngine, stack, x, y, 1.0f);

                float durability = (float)(stack.getMaxDamage() - stack.getMetadata()) / (float)stack.getMaxDamage();
                l = (int)(durability * 255.0f);
                i1 = 255 - l << 16 | l << 8;
                GL11.glDisable(3042);
                GL11.glDisable(2896);

                String s = String.valueOf((stack.getMaxDamage() - stack.getMetadata() + 1));
                int string_x = x - 4 - (fontrenderer.getStringWidth(s));
                int string_y = y + 4;

                fontrenderer.drawStringWithShadow(s, string_x, string_y, i1);
            }
            GL11.glDisable(3042);
            GL11.glDisable(2896);
        }
    }

    // render clock, compass, calendar overlays when they are equipped in the accessory inventory
    @Inject(method = "renderGameOverlay", at=@At(value = "INVOKE", target="Lnet/minecraft/client/entity/player/EntityPlayerSP;getGamemode()Lnet/minecraft/core/player/gamemode/Gamemode;", ordinal = 0 ))
    public void updateOverlayButtons2(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci,
                                      @Local(ordinal = 1) LocalBooleanRef clock,
                                      @Local(ordinal = 2) LocalBooleanRef compass,
                                      @Local(ordinal = 3) LocalBooleanRef calendar
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