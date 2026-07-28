package teamport.aether.effect.render;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.render.TintEffectRender;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.HeartContainer;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.HeartContainerSimple;
import sunsetsatellite.catalyst.effects.api.effect.render.heartContainer.IHasCustomHeartContainer;

public class RemedyEffectRenderer<T extends Effect> extends TintEffectRender<T> implements IHasCustomHeartContainer {
    public final String pathHeart;

    public RemedyEffectRenderer(T effect, String vignette, int tint, String heartPath) {
        super(effect, vignette, tint);
        pathHeart = heartPath;
    }

    @Override
    public float calcAlpha(EffectStack effectStack) {
        float percent = (float) effectStack.getTimeLeft() / (float) effectStack.getDuration();
        return (float) Math.pow(percent, 4.0f);
    }

    @Override
    public HeartContainer getCustomContainer(Player player) {
        return new HeartContainerSimple(player, pathHeart);
    }
}
