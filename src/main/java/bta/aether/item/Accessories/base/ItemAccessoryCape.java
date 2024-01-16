package bta.aether.item.Accessories.base;

import bta.aether.item.ItemToolAccessory;
import net.minecraft.core.item.ItemStack;

public class ItemAccessoryCape extends ItemToolAccessory {

    public ItemAccessoryCape(int id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"cape"};
    }

    public String getTexturePath() {
        return "assets/aether/other/AetherCape.png";
        //return "aether/other/AgilityCape.png";
        //return "aether/other/BlueCape.png";
        //return "aether/other/RedCape.png";
        //return "aether/other/WhiteCape.png";
        //return "aether/other/YellowCape.png";
        //return null;
    }
}
