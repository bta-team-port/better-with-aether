package teamport.aether.effect.api;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EffectContainer<T> {
    private final T parent;
    private final List<EffectStack> effects = new ArrayList<>();
    private boolean dirty;

    public EffectContainer(T parent) { this.parent = parent; }
    public T getParent() { return parent; }
    public List<EffectStack> getEffects() { return Collections.unmodifiableList(effects); }
    public boolean hasEffect(Effect effect) { return effects.stream().anyMatch(stack -> stack.getEffect() == effect); }
    public void add(EffectStack stack) {
        if (isClientSide()) return;
        if (!hasEffect(stack.getEffect()) && stack.getAmount() > 0) {
            effects.add(stack);
            dirty = true;
        }
    }
    public void remove(Effect effect) {
        if (isClientSide()) return;
        if (effects.removeIf(stack -> stack.getEffect() == effect)) dirty = true;
    }
    public void tick() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).tick(this);
        }
    }
    public void tickClientTimers() { for (EffectStack stack : effects) stack.tickClientTimer(); }
    public void markDirty() { dirty = true; }
    public boolean consumeDirty() {
        boolean changed = dirty;
        dirty = false;
        return changed;
    }

    private boolean isClientSide() {
        return parent instanceof Entity && ((Entity) parent).world != null && ((Entity) parent).world.isClientSide;
    }

    public void save(CompoundTag tag) {
        ListTag savedEffects = new ListTag();
        for (EffectStack stack : effects) {
            CompoundTag effectTag = new CompoundTag();
            effectTag.putString("Id", stack.getEffect().id);
            effectTag.putInt("Duration", stack.getDuration());
            effectTag.putInt("TimeLeft", stack.getTimeLeft());
            effectTag.putInt("Amount", stack.getAmount());
            savedEffects.addTag(effectTag);
        }
        tag.put("AetherEffects", savedEffects);
    }

    public void load(CompoundTag tag, IHasEffects<?> owner) {
        effects.clear();
        ListTag savedEffects = tag.getList("AetherEffects");
        for (int i = 0; i < savedEffects.tagCount(); ++i) {
            CompoundTag effectTag = (CompoundTag) savedEffects.tagAt(i);
            Effect effect = Effects.getInstance().get(effectTag.getString("Id"));
            if (effect == null || hasEffect(effect) || !(parent instanceof Entity) || !effect.canApplyTo((Entity) parent)) continue;
            EffectStack stack = EffectStack.load(
                owner,
                effect,
                effectTag.getInteger("Duration"),
                effectTag.getInteger("TimeLeft"),
                effectTag.getInteger("Amount")
            );
            if (stack.getAmount() > 0) effects.add(stack);
        }
        dirty = false;
    }
}
