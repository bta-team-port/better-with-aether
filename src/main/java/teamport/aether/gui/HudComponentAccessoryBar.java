package teamport.aether.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudEditor;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.Shaders;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.enums.ArmorHiddenState;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.option.AetherGameSettingsHolder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class HudComponentAccessoryBar extends HudComponentMovable {
    private final @NonNull Random random = new Random();
    private final @NonNull StringBuilder queryBuilder = new StringBuilder();
    private static final @NonNull String SLOT_EMPTY = "empty";
    private static final @NonNull String SLOT_UNKNOWN = "unknown";
    private static final @NonNull NamespaceID PREVIEW_HELMET_ID = new NamespaceID("aether", "item/armor_gloves_gravitite");
    private static final @NonNull NamespaceID PREVIEW_CHESTPLATE_ID = new NamespaceID("aether", "item/armor_cape_red");
    private static final @NonNull NamespaceID PREVIEW_LEGGINGS_ID = new NamespaceID("aether", "item/armor_talisman_zanite");
    private static final @NonNull NamespaceID PREVIEW_BOOTS_ID = new NamespaceID("aether", "item/armor_talisman_feather_gold");
    private final Map<String, IconCoordinate> icons = new HashMap<>();
    private final Map<String, Boolean> iconExists = new HashMap<>();
    private int cachedReloadGeneration = -1;
    private final @NonNull HumanAccessoryShape targetShape;
    private final int slotIndex;

    public HudComponentAccessoryBar(@NonNull String key, @NonNull Layout layout, @NonNull HumanAccessoryShape targetShape, int slotIndex) {
        super(key, 9, 12, layout);
        this.targetShape = targetShape;
        this.slotIndex = slotIndex;
    }

    public HudComponentAccessoryBar(@NonNull String key, @NonNull Layout layout, @NonNull HumanAccessoryShape targetShape) {
        this(key, layout, targetShape, targetShape.getSlotIndex());
    }

    @Override
    public int getDisplayedYSize() {
        int baseSize = super.getDisplayedYSize();
        if (baseSize == 0) {
            return 0;
        } else if (mc.currentScreen instanceof ScreenHudEditor) {
            return 12;
        } else {
            ItemStack piece = this.getWornAccessory();
            return piece != null && AccessoryState.calculate(piece) != AccessoryState.EMPTY ? 12 : 9;
        }
    }

    private boolean isFlipped() {
        return switch (this.getKey()) {
            case "gloves_bar" -> AetherGameSettingsHolder.FLIP_GLOVES_BAR.value;
            case "capes_bar" -> AetherGameSettingsHolder.FLIP_CAPE_BAR.value;
            case "trinket_1_bar" -> AetherGameSettingsHolder.FLIP_TRINKET_1_BAR.value;
            case "trinket_2_bar" -> AetherGameSettingsHolder.FLIP_TRINKET_2_BAR.value;
            default -> false;
        };
    }

    private @NonNull ArmorHiddenState getHiddenState() {
        return switch (this.getKey()) {
            case "gloves_bar" -> AetherGameSettingsHolder.HIDE_GLOVES_BAR.value;
            case "capes_bar" -> AetherGameSettingsHolder.HIDE_CAPE_BAR.value;
            case "trinket_1_bar" -> AetherGameSettingsHolder.HIDE_TRINKET_1_BAR.value;
            case "trinket_2_bar" -> AetherGameSettingsHolder.HIDE_TRINKET_2_BAR.value;
            default -> ArmorHiddenState.NEVER;
        };
    }

    private @NonNull IconCoordinate getIcon(@NonNull String query) {
        return this.icons.computeIfAbsent(query, TextureRegistry::getTexture);
    }

    private @Nullable String resolveItemIcon(@NonNull Item item, @NonNull AccessoryState state) {
        String query = this.itemQuery(item, state);
        Boolean exists = this.iconExists.get(query);
        if (exists == null) {
            exists = TextureRegistry.hasSourceFile(query);
            this.iconExists.put(query, exists);
        }

        return exists ? query : null;
    }

    private void refreshCachesIfStale() {
        int generation = TextureRegistry.reloadGeneration;
        if (generation != this.cachedReloadGeneration) {
            this.cachedReloadGeneration = generation;
            this.icons.clear();
            this.iconExists.clear();
        }

    }

    private @NonNull String itemQuery(@NonNull Item item, @NonNull AccessoryState state) {
        this.queryBuilder.setLength(0);

        if (item instanceof ItemQuiver) {
            this.queryBuilder.append("minecraft:gui/hud/armor_bar/item/armor_quiver/").append(state.getRegistry());
            return this.queryBuilder.toString();
        }
        if (item instanceof ItemQuiverEndless) {
            this.queryBuilder.append("minecraft:gui/hud/armor_bar/item/armor_quiver_gold/").append(state.getRegistry());
            return this.queryBuilder.toString();
        }

        return this.itemQuery(item.namespaceID, state);
    }

    private @NonNull String itemQuery(@NonNull NamespaceID itemKey, @NonNull AccessoryState state) {
        this.queryBuilder.setLength(0);
        this.queryBuilder.append(itemKey.namespace()).append(":gui/hud/accessory_bar/").append(itemKey.value()).append('/').append(state.getRegistry());
        return this.queryBuilder.toString();
    }

    private @NonNull String slotQuery(@NonNull String name) {
        this.queryBuilder.setLength(0);
        this.queryBuilder.append("aether:gui/hud/accessory_bar/slot/").append(this.getShapeName()).append('/').append(name);
        return this.queryBuilder.toString();
    }

    private @NonNull String getShapeName() {
        String var10000;
        switch (this.targetShape) {
            case GLOVES -> var10000 = "gloves";
            case CAPE -> var10000 = "cape";
            case TRINKET -> var10000 = "trinket";
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    public boolean isVisible() {
        if (!GameSettings.IMMERSIVE_MODE.drawHotbar()) {
            return false;
        } else {
            ArmorHiddenState hiddenState = this.getHiddenState();
            if (hiddenState == ArmorHiddenState.ALWAYS) {
                return false;
            } else if (hiddenState != ArmorHiddenState.WHEN_NOT_WEARING) {
                return true;
            } else {
                ItemStack accessoryStack;
                return mc.thePlayer != null && (accessoryStack = this.getWornAccessory()) != null && AccessoryState.calculate(accessoryStack) != AccessoryState.EMPTY;
            }
        }
    }

    @Override
    public boolean isPreviewTranslucent() {
        return this.getHiddenState() == ArmorHiddenState.ALWAYS;
    }

    public void render(HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {
        this.refreshCachesIfStale();
        boolean isFlipped = this.isFlipped();
        ItemStack accessoryStack = this.getWornAccessory();
        double currentDurability = 0.0F;
        double maxDurability = 0.0F;
        AccessoryState state = AccessoryState.EMPTY;
        String query = this.slotQuery(SLOT_EMPTY);
        if (accessoryStack != null) {
            Item item = accessoryStack.getItem();
            maxDurability = accessoryStack.getMaxDamage();
            currentDurability = maxDurability > 0.0D ? maxDurability - (double) accessoryStack.getItemDamageForDisplay() : 0.0D;

            boolean isAccessoryItem = item instanceof IAccessoryItem<?>;
            boolean isQuiver = item instanceof ItemQuiver || item instanceof ItemQuiverEndless;

            if (isAccessoryItem || isQuiver) {
                state = AccessoryState.calculate(accessoryStack);
            }

            if (state != AccessoryState.EMPTY) {
                String itemIcon = this.resolveItemIcon(item, state);
                if (itemIcon != null) {
                    query = itemIcon;
                } else if (!isAccessoryItem) {
                    query = this.slotQuery(SLOT_EMPTY);
                } else {
                    query = this.slotQuery(SLOT_UNKNOWN);
                }
            }
        }

        int x = this.getLayout().getComponentX(this, xSizeScreen);
        int y = this.getLayout().getComponentY(this, ySizeScreen);
        int iconY = y;
        boolean inAcidWarning = mc.thePlayer != null && mc.thePlayer.shouldShowAcidVisualEffects() && mc.thePlayer.getGamemode().hasToolDurability();
        if (maxDurability > (double) 0.0F && (currentDurability / maxDurability <= 0.15 || inAcidWarning) && state != AccessoryState.EMPTY) {
            this.random.setSeed((long) hud.updateCounter * 312871L + (long) this.targetShape.getSlotIndex() * 2654435769L);
            iconY = y + this.random.nextInt(2);
        }

        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GLRenderer.enableState(State.BLEND);
        TextureRegistry.guiSpriteAtlas.bind();
        TessellatorGeneral t = GLRenderer.getTessellator();
        t.startDrawingQuads();

        boolean flipIcon = isFlipped && state == AccessoryState.HALF;
        this.drawIcon(t, x, iconY, this.getIcon(query), flipIcon);

        t.draw();
        this.drawDurabilityBar(hud, x, y, state, currentDurability, maxDurability, 255, isFlipped, inAcidWarning);
    }

    @Override
    public void renderPreview(Gui gui, @NonNull Layout layout, int xSizeScreen, int ySizeScreen) {
        int x = layout.getComponentX(this, xSizeScreen);
        int y = layout.getComponentY(this, ySizeScreen);
        GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderTranslucency();
        int alpha = this.isPreviewTranslucent() ? 85 : 255;

        NamespaceID previewId;
        int previewDurability = switch (this.getKey()) {
            case "gloves_bar" -> {
                previewId = PREVIEW_HELMET_ID;
                yield 45;
            }
            case "capes_bar" -> {
                previewId = PREVIEW_CHESTPLATE_ID;
                yield 35;
            }
            case "trinket_1_bar" -> {
                previewId = PREVIEW_LEGGINGS_ID;
                yield 25;
            }
            default -> {
                previewId = PREVIEW_BOOTS_ID;
                yield 15;
            }
        };

        boolean isFlipped = this.isFlipped();

        String query = this.itemQuery(previewId, AccessoryState.HALF);
        AccessoryState previewState = AccessoryState.HALF;

        if (!TextureRegistry.hasSourceFile(query)) {
            query = this.itemQuery(previewId, AccessoryState.INFINITE);
            previewState = AccessoryState.INFINITE;

            if (!TextureRegistry.hasSourceFile(query)) {
                String unknown = this.slotQuery(SLOT_UNKNOWN);
                query = TextureRegistry.hasSourceFile(unknown) ? unknown : this.slotQuery(SLOT_EMPTY);
                previewState = AccessoryState.EMPTY;
            }
        }

        TextureRegistry.guiSpriteAtlas.bind();
        TessellatorGeneral t = GLRenderer.getTessellator();
        t.startDrawingQuads();

        this.drawIcon(t, x, y, this.getIcon(query), isFlipped);

        t.draw();

        if (previewState != AccessoryState.EMPTY) {
            this.drawDurabilityBar(gui, x, y, previewState, previewDurability, 100.0, alpha, isFlipped, false);
        }
    }

    private void drawIcon(@NonNull TessellatorGeneral tessellator, double x, double y, @NonNull IconCoordinate icon, boolean flipHorizontally) {
        double uMin = icon.getIconUMin();
        double uMax = icon.getIconUMax();
        double vMin = icon.getIconVMin();
        double vMax = icon.getIconVMax();

        if (flipHorizontally) {
            double temp = uMin;
            uMin = uMax;
            uMax = temp;
        }

        tessellator.addVertexWithUV(x, y + 9.0, 0.0, uMin, vMax);
        tessellator.addVertexWithUV(x + 9.0, y + 9.0, 0.0, uMax, vMax);
        tessellator.addVertexWithUV(x + 9.0, y, 0.0, uMax, vMin);
        tessellator.addVertexWithUV(x, y, 0.0, uMin, vMin);
    }

    private void drawDurabilityBar(@NonNull Gui gui, int x, int y, AccessoryState state, double currentDurability, double maxDurability, int alpha, boolean isFlipped, boolean acidWarning) {
        GLRenderer.enableState(State.BLEND);
        GLRenderer.setShader(Shaders.COLOR);
        GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
        TessellatorGeneral tess = GLRenderer.getTessellator();
        tess.startDrawingQuads();
        int barMaxWidth = 9;
        int barY = y + 10;
        int a = alpha << 24;
        gui.drawRect(tess, x, barY, x + 9, barY + 2, a);
        if (state == AccessoryState.INFINITE) {
            int magentaColor = 16711935 | a;
            gui.drawRect(tess, x, barY, x + barMaxWidth, barY + 1, magentaColor);
        } else {
            if (maxDurability <= (double) 0.0F) {
                barMaxWidth = 8;
            }

            int barWidth = (int) Math.round(currentDurability / maxDurability * (double) barMaxWidth);
            int progress = (int) Math.round(currentDurability / maxDurability * (double) 255.0F);
            int colorFG;
            int colorBG;
            if (acidWarning) {
                float t = (float) progress / 255.0F;
                colorFG = Color.HSBtoRGB(0.11F + t * 0.06F, 1.0F, 1.0F);
                colorBG = Color.HSBtoRGB(0.11F, 1.0F, 0.35F);
            } else {
                colorFG = Color.HSBtoRGB((float) progress / 255.0F / 3.0F, 1.0F, 1.0F);
                colorBG = ((255 - progress) / 4) << 16 | 16128;
            }

            colorFG = colorFG & 16777215 | a;
            colorBG = colorBG & 16777215 | a;
            gui.drawRect(tess, x, barY, x + barMaxWidth, barY + 1, colorBG);
            if (barWidth > 0) {
                if (isFlipped) {
                    int startX = x + barMaxWidth - barWidth;
                    gui.drawRect(tess, startX, barY, startX + barWidth, barY + 1, colorFG);
                } else {
                    gui.drawRect(tess, x, barY, x + barWidth, barY + 1, colorFG);
                }
            }
        }

        tess.draw();
    }

    private @Nullable ItemStack getWornAccessory() {
        if (mc.thePlayer == null) {
            return null;
        }

        if (mc.thePlayer.inventory instanceof IContainerInventoryAether accessoryInv) {
            ItemStack[] accessories = accessoryInv.aether$getAccessoryInventory();

            if (this.slotIndex >= 0 && this.slotIndex < accessories.length) {
                return accessories[this.slotIndex];
            }
        }

        return null;
    }

    @Environment(EnvType.CLIENT)
    public enum AccessoryState {
        EMPTY,
        HALF,
        FULL,
        INFINITE;

        AccessoryState() {
        }

        public @NonNull String getRegistry() {
            return this.name().toLowerCase();
        }

        public static @NonNull AccessoryState calculate(@NonNull ItemStack accessoryStack) {
            int maxDurability = accessoryStack.getMaxDamage();
            if (maxDurability <= 0) {
                return INFINITE;
            } else {
                int currentDurability = maxDurability - accessoryStack.getItemDamageForDisplay();
                if (currentDurability <= 0) {
                    return EMPTY;
                } else {
                    float durabilityPercentage = (float) currentDurability / (float) maxDurability;
                    return durabilityPercentage > 0.5F ? FULL : HALF;
                }
            }
        }
    }
}
