package teamport.aether.effect.api;

import net.minecraft.core.data.tag.ITaggable;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;

import java.util.List;

public class Effect implements ITaggable<Effect> {
    public final String nameKey;
    public final String id;
    private final List<Modifier<?>> modifiers;
    private final EffectTimeType effectTimeType;
    private final int maxStack;
    private int defaultDuration;

    public Effect(String nameKey, String id, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int maxStack) {
        this.nameKey = nameKey;
        this.id = id;
        this.modifiers = modifiers;
        this.effectTimeType = effectTimeType;
        this.maxStack = maxStack;
    }

    public Effect setDefaultDuration(int duration) {
        this.defaultDuration = duration;
        return this;
    }

    public int getDefaultDuration() { return defaultDuration; }
    public int getMaxStack() { return maxStack; }
    public EffectTimeType getEffectTimeType() { return effectTimeType; }
    public List<Modifier<?>> getModifiers() { return modifiers; }

    public boolean canApplyTo(Entity target) {
        return target != null && !EffectTagDispatcher.isImmune(target.getClass(), this);
    }

    public <T> void activated(EffectStack stack, EffectContainer<T> container) { }
    public <T> void expired(EffectStack stack, EffectContainer<T> container) { container.remove(this); }
    public <T> void tick(EffectStack stack, EffectContainer<T> container) { }
    public <T> void stackAdded(EffectStack stack, EffectContainer<T> container) { }

    @Override
    public boolean isIn(Tag<Effect> tag) {
        return tag.appliesTo(this);
    }
}
