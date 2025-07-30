package teamport.aether.items.accessory;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class ItemAccessoryGloves extends ItemAccessory implements IArmorItem {
    public final ArmorMaterial material;

    public ItemAccessoryGloves(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
        this.material = material;
        float maxDurability = ItemArmor.ARMOR_PIECE_DURABILITY_MODIFIERS[3] * material.durability;
        this.setMaxDamage((int)Math.ceil(maxDurability));
    }

    @Override
    public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public int armorPieceProtection(){
        return 1;
    }

    @Override
    public int getArmorPiece() {
        return this.getAccessorySlot();
    }

}
