package teamport.aether.mixin.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DyeColor;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import teamport.aether.effect.AetherEffects;
import teamport.aether.effect.render.AetherCustomHeartContainer;
import teamport.aether.effect.render.HeartContainer;
import teamport.aether.gameSettings.AetherGameSettingsOptions;
import teamport.aether.gameSettings.ExtraHealthDisplayEnum;
import teamport.aether.helper.HealthHelper;

import java.util.Random;

@Mixin(value = HudComponentHealthBar.class, remap = false)
public abstract class HudComponentHealthBarMixin extends HudComponentMovable {

    @Unique
    Random random = new Random();

    @Unique
    int iconWidth = 9;
    @Unique
    int iconHeight = 9;

    @Unique
    int spacing = 1;

    public HudComponentHealthBarMixin(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Unique
    private ExtraHealthDisplayEnum getHeartDisplay(Minecraft mc) {
        return ((AetherGameSettingsOptions) mc.gameSettings).aether$getExtraHealthDisplayOptionEnum().value;
    }

    @Unique
    int getRows(Player player) {
        return (int) Math.ceil((double) player.getMaxHealth() / 20);
    }

    @Override
    public int getYSize(Minecraft mc) {
        ExtraHealthDisplayEnum extraHealthDisplay = getHeartDisplay(mc);

        if (extraHealthDisplay == ExtraHealthDisplayEnum.EXTRA_BARS) {
            return iconHeight * getRows(mc.thePlayer) + spacing;
        }

        return super.getYSize(mc);
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int) (anchor.yPosition * (float) this.getYSize(Minecraft.getMinecraft()));
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {
        Player player = mc.thePlayer;

        EffectStack stack = AetherEffects.resolveDominantEffect(player);
        if (stack == null && getRows(player) == 1) return;

        HeartContainer heartContainer = null;
        ci.cancel();

        if (stack != null) {
            EffectRenderer<?> renderer = EffectRendererDispatcher.getInstance().getDispatch(stack.getEffect());
            if (renderer instanceof AetherCustomHeartContainer) {
                heartContainer = ((AetherCustomHeartContainer) renderer).getCustomContainer(player);
            }
        }

        if (heartContainer == null) heartContainer = new HeartContainer(player);

        int x = this.getLayout().getComponentX(mc, this, xSizeScreen);
        int y = this.getLayout().getComponentY(mc, this, ySizeScreen);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);

        this.random.setSeed((long) hud.updateCounter * 312871L);

        ExtraHealthDisplayEnum extraHealthDisplay = getHeartDisplay(mc);

        if (extraHealthDisplay == ExtraHealthDisplayEnum.EXTRA_BARS) {
            drawExtraBars(mc, hud, player, heartContainer, x, y);
        }

        if (extraHealthDisplay == ExtraHealthDisplayEnum.MULTIPLIER) {
            drawNumberBar(mc, hud, player, heartContainer, x, y);
        }
    }

    @Unique
    private void drawNumberBar(Minecraft mc, HudIngame hud, Player player, HeartContainer heartContainer, int x, int y) {
        float playerHealthPercent = (float) player.getHealth() / HealthHelper.getMaxHealth(player);
        int extraHearts = HealthHelper.getMaxHealth(player) / 2;
        final int heartsToRender = 8;

        drawRow(
                mc, hud, player,
                heartContainer,
                heartsToRender,
                (int) (playerHealthPercent * (heartsToRender * 2)),
                x, y
        );

        hud.drawString(
                mc.font,
                String.format("+%s", extraHearts),
                x + 3 * spacing + (heartsToRender - 1) * iconWidth,
                y + (heartContainer.shouldShake() ? this.random.nextInt(2) : 0),
                DyeColor.WHITE.color.value
        );
    }

    @Unique
    public void drawExtraBars(Minecraft mc, HudIngame hud, Player player, HeartContainer heartContainer, int x, int y) {
        int health = player.getHealth();

        for (int barCount = 0; barCount < getRows(player); barCount++) {
            int totalHealth = HealthHelper.getMaxHealth(player);

            int healthInRow = Math.min(totalHealth - barCount * 20, 20);
            int heartsToRender = (healthInRow + 1) / 2;
            if (heartsToRender == 0) continue;

            int healthInCurrentRow = health - barCount * 20;
            GL11.glTranslated(0, 0, -0.01 * barCount);

            int rowY = y - (iconHeight * barCount) + this.getYSize(mc) - iconHeight - spacing;
            drawRow(mc, hud, player, heartContainer, heartsToRender, healthInCurrentRow, x, rowY);
        }
    }

    @Unique
    public void drawRow(Minecraft mc, HudIngame hud, Player player, HeartContainer heartContainer, int heartsToRender, int healthInCurrentRow, int x, int y) {
        HeartContainer.HeartGlyphVariant glyphVariant = player.getGamemode() == Gamemode.hardcore
                ? HeartContainer.HeartGlyphVariant.HARDCORE
                : HeartContainer.HeartGlyphVariant.NONE;

        for (int i = 0; i < heartsToRender; ++i) {
            int xHeart = x + i * 8;
            int yHeart = y;

            if (heartContainer.shouldShake()) {
                yHeart += this.random.nextInt(2);
            }

            int currentHeart = i * 2 + 1;

            heartContainer.drawHeart(glyphVariant, HeartContainer.HeartGlyphType.CONTAINER, xHeart, yHeart, hud);

            if (currentHeart < healthInCurrentRow)
                heartContainer.drawHeart(glyphVariant, HeartContainer.HeartGlyphType.FULL, xHeart, yHeart, hud);

            if (currentHeart == healthInCurrentRow)
                heartContainer.drawHeart(glyphVariant, HeartContainer.HeartGlyphType.HALF, xHeart, yHeart, hud);

            if (player.inventory.getCurrentItem() != null
                    && player.inventory.getCurrentItem().getItem() instanceof ItemFood
                    && mc.gameSettings.foodHealthRegenOverlay.value
            ) {
                int healing = ((ItemFood) player.inventory.getCurrentItem().getItem()).getHealAmount();

                if (currentHeart < healthInCurrentRow) continue;

                if (currentHeart == healthInCurrentRow)
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW, HeartContainer.HeartGlyphType.HALF_RIGHT, xHeart, yHeart, hud);

                else if (currentHeart < healthInCurrentRow + healing)
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW, HeartContainer.HeartGlyphType.FULL, xHeart, yHeart, hud);

                else if (currentHeart == healthInCurrentRow + healing)
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW, HeartContainer.HeartGlyphType.HALF, xHeart, yHeart, hud);
            }
        }
    }
}
