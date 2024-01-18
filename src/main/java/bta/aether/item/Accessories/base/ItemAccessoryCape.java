package bta.aether.item.Accessories.base;

import bta.aether.item.ItemToolAccessory;
import bta.aether.item.TexturePath;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryCape extends ItemToolAccessory implements TexturePath {
    private String texturePath;

    public ItemAccessoryCape(int id, String texturePath) {
        super(id);
        this.texturePath = texturePath;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"cape"};
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }
}
