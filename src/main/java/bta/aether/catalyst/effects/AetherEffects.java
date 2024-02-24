package bta.aether.catalyst.effects;

import sunsetsatellite.catalyst.CatalystEffects;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.effect.Effects;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import static bta.aether.Aether.MOD_ID;

public class AetherEffects {

    //  CatalystEffects.listOf(new Modifier[]{new IntModifier(Attributes.EFFECT_DURATION, ModifierType.MULTIPLY, 2)})
    public static final PoisonEffect poisonEffect = new PoisonEffect("effect.aether.poison", MOD_ID+":poison", "assets/aether/poison/poisonvignette.png", 0xFF9a009a, CatalystEffects.listOf(new Modifier[]{}), EffectTimeType.RESET, 100, 10);
    public static final RemedyEffect remedyEffect = new RemedyEffect("effect.aether.remedy", MOD_ID+":remedy", "assets/aether/poison/curevignette.png", 0xFF99FF99, CatalystEffects.listOf(new Modifier[]{}), EffectTimeType.RESET, 10, 1);

    public void initializeEffects() {
        Effects.getInstance().register(poisonEffect.id, poisonEffect);
    }
}
