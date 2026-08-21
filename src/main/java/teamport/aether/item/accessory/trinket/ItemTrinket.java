package teamport.aether.item.accessory.trinket;

import net.minecraft.core.item.Item;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.item.accessory.ItemAccessory;
import teamport.aether.item.accessory.SlotAccessory;
import teamport.aether.lookup.LookupTrinketIcons;

public class ItemTrinket extends ItemAccessory<HumanAccessoryShape> implements IAccessoryItem<HumanAccessoryShape> {
    private final String name;

    public ItemTrinket(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, HumanAccessoryShape.TRINKET);
        this.name = name;
        this.maxStackSize = 1;
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    public static void setIcon(@NonNull Item item, String path) {
        LookupTrinketIcons.INSTANCE.addEntry(item, path);
        item.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    @Override
    public boolean fitsInShape(@NonNull HumanAccessoryShape shape) {
        return shape == HumanAccessoryShape.TRINKET;
    }

    @Override
    public boolean isEquipped(int relativeSlot) {
        return relativeSlot == SlotAccessory.TRINKET_1_SLOT || relativeSlot == SlotAccessory.TRINKET_2_SLOT;
    }

    @Override
    public String name() {
        return name;
    }
}
