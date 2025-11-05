package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;

import java.util.ArrayList;
import java.util.List;

public class RegenerationEffect extends Effect {
    static final int tickBetweenHealing = 10;

    public RegenerationEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public boolean canApplyTo(Entity target) {
        return target instanceof Mob && super.canApplyTo(target);
    }

    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob)) return;
        Mob mob = (Mob) effectContainer.getParent();
        if(mob.tickCount % tickBetweenHealing == 0){
            mob.heal(1);
            ParticleMaker.spawnHeartParticles(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
        }
    }
}
