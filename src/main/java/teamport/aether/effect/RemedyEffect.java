package teamport.aether.effect;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.particle.ParticalHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RemedyEffect extends Effect implements ILockInteractable {

    public RemedyEffect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        super(nameKey, id, modifiers, effectTimeType, maxStack);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        HashSet<Effect> remove = AetherEffects.LookupLooks.instance.getLockedEffects(this);
        if(remove == null) return;
        List<EffectStack> check = new ArrayList<>(effectContainer.getEffects());
        for(EffectStack stack : check){
            for(Effect effect : remove){
                if(effect.equals(stack.getEffect())){
                    effectContainer.remove(stack.getEffect());
                    Mob mob = (Mob) effectContainer.getParent();
                    spawnParticles(mob);
                }
            }
        }
    }

    @Override
    public void lockTriggered(IHasEffects hasEffects) {
        if (!(hasEffects instanceof Mob)) {
            return;
        }
        Mob mob = (Mob) hasEffects;
        spawnParticles(mob);
    }

    private static void spawnParticles(Mob mob) {
        if (mob instanceof Player) {
            ParticalHelper.spawnRemedyParticle(mob.world, mob.x, mob.y - mob.bbHeight, mob.z, mob.bbHeight, mob.bbWidth);
        } else {
            ParticalHelper.spawnRemedyParticle(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
        }
    }
}