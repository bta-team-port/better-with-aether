package teamport.aether.items.accessory.trinket;

import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.items.accessory.ItemAccessoryTrinket;

public class ItemDamageablePendant extends ItemAccessoryTrinket {
    public ItemDamageablePendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
        float maxDurability = ItemArmor.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
    }
}
