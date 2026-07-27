package teamport.aether.effect.api;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;

public class HeartContainer {
    public enum HeartGlyphVariant {
        NONE(""),
        HARDCORE("hardcore"),
        PREVIEW("preview"),
        OVERHEAL("overheal");

        private final String name;

        HeartGlyphVariant(String name) {
            this.name = name;
        }
    }

    public enum HeartGlyphType {
        FULL("full"),
        HALF("half"),
        HALF_RIGHT("half_right"),
        CONTAINER("container");

        private final String name;

        HeartGlyphType(String name) {
            this.name = name;
        }
    }

    protected final Player player;

    public HeartContainer(Player player) {
        this.player = player;
    }

    public String getBasePath() {
        return "minecraft:gui/hud/heart/";
    }

    public String getPathForGlyph(HeartGlyphVariant variant, HeartGlyphType type) {
        if (!getBasePath().equals("minecraft:gui/hud/heart/")) {
            return getFlatPathForGlyph(variant, type);
        }

        if (type == HeartGlyphType.CONTAINER) {
            return getBasePath() + type.name + (isHeartFlashing() ? "_blinking" : "");
        }

        String prefix = variant != HeartGlyphVariant.NONE ? variant.name + "/" : "survival/";
        if (variant == HeartGlyphVariant.PREVIEW) {
            prefix = "survival/" + prefix;
        }
        return getBasePath() + prefix + type.name + (isHeartFlashing() ? "_blinking" : "");
    }

    private String getFlatPathForGlyph(HeartGlyphVariant variant, HeartGlyphType type) {
        String name;
        if (type == HeartGlyphType.CONTAINER || variant == HeartGlyphVariant.NONE) {
            name = type.name;
        } else if (variant == HeartGlyphVariant.PREVIEW) {
            name = "preview_" + type.name;
        } else {
            name = variant.name + "_" + type.name;
        }
        boolean preview = variant == HeartGlyphVariant.PREVIEW;
        return getBasePath() + name + (isHeartFlashing() && !preview ? "_blinking" : "");
    }

    public void drawHeart(HeartGlyphVariant variant, HeartGlyphType type, int x, int y, Gui hud) {
        hud.drawGuiIcon(x, y, 9, 9, TextureRegistry.getTexture(getPathForGlyph(variant, type)));
    }

    public boolean isHeartFlashing() {
        return player.heartsFlashTime / 3 % 2 == 1 && player.heartsFlashTime >= 10;
    }

    public boolean shouldShake() {
        return player.getHealth() < 4 || isHeartFlashing();
    }
}
