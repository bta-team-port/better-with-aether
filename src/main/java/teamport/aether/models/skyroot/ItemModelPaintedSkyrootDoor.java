package teamport.aether.models.skyroot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ItemModelPaintedSkyrootDoor extends ItemModelStandard {
    private static final IconCoordinate[] DOOR_ICONS = new IconCoordinate[16];

    public ItemModelPaintedSkyrootDoor(Item item) {
        super(item, null);

    }

    @Override
    public @NonNull IconCoordinate getIcon(Entity entity, ItemStack itemStack) {
        int meta = itemStack.getMetadata();
        return DOOR_ICONS[meta & 15];
    }

    static {
        for (DyeColor c : DyeColor.itemOrderedColors()) {
            DOOR_ICONS[c.itemMeta] = TextureRegistry.getTexture("aether:item/door_skyroot/" + c.colorID);
        }
    }
}
