package teamport.aether.mixin.gui.screens;

import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.DamageType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScreenInventory.class, remap = false)
public abstract class ScreenInventoryMixin extends ScreenContainerAbstract {
    @Shadow
    protected Color protectionOverlayBgColor;
    @Shadow
    private DamageType hoveredDamageType;
    @Shadow
    protected int armourValuesFloat;

    public ScreenInventoryMixin(Player player) {
        super(player.inventorySlots);
    }

    @Inject(method = "drawProtectionOverlay", at = @At("HEAD"), cancellable = true)
    private void drawProtectionOverlay(int mouseX, int mouseY, CallbackInfo ci) {
        this.hoveredDamageType = null;
        int x = this.width / 2 - this.armourValuesFloat - 4;
        int y = this.height / 2 - 79;
        int w = 44;

        int visibleCount = 0;
        for (DamageType dt : DamageType.values()) {
            if (dt.shouldDisplay()) {
                ++visibleCount;
            }
        }
        int h = Math.max(visibleCount * 10 + 4, 44);

        GL11.glEnable(3042);
        this.drawGradientRect(x, y, x + w, y + h, this.protectionOverlayBgColor.getARGB(), this.protectionOverlayBgColor.getARGB());
        GL11.glDisable(3042);
        GL11.glDisable(2884);

        int w2 = 26;
        int h2 = 4;
        int i = 0;

        for (DamageType damageType : DamageType.values()) {
            if (damageType.shouldDisplay()) {
                int y2 = y + i * 10;
                float protection = this.mc.thePlayer.inventory.getTotalProtectionAmount(damageType);
                if (protection > 1.0F) {
                    protection = 1.0F;
                }

                int l = (int) (protection * 255.0F);
                int color = 255 - l << 16 | l << 8 | -16777216;
                GL11.glEnable(3553);
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                this.drawGuiIcon(x + 2, y2 + 2, 9, 9, TextureRegistry.getTexture(damageType.getIcon()));
                GL11.glDisable(3553);
                this.drawRectWidthHeight(x + 14, y2 + 4, w2 + 2, h2 + 1, -16777216);
                this.drawRectWidthHeight(x + 15, y2 + 4, (int) (protection * (float) w2), h2, color);
                if (mouseX >= x && mouseY >= y2 + 2 && mouseX <= x + w && mouseY <= y2 + 12) {
                    this.hoveredDamageType = damageType;
                }
                ++i;
            }
        }

        GL11.glEnable(3553);
        TooltipElement tooltip = ((ScreenContainerAbstractAccessor) this).getTooltipElement();
        if (this.hoveredDamageType != null && tooltip != null) {
            int protection = Math.round(this.mc.thePlayer.inventory.getTotalProtectionAmount(this.hoveredDamageType) * 100.0F);
            if (protection < 0) {
                protection = 0;
            }

            if (protection > 100) {
                protection = 100;
            }

            String str = I18n.getInstance().translateKey(this.hoveredDamageType.getLanguageKey()) + ":\n" + protection + " / 100";
            tooltip.render(str, mouseX, mouseY, 8, -8);
        }

        ci.cancel();
    }
}