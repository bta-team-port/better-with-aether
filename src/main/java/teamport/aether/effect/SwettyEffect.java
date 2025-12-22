package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;

public class SwettyEffect extends Effect {
    public SwettyEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public boolean canApplyTo(Entity target) {
        return target instanceof Player && super.canApplyTo(target);
    }
}
