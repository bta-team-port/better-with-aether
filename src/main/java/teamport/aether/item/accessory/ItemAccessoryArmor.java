package teamport.aether.item.accessory;

import net.minecraft.core.item.Item;

public class ItemAccessoryArmor extends Item implements IAccessory {
    private final int slotID;
    private final String name;

    public ItemAccessoryArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.slotID = accessoryPiece;
        this.maxStackSize = 1;
    }

    public int getSlotID() {
        return this.slotID;
    }

    @Override
    public String name() {
        return name;
    }
}
