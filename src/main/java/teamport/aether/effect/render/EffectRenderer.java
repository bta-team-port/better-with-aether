package teamport.aether.effect.render;

import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import teamport.aether.gui.IHudVisibility;

public interface EffectRenderer {
    void drawEffect(int width, int height, EffectStack effectStack, IHudVisibility effect);
}
