package teamport.aether.net;

import net.minecraft.core.net.entity.entries.NetEntryAnimal;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.animal.aerwhale.MobAerwhale;

public class NetEntryAerwhale extends NetEntryAnimal<MobAerwhale> {
    @Override
    public @NotNull Class<MobAerwhale> getAppliedClass() {
        return MobAerwhale.class;
    }

    @Override
    public boolean sendMotionUpdates() {
        return true;
    }
}
