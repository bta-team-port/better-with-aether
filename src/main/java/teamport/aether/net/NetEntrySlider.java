package teamport.aether.net;

import net.minecraft.core.net.entity.entries.NetEntryAnimal;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.boss.slider.MobBossSlider;

public class NetEntrySlider extends NetEntryAnimal<MobBossSlider> {

    @Override
    public @NotNull Class<MobBossSlider> getAppliedClass() {
        return MobBossSlider.class;
    }


    @Override
    public int getTrackingDistance() {
        return super.getTrackingDistance();
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
