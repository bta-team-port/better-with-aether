package teamport.aether.item.accessory.pendant;

import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.item.accessory.ItemAccessory;
import teamport.aether.item.accessory.SlotAccessory;

public class ItemPendant extends ItemAccessory<HumanAccessoryShape> implements IAccessoryItem<HumanAccessoryShape> {
    private final String name;
    private boolean canHarvestDamage;

    public ItemPendant(@NonNull String translationKey, @NonNull String namespaceId, int id, @NonNull ArmorMaterial material, String name) {
        super(translationKey, namespaceId, id, material, HumanAccessoryShape.TRINKET);
        this.name = name;
        this.maxStackSize = 1;
        float maxDurability = HumanArmorShape.BOOTS.getDurabilityModifier() * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
        this.canHarvestDamage = false;
    }

    public ItemPendant(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, HumanAccessoryShape.TRINKET);
        this.name = name;
        this.maxStackSize = 1;
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
        this.canHarvestDamage = false;
    }

    @Override
    public boolean isEquipped(int relativeSlot) {
        return relativeSlot == SlotAccessory.TRINKET_1_SLOT || relativeSlot == SlotAccessory.TRINKET_2_SLOT;
    }

    @Override
    public boolean fitsInShape(@NonNull HumanAccessoryShape shape) {
        return shape == HumanAccessoryShape.TRINKET;
    }

    @Override
    public String name() {
        return name;
    }

    public ItemPendant setHarvestDamageable() {
        this.canHarvestDamage = true;
        return this;
    }

    public boolean canHarvestDamage() {
        return this.canHarvestDamage;
    }
}
