package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.EffectStack;

public class PoisonEffectRenderer extends TintEffectRender {
    public PoisonEffectRenderer(String vignette, int tint) {
        super(vignette, tint);
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F +  percent / 3.0F;
    }
}
