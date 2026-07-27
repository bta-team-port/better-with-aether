package teamport.aether.effect.api;

public final class EffectRendererManager {
    private static final EffectRendererManager INSTANCE = new EffectRendererManager();

    private EffectRendererManager() {
    }

    public static EffectRendererManager getInstance() {
        return INSTANCE;
    }

    public static EffectStack resolveDominantHeartContainer(EffectContainer<?> container) {
        EffectStack dominant = null;
        for (EffectStack stack : container.getEffects()) {
            IEffectRenderer renderer = INSTANCE.get(stack.getEffect());
            if (!(renderer instanceof HeartContainerIconProvider)) continue;
            if (dominant == null || stack.getAmount() * stack.getDuration() > dominant.getAmount() * dominant.getDuration()) {
                dominant = stack;
            }
        }
        return dominant;
    }

    public void addDispatch(Effect effect, IEffectRenderer renderer) {
        EffectRendererDispatcher.getInstance().addDispatch(effect, renderer);
    }

    public IEffectRenderer get(Effect effect) {
        return EffectRendererDispatcher.getInstance().get(effect);
    }
}
