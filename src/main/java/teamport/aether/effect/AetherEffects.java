package teamport.aether.effect;

import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.attribute.Attributes;
import sunsetsatellite.catalyst.effects.api.attribute.type.IntAttribute;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.effect.render.EffectRendererDispatcher;
import sunsetsatellite.catalyst.effects.api.modifier.ModifierType;
import sunsetsatellite.catalyst.effects.api.modifier.type.IntModifier;
import teamport.aether.effect.render.ExtraHealthEffectRenderer;
import teamport.aether.effect.render.PoisonEffectRenderer;
import teamport.aether.effect.render.RemedyEffectRenderer;
import teamport.aether.entity.AetherEntities;
import teamport.aether.entity.monster.aechorplant.MobAechorPlant;
import teamport.aether.entity.monster.cockatrice.MobCockatrice;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.EnvironmentHelper;

import javax.annotation.Nullable;
import java.util.*;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {

    public static class LookupLooks {
        public static final LookupLooks instance = new LookupLooks();
        public final Map<Effect, Effect> locker = new HashMap<>();
        public final Map<Effect, HashSet<Effect>> lockedEffects = new HashMap<>();

        public void addEntry(Effect getLocked, Effect lock){
            this.locker.put(getLocked, lock);
            if(this.lockedEffects.containsKey(lock)){
                HashSet<Effect> effects = this.lockedEffects.get(lock);
                effects.add(getLocked);
                return;
            }
            HashSet<Effect> effects = new HashSet<>();
            effects.add(getLocked);
            this.lockedEffects.put(lock, effects);
        }

        public @Nullable Effect getLocker(Effect id){
            return this.locker.getOrDefault(id,null);
        }

        public @Nullable HashSet<Effect> getLockedEffects(Effect id){
            return this.lockedEffects.getOrDefault(id, null);
        }

        public Map<Effect, Effect> getLockerMap(){
            return this.locker;
        }
        public Map<Effect, HashSet<Effect>> getLockedEffectsMap(){
            return this.lockedEffects;
        }
    }

    private static boolean hasInit = false;
    public static void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;
        registerAttributes();
        assignEffects();
        registerEffects();
        if (!EnvironmentHelper.isServerEnvironment()) assignEffectRenderers();
    }


    public static IntAttribute EXTRA_HEALTH = (IntAttribute) new IntAttribute("attribute.aether.extraHealth", 0).setAsDefault();

    private static void registerAttributes() {
        Attributes catalystAttributes = Attributes.getInstance();

        catalystAttributes.register("aether:extra_health", EXTRA_HEALTH);
    }

    public static Effect poisonEffect;
    public static Effect remedyEffect;
    public static Effect extraHealthEffect;

    public static Tag<Effect> IMMUNE_TO_POISON = Tag.of("immune_to_poison");

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */
    private static void assignEffects() {
        extraHealthEffect = new Effect(
            "effect.aether.extra_health",
            MOD_ID + ":extra_health" ,
            Arrays.asList(new IntModifier(EXTRA_HEALTH, ModifierType.ADD, 1)),
            EffectTimeType.PERMANENT,
            40
        ).setPersistent();

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
        effects.register(extraHealthEffect.id, extraHealthEffect);
        effects.register(poisonEffect.id, poisonEffect);
        effects.register(remedyEffect.id, remedyEffect);

        IMMUNE_TO_POISON.tag(poisonEffect);
        EffectTagDispatcher.setImmunityFor(MobAechorPlant.class, IMMUNE_TO_POISON);
        EffectTagDispatcher.setImmunityFor(MobCockatrice.class, IMMUNE_TO_POISON);
    }

    private static void assignEffectRenderers() {
        EffectRendererDispatcher dispatcher = EffectRendererDispatcher.getInstance();
        dispatcher.addDispatch(extraHealthEffect,
            new ExtraHealthEffectRenderer<>(extraHealthEffect)
                .setIcon(TextureRegistry.getTexture(AetherItems.LIFESHARD.namespaceID))
        );

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
     * @param lock affected effect won't apply if this effect is present
     */
    public static void registerLock(Effect affected, Effect lock){
        LookupLooks.instance.addEntry(affected, lock);
    }

    /**
     * @param player affected Player
     * @return most potent EffectStack affecting the player
     */
    public static EffectStack resolveDominantEffect(Player player) {
        EffectStack dominant = null;
        for (EffectStack effectStack : ((IHasEffects) player).getContainer().getEffects()) {
            if(effectStack.getEffect() instanceof IHudVisibility){
                if (dominant == null) dominant = effectStack;
                int effectStackPotency = effectStack.getAmount() * effectStack.getDuration();
                int dominantPotency = dominant.getAmount() * dominant.getDuration();
                if (effectStackPotency > dominantPotency) dominant = effectStack;
            }
        }
        return dominant;
    }


    /**
     * @apiNote If you want aether style effect use this function to add your effects.
     * @see ILockInteractable
     * @implNote  Effect can only affect mob if the effect is not locked.
     * Each effect defined what effect lock it out from being reapplied.
     * Returns always false if a given effect is locked.
     *
     * @param mob affected Mob
     * @param newEffect Effect affecting the mob
     * @param amount stack size of the effect
     * @return true if the effect was applied false otherwise
     * */
    public static boolean add(Mob mob, Effect newEffect, int amount) {
        if(!(mob instanceof IHasEffects)) return false;
        EffectStack stack = new EffectStack((IHasEffects) mob, newEffect, amount);
        return AetherEffects.add(mob, stack);
    }


    /**
     * @apiNote If you want aether style effect use this function to add your effects.
     * @see ILockInteractable
     * @implNote  Effect can only affect mob if the effect is not locked.
     * Each effect defined what effect lock it out from being reapplied.
     * Returns always false if a given effect is locked.
     *
     * @param mob affected Mob
     * @param stackToAdd Effect stack affecting the mob
     * @return true if the effect was applied false otherwise
     * */
    public static boolean add(Mob mob, EffectStack stackToAdd) {
        if(!(mob instanceof IHasEffects)) return false;
        IHasEffects entity = (IHasEffects) mob;

        for (EffectStack currStack : entity.getContainer().getEffects()) {
            Effect currEffect = currStack.getEffect();
            int currMax = currEffect.getMaxStack();

            if (currEffect == stackToAdd.getEffect()) {
                if (currStack.getAmount() + stackToAdd.getAmount() >= currMax){
                    int amountToAdd = currMax - currStack.getAmount();

                    currStack.add(amountToAdd, entity.getContainer());
                    return true;
                }
            }
        }

        if (isLocked(stackToAdd, ((IHasEffects) mob).getContainer())) return false;

        stackToAdd.start(entity.getContainer());
        entity.getContainer().add(stackToAdd);
        return true;
    }


    public static <T> boolean isLocked(EffectStack effectStack, EffectContainer<T> effectContainer) {
        Effect effectBlocked = effectStack.getEffect();
        Effect effectBlocker = AetherEffects.LookupLooks.instance.getLocker(effectBlocked);

        if (effectBlocker == null) return false;

        T parent = effectContainer.getParent();
        if (parent instanceof IHasEffects && parent instanceof Mob) {
            if (effectBlocker instanceof ILockInteractable && effectContainer.hasEffect(effectBlocker)) {
                ((ILockInteractable) effectBlocker).lockTriggered((IHasEffects) parent);

                effectContainer.remove(effectBlocked);
                return true;
            }
        }

        return false;
    }
}
