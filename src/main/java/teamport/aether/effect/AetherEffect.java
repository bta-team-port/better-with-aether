package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;

public class AetherEffect extends Effect {
    public AetherEffect(
        String nameKey, String id,
        List<Modifier<?>> modifiers,
        EffectTimeType effectTimeType, int maxStack
    ) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public boolean canApplyTo(Entity target) {
        return target instanceof Mob && super.canApplyTo(target);
    }
}
