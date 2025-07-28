package teamport.aether.items.accessory;

import net.minecraft.core.item.material.ArmorMaterial;

public class ItemShield extends ItemAccessory{
    public ItemShield(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
    }

    public ItemShield(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }
}
