package teamport.aether.entity.boss.slider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import net.minecraft.client.gui.modelviewer.elements.ListenerSliderElement;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EntityEntryBossSlider extends EntityEntry<MobBossSlider> {
    public EntityEntryBossSlider() {}

    @Override
    public void onTick(MobBossSlider entity) {
    }

    @Override
    public List<ButtonElement> getEntryButtons(Minecraft mc, Screen parentScreen, MobBossSlider slider) {
        List<ButtonElement> buttonList = new ArrayList<>();
        I18n translator = I18n.getInstance();

        ListenerSliderElement sliderStateSlider = new ListenerSliderElement(-1, -120, 0, 120, 20,
            translator.translateKeyAndFormat("model.category.entity.slider.slider.state", "Asleep Calm"), 0.0F);
        sliderStateSlider.setOnValueChanged(() -> {
            float sliderValue = (float) sliderStateSlider.sliderValue;
            if (sliderValue < 0.25F) {
                slider.setState(MobBossSlider.State.ASLEEP);
                slider.setHealthRaw(slider.getMaxHealth());
                sliderStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.slider.slider.state", "Asleep Calm");
            } else if (sliderValue < 0.50F) {
                slider.setState(MobBossSlider.State.ASLEEP);
                slider.setHealthRaw(1);
                sliderStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.slider.slider.state", "Asleep Angry");
            } else if (sliderValue < 0.75F) {
                slider.setState(MobBossSlider.State.AWAKE);
                slider.setHealthRaw(slider.getMaxHealth());
                sliderStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.slider.slider.state", "Awake Calm");
            } else {
                slider.setState(MobBossSlider.State.AWAKE);
                slider.setHealthRaw(1);
                sliderStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.slider.slider.state", "Awake Angry");
            }
        });
        buttonList.add(sliderStateSlider);

        return buttonList;
    }

    @Override
    public MobBossSlider getEntityInstance(Minecraft mc, World world) {
        MobBossSlider slider = new MobBossSlider(world);
        slider.returnToOriginalState();
        return slider;
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClose() {}
}
