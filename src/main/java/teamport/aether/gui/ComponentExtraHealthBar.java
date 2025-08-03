package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.effect.AetherEffects;
import teamport.aether.helper.HealthHelper;

import java.util.Random;

// this is a mystery to me
@Environment(EnvType.CLIENT)
public class ComponentExtraHealthBar extends HudComponentMovable {
    public static final String PREVIEW = "minecraft:gui/hud/heart/";
    public final Random random = new Random();
    public int barCount;

    public ComponentExtraHealthBar(String key, Layout layout, int barIndex) {
        super(key, 81, 10, layout);
        this.barCount = barIndex;
    }

    @Override
    public boolean isVisible(Minecraft mc) {
        return mc.playerController.canHurtPlayer() && !mc.thePlayer.getGamemode().isPlayerInvulnerable() && mc.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {
        Player player = mc.thePlayer;
        // copied from HealthBar ---------------------------------------------------------------------------------------
        int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
        int y = this.getLayout().getComponentY(mc, this, ySizeScreen);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);

        boolean heartsFlash = player.heartsFlashTime / 3 % 2 == 1;
        if (player.heartsFlashTime < 10) {
            heartsFlash = false;
        }
        int health = player.getHealth();
        int prevHealth = player.prevHealth;
        this.random.setSeed((long) hud.updateCounter * 312871L);
        boolean isHardcore = player.getGamemode() == Gamemode.hardcore;
        //--------------------------------------------------------------------------------------------------------------
        String hardcoreHearths = isHardcore ? "hardcore_" : "";
        String PATH_HEART = getPath(player);
        String guiHeart = PATH_HEART + hardcoreHearths;
        //--------------------------------------------------------------------------------------------------------------
        int totalHealth = HealthHelper.getMaxHealth(player);
        int renderHealth = Math.min(totalHealth - barCount * 20, 20);
        int renderHeart = (renderHealth + 1) / 2;
        if (renderHeart == 0) return;

        // these 2 lines are magical pls do not change
        int barprevHealth = prevHealth - barCount * 20;
        int barhealth = health - barCount * 20;

        GL11.glTranslated(0, 0, -0.01 * barCount);

        for (int i = 0; i < renderHeart; ++i) {
            int heartOffset = heartsFlash ? 1 : 0;
            int xHeart = x + i * 8;
            int yHeart = y + (5 * barCount);
            if (health <= 4) {
                yHeart += this.random.nextInt(2);
            }
            int currentHeart = i * 2 + 1;
            String heartTexturePath = PATH_HEART + (heartOffset == 0 ? "container" : "container_blinking");

                                                             hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(heartTexturePath));
            if (heartsFlash) {
                if (currentHeart < barprevHealth)              {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(PATH_HEART + "full_blinking"));}
                if (currentHeart == barprevHealth)             {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "half_blinking"));}
            }
            if (currentHeart < barhealth)                      {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "full"));}
            if (currentHeart == barhealth)                     {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "half"));}

            // healing -------------------------------------------------------------------------------------------------
            if (
                    player.inventory.getCurrentItem() == null
                            || (!(player.inventory.getCurrentItem().getItem() instanceof ItemFood)
                            && !(player.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream))
                            || !((Boolean) mc.gameSettings.foodHealthRegenOverlay.value)
            ) {
                continue;
            }
            int healing;
            if (player.inventory.getCurrentItem().getItem() instanceof ItemFood)  {healing = ((ItemFood) player.inventory.getCurrentItem().getItem()).getHealAmount();}
            else                                                                  {healing = ((ItemBucketIceCream) player.inventory.getCurrentItem().getItem()).getHealAmount();}
            if (currentHeart < barhealth)                      {continue;}
            if (currentHeart == barhealth)                     {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_half_right"));}
            else if (currentHeart < barhealth + healing)       {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_full"));}
            else if (currentHeart == barhealth + healing)      {hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_half"));}
            // ---------------------------------------------------------------------------------------------------------
        }
    }

    private static String getPath(Player player) {
        EffectStack effect = AetherEffects.resolveDominantEffect(player);
        if(effect == null || !(effect.getEffect() instanceof IHudVisibility)) return "minecraft:gui/hud/heart/";
        return ((IHudVisibility) effect).getPath();
    }


    @Override
    public void renderPreview(Minecraft mc, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
        int x = layout.getComponentX(mc, this, xSizeScreen);
        int y = layout.getComponentY(mc, this, ySizeScreen);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        int health = 11 - barCount;
        for (int i = 0; i < 10; ++i) {
            int xHeart = x + (i) * 8;
            gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture(PREVIEW + "container"));
            if (i * 2 + 1 < health) {
                gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture(PREVIEW + "full"));
            }
            if (i * 2 + 1 == health) {
                gui.drawGuiIcon(xHeart, y, 9, 9, TextureRegistry.getTexture(PREVIEW + "half"));
            }
        }

    }
}
