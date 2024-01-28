package bta.aether.item.Accessories.base;

import bta.aether.entity.IAetherAccessories;
import bta.aether.item.ItemToolAccessory;
import bta.aether.item.TexturePath;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryGloves extends ItemToolAccessory implements TexturePath {
    private final String texturePath;

    public ItemAccessoryGloves(String name, int id, String texturePath) {
        super(name, id);
        this.texturePath = texturePath;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"gloves"};
    }

    @Override
    public void onAccessoryRemoved(EntityPlayer player, ItemStack accessory) {
        ((IAetherAccessories)player).aether$setInvisible(false);
    }

    public String getTexturePath() {
        return texturePath;
    }
}
