package teamport.aether.effect.render;

import net.minecraft.core.entity.player.Player;
import teamport.aether.effect.api.Effect;
import teamport.aether.effect.api.EffectStack;
import teamport.aether.effect.api.HeartContainer;
import teamport.aether.effect.api.HeartContainerIconProvider;
import teamport.aether.effect.api.TintEffectRender;

public class PoisonEffectRenderer<T extends Effect> extends TintEffectRender<T> implements HeartContainerIconProvider {
    public final String pathHeart;

    public PoisonEffectRenderer(T effect, String vignette, int tint, String heartPath) {
        super(effect, vignette, tint);
        pathHeart = heartPath;
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F + percent / 2.0F;
    }


    @Override
    public HeartContainer getCustomContainer(Player player) {
        return new HeartContainer(player) {
            @Override
            public String getBasePath() {
                return pathHeart;
            }
        };
    }
}
