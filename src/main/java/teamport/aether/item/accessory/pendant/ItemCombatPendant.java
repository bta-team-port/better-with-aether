package teamport.aether.item.accessory.pendant;

import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;

public class ItemCombatPendant extends ItemPendant {

    public ItemCombatPendant(@NonNull String translationKey, @NonNull String namespaceId, int id, @NonNull ArmorMaterial material, String name) {
        super(translationKey, namespaceId, id, material, name);
    }

    @Override
    public int armorPieceProtection() {
        return HumanArmorShape.BOOTS.getProtectionValue();
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return (float) this.armorPieceProtection() / 80.0f;
    }

}
