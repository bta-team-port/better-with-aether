package teamport.aether.item.accessory;

import net.minecraft.core.item.Item;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItemTags;
import teamport.aether.lookup.LookupTrinketIcons;

public class ItemTrinket extends Item implements IAccessory {
    private final String name;

    public ItemTrinket(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    public ItemTrinket(String translationKey, String namespaceId, int id, String name, String path) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        LookupTrinketIcons.INSTANCE.addEntry(this.namespaceID, path);
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    public static void setIcon(@NonNull Item item, String path) {
        LookupTrinketIcons.INSTANCE.addEntry(item.namespaceID, path);
        item.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    @Override
    public String name() {
        return name;
    }
}
