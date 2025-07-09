package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import org.lwjgl.opengl.GL11;
import teamport.aether.accessory.api.HealthHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class HudComponentExtraHealthBar extends HudComponentMovable {
    private final Random random = new Random();

    public HudComponentExtraHealthBar(String key, Layout layout) {
        super(key, 81, 10, layout);
    }

    @Override
    public boolean isVisible(Minecraft mc) {
        return mc.playerController.canHurtPlayer() && !mc.thePlayer.getGamemode().isPlayerInvulnerable() && mc.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {

        int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
        int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        boolean heartsFlash = mc.thePlayer.heartsFlashTime / 3 % 2 == 1;
        if (mc.thePlayer.heartsFlashTime < 10) {heartsFlash = false;}
        int health = mc.thePlayer.getHealth();
        int prevHealth = mc.thePlayer.prevHealth;

        // additional information
        int extra_health = HealthHelper.getExtraHealth(mc.thePlayer);
        int extra_heart_amount = extra_health / 2;
        if (extra_heart_amount == 0){return;}

        for (int i = 10; i < 10 - extra_heart_amount; ++i) {
            int heartOffset = 0;
            if (heartsFlash) {
                heartOffset = 1;
            }
            int xHeart = x + (i - 10) * 8;
            int yHeart = y;
            if (health <= 4) {
                yHeart += this.random.nextInt(2);
            }
            hud.drawTexturedModalRect(xHeart, yHeart, 16 + heartOffset * 9, 0, 9, 9);
            if (heartsFlash) {
                if (i * 2 + 1 < prevHealth) {
                    hud.drawTexturedModalRect(xHeart, yHeart, 70, 0, 9, 9);
                }
                if (i * 2 + 1 == prevHealth) {
                    hud.drawTexturedModalRect(xHeart, yHeart, 79, 0, 9, 9);
                }
            }
            if (i * 2 + 1 < health) {
                hud.drawTexturedModalRect(xHeart, yHeart, 52, 0, 9, 9);
            }
            if (i * 2 + 1 == health) {
                hud.drawTexturedModalRect(xHeart, yHeart, 61, 0, 9, 9);
            }
            if (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) && !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) || !((Boolean) mc.gameSettings.foodHealthRegenOverlay.value).booleanValue())
                continue;

            int healing = mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood ? ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount() : ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
            if (i * 2 + 1 < health) continue;
            if (i * 2 + 1 == health) {
                hud.drawTexturedModalRect(xHeart, yHeart, 106, 0, 9, 9);
                continue;
            }
            if (i * 2 + 1 < health + healing) {
                hud.drawTexturedModalRect(xHeart, yHeart, 88, 0, 9, 9);
                continue;
            }
            if (i * 2 + 1 != health + healing) continue;
            hud.drawTexturedModalRect(xHeart, yHeart, 97, 0, 9, 9);
        }
    }

    @Override
    public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
        int x = layout.getComponentX(mc, this, xSizeScreen);
        int y = layout.getComponentY(mc, this, ySizeScreen);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        int health = 11;

        for(int i = 0; i < 10; ++i) {
            int xHeart = x + (i - 10) * 8;
            gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/container"));
            if (i * 2 + 1 < health) {
                gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/full"));
            }

            if (i * 2 + 1 == health) {
                gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/half"));
            }
        }

    }
}
