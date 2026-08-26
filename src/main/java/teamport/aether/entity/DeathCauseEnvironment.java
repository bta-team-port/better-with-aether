package teamport.aether.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import turniplabs.halplibe.util.deathcause.DeathCause;

public class DeathCauseEnvironment extends DeathCause {
    private String keyShard;

    public DeathCauseEnvironment() {
    }

    public DeathCauseEnvironment(Mob victim, String keyShard) {
        super(victim);
        this.keyShard = keyShard;
    }

    @Override
    public void serialize(CompoundTag tag) {
        super.serialize(tag);
    }

    @Override
    public void deserialize(CompoundTag tag) {
        super.deserialize(tag);
    }

    @Override
    protected String getTranslationKeyShard() {
        return this.keyShard;
    }
}
