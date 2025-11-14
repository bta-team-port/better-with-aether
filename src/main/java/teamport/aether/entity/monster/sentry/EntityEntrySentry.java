package teamport.aether.entity.monster.sentry;

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
public class EntityEntrySentry extends EntityEntry<MobSentry> {
    public EntityEntrySentry() {}

    @Override
    public void onTick(MobSentry entity) {}

    @Override
    public List<ButtonElement> getEntryButtons(Minecraft mc, Screen parentScreen, MobSentry sentry) {
        List<ButtonElement> buttonList = new ArrayList<>();
        I18n translator = I18n.getInstance();

        ListenerSliderElement sentryStateSlider = new ListenerSliderElement(-1, -120, 0, 120, 20,
            translator.translateKeyAndFormat("model.category.entity.sentry.slider.state", "Deactivated"), 0.0F);
        sentryStateSlider.setOnValueChanged(() -> {
            float sentryValue = (float) sentryStateSlider.sliderValue;
            if (sentryValue < 0.50F) {
                sentry.setActivated(false);
                sentryStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sentry.slider.state", "Deactivated");
            } else {
                sentry.setActivated(true);
                sentryStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sentry.slider.state", "Activated");
            }
        });
        buttonList.add(sentryStateSlider);

        return buttonList;
    }

    @Override
    public MobSentry getEntityInstance(Minecraft mc, World world) {
        return new MobSentry(world);
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClose() {}
}
