package bta.aether.catalyst.effects;

import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;

public class RemedyEffect extends Effect {
    public RemedyEffect(String nameKey, String id, String imagePath, int color, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int defaultDuration, int maxStack) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        effectContainer.remove(AetherEffects.poisonEffect);
    }
}
