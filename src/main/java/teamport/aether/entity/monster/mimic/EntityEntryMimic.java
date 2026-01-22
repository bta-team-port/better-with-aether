package teamport.aether.entity.monster.mimic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntry;
import net.minecraft.client.gui.modelviewer.elements.TextCycleElement;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EntityEntryMimic extends EntityEntry<MobMimic> {

    @Override
    public List<ButtonElement> getEntryButtons(Minecraft mc, Screen parentScreen, MobMimic mimic) {
        List<ButtonElement> buttonList = new ArrayList<>();
        I18n translator = I18n.getInstance();
        TextCycleElement<String> type = new TextCycleElementMimic(parentScreen, mc.font, -120, 0, 120, 20, "Skyroot");
        type.textField.setPrefaceText(translator.translateKey("model.category.entity.mimic.placeholder.text"));
        type.textField.setPlaceholder(translator.translateKey("model.category.entity.mimic.placeholder"));
        type.setOnValueChanged(() -> setNextType(type, mimic));
        buttonList.add(type);
        return buttonList;
    }

    private static void setNextType(TextCycleElement<String> type, MobMimic mobGolem) {
        mobGolem.setVariant(MimicRegistry.getMimicVariantByName(type.getCurrentElement()).getMimicVariant());
    }

    @Override
    public MobMimic getEntityInstance(Minecraft mc, World world) {
        return new MobMimic(world);
    }

    @Override
    public void onOpen() {
        /*does not need to onOpen*/
    }

    @Override
    public void onClose() {
        /*does not need to onClose*/
    }

    @Override
    public void onTick(MobMimic entity) {
        /*does not need to tick*/
    }
}
