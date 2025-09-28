package teamport.aether.effect;

import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.helper.Union;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherEffectBuilder {
    private String nameKey;
    private String id;
    private int color = 0x000000;
    private List<Modifier<?>> modifiers = new ArrayList<>();
    private EffectTimeType effectTimeType = EffectTimeType.KEEP;
    private int defaultDuration = 20;
    private int maxStack = 1;
    private int tint = 0x0;
    private String heartPath = "minecraft:gui/hud/heart/";
    private String vignette = "";
    private boolean isPersistent = false;

    private Union icon = EnvironmentHelper.isServerEnvironment() ? null : new Union(String.class, IconCoordinate.class);

    public AetherEffectBuilder init(String nameKey, String id){
        this.nameKey = nameKey;
        this.id = id;
        return this;
    }

    public AetherEffectBuilder setIcon(Supplier<Union> iconCoordinateUnion) {
        if (!EnvironmentHelper.isServerEnvironment()) {
            icon.set(iconCoordinateUnion.get());
        }

        return this;
    }

    public AetherEffectBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public AetherEffectBuilder addModifier(Modifier<?>... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    public AetherEffectBuilder setEffectTimeType(EffectTimeType effectTimeType) {
        this.effectTimeType = effectTimeType;
        return this;
    }

    public AetherEffectBuilder setDefaultDuration(int defaultDuration) {
        this.defaultDuration = defaultDuration;
        return this;
    }

    public AetherEffectBuilder setMaxStack(int maxStack) {
        this.maxStack = maxStack;
        return this;
    }

    public AetherEffectBuilder setTint(int tint) {
        this.tint = tint;
        return this;
    }


    public AetherEffectBuilder setHeartPath(String heartPath) {
        this.heartPath = heartPath;
        return this;
    }

    public AetherEffectBuilder setVignette(String vignette){
        this.vignette = vignette;
        return this;
    }

    public AetherEffectBuilder setPersistent() {
        this.isPersistent = true;
        return this;
    }

    public <T extends Effect> T build(Function<AetherEffectBuilder, T> constructor) {
        return constructor.apply(this);
    }

    public Effect buildRegularEffect() {
        // You might be wondering, "why?"... I am too. c:

        Effect result = null;
        if (icon != null) {
            if (icon.of(String.class).isPresent()) {
                result = new Effect(
                        this.getNameKey(),
                        this.getId(),
                        icon.of(String.class).get(),
                        this.getColor(),
                        this.getModifiers(),
                        this.getEffectTimeType(),
                        this.getDefaultDuration(),
                        this.getMaxStack()
                );
            }

            else if (icon.of(IconCoordinate.class).isPresent()) {
                result = new Effect(
                        this.getNameKey(),
                        this.getId(),
                        icon.of(IconCoordinate.class).get(),
                        this.getColor(),
                        this.getModifiers(),
                        this.getEffectTimeType(),
                        this.getDefaultDuration(),
                        this.getMaxStack()
                );
            }
        }

        if (result == null) {
            result = new Effect(
                this.getNameKey(),
                this.getId(),
                (String) null,
                this.getColor(),
                this.getModifiers(),
                this.getEffectTimeType(),
                this.getDefaultDuration(),
                this.getMaxStack()
            );
        }

        if (isPersistent()) result.setPersistent();
        return result;
    }

    // Getters for constructor lambda
    public String getNameKey() { return nameKey; }
    public String getId() { return id; }
    public int getColor() { return color; }
    public List<Modifier<?>> getModifiers() { return modifiers; }
    public EffectTimeType getEffectTimeType() { return effectTimeType; }
    public int getDefaultDuration() { return defaultDuration; }
    public int getMaxStack() { return maxStack; }
    public int getTint(){return tint;}
    public String getHeartPath(){return heartPath;}
    public String getVignette(){return vignette;}

    public Union getIcon() {return icon;}

    public boolean isPersistent() {
        return this.isPersistent;
    }
}
