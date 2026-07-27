package teamport.aether.item.accessory.pendant;

import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.Nullable;

public class ItemCombatPendant extends ItemPendant implements IArmorItem<HumanArmorShape> {


    public ItemCombatPendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return this.armorPieceProtection() / 20.0F;
    }

    @Override
    public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }


    @Override
    public int armorPieceProtection() {
        return 1;
    }

    @Override
    public HumanArmorShape getArmorShape() {
        return HumanArmorShape.BOOTS;
    }
}
