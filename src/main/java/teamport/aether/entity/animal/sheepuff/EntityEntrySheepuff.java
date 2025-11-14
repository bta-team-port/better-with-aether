package teamport.aether.entity.animal.sheepuff;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import net.minecraft.client.gui.modelviewer.elements.ListenerSliderElement;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EntityEntrySheepuff extends EntityEntry<MobSheepuff> {
    public EntityEntrySheepuff() {}

    @Override
    public void onTick(MobSheepuff entity) {}


    @Override
    public List<ButtonElement> getEntryButtons(Minecraft mc, Screen parentScreen, MobSheepuff sheepuff) {
        List<ButtonElement> buttonList = new ArrayList<>();
        I18n translator = I18n.getInstance();

        ListenerSliderElement colorSlider = new ListenerSliderElement(-1, -120, 0, 120, 20,
            translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.color", "White"), 0.0F);
        colorSlider.setOnValueChanged(() -> {
            sheepuff.setFleeceColor(DyeColor.colorFromBlockMeta((int) (colorSlider.sliderValue * 15.0)));
            String color = sheepuff.getFleeceColor().colorID;
            String c = String.valueOf(color.charAt(0));
            color = c.toUpperCase() + color.substring(1);
            colorSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.color", color);
        });
        buttonList.add(colorSlider);

        ListenerSliderElement woolStateSlider = new ListenerSliderElement(-1, -120, 21, 120, 20,
            translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.wool_state", "Normal"), 0.5F);
        woolStateSlider.setOnValueChanged(() -> {
            float sliderValue = (float) woolStateSlider.sliderValue;
            if (sliderValue < 0.33F) {
                sheepuff.setSheared(true);
                sheepuff.setPuffed(false);
                woolStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.wool_state", "Sheared");
            } else if (sliderValue < 0.66F) {
                sheepuff.setSheared(false);
                sheepuff.setPuffed(false);
                woolStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.wool_state", "Normal");
            } else {
                sheepuff.setSheared(false);
                sheepuff.setPuffed(true);
                woolStateSlider.displayString = translator.translateKeyAndFormat("model.category.entity.sheepuff.slider.wool_state", "Puffy");
            }
        });
        buttonList.add(woolStateSlider);

        return buttonList;
    }

    @Override
    public MobSheepuff getEntityInstance(Minecraft mc, World world) {
        MobSheepuff sheepuff = new MobSheepuff(world);
        sheepuff.setFleeceColor(DyeColor.WHITE);
        sheepuff.setSheared(false);
        sheepuff.setPuffed(false);
        return sheepuff;
    }

    @Override
    public void onOpen() {}

    @Override
    public void onClose() {}
}
