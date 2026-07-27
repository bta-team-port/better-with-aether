package teamport.aether.effect.api;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Effects {
    private static final Effects INSTANCE = new Effects();
    public static final Effect EXTRA_HEALTH = new Effect("effect.aether.extra_health", "aether:extra_health", java.util.List.of(), EffectTimeType.PERMANENT, 1);
    private final Map<String, Effect> effects = new LinkedHashMap<>();
    private Effects() { }
    public static Effects getInstance() { return INSTANCE; }
    public void register(String id, Effect effect) { effects.put(id, effect); }
    public Effect get(String id) { return effects.get(id); }
}
