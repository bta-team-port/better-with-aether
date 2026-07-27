package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import teamport.aether.effect.api.EffectContainer;
import teamport.aether.effect.api.EffectStack;
import teamport.aether.effect.api.EffectTimeType;
import teamport.aether.effect.api.IHasEffects;
import teamport.aether.effect.api.Modifier;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;

import java.util.List;

public class PoisonEffect extends AetherEffect {
    public PoisonEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!canApplyTo((Entity) effectContainer.getParent())) {
            return;
        }
        if (AetherEffects.isLocked(effectStack, effectContainer)) {
            return;
        }
        if (effectStack.getAmount() == 1) ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    @Override
    public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
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
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob)) return;
        Mob mob = (Mob) effectContainer.getParent();
        if (mob.world == null) {
            AetherMod.LOGGER.warn("PoisonEffect is not applied cause the world is null");
            return;
        }
        if (mob.tickCount % 4 == 0) {
            if (mob instanceof Player) {
                Direction dir = Direction.fromYaw(mob.yRot).opposite();
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x + dir.offsetX(), mob.y - 2, mob.z + dir.offsetZ(), mob.bbHeight, mob.bbWidth);
            } else {
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
            }
        }
        slideEntity(mob, effectStack);
    }

    private void slideEntity(Mob mob, EffectStack effectStack) {
        double gauss = mob.world.rand.nextGaussian();
        double newMotD = 0.1 * gauss;
        double motD = 0.2 * newMotD + (1.0 - 0.2) * effectStack.getMotionDrift();
        effectStack.setMotionDrift(motD);
        mob.xd += motD;
        mob.zd += motD;
        double newRotD = 0.7853981633974483 * gauss;
        double rotD = 0.125 * newRotD + (1.0 - 0.125) * effectStack.getRotationDrift();
        effectStack.setRotationDrift(rotD);
        mob.yRot = (float) (mob.yRot + rotD);
        mob.xRot = (float) (mob.xRot + rotD);
    }

    @Override
    public <T> void stackAdded(EffectStack effectStack, EffectContainer<T> effectContainer) {
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
        super.stackAdded(effectStack, effectContainer);
    }

}
