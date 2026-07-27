package teamport.aether.effect.api;

public class EffectStack {
    private final IHasEffects<?> owner;
    private final Effect effect;
    private int duration;
    private int timeLeft;
    private int amount;
    private double motionDrift;
    private double rotationDrift;

    public EffectStack(IHasEffects<?> owner, Effect effect, int amount) {
        this(owner, effect, effect.getDefaultDuration(), amount);
    }

    public EffectStack(IHasEffects<?> owner, Effect effect, int duration, int amount) {
        this.owner = owner;
        this.effect = effect;
        this.duration = Math.max(1, duration);
        this.timeLeft = this.duration;
        this.amount = Math.max(0, Math.min(amount, effect.getMaxStack()));
    }

    public static EffectStack load(IHasEffects<?> owner, Effect effect, int duration, int timeLeft, int amount) {
        EffectStack stack = new EffectStack(owner, effect, duration, amount);
        stack.timeLeft = Math.max(0, Math.min(timeLeft, stack.duration));
        return stack;
    }

    public Effect getEffect() { return effect; }
    public int getDuration() { return duration; }
    public int getTimeLeft() { return timeLeft; }
    public int getAmount() { return amount; }
    public double getMotionDrift() { return motionDrift; }
    public void setMotionDrift(double motionDrift) { this.motionDrift = motionDrift; }
    public double getRotationDrift() { return rotationDrift; }
    public void setRotationDrift(double rotationDrift) { this.rotationDrift = rotationDrift; }

    public void start(EffectContainer<?> container) {
        timeLeft = duration;
        effect.activated(this, container);
    }

    public void add(int count, EffectContainer<?> container) {
        if (count <= 0) return;
        amount = Math.min(effect.getMaxStack(), amount + count);
        if (effect.getEffectTimeType() == EffectTimeType.RESET) timeLeft = duration;
        effect.stackAdded(this, container);
    }

    public void refresh() {
        timeLeft = duration;
    }

    void tick(EffectContainer<?> container) {
        if (effect.getEffectTimeType() == EffectTimeType.PERMANENT) {
            effect.tick(this, container);
            return;
        }
        if (timeLeft <= 0) {
            effect.expired(this, container);
            return;
        }
        effect.tick(this, container);
        if (--timeLeft <= 0) effect.expired(this, container);
    }

    void tickClientTimer() {
        if (effect.getEffectTimeType() != EffectTimeType.PERMANENT && timeLeft > 0) {
            --timeLeft;
        }
    }
}
