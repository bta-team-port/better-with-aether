package teamport.aether.mixin.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentHealthBar;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.effect.api.EffectRendererManager;
import teamport.aether.effect.api.EffectStack;
import teamport.aether.effect.api.HeartContainer;
import teamport.aether.effect.api.HeartContainerIconProvider;
import teamport.aether.effect.api.IHasEffects;
import teamport.aether.effect.api.IEffectRenderer;

import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(value = HudComponentHealthBar.class, remap = false)
public abstract class HudComponentHealthBarMixin extends HudComponentMovable {
    @Shadow
    public abstract int getDisplayedYSize();

    @Unique
    private final Random aether$random = new Random();

    protected HudComponentHealthBarMixin(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Override
    public int getBaseYSize() {
        if (mc.thePlayer == null) return super.getBaseYSize();
        return Math.max(super.getBaseYSize(), aether$getRows(mc.thePlayer) * 10);
    }

    @Unique
    private int aether$getRows(Player player) {
        return Math.max(1, (player.getMaxHealth() + 19) / 20);
    }

    @Unique
    private HeartContainer aether$getHeartContainer(Player player, EffectStack stack) {
        if (stack != null) {
            IEffectRenderer renderer = EffectRendererManager.getInstance().get(stack.getEffect());
            if (renderer instanceof HeartContainerIconProvider provider) {
                return provider.getCustomContainer(player);
            }
        }
        return new HeartContainer(player);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderCustomHealthBar(HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci) {
        Player player = mc.thePlayer;
        if (player == null) return;

        EffectStack customStack = EffectRendererManager.resolveDominantHeartContainer(((IHasEffects<?>) player).getContainer());
        int maxHealth = player.getMaxHealth();
        if (customStack == null && maxHealth <= 20) return;

        ci.cancel();
        HeartContainer heartContainer = aether$getHeartContainer(player, customStack);
        int x = getLayout().getComponentX(this, xSizeScreen);
        int y = getLayout().getComponentY(this, ySizeScreen);
        int rows = aether$getRows(player);

        aether$random.setSeed(hud.updateCounter * 312871L);
        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GLRenderer.enableState(State.BLEND);
        for (int row = 0; row < rows; row++) {
            int healthInRow = Math.min(maxHealth - row * 20, 20);
            int healthInCurrentRow = Math.max(0, player.getHealth() - row * 20);
            int rowY = y + (rows - 1 - row) * 10;
            aether$drawRow(hud, player, heartContainer, healthInRow, healthInCurrentRow, x, rowY);
        }
        GLRenderer.disableState(State.BLEND);
    }

    @Unique
    private void aether$drawRow(Gui hud, Player player, HeartContainer heartContainer, int healthInRow,
                                int healthInCurrentRow, int x, int y) {
        int heartsToRender = (healthInRow + 1) / 2;
        HeartContainer.HeartGlyphVariant variant = player.getGamemode() == Gamemodes.HARDCORE
            ? HeartContainer.HeartGlyphVariant.HARDCORE
            : HeartContainer.HeartGlyphVariant.NONE;

        for (int i = 0; i < heartsToRender; i++) {
            int xHeart = x + i * 8;
            int yHeart = y + (heartContainer.shouldShake() ? aether$random.nextInt(2) : 0);
            int currentHeart = i * 2 + 1;

            heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.CONTAINER, xHeart, yHeart, hud);
            if (currentHeart < healthInCurrentRow) {
                heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.FULL, xHeart, yHeart, hud);
            } else if (currentHeart == healthInCurrentRow) {
                heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.HALF, xHeart, yHeart, hud);
            }

            if (player.inventory.getCurrentItem() != null
                && player.inventory.getCurrentItem().getItem() instanceof ItemFood
                && GameSettings.FOOD_HEALTH_REGEN_OVERLAY.value
                && currentHeart >= healthInCurrentRow) {

                int healing = ((ItemFood) player.inventory.getCurrentItem().getItem())
                    .getHealAmount(player.inventory.getCurrentItem());
                if (currentHeart == healthInCurrentRow) {
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW,
                        HeartContainer.HeartGlyphType.HALF_RIGHT, xHeart, yHeart, hud);
                } else if (currentHeart < healthInCurrentRow + healing) {
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW,
                        HeartContainer.HeartGlyphType.FULL, xHeart, yHeart, hud);
                } else if (currentHeart == healthInCurrentRow + healing) {
                    heartContainer.drawHeart(HeartContainer.HeartGlyphVariant.PREVIEW,
                        HeartContainer.HeartGlyphType.HALF, xHeart, yHeart, hud);
                }
            }
        }
    }

    @Inject(method = "renderPreview", at = @At("HEAD"), cancellable = true)
    private void renderCustomHealthBarPreview(Gui gui, Layout layout, int xSizeScreen, int ySizeScreen,
                                              CallbackInfo ci) {
        Player player = mc.thePlayer;
        if (player == null) return;

        EffectStack customStack = EffectRendererManager.resolveDominantHeartContainer(
            ((IHasEffects<?>) player).getContainer());
        if (customStack == null && aether$getRows(player) == 1) return;

        ci.cancel();
        int x = layout.getComponentX(this, xSizeScreen);
        int y = layout.getComponentY(this, ySizeScreen);
        HeartContainer heartContainer = new HeartContainer(player);

        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GLRenderer.disableState(State.BLEND);
        for (int row = 0; row < 2; row++) {
            int healthInCurrentRow = Math.max(0, 21 - row * 20);
            aether$drawPreviewRow(gui, player, heartContainer, healthInCurrentRow, x, y + 10 - row * 10);
        }
    }

    @Unique
    private void aether$drawPreviewRow(Gui gui, Player player, HeartContainer heartContainer,
                                       int healthInCurrentRow, int x, int y) {
        HeartContainer.HeartGlyphVariant variant = player.getGamemode() == Gamemodes.HARDCORE
            ? HeartContainer.HeartGlyphVariant.HARDCORE
            : HeartContainer.HeartGlyphVariant.NONE;

        for (int i = 0; i < 10; i++) {
            int xHeart = x + i * 8;
            int currentHeart = i * 2 + 1;
            heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.CONTAINER, xHeart, y, gui);
            if (currentHeart < healthInCurrentRow) {
                heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.FULL, xHeart, y, gui);
            } else if (currentHeart == healthInCurrentRow) {
                heartContainer.drawHeart(variant, HeartContainer.HeartGlyphType.HALF, xHeart, y, gui);
            }
        }
    }
}
