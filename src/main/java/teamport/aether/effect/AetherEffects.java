package teamport.aether.effect;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import teamport.aether.gui.IHudVisibility;

import java.util.List;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {

    public static PoisonEffect poisonEffect;
    public static RemedyEffect remedyEffect;
    public static RemedyEffect permanentRemedyEffect;
    public static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeItems();
        }
    }

    public static void initializeItems() {
        assignEffects();
        registerEffects();
    }

    /**
     * @implNote The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */

    public static void assignEffects() {
        //TODO change the icon once we have better ones
        poisonEffect = new AetherEffectBuilder()
                .init("effect.aether.poison", MOD_ID + ":poison", "petal_aechor.png")
                .setEffectTimeType(EffectTimeType.KEEP)
                .setColor(0x000000)
                .setDefaultDuration(60)
                .setMaxStack(10)
                .setTint(0xa05cff)
                .setHeartPath("aether:gui/hud/poison/")
                .build(PoisonEffect::new);


        remedyEffect = new AetherEffectBuilder()
                .init("effect.aether.remedy", MOD_ID + ":remedy", "bucket_skyroot_remedy.png")
                .setEffectTimeType(EffectTimeType.RESET)
                .setDefaultDuration(240)
                .setMaxStack(1)
                .setTint(0x99FF99)
                .setHeartPath("aether:gui/hud/remedy/")
                .build(RemedyEffect::new);


        permanentRemedyEffect = new AetherEffectBuilder()
                .init("effect.aether.permanent.remedy", MOD_ID + ":permanent-remedy", "diamond.png")
                .setEffectTimeType(EffectTimeType.PERMANENT)
                .setDefaultDuration(240)
                .setMaxStack(1)
                .setTint(0x99FF99)
                .setHeartPath("aether:gui/hud/remedy/")
                .build(RemedyEffect::new);
    }

    public static void registerEffects() {
        Effects.getInstance().register(poisonEffect.id, poisonEffect);
        Effects.getInstance().register(remedyEffect.id, remedyEffect);
        Effects.getInstance().register(permanentRemedyEffect.id, permanentRemedyEffect);
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


    public static void fixedAdd(IHasEffects player, Effect newEffect, int amount) {
        List<EffectStack> effects = player.getContainer().getEffects();
        for(EffectStack effect : effects) {
            if (effect.getEffect() == newEffect) {
                if(effect.getAmount() + amount >= effect.getEffect().getMaxStack()){
                    amount = effect.getEffect().getMaxStack() - effect.getAmount();
                }
            }
        }
        EffectStack stack = new EffectStack(player, newEffect, amount);
        stack.start(player.getContainer());
        player.getContainer().add(stack);
    }
}
