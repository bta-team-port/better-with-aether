package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.EffectStack;

public class RemedyEffectRenderer extends TintEffectRender {
    public RemedyEffectRenderer(String vignette, int tint) {
        super(vignette, tint);
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float percent = (float)effectStack.getTimeLeft() / (float)(effectStack.getDuration());
        return (float)(Math.pow(percent, 4.0f));
    }
}
