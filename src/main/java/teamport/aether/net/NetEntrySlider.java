package teamport.aether.net;

import net.minecraft.core.net.entity.entries.NetEntryAnimal;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.boss.slider.MobBossSlider;

public class NetEntrySlider extends NetEntryAnimal<MobBossSlider> {

    @Override
    public @NonNull Class<MobBossSlider> getAppliedClass() {
        return MobBossSlider.class;
    }

    @Override
    public boolean sendMotionUpdates() {
        return true;
    }

    @Override
    public int getPacketDelay() {
        return 1;
    }
}
