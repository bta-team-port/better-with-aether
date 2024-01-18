package bta.aether.item.Accessories.base;

import bta.aether.item.AetherArmorMaterial;
import bta.aether.item.ItemToolAccessory;
import bta.aether.item.TexturePath;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessoryPendant extends ItemToolAccessory implements TexturePath {
    private final String texturePath;

    public ItemAccessoryPendant(int id, String texturePath) {
        super(id);
        this.texturePath = texturePath;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"pendant"};
    }

    public String getTexturePath() {
        return texturePath;
    }
}
