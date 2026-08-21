package teamport.aether.item.accessory;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.material.ArmorMaterial;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * @implNote All accessories are denoted by this, so we can treat them as a group
 * nothing more nothing less.
 */
public interface IAccessoryItem<T extends IAccessoryShape> extends IItemConvertible {
    @NonNull Item asItem();

    @Nullable ArmorMaterial getArmorMaterial();

    @NonNull T getArmorShape();

    default boolean fitsInShape(@NonNull T shape) {
        return getArmorShape() == shape;
    }

    default int armorPieceProtection() {
        return 0;
    }

    default float getArmorPieceProtectionPercentage() {
        return 0.0F;
    }

    default boolean takesArmorDamage() {
        return armorPieceProtection() > 0;
    }

    String name();
}
