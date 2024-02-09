package bta.aether.item.Accessories.base;

import bta.aether.item.ItemToolAccessory;
import bta.aether.item.TexturePath;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessoryGloves extends ItemToolAccessory implements TexturePath {
    private final String texturePath;
    private final ArmorMaterial armorMaterial;

    public ItemAccessoryGloves(String name, int id, String texturePath, ArmorMaterial armorMaterial) {
        super(name, id);
        this.texturePath = texturePath;
        this.armorMaterial = armorMaterial;
        // 3 is the boots piece type
        setMaxDamage((int) (ItemArmor.armorPieceDurabilityModifiers[3] * armorMaterial.durability));
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"gloves"};
    }

    public String getTexturePath() {
        return texturePath;
    }

    public ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }
}
