package teamport.aether.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketIceCream;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.effect.AetherEffects;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.helper.HealthHelper;

import java.util.Random;
@Mixin(value = HudComponentHealthBar.class, remap = false)
public abstract class HudComponentHealthBarMixin extends HudComponentMovable {

    @Shadow public abstract boolean isVisible(Minecraft mc);

    @Unique
    Minecraft mc = Minecraft.getMinecraft();

    @Unique
    Random random = new Random();

    @Unique
    int iconWidth = 9;
    @Unique
    int iconHeight = 9;

    @Unique
    int spacing = 1;

    @Override
    public int getYSize(Minecraft mc) {
        if (mc.currentScreen instanceof ScreenHudDesigner || mc.thePlayer == null) return iconHeight + spacing;
        if (!isVisible(mc)) return 0;
        return iconHeight * getRows(mc.thePlayer) + spacing;
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * (float)this.getYSize(mc));
    }

    public HudComponentHealthBarMixin(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Unique
    int getRows(Player player) {
        return (int) Math.ceil((double) player.getMaxHealth() / 20);
    }

    @Unique
    private static String getPath(Player player) {
        EffectStack effect = AetherEffects.resolveDominantEffect(player);
        if( effect != null && effect.getEffect() instanceof IHudVisibility){
            return ((IHudVisibility) effect.getEffect()).getPath();
        }
        return "minecraft:gui/hud/heart/";
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

        for (int barCount = 0; barCount < getRows(player); barCount++) {
            int totalHealth = HealthHelper.getMaxHealth(player);
            int renderHealth = Math.min(totalHealth - barCount * 20, 20);
            int renderHeart = (renderHealth + 1) / 2;

            if (renderHeart == 0) continue;

            // these 2 lines are magical pls do not change
            int barPreviousHealth = prevHealth - barCount * 20;
            int barHealth = health - barCount * 20;

            GL11.glTranslated(0, 0, -0.01 * barCount);
            for (int i = 0; i < renderHeart; ++i) {
                int heartOffset = heartsFlash ? 1 : 0;

                int xHeart = x + i * 8;
                int yHeart = y - (iconHeight * barCount) + this.getYSize(mc) - iconHeight - spacing;

                if (health <= 4) {
                    yHeart += this.random.nextInt(2);
                }

                int currentHeart = i * 2 + 1;
                String heartTexturePath = PATH_HEART + (heartOffset == 0 ? "container" : "container_blinking");
                hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(heartTexturePath));

                if (heartsFlash) {
                    if (currentHeart < barPreviousHealth)
                        hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(PATH_HEART + "full_blinking"));

                    if (currentHeart == barPreviousHealth)
                        hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "half_blinking"));
                }

                if (currentHeart < barHealth) {
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "full"));
                }

                if (currentHeart == barHealth) {
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "half"));
                }

                // healing -------------------------------------------------------------------------------------------------

                if (
                    player.inventory.getCurrentItem() == null
                    || (!(player.inventory.getCurrentItem().getItem() instanceof ItemFood)
                    && !(player.inventory.getCurrentItem().getItem() instanceof ItemBucketIceCream))
                    || !((Boolean) mc.gameSettings.foodHealthRegenOverlay.value)
                ) continue;

                int healing;
                if (player.inventory.getCurrentItem().getItem() instanceof ItemFood) {
                    healing = ((ItemFood) player.inventory.getCurrentItem().getItem()).getHealAmount();
                } else {
                    healing = ((ItemBucketIceCream) player.inventory.getCurrentItem().getItem()).getHealAmount();
                }

                if (currentHeart < barHealth) continue;

                if (currentHeart == barHealth)
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_half_right"));
                else if (currentHeart < barHealth + healing)
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_full"));
                else if (currentHeart == barHealth + healing)
                    hud.drawGuiIcon(xHeart, yHeart, 9, 9, TextureRegistry.getTexture(guiHeart + "preview_half"));
            }
        }
    }
}
