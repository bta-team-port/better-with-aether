package teamport.aether.effect;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import turniplabs.halplibe.util.deathcause.DeathCause;

public class DeathCauseEffects extends DeathCause {
    private String effectsID;
    private boolean hasSecondary = false;
    private String keyShard;

    public DeathCauseEffects(){
        super();
    }

    public DeathCauseEffects(Mob victim, Effect effect){
        super(victim);
        this.effectsID = effect.id;
    }

    public DeathCause setSecondary(String keyShard){
        this.hasSecondary = true;
        this.keyShard = keyShard;
        return this;
    }

    @Override
    public String getQualifiedTranslationKey() {
        String effect = "effect";
        String[] splitted = this.effectsID.split(":");
        if(splitted.length > 1){
            effect = splitted[1];
        }
        return "messages.death.%s%s".formatted(effect, this.getTranslationKeyShard());
    }

    @Override
    protected String getTranslationKeyShard() {
        if(this.hasSecondary){
            return "." + this.keyShard;
        }
        return "";
    }

    @Override
    public void serializeAdditional(CompoundTag tag) {
        super.serialize(tag);
        tag.putString("aether:effectsID", this.effectsID);
        if(this.hasSecondary) {
            tag.putString("aether:poison_keyShard", this.keyShard);
        }
    }

    @Override
    public void deserializeAdditional(CompoundTag tag) {
        super.deserialize(tag);
        this.effectsID = tag.getString("aether:effectsID");
        this.hasSecondary = tag.containsKey("aether:poison_keyShard");
        if(this.hasSecondary){
            this.keyShard = tag.getString("aether:poison_keyShard");
        }
    }
}
