package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.TintEffectRender;
import teamport.aether.gui.IHudVisibility;

public class PoisonEffectRenderer<T extends Effect> extends TintEffectRender<T> implements IHudVisibility {
    public String PATH_HEART;

    public PoisonEffectRenderer(T effect, String vignette, int tint,  String heartPath) {
        super(effect, vignette, tint);
        PATH_HEART = heartPath;
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F +  percent / 3.0F;
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }
}
