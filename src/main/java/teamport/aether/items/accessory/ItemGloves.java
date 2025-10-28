package teamport.aether.items.accessory;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class ItemGloves extends ItemAccessoryArmor implements IArmorItem {
    public final ArmorMaterial material;
    public int damage;

    public ItemGloves(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material.identifier.value(), accessoryPiece);
        this.material = material;
        float maxDurability = ItemArmor.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int) Math.ceil(maxDurability));
        this.damage = 1;
    }

    public ItemGloves setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public int armorPieceProtection() {
        return 3;
    }

    @Override
    public float getArmorPieceProtectionPercentage() {
        return (float) this.armorPieceProtection() / 40.0f;
    }

    @Override
    public int getArmorPiece() {
        return this.getSlotID();
    }
}

