package teamport.aether.items.accessory;

import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessoryPendant extends ItemAccessory{
    public ItemAccessoryPendant(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public ItemAccessoryPendant(String translationKey, String namespaceId, int id, ArmorMaterial name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }
}
