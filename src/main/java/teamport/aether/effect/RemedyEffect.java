package teamport.aether.effect;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.helper.ParticleMaker;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RemedyEffect extends AetherEffect implements ILockInteractable {

    public RemedyEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        Set<Effect> remove = AetherEffects.LookupLooks.instance.getLockedEffects(this);
        if (remove == null) return;
        List<EffectStack> check = new ArrayList<>(effectContainer.getEffects());
        for (EffectStack stack : check) {
            for (Effect effect : remove) {
                if (effect.equals(stack.getEffect())) {
                    effectContainer.remove(stack.getEffect());
                    Mob mob = (Mob) effectContainer.getParent();
                    spawnParticles(mob);
                }
            }
        }
    }

    @Override
    public void lockTriggered(IHasEffects<?> hasEffects) {
        if (!(hasEffects instanceof Mob)) {
            return;
        }
        Mob mob = (Mob) hasEffects;
        spawnParticles(mob);
    }

    private static void spawnParticles(Mob mob) {
        if (EnvironmentHelper.isSinglePlayer()) {
            if (mob instanceof Player) {
                ParticleMaker.spawnRemedyParticle(mob.world, mob.x, mob.y - mob.bbHeight, mob.z, mob.bbHeight, mob.bbWidth);
            } else {
                ParticleMaker.spawnRemedyParticle(mob.world, mob.x, mob.y, mob.z, mob.bbHeight + 0.5, mob.bbWidth);
            }
        } else if (mob instanceof Player) {
            ParticleMaker.spawnRemedyParticle(mob.world, mob.x, mob.y - mob.bbHeight, mob.z, mob.bbHeight + 1.5, mob.bbWidth);
        } else {
            ParticleMaker.spawnRemedyParticle(mob.world, mob.x, mob.y, mob.z, mob.bbHeight + 0.5, mob.bbWidth);
        }
    }
}
