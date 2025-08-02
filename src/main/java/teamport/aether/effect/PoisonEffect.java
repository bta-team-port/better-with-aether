package teamport.aether.effect;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.gui.IHudVisibility;

import java.util.List;
import java.util.Random;

public class PoisonEffect extends Effect implements IHudVisibility {
    public final String PATH_HEART = "aether:gui/hud/poison/";
    Random random = new Random();
    double slideX, slideZ;

    public PoisonEffect(String nameKey, String id, String imagePath, int color, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int defaultDuration, int maxStack) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if(effectStack.getAmount() == 1) ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
        this.slideX = random.nextGaussian() * 0.02;
        this.slideZ = random.nextGaussian() * 0.02;
    }

    @Override
    public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
        effectContainer.remove(AetherEffects.poisonEffect);
        EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), AetherEffects.poisonEffect, effectStack.getAmount() - 1);
        newStack.start(effectContainer);
        effectContainer.add(newStack);
        ((Mob) effectContainer.getParent()).hurt(null, 4, DamageType.GENERIC);
    }

    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob)) return;
        ((Mob)effectContainer.getParent()).fling(slideX, -0.001, slideZ, 1);
    }

    public <T> void stackAdded(EffectStack effectStack, EffectContainer<T> effectContainer) {
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    public String getPath(){
        return PATH_HEART;
    }
}
