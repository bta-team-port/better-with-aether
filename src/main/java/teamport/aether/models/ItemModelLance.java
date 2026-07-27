package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import org.useless.dragonfly.DisplayPos;

@Environment(EnvType.CLIENT)
public class ItemModelLance extends ItemModelStandard {
    public ItemModelLance(Item item, boolean handheld) {
        super(item, handheld);
        this.setDisplayPos(DisplayPos.THIRD_PERSON_RIGHT_HAND, new DisplayPos(
            0f, 0.4f, 0.03125f, 0f, -90f, -35f, 1.25f, 1.25f, 1.25f
        ));
        this.setDisplayPos(DisplayPos.THIRD_PERSON_LEFT_HAND, new DisplayPos(
            0f, 0.4f, 0.03125f, 0f, 90f, 35f, 1.25f, 1.25f, 1.25f
        ));
        this.setDisplayPos(DisplayPos.FIRST_PERSON_RIGHT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_RIGHT_HAND);
        this.setDisplayPos(DisplayPos.FIRST_PERSON_LEFT_HAND, ItemModelDispatcher.HANDHELD_FIRST_PERSON_LEFT_HAND);
    }
}
