package teamport.aether.effect.api;

public class TintEffectRender<T extends Effect> extends EffectRenderer<T> {
    public final String vignette;
    public final int tint;
    public TintEffectRender(T effect, String vignette, int tint) {
        super(effect);
        this.vignette = vignette;
        this.tint = tint;
    }
    public float calcAlpha(EffectStack stack) { return 1.0F; }
    @Override public TintEffectRender<T> setIcon(String icon) { super.setIcon(icon); return this; }
}
