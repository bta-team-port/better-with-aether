package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import teamport.aether.effect.api.Effect;
import teamport.aether.effect.api.EffectTimeType;
import teamport.aether.effect.api.Modifier;

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
