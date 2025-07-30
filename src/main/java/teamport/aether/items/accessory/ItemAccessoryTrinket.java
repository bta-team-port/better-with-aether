package teamport.aether.items.accessory;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.items.AetherItemTags;

public class ItemAccessoryTrinket extends ItemAccessory{
    public ItemAccessoryTrinket(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name, 6);
        this.withTags(new Tag[]{AetherItemTags.TRINKET});
    }

    public ItemAccessoryTrinket(String translationKey, String namespaceId, int id, ArmorMaterial name) {
        super(translationKey, namespaceId, id, name, 6);
        this.withTags(new Tag[]{AetherItemTags.TRINKET});
    }
}
