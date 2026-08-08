package teamport.aether.item.accessory.pendant;

import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.IAccessory;

public class ItemPendant extends Item implements IAccessory {
    private final String name;
    protected final ArmorMaterial material;
    private boolean canHarvestDamage;

    public ItemPendant(String translationKey, String namespaceId, int id, String name, @NonNull ArmorMaterial material) {
        super(translationKey, namespaceId, id);
        this.name = name;
        this.maxStackSize = 1;
        this.material = material;
        float maxDurability = HumanArmorShape.BOOTS.getDurabilityModifier() * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
        this.canHarvestDamage = false;
    }

    public ItemPendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        this(translationKey, namespaceId, id, material.identifier.value(), material);
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
