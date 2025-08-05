package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.EffectStack;

public class RemedyEffectRenderer extends TintEffectRender {
    public RemedyEffectRenderer(String vignette, int tint) {
        super(vignette, tint);
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float percent = (float)effectStack.getTimeLeft() / (float)(effectStack.getDuration());
        return 0.35F * percent * percent;
    }
}
