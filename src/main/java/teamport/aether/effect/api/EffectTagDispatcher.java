package teamport.aether.effect.api;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public final class EffectTagDispatcher {
    private static final Map<Class<?>, Tag<Effect>> IMMUNITIES = new HashMap<>();
    private EffectTagDispatcher() { }
    public static void setImmunityFor(Class<? extends Entity> type, Tag<Effect> tag) { IMMUNITIES.put(type, tag); }
    static boolean isImmune(Class<?> type, Effect effect) {
        return IMMUNITIES.entrySet().stream().anyMatch(entry -> entry.getKey().isAssignableFrom(type) && entry.getValue().appliesTo(effect));
    }
}
