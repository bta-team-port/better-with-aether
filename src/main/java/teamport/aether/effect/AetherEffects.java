package teamport.aether.effect;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import teamport.aether.gui.IHudVisibility;

import javax.annotation.Nullable;
import java.util.*;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {

    protected static class LookupLooks {
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
        if (!hasInit) {
            hasInit = true;
            initializeItems();
        }
    }

    private static void initializeItems() {
        assigneEffects();
        registerEffects();
        registerLocks();
    }

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */

    private static void assigneEffects() {
        //TODO change the icon once we have better ones
        poisonEffect = new AetherEffectBuilder()
                .init("effect.aether.poison", MOD_ID + ":poison", "petal_aechor.png")
                .setEffectTimeType(EffectTimeType.KEEP)
                .setColor(0x000000)
                .setDefaultDuration(60)
                .setMaxStack(10)
                .setTint(0xa05cff)
                .setVignette("/assets/aether/textures/other/poisonvignette.png")
                .setHeartPath("aether:gui/hud/poison/")
                .build(PoisonEffect::new);


        remedyEffect = new AetherEffectBuilder()
                .init("effect.aether.remedy", MOD_ID + ":remedy", "bucket_skyroot_remedy.png")
                .setEffectTimeType(EffectTimeType.RESET)
                .setDefaultDuration(240)
                .setMaxStack(1)
                .setTint(0x99FF99)
                .setVignette("/assets/aether/textures/other/curevignette.png")
                .setHeartPath("aether:gui/hud/remedy/")
                .build(RemedyEffect::new);

    }

    private static void registerEffects() {
        Effects.getInstance().register(poisonEffect.id, poisonEffect);
        Effects.getInstance().register(remedyEffect.id, remedyEffect);
    }

    private static void registerLocks() {
        LookupLooks.instance.addEntry(poisonEffect, remedyEffect);
    }


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


    public static void fixedAdd(IHasEffects entity, Effect newEffect, int amount) {
        for(EffectStack effect : entity.getContainer().getEffects()) {
            if (effect.getEffect() == newEffect) {
                if(effect.getAmount() + amount >= effect.getEffect().getMaxStack()){
                    amount = effect.getEffect().getMaxStack() - effect.getAmount();
                    effect.add(amount, entity.getContainer());
                    return;
                }
            }
        }
        Effect lock = LookupLooks.instance.getLocker(newEffect);
        if(lock != null && entity.getContainer().hasEffect(lock) && lock instanceof ILockInteractable){
            ((ILockInteractable) lock).lockTriggered(entity);
            return;
        }
        EffectStack stack = new EffectStack(entity, newEffect, amount);
        stack.start(entity.getContainer());
        entity.getContainer().add(stack);
    }
}
