package teamport.aether.item.accessory.pendant;

import net.minecraft.core.item.material.ArmorMaterial;

public class ItemChainPendant extends ItemPendant {
    public ItemChainPendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
    }

    @Override
    public int armorPieceProtection() {
        return 1;
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return this.armorPieceProtection() / 20.0F;
    }
}
