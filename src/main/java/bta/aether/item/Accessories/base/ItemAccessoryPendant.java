package bta.aether.item.Accessories.base;

import bta.aether.item.AetherArmorMaterial;
import bta.aether.item.ItemToolAccessory;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessoryPendant extends ItemToolAccessory {
    ArmorMaterial armorMaterial;

    public ItemAccessoryPendant(int id, ArmorMaterial material) {
        super(id);
        armorMaterial = material;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"pendant"};
    }

    public String getTexturePath() {
        if (armorMaterial == ArmorMaterial.LEATHER) return "/armor/LeatherAccessories.png";
        if (armorMaterial == ArmorMaterial.CHAINMAIL) return "/armor/ChainAccessories.png";
        if (armorMaterial == ArmorMaterial.IRON) return "/armor/Accessories.png";
        if (armorMaterial == ArmorMaterial.GOLD) return "/armor/GoldAccessories.png";
        if (armorMaterial == ArmorMaterial.DIAMOND) return "/armor/DiamondAccessories.png";
        if (armorMaterial == ArmorMaterial.STEEL) return "/armor/SteelAccessories.png";
        if (armorMaterial == AetherArmorMaterial.ZANITE) return "/armor/ZaniteAccessories.png";
        if (armorMaterial == AetherArmorMaterial.GRAVITITE) return "/armor/GravititeAccessories.png";
        if (armorMaterial == AetherArmorMaterial.PHOENIX) return "/armor/Phoenix.png";
        if (armorMaterial == AetherArmorMaterial.OBSIDIAN) return "/armor/ObsidianAccessories.png";
        if (armorMaterial == AetherArmorMaterial.NEPTUNE) return "/armor/NeptuneAccessories.png";
        if (armorMaterial == AetherArmorMaterial.ICE) return "/armor/IceAccessories.png";
        return null;
    }
}
