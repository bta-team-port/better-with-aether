package teamport.aether.effect;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.attribute.Attributes;
import sunsetsatellite.catalyst.effects.api.attribute.type.IntAttribute;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.ModifierType;
import sunsetsatellite.catalyst.effects.api.modifier.type.IntModifier;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.helper.Union;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
        initEffects();
    }

    public static IntAttribute EXTRA_HEALTH = (IntAttribute) new IntAttribute("attribute.aether.extraHealth", 0).setAsDefault();

    private static void registerAttributes() {
        Attributes catalystAttributes = Attributes.getInstance();

        catalystAttributes.register("aether:extra_health", EXTRA_HEALTH);
    }

    public static PoisonEffect poisonEffect;
    public static RemedyEffect remedyEffect;
    public static Effect extraHealthEffect;

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */
    private static void assignEffects() {

        extraHealthEffect = new AetherEffectBuilder()
                .init("effect.aether.extra_health",MOD_ID + ":extra_health")
                .setEffectTimeType(EffectTimeType.PERMANENT)
                .addModifier(new IntModifier(EXTRA_HEALTH, ModifierType.ADD, 1))
                .setMaxStack(40)
                .setPersistent()
                .buildRegularEffect();

        poisonEffect = new AetherEffectBuilder()
                .init("effect.aether.poison", MOD_ID + ":poison")
                .setEffectTimeType(EffectTimeType.KEEP)
                .setDefaultDuration(60)
                .setMaxStack(10)
                .setTint(0x8218cb)
                .setVignette("/assets/aether/textures/other/poisonvignette.png")
                .setHeartPath("aether:gui/hud/poison/")
                .build(b -> new PoisonEffect(b, "icon_poison.png"));


        remedyEffect = new AetherEffectBuilder()
                .init("effect.aether.remedy", MOD_ID + ":remedy")
                .setEffectTimeType(EffectTimeType.RESET)
                .setDefaultDuration(240)
                .setMaxStack(1)
                .setTint(0x009bc2)
                .setVignette("/assets/aether/textures/other/curevignette.png")
                .setHeartPath("aether:gui/hud/remedy/")
                .build(b -> new RemedyEffect(b, "icon_remedy.png"));

    }

    private static void initEffects() {
        AetherEffects.registerEffect(poisonEffect);
        AetherEffects.registerEffect(remedyEffect);
        AetherEffects.registerEffect(extraHealthEffect);
        AetherEffects.registerLock(poisonEffect, remedyEffect);
    }

    /**
     * @param effect effect to add to the catalyst effect registry
     */
    public static void registerEffect(Effect effect){
        Effects.getInstance().register(effect.id, effect);
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
