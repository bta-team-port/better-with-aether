package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;

import java.util.List;
import java.util.Random;

public class PoisonEffect extends AetherEffect {
    public final Random random = new Random();
    private double rotD;
    private double motD;

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
        EffectStack newStack = new EffectStack((IHasEffects<?>) effectContainer.getParent(), AetherEffects.poisonEffect, effectStack.getAmount() - 1);
        newStack.start(effectContainer);
        effectContainer.add(newStack);
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    // TODO Maybe apply poison more frequently, also fix the overlay when the duration is very long
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
                Direction dir = Direction.getDirection(mob).getOpposite();
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x + dir.getOffsetX(), mob.y - 2, mob.z + dir.getOffsetZ(), mob.bbHeight, mob.bbWidth);
            } else {
                ParticleMaker.spawnPoisonParticles(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
            }
        }
        slideEntity(mob);
    }

    private void slideEntity(Mob mob) {
        double gauss = this.random.nextGaussian();
        double newMotD = 0.1 * gauss;
        motD = 0.2 * newMotD + (1.0 - 0.2) * motD;
        mob.xd += motD;
        mob.zd += motD;
        double newRotD = 0.7853981633974483 * gauss;
        rotD = 0.125 * newRotD + (1.0 - 0.125) * rotD;
        mob.yRot = (float) (mob.yRot + rotD);
        mob.xRot = (float) (mob.xRot + rotD);
    }

    @Override
    public <T> void stackAdded(EffectStack effectStack, EffectContainer<T> effectContainer) {
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
        super.stackAdded(effectStack, effectContainer);
    }

}
