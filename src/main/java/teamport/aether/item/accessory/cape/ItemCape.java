package teamport.aether.item.accessory.cape;

import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.item.accessory.ItemAccessory;

public class ItemCape extends ItemAccessory<HumanAccessoryShape> implements IAccessoryItem<HumanAccessoryShape> {
    private final String name;

    public ItemCape(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, HumanAccessoryShape.CAPE);
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

}
