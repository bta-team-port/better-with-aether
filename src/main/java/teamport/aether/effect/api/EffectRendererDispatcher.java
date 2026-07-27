package teamport.aether.effect.api;

import java.util.IdentityHashMap;
import java.util.Map;

public final class EffectRendererDispatcher {
    private static final EffectRendererDispatcher INSTANCE = new EffectRendererDispatcher();
    private final Map<Effect, IEffectRenderer> renderers = new IdentityHashMap<>();
    private EffectRendererDispatcher() { }
    public static EffectRendererDispatcher getInstance() { return INSTANCE; }
    public void addDispatch(Effect effect, IEffectRenderer renderer) { renderers.put(effect, renderer); }
    public IEffectRenderer get(Effect effect) { return renderers.get(effect); }
}
