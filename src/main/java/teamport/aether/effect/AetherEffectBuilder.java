package teamport.aether.effect;

import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AetherEffectBuilder {
    public String nameKey;
    public String id;
    public String imagePath;
    public int color = 0x000000;
    public List<Modifier<?>> modifiers = new ArrayList<>();
    public EffectTimeType effectTimeType;
    public int defaultDuration = 20;
    public int maxStack = 1;
    public int tint;
    public String heartPath;

    public AetherEffectBuilder init(String nameKey, String id, String imagePath){
        this.nameKey = nameKey;
        this.id = id;
        this.imagePath = imagePath;
        return this;
    }

    public AetherEffectBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public AetherEffectBuilder setModifiers(List<Modifier<?>> modifiers) {
        this.modifiers = modifiers;
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

    public <T extends Effect> T build(Function<AetherEffectBuilder, T> constructor) {
        return constructor.apply(this);
    }

    // Getters for constructor lambda
    public String getNameKey() { return nameKey; }
    public String getId() { return id; }
    public String getImagePath() { return imagePath; }
    public int getColor() { return color; }
    public List<Modifier<?>> getModifiers() { return modifiers; }
    public EffectTimeType getEffectTimeType() { return effectTimeType; }
    public int getDefaultDuration() { return defaultDuration; }
    public int getMaxStack() { return maxStack; }
    public int getTint(){return tint;}
    public String getHeartPath(){return heartPath;}


}
