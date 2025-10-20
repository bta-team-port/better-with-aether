package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRenderer;

public class ExtraHealthEffectRenderer<T extends Effect> extends EffectRenderer<T> {
    public ExtraHealthEffectRenderer(T effect) {
        super(effect);
    }

    @Override
    public boolean shouldDisplayIcon() {
        return false;
    }

}
