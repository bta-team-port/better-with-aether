package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.helper.ParticleMaker;

import java.util.List;
import java.util.WeakHashMap;

public class PoisonEffect extends Effect {
    private final WeakHashMap<Mob, double[]> drift = new WeakHashMap<>();

    public PoisonEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, @NonNull EffectContainer<T> effectContainer) {
        if (!canApplyTo((Entity) effectContainer.getParent())) {
            return;
        }
        if (AetherEffects.isLocked(effectStack, effectContainer)) {
            return;
        }
        if (effectStack.getAmount() == 1) ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    @Override
    public <T> void expired(@NonNull EffectStack effectStack, @NonNull EffectContainer<T> effectContainer) {
        effectContainer.remove(AetherEffects.poisonEffect);
        if (effectStack.getAmount() > 1) {
            EffectStack newStack = new EffectStack(
                (IHasEffects<?>) effectContainer.getParent(),
                AetherEffects.poisonEffect,
                effectStack.getDuration(),
                effectStack.getAmount() - 1
            );
            newStack.start(effectContainer);
            effectContainer.add(newStack);
        }
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    @Override
    public <T> void tick(EffectStack effectStack, @NonNull EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob mob)) return;
        if (mob.tickCount % 4 == 0) {
            if (mob instanceof Player) {
                Direction dir = Direction.fromYaw(mob.yRot).opposite();
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x + dir.offsetX(), mob.y - 2, mob.z + dir.offsetZ(), mob.bbHeight, mob.bbWidth);
            } else {
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
            }
        }
        slideEntity(mob);
    }

    private void slideEntity(Mob mob) {
        double[] d = drift.computeIfAbsent(mob, k -> new double[2]);
        double gauss = mob.world.rand.nextGaussian();
        d[0] = 0.2 * (0.1 * gauss) + 0.8 * d[0];
        d[1] = 0.125 * (0.7853981633974483 * gauss) + 0.875 * d[1];
        mob.xd += d[0];
        mob.zd += d[0];
        mob.yRot = (float) (mob.yRot + d[1]);
        mob.xRot = (float) (mob.xRot + d[1]);
    }

    @Override
    public <T> void stackAdded(EffectStack effectStack, @NonNull EffectContainer<T> effectContainer) {
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
        super.stackAdded(effectStack, effectContainer);
    }

}
