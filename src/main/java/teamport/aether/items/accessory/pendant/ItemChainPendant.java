package teamport.aether.items.accessory.pendant;

import net.minecraft.core.item.material.ArmorMaterial;

public class ItemChainPendant extends ItemPendant {
    public final String name;

    public ItemChainPendant(String translationKey, String namespaceId, int id, String name, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
        this.name = name;
    }

    @Override
    public int armorPieceProtection() {
        return 1;
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return (float) this.armorPieceProtection() / 20.0F;
    }
}
