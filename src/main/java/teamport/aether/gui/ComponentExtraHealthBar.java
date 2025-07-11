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
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import teamport.aether.api.HealthHelper;

import java.util.Random;

// this is a mystery to me
@Environment(EnvType.CLIENT)
public class ComponentExtraHealthBar extends HudComponentMovable {
    public final Random random = new Random();

    public ComponentExtraHealthBar(String key, Layout layout) {
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
        if (mc.thePlayer.heartsFlashTime < 10) {
            heartsFlash = false;
        }
        int health = mc.thePlayer.getHealth();
        int prevHealth = mc.thePlayer.prevHealth;
        this.random.setSeed((long)hud.updateCounter * 312871L);
        boolean isHardcore = mc.thePlayer.getGamemode() == Gamemode.hardcore;

        // additional information
        int extra_health = HealthHelper.getExtraHealth(mc.thePlayer);
        int extra_heart_amount = (extra_health + 1) / 2;
        if (extra_heart_amount == 0) {
            return;
        }

        for (int i = 0; i < 10; ++i) {
            int heartOffset = 0;
            if (heartsFlash) {
                heartOffset = 1;
            }
            int xHeart = x + (i) * 8;
            int yHeart = y;
            if (health <= 4) {
                yHeart += this.random.nextInt(2);
            }
            hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                    heartOffset == 0
                            ? TextureRegistry.getTexture("minecraft:gui/hud/heart/container")
                            : TextureRegistry.getTexture("minecraft:gui/hud/heart/container_blinking"));
            if (heartsFlash) {
                if (i * 2 + 1 < prevHealth) {
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full_blinking"));
                }

                if (i * 2 + 1 == prevHealth) {
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                            TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half_blinking"));
                }
            }

            if (i * 2 + 1 < health) {
                hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                        TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "full"));
            }

            if (i * 2 + 1 == health) {
                hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                        TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "half"));
            }

            if (mc.thePlayer.inventory.getCurrentItem() != null && (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood || mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream) && (Boolean) mc.gameSettings.foodHealthRegenOverlay.value) {
                int healing;
                if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood) {
                    healing = ((ItemFood) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
                } else {
                    healing = ((ItemBucketIceCream) mc.thePlayer.inventory.getCurrentItem().getItem()).getHealAmount();
                }

                if (i * 2 + 1 >= health) {
                    if (i * 2 + 1 == health) {
                        hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                                TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half_right"));
                    } else if (i * 2 + 1 < health + healing) {
                        hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                                TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_full"));
                    } else if (i * 2 + 1 == health + healing) {
                        hud.drawGuiIcon(xHeart, yHeart, 9, 9,
                                TextureRegistry.getTexture("minecraft:gui/hud/heart/" + (isHardcore ? "hardcore_" : "") + "preview_half"));
                    }
                }
            }
        }
    }


    @Override
    public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
        int x = layout.getComponentX(mc, this, xSizeScreen);
        int y = layout.getComponentY(mc, this, ySizeScreen);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        int health = 11;

        for (int i = 0; i < 10; ++i) {
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
