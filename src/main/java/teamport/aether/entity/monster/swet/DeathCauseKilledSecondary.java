package teamport.aether.entity.monster.swet;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;

public class DeathCauseKilledSecondary extends DeathCauseKilledBy {
    private boolean hasSecondary = false;
    private String keyShard;

    public DeathCauseKilledSecondary() {
    }

    public DeathCauseKilledSecondary(Mob victim, Entity attacker) {
        super(victim, attacker);
    }

    public DeathCause setSecondary(String keyShard) {
        this.hasSecondary = true;
        this.keyShard = keyShard;
        return this;
    }


    @Override
    public void serialize(CompoundTag tag) {
        super.serialize(tag);
        if(this.hasSecondary) {
            tag.putString("aether:second_keyShard", this.keyShard);
        }
    }

    @Override
    public void deserialize(CompoundTag tag) {
        super.deserialize(tag);
        this.hasSecondary = tag.containsKey("aether:second_keyShard");
        if(this.hasSecondary){
            this.keyShard = tag.getString("aether:second_keyShard");
        }
    }

    @Override
    public String getQualifiedTranslationKey() {
        final String original = super.getQualifiedTranslationKey();
        if (this.hasSecondary) {
            return original + "." + this.keyShard;
        }
        return original;
    }
}
