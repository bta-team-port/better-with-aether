package teamport.aether.items.accessory.trinket;

import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.items.accessory.ItemAccessoryTrinket;

public class ItemShield extends ItemAccessoryTrinket {
    public ItemShield(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
    }

    public ItemShield(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }
}
