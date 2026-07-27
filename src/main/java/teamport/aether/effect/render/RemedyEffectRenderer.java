package teamport.aether.effect.render;

import net.minecraft.core.entity.player.Player;
import teamport.aether.effect.api.Effect;
import teamport.aether.effect.api.EffectStack;
import teamport.aether.effect.api.HeartContainer;
import teamport.aether.effect.api.HeartContainerIconProvider;
import teamport.aether.effect.api.TintEffectRender;

public class RemedyEffectRenderer<T extends Effect> extends TintEffectRender<T> implements HeartContainerIconProvider {
    public final String pathHeart;

    public RemedyEffectRenderer(T effect, String vignette, int tint, String heartPath) {
        super(effect, vignette, tint);
        pathHeart = heartPath;
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float percent = (float) effectStack.getTimeLeft() / (float) (effectStack.getDuration());
        return (float) (Math.pow(percent, 4.0f));
    }

    @Override
    public HeartContainer getCustomContainer(Player player) {
        return new HeartContainer(player) {
            @Override
            public String getBasePath() {
                return pathHeart;
            }

            @Override
            public String getPathForGlyph(HeartGlyphVariant variant, HeartGlyphType type) {
                if (variant == HeartGlyphVariant.PREVIEW && type == HeartGlyphType.HALF_RIGHT) {
                    return pathHeart + "preview_right";
                }
                return super.getPathForGlyph(variant, type);
            }
        };
    }
}
