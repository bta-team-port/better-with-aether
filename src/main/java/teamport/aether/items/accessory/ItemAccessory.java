package teamport.aether.items.accessory;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ArmorMaterial;

public class ItemAccessory extends Item implements Accessory{
    private final int accessoryPiece;
    private final String name;

    public ItemAccessory(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id);
        this.accessoryPiece = accessoryPiece;
        this.name = name;
        this.maxStackSize = 1;
    }

    public ItemAccessory(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id);
        this.accessoryPiece = accessoryPiece;
        this.name = material.identifier.value();
        this.maxStackSize = 1;
    }

    @Override
    public int getAccessorySlot() {
        return this.accessoryPiece;
    }

    public String name(){return this.name;}
}
