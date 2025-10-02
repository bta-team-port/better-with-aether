package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ItemModelPaintedSign extends ItemModelStandard{
    public static IconCoordinate[] sign = new IconCoordinate[16];

    public ItemModelPaintedSign(Item item) {
        super(item, (String) null);
        for (DyeColor dye : DyeColor.itemOrderedColors()) {
            sign[dye.itemMeta] = TextureRegistry.getTexture("aether:item/sign_skyroot/" + dye.colorID);
        }

    }

    public @NotNull IconCoordinate getIcon(Entity entity, ItemStack itemStack) {
        int meta = itemStack.getMetadata();
        return sign[meta & 15];
    }
}

