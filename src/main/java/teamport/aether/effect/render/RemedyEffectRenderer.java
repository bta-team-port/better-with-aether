package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.TintEffectRender;
import teamport.aether.gui.IHudVisibility;

public class RemedyEffectRenderer<T extends Effect> extends TintEffectRender<T> implements IHudVisibility {
    public String PATH_HEART;

    public RemedyEffectRenderer(T effect, String vignette, int tint, String heartPath) {
        super(effect, vignette, tint);
        PATH_HEART = heartPath;
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float percent = (float)effectStack.getTimeLeft() / (float)(effectStack.getDuration());
        return (float)(Math.pow(percent, 4.0f));
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }
}
