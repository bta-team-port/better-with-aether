package teamport.aether.effect.render;

import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.TintEffectRender;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.HeartContainer;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.HeartContainerSimple;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.IHasCustomHeartContainer;

public class PoisonEffectRenderer<T extends Effect> extends TintEffectRender<T> implements IHasCustomHeartContainer {
    public final String pathHeart;

    public PoisonEffectRenderer(T effect, String vignette, int tint, String heartPath) {
        super(effect, vignette, tint);
        pathHeart = heartPath;
    }

    @Override
    public float calcAlpha(@NonNull EffectStack effectStack) {
        float currentAmount = (float) effectStack.getDuration() * (effectStack.getAmount() - 1);
        float totalTime = (float) effectStack.getDuration() * effectStack.getEffect().getMaxStack();
        float percent = (currentAmount + effectStack.getTimeLeft()) / totalTime;
        return 0.35F + percent / 2.0F;
    }

    @Override
    public HeartContainer getCustomContainer(Player player) {
        return new HeartContainerSimple(player, pathHeart);
    }
}
