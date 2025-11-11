package teamport.aether.models.dungeon;

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
    public static final IconCoordinate[] doorIcons = new IconCoordinate[16];

    public ItemModelPaintedSkyrootDoor(Item item) {
        super(item, null);

    }

    public @NonNull IconCoordinate getIcon(Entity entity, ItemStack itemStack) {
        int meta = itemStack.getMetadata();
        return doorIcons[meta & 15];
    }

    static {
        for (DyeColor c : DyeColor.itemOrderedColors()) {
            doorIcons[c.itemMeta] = TextureRegistry.getTexture("aether:item/door_skyroot/" + c.colorID);
        }
    }
}
