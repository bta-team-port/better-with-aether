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
public class ItemModelPaintedSkyrootSign extends ItemModelStandard {
    public static IconCoordinate[] signIcons = new IconCoordinate[16];

    public ItemModelPaintedSkyrootSign(Item item) {
        super(item, false);
    }

    @Override
    public @NonNull IconCoordinate getIcon(Entity entity, @NonNull ItemStack itemStack) {
        int meta = itemStack.getMetadata();
        return signIcons[meta & 15];
    }

    static {
        for (DyeColor dyeColor : DyeColor.itemOrderedColors()) {
            signIcons[dyeColor.itemMeta] = TextureRegistry.getTexture("aether:item/sign_skyroot/" + dyeColor.colorID);
        }

    }
}
