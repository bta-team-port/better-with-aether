package teamport.aether.effect;

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
import java.util.List;

public class RemedyEffect extends Effect implements IHudVisibility {
    public final EffectRenderer renderer;
    public String PATH_HEART;

    public String[] preventApplying = new String[]{AetherEffects.poisonEffect.getNameKey()};

    public RemedyEffect(AetherEffectBuilder builder) {
        this(
                builder.getNameKey(), builder.getId(),
                builder.getImagePath(), builder.getHeartPath(), builder.getVignette(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getColor(), builder.getTint(),
                builder.getDefaultDuration(), builder.getMaxStack()
        );
    }

    public RemedyEffect(
            String nameKey, String id,
            String imagePath, String PATH_HEART, String vignette,
            List<Modifier<?>> modifiers,
            EffectTimeType effectTimeType,
            int color, int tint,
            int defaultDuration, int maxStack
    ) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
        this.PATH_HEART = PATH_HEART;
        renderer = new RemedyEffectRenderer(vignette, tint);
    }

    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        List<EffectStack> check = new ArrayList<>(effectContainer.getEffects());
        for(EffectStack stack : check){
            for(String ids : preventApplying){
                if(stack.getEffect().getNameKey().equals(ids)){
                    Mob mob = (Mob) effectContainer.getParent();
                    double mobY = mob.y + (mob instanceof Player ? -1.0F : 0.0F);
                    ParticalHelper.spawnRemedyParticle(mob.world, mob.x, mobY, mob.z, mob.bbHeight, mob.bbWidth);
                    effectContainer.remove(stack.getEffect());
                }
            }
        }
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }

    @Override
    public EffectRenderer getRenderer(){
        return this.renderer;
    }
}