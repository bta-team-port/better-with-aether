package teamport.aether.effect;

import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.ArrayList;

import static teamport.aether.AetherMod.MOD_ID;


public class AetherEffects {

    public static PoisonEffect poisonEffect;
    public static RemedyEffect remedyEffect;
    private static boolean hasInit = false;

    public static void init(){
        if(!hasInit){
            hasInit = true;
            initializeItems();
        }
    }

    private static void initializeItems() {
       assigneEffects();
       registerEffects();
    }

    /**
     * @implNote
     * The path for the assets that effects uses is: assets/ + MOD_ID +/effects/icon/ + imagePath
     */

    private static void assigneEffects(){
        poisonEffect = new PoisonEffect(
                "effect.aether.poison",
                MOD_ID+":poison",
                "poison.png",
                0x000000,
                new ArrayList<Modifier<?>>(),
                EffectTimeType.KEEP,
                120, 10
        );
        remedyEffect = new RemedyEffect(
                "effect.aether.remedy",
                MOD_ID+":remedy",
                "remedy.png",
                0x000000,
                new ArrayList<Modifier<?>>(), EffectTimeType.RESET,
                60, 1);
    }

    private static void registerEffects(){
        Effects.getInstance().register(poisonEffect.id, poisonEffect);
        Effects.getInstance().register(remedyEffect.id, remedyEffect);
    }

    public static Effect resolveDominantEffect(Player player){
        EffectStack dominant = null;
        for(EffectStack effectStack: ((IHasEffects)player).getContainer().getEffects()){
            if(dominant == null) dominant = effectStack;
            if(effectStack.getAmount() > dominant.getAmount()) dominant = effectStack;
        }
        return dominant != null ? dominant.getEffect() : null;
    }
}
