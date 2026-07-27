package teamport.aether.effect.api;

public class EffectRenderer<T extends Effect> implements IEffectRenderer {
    protected final T effect;
    private String icon;
    public EffectRenderer(T effect) { this.effect = effect; }
    public EffectRenderer<T> setIcon(String icon) { this.icon = icon; return this; }
    @Override public T getEffect() { return effect; }
    @Override public String getIcon() { return icon; }
}
