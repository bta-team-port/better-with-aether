package teamport.aether.effect;

import net.minecraft.core.Global;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTagDispatcher;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.effect.Effects;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;

import java.util.*;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherEffects {
    public static class LookupLooks {
        public static final LookupLooks instance = new LookupLooks();
        public final Map<Effect, Effect> locker = new HashMap<>();
        public final Map<Effect, HashSet<Effect>> lockedEffects = new HashMap<>();

        public void addEntry(Effect getLocked, Effect lock) {
            this.locker.put(getLocked, lock);
            if (this.lockedEffects.containsKey(lock)) {
                HashSet<Effect> effects = this.lockedEffects.get(lock);
                effects.add(getLocked);
                return;
            }
            HashSet<Effect> effects = new HashSet<>();
            effects.add(getLocked);
            this.lockedEffects.put(lock, effects);
        }

        public @Nullable Effect getLocker(Effect id) {
            return this.locker.get(id);
        }

        public @Nullable Set<Effect> getLockedEffects(Effect id) {
            return this.lockedEffects.get(id);
        }
    }

    private static boolean hasInit = false;

    private AetherEffects(){}

    public static void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;
        assignEffects();
        registerEffects();
    }

    public static Effect poisonEffect;
    public static Effect remedyEffect;
    public static Effect invisibility;
    public static Effect swetty;
    private static final Tag<Effect> IMMUNE_TO_POISON = Tag.of("immune_to_poison");

    private static void assignEffects() {
        AetherEffects.poisonEffect = new PoisonEffect(
            "effect.aether.poison",
            MOD_ID + ":poison",
            new ArrayList<>(),
            EffectTimeType.KEEP,
            10)
            .setDefaultDuration(6 * Global.TICKS_PER_SECOND);

        AetherEffects.remedyEffect = new RemedyEffect(
            "effect.aether.remedy",
            MOD_ID + ":remedy",
            new ArrayList<>(),
            EffectTimeType.RESET,
            1)
            .setDefaultDuration(12 * Global.TICKS_PER_SECOND);

        AetherEffects.invisibility = new InvisibilityEffect(
            "effect.aether.invisibility",
            MOD_ID + ":invisibility",
            new ArrayList<>(),
            EffectTimeType.PERMANENT,
            1)
            .setDefaultDuration(6 * Global.TICKS_PER_SECOND);

        AetherEffects.swetty = new SwettyEffect(
            "effect.aether.swetty",
            MOD_ID + ":swetty",
            new ArrayList<>(),
            EffectTimeType.PERMANENT,
            1)
            .setDefaultDuration(30 * Global.TICKS_PER_SECOND);

        AetherEffects.registerLock(AetherEffects.poisonEffect, AetherEffects.remedyEffect);
    }

    private static void registerEffects() {
        Effects effects = Effects.getInstance();
        effects.register(AetherEffects.poisonEffect.id, AetherEffects.poisonEffect);
        effects.register(AetherEffects.remedyEffect.id, AetherEffects.remedyEffect);
        effects.register(AetherEffects.invisibility.id, AetherEffects.invisibility);
        effects.register(AetherEffects.swetty.id, AetherEffects.swetty);

        effects.register(MOD_ID + ":extra_health", Effects.EXTRA_HEALTH);

        IMMUNE_TO_POISON.tag(AetherEffects.poisonEffect);
        EffectTagDispatcher.setImmunityFor(MobAechorPlant.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobCockatrice.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobSentry.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossSlider.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobValkyrie.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossValkyrie.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobFireMinion.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossSunspirit.class, IMMUNE_TO_POISON);
    }

    /**
     * @param affected effect that lock will act on
     * @param lock     affected effect won't apply if this effect is present
     */
    public static void registerLock(Effect affected, Effect lock) {
        LookupLooks.instance.addEntry(affected, lock);
    }


    /**
     * @param entity    affected Mob
     * @param newEffect Effect affecting the entity
     * @param amount    stack size of the effect
     * @return true if the effect was applied false otherwise
     * @apiNote If you want aether style effect use this function to add your effects.
     * @implNote Effect can only affect entity if the effect is not locked.
     * Each effect defined what effect lock it out from being reapplied.
     * Returns always false if a given effect is locked.
     * @see ILockInteractable
     */
    public static boolean add(Entity entity, Effect newEffect, int amount) {
        if (!(entity instanceof IHasEffects)) return false;
        EffectStack stack = new EffectStack((IHasEffects<?>) entity, newEffect, amount);
        return AetherEffects.add(entity, stack);
    }


    public static boolean add(Entity entity, EffectStack stackToAdd) {
        if (!(entity instanceof IHasEffects)) return false;
        if (entity.world.isClientSide) return false;
        if (!stackToAdd.getEffect().canApplyTo(entity)) return false;
        if (stackToAdd.getAmount() <= 0) return false;
        IHasEffects<?> hasEffects = (IHasEffects<?>) entity;

        for (EffectStack effect : hasEffects.getContainer().getEffects()) {
            if(effect.getEffect() == stackToAdd.getEffect()){
                int amount = Math.min(stackToAdd.getAmount(), effect.getEffect().getMaxStack() - effect.getAmount());
                if (amount <= 0) {
                    if (effect.getEffect().getTimeType() != EffectTimeType.RESET) return false;
                    effect.add(0, hasEffects.getContainer());
                    return true;
                }
                effect.add(amount, hasEffects.getContainer());
                return true;
            }
        }

        if (isLocked(stackToAdd, hasEffects.getContainer())) return false;

        stackToAdd.start(hasEffects.getContainer());
        hasEffects.getContainer().add(stackToAdd);
        return true;
    }

    public static <T> boolean isLocked(@NonNull EffectStack effectStack, EffectContainer<T> effectContainer) {
        Effect effectToAdd = effectStack.getEffect();
        Effect effectBlocker = AetherEffects.LookupLooks.instance.getLocker(effectToAdd);

        if (effectBlocker == null) return false;

        T parent = effectContainer.getParent();
        if (parent instanceof IHasEffects
            && parent instanceof Mob
            && effectBlocker instanceof ILockInteractable
            && effectContainer.hasEffect(effectBlocker)
        ) {
            ((ILockInteractable) effectBlocker).lockTriggered((IHasEffects<?>) parent);

            effectContainer.remove(effectToAdd);
            return true;
        }

        return false;
    }
}
