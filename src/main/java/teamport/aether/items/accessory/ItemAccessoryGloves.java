package teamport.aether.items.accessory;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class ItemAccessoryGloves extends ItemAccessory implements IArmorItem {
    private final ArmorMaterial material;

    public ItemAccessoryGloves(String translationKey, String namespaceId, int id, ArmorMaterial material, int accessoryPiece) {
        super(translationKey, namespaceId, id, material, accessoryPiece);
        this.material = material;
        this.setMaxDamage(material.durability);
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
        return this.getAccessoryTypes();
    }
}
