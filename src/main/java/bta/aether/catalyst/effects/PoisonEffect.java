package bta.aether.catalyst.effects;

import bta.aether.entity.IAetherEntityLiving;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.DamageType;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;
import java.util.Random;

public class PoisonEffect extends Effect {
    public PoisonEffect(String nameKey, String id, String imagePath, int color, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int defaultDuration, int maxStack) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (effectStack.getEffect() != this) return;
        if (!(effectContainer.getParent() instanceof EntityLiving)) return;
        EntityLiving entityLiving = (EntityLiving) effectContainer.getParent();
        Random random = new Random();
        ((IAetherEntityLiving) entityLiving).setPoisonSlide(random.nextDouble()-0.5, random.nextDouble()-0.5);
        entityLiving.hurt(null, 1, DamageType.GENERIC);
    }

    @Override
    public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (effectStack.getEffect() != this) return;
        int amount = effectStack.getAmount();
        if (amount-1 <= 0) {
            IAetherEntityLiving entityLiving = (IAetherEntityLiving) effectContainer.getParent();
            entityLiving.setPoisonSlide(0, 0);
            return;
        }
        effectContainer.remove(AetherEffects.poisonEffect);
        EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), AetherEffects.poisonEffect, amount-1);
        effectContainer.add(newStack);
        newStack.start(effectContainer);
    }
}
