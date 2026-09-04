package teamport.aether.entity;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import turniplabs.halplibe.util.deathcause.DeathCause;

/**
 * @deprecated Will be deprecated in the next HalpLibe release (6.2.1).
 */
@Deprecated(forRemoval = true)
public class DeathCauseKeyed extends DeathCause {
    String keyShard = "generic";

    public DeathCauseKeyed(){
        super();
    }

    public DeathCauseKeyed(Mob victim){
        super(victim);
    }

    public DeathCauseKeyed(Mob victim, String keyShard){
        super(victim);
        this.keyShard = keyShard;

    }

    @Override
    protected String getTranslationKeyShard() {
        return this.keyShard;
    }

    @Override
    protected void serializeAdditional(CompoundTag tag) {
        tag.putString("halplibe:key_shard", this.keyShard);
    }

    @Override
    protected void deserializeAdditional(CompoundTag tag) {
        if(tag.containsKey("halplibe:key_shard")){
            this.keyShard = tag.getString("halplibe:key_shard");
        }
    }
}
