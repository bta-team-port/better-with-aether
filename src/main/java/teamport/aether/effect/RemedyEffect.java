package teamport.aether.effect;

import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.effect.render.EffectRenderer;
import teamport.aether.effect.render.PoisonEffectRenderer;
import teamport.aether.effect.render.RemedyEffectRenderer;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.particle.ParticalHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RemedyEffect extends Effect implements IHudVisibility, ILockInteractable {
    public final EffectRenderer renderer;
    public String PATH_HEART;

    public RemedyEffect(AetherEffectBuilder builder, IconCoordinate icon) {
        super(
                builder.getNameKey(),
                builder.getId(),
                icon,
                builder.getColor(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getDefaultDuration(),
                builder.getMaxStack()
        );

        this.PATH_HEART = builder.getHeartPath();
        this.renderer = new PoisonEffectRenderer(builder.getVignette(), builder.getTint());
    }

    public RemedyEffect(AetherEffectBuilder builder, String icon) {
        super(
                builder.getNameKey(),
                builder.getId(),
                icon,
                builder.getColor(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getDefaultDuration(),
                builder.getMaxStack()
        );

        this.PATH_HEART = builder.getHeartPath();
        this.renderer = new PoisonEffectRenderer(builder.getVignette(), builder.getTint());
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }

    @Override
    public EffectRenderer getRenderer(){
        return this.renderer;
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