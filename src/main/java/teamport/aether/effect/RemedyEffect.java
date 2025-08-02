package teamport.aether.effect;

import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.gui.IHudVisibility;

import java.util.List;

public class RemedyEffect extends Effect implements IHudVisibility {

    public RemedyEffect(String nameKey, String id, String imagePath, int color, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int defaultDuration, int maxStack) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
    }

    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        effectContainer.removeAll();
    }

    @Override
    public String getPath() {
        return "";
    }
}