package teamport.aether.effect;

import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.effect.render.EffectRenderer;
import teamport.aether.effect.render.RemedyEffectRenderer;
import teamport.aether.gui.IHudVisibility;

import java.util.List;

public class RemedyEffect extends Effect implements IHudVisibility {
    public final EffectRenderer renderer = new RemedyEffectRenderer();
    public String PATH_HEART;
    private final int tint;

    public String[] preventApplying = new String[]{AetherEffects.poisonEffect.id};

    public RemedyEffect(AetherEffectBuilder builder) {
        this(
                builder.getNameKey(), builder.getId(),
                builder.getImagePath(), builder.getHeartPath(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getColor(), builder.getTint(),
                builder.getDefaultDuration(), builder.getMaxStack()
        );
    }

    public RemedyEffect(
            String nameKey, String id,
            String imagePath, String PATH_HEART,
            List<Modifier<?>> modifiers,
            EffectTimeType effectTimeType,
            int color, int tint,
            int defaultDuration, int maxStack
    ) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
        this.tint = tint;
        this.PATH_HEART = PATH_HEART;
    }

    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        for (String id : preventApplying) {
            effectContainer.remove(Effects.getInstance().getItem(id));
        }
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }

    @Override
    public int getTint(){
        return tint;
    }

    @Override
    public EffectRenderer getRenderer(){
        return this.renderer;
    }
}