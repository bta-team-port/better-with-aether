package teamport.aether.items.accessory.pendant;

import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.IAccessory;

public class ItemPendant extends Item implements IAccessory {
    public final String name;

    public ItemPendant(String translationKey, String namespaceId, int id, String name, ArmorMaterial material) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        float maxDurability = ItemArmor.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.withTags(new Tag[]{AetherItemTags.TRINKET});
    }

    public ItemPendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        this(translationKey, namespaceId, id, material.identifier.value(), material);
    }

    @Override
    public String name() {
        return name;
    }
}
