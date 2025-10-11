package teamport.aether.effect.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.player.Player;

@Environment(EnvType.CLIENT)
public class HeartContainer {
    public enum HeartGlyphVariant {
        NONE(""),
        HARDCORE("hardcore"),
        PREVIEW("preview"),
        OVERHEAL("overheal");

        private final String name;
        HeartGlyphVariant(String colorName) { this.name = colorName; }
        public String getName() { return name; }
    }

    public enum HeartGlyphType {
        FULL("full"),
        HALF("half"),
        HALF_RIGHT("half_right"),
        CONTAINER("container");

        private final String name;
        HeartGlyphType(String colorName) { this.name = colorName; }
        public String getName() { return name; }
    }

    protected final Player player;

    public HeartContainer(Player player) {
        this.player = player;
    }

    public String getBasePath() {
        return "minecraft:gui/hud/heart/";
    }

    public String getPathForGlyph(HeartGlyphVariant glyphVariant, HeartGlyphType glyphType) {
        if (glyphType == HeartGlyphType.CONTAINER) {
            return getBasePath() + glyphType.name + (isHeartFlashing() ? "_blinking" : "");
        }

        String prefix = (glyphVariant != HeartGlyphVariant.NONE ? glyphVariant.name + "_" : "");
        return getBasePath() + prefix + glyphType.name + (isHeartFlashing() ? "_blinking" : "");
    }

    public void drawHeart(HeartGlyphVariant variant, HeartGlyphType glyphType, int x, int y, HudIngame hud) {
        hud.drawGuiIcon(x, y, 9, 9, TextureRegistry.getTexture(getPathForGlyph(variant, glyphType)));
    }

    public boolean isHeartFlashing() {
        boolean heartsFlash = player.heartsFlashTime / 3 % 2 == 1;
        if (player.heartsFlashTime < 10) {
            heartsFlash = false;
        }

        return heartsFlash;
    }

    public boolean shouldShake() {
        return player.getHealth() < 4 || isHeartFlashing();
    }

}

