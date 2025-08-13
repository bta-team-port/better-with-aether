package teamport.aether.effect;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import teamport.aether.gui.IHudVisibility;

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

    public static PoisonEffect poisonEffect;
    public static RemedyEffect remedyEffect;
    private static boolean hasInit = false;

    public static void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;
        assignEffects();
        initEffects();
    }

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */
    private static void assignEffects() {
        poisonEffect = new AetherEffectBuilder()
                .init("effect.aether.poison", MOD_ID + ":poison", "icon_poison.png")
                .setEffectTimeType(EffectTimeType.KEEP)
                .setDefaultDuration(60)
                .setMaxStack(10)
                .setTint(0x8218cb)
                .setVignette("/assets/aether/textures/other/poisonvignette.png")
                .setHeartPath("aether:gui/hud/poison/")
                .build(PoisonEffect::new);


        remedyEffect = new AetherEffectBuilder()
                .init("effect.aether.remedy", MOD_ID + ":remedy", "icon_remedy.png")
                .setEffectTimeType(EffectTimeType.RESET)
                .setDefaultDuration(240)
                .setMaxStack(1)
                .setTint(0x009bc2)
                .setVignette("/assets/aether/textures/other/curevignette.png")
                .setHeartPath("aether:gui/hud/remedy/")
                .build(RemedyEffect::new);

    }

    private static void initEffects() {
        AetherEffects.registerEffect(poisonEffect);
        AetherEffects.registerEffect(remedyEffect);
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
     * @param newEffect Effect affecting the mob
     * @return true if the effect was applied false otherwise
     * */
    public static boolean add(Mob mob, EffectStack newEffect) {
        if(!(mob instanceof IHasEffects)) return false;
        IHasEffects entity = (IHasEffects) mob;
        for(EffectStack effect : entity.getContainer().getEffects()) {
            if (effect.getEffect() == newEffect.getEffect()) {
                if(effect.getAmount() + newEffect.getAmount() >= effect.getEffect().getMaxStack()){
                    int amount = effect.getEffect().getMaxStack() - effect.getAmount();
                    effect.add(amount, entity.getContainer());
                    return true;
                }
            }
        }
        if(isLocked(newEffect, ((IHasEffects) mob).getContainer())) return false;
        newEffect.start(entity.getContainer());
        entity.getContainer().add(newEffect);
        return true;
    }


    public static <T> boolean isLocked(EffectStack effectStack, EffectContainer<T> effectContainer) {
        Effect these = effectStack.getEffect();
        Effect effect = AetherEffects.LookupLooks.instance.getLocker(these);
        if(effect == null){
            return false;
        }
        if ((effectContainer.getParent() instanceof IHasEffects) && (effectContainer.getParent() instanceof Mob)) {
            IHasEffects affected = (IHasEffects) effectContainer.getParent();
            if (effectContainer.hasEffect(effect) && effect instanceof ILockInteractable) {
                ((ILockInteractable) effect).lockTriggered(affected);
                effectContainer.remove(these);
                return true;
            }
        }
        return false;
    }
}
