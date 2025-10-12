package teamport.aether.items.accessory;

import net.minecraft.core.item.Item;
import teamport.aether.items.AetherItemTags;
import teamport.aether.lookup.LookupTrinketIcons;

public class ItemTrinket extends Item implements IAccessory {
    public final String name;

    public ItemTrinket(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        this.withTags(AetherItemTags.TRINKET);
    }

    public ItemTrinket(String translationKey, String namespaceId, int id, String name, String path) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        LookupTrinketIcons.instance.addEntry(this.namespaceID, path);
        this.withTags(AetherItemTags.TRINKET);
    }

    public void setIcon(String path) {
        LookupTrinketIcons.instance.addEntry(this.namespaceID, path);
    }

    public static void setIcon(Item item, String path) {
        LookupTrinketIcons.instance.addEntry(item.namespaceID, path);
        item.withTags(AetherItemTags.TRINKET);
    }

    @Override
    public String name() {
        return name;
    }
}