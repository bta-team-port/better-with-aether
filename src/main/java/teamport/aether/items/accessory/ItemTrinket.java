package teamport.aether.items.accessory;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import teamport.aether.items.AetherItemTags;
import java.util.Set;

public class ItemTrinket extends Item implements IAccessory {
    public final String name;
    public Set<Integer> slotIDs;

    public ItemTrinket(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        this.withTags(new Tag[]{AetherItemTags.TRINKET});
    }

    @Override
    public String name() {
        return name;
    }
}