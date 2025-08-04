package teamport.aether.effect;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;

import java.util.List;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {

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
                .setTint(0x9A009A)
                .setHeartPath("aether:gui/hud/poison/")
                .build(PoisonEffect::new);


        remedyEffect = new AetherEffectBuilder()
                .init("effect.aether.remedy", MOD_ID + ":remedy", "bucket_skyroot_remedy.png")
                .setEffectTimeType(EffectTimeType.RESET)
                .setDefaultDuration(20)
                .setMaxStack(1)
                .setTint(0x99FF99)
                .setHeartPath("minecraft:gui/hud/heart/")
                .build(RemedyEffect::new);
    }

    private static void registerEffects() {
        Effects.getInstance().register(poisonEffect.id, poisonEffect);
        Effects.getInstance().register(remedyEffect.id, remedyEffect);
    }

    public static EffectStack resolveDominantEffect(Player player) {
        EffectStack dominant = null;
        for (EffectStack effectStack : ((IHasEffects) player).getContainer().getEffects()) {
            if (dominant == null) dominant = effectStack;
            int effectStackPotency = effectStack.getAmount() * effectStack.getDuration();
            int dominantPotency = dominant.getAmount() * dominant.getDuration();
            if (effectStackPotency > dominantPotency) dominant = effectStack;
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
