package teamport.aether.effect;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import teamport.aether.effect.render.PoisonEffectRenderer;
import teamport.aether.effect.render.RemedyEffectRenderer;
import teamport.aether.entity.boss.slider.MobBossSlider;
import teamport.aether.entity.boss.sunspirit.MobBossSunspirit;
import teamport.aether.entity.boss.valkyrie.queen.MobBossValkyrie;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.*;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {
    @SuppressWarnings("java:S6548")
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
            return this.locker.getOrDefault(id, null);
        }

        public @Nullable Set<Effect> getLockedEffects(Effect id) {
            return this.lockedEffects.getOrDefault(id, null);
        }
    }

    private static boolean hasInit = false;

    public static void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;
        assignEffects();
        registerEffects();
        if (!EnvironmentHelper.isServerEnvironment()) assignEffectRenderers();
    }

    @SuppressWarnings({"java:S1104", "java:S1444"})
    public static Effect poisonEffect;
    @SuppressWarnings({"java:S1104", "java:S1444"})
    public static Effect remedyEffect;
    @SuppressWarnings("java:S3008")
    private static final Tag<Effect> IMMUNE_TO_POISON = Tag.of("immune_to_poison");

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */
    private static void assignEffects() {
        poisonEffect = new PoisonEffect(
            "effect.aether.poison",
            MOD_ID + ":poison",
            new ArrayList<>(),
            EffectTimeType.KEEP,
            10
        ).setDefaultDuration(60);

        remedyEffect = new RemedyEffect(
            "effect.aether.remedy",
            MOD_ID + ":remedy",
            new ArrayList<>(),
            EffectTimeType.RESET,
            1
        ).setDefaultDuration(240);

        AetherEffects.registerLock(poisonEffect, remedyEffect);
    }

    private static void registerEffects() {
        Effects effects = Effects.getInstance();
        effects.register(poisonEffect.id, poisonEffect);
        effects.register(remedyEffect.id, remedyEffect);

        // in here for compatibility reasons.
        effects.register(MOD_ID + ":extra_health", Effects.EXTRA_HEALTH);

        IMMUNE_TO_POISON.tag(poisonEffect);
        EffectTagDispatcher.setImmunityFor(MobAechorPlant.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobCockatrice.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobSentry.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossSlider.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobValkyrie.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossValkyrie.class, IMMUNE_TO_POISON);

        EffectTagDispatcher.setImmunityFor(MobFireMinion.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobBossSunspirit.class, IMMUNE_TO_POISON);
    }

    private static void assignEffectRenderers() {
        EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();

        dispatcher.addDispatch(poisonEffect, new PoisonEffectRenderer<>(
                poisonEffect,
                "/assets/aether/textures/other/poisonvignette.png",
                0x8218cb,
                "aether:gui/hud/poison/"
            )
                .setIcon("icon_poison.png")
        );

        dispatcher.addDispatch(remedyEffect, new RemedyEffectRenderer<>(
                remedyEffect,
                "/assets/aether/textures/other/curevignette.png",
                0x009bc2,
                "aether:gui/hud/remedy/"
            )
                .setIcon("icon_remedy.png")
        );
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
        IHasEffects<?> hasEffects = (IHasEffects<?>) entity;

        for (EffectStack currStack : hasEffects.getContainer().getEffects()) {
            Effect currEffect = currStack.getEffect();
            int currMax = currEffect.getMaxStack();

            if (currEffect == stackToAdd.getEffect()) {
                if (currStack.getAmount() + stackToAdd.getAmount() >= currMax) {
                    int amountToAdd = currMax - currStack.getAmount();

                    currStack.add(amountToAdd, hasEffects.getContainer());
                    return true;
                }
            }
        }

        if (isLocked(stackToAdd, hasEffects.getContainer())) return false;

        stackToAdd.start(hasEffects.getContainer());
        hasEffects.getContainer().add(stackToAdd);
        return true;
    }


    public static <T> boolean isLocked(EffectStack effectStack, EffectContainer<T> effectContainer) {
        Effect effectToAdd = effectStack.getEffect();
        Effect effectBlocker = AetherEffects.LookupLooks.instance.getLocker(effectToAdd);

        if (effectBlocker == null) return false;

        T parent = effectContainer.getParent();
        if (parent instanceof IHasEffects && parent instanceof Mob) {
            if (effectBlocker instanceof ILockInteractable && effectContainer.hasEffect(effectBlocker)) {
                ((ILockInteractable) effectBlocker).lockTriggered((IHasEffects<?>) parent);

                effectContainer.remove(effectToAdd);
                return true;
            }
        }

        return false;
    }
}
