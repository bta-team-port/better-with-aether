package teamport.aether.item.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public interface IAccessoryWearing<T extends IAccessoryShape> {
    @Nullable ItemStack getAccessoryInSlot(@NonNull T slot);

    void setAccessoryInSlot(@NonNull T slot, @Nullable ItemStack stack);

    int getNumAccessorySlots();

    @Nullable T getAccessorySlotByIndex(int index);

    default float getTotalAccessoryProtectionAmount(@NonNull DamageType damageType) {
        float protectionPercentage = 0.0F;

        for (int i = 0; i < this.getNumAccessorySlots(); ++i) {
            T slot = Objects.requireNonNull(this.getAccessorySlotByIndex(i));
            ItemStack itemStack = this.getAccessoryInSlot(slot);
            if (itemStack != null) {
                Item item = itemStack.getItem();
                if (item instanceof IAccessoryItem<?> accessory) {
                    ArmorMaterial material = accessory.getArmorMaterial();
                    if (material != null) {
                        protectionPercentage += material.getProtection(damageType) * accessory.getArmorPieceProtectionPercentage();
                    }
                }
            }
        }

        return protectionPercentage;
    }

    default void damageAccessories(int damage, @NonNull T slot) {
        ItemStack itemStack = this.getAccessoryInSlot(slot);
        if (itemStack != null) {
            Item item = itemStack.getItem();
            if (item instanceof IAccessoryItem<?> accessory) {
                if (!accessory.takesArmorDamage()) {
                    return;
                }

                ArmorMaterial material = accessory.getArmorMaterial();
                if (material != null) {
                    if (this instanceof Entity entity) {
                        itemStack.damageItem(damage, entity);
                    } else {
                        itemStack.damageItem(damage, null);
                    }

                    if (itemStack.stackSize <= 0) {
                        this.setAccessoryInSlot(slot, null);
                    }
                }
            }
        }
    }

    default void damageAccessories(int damage) {
        for (int i = 0; i < this.getNumAccessorySlots(); ++i) {
            T slot = Objects.requireNonNull(this.getAccessorySlotByIndex(i));
            this.damageAccessories(damage, slot);
        }
    }

    default boolean canItemGoInAccessorySlot(@NonNull T slot, @Nullable ItemStack item) {
        return item == null || this.canItemGoInAccessorySlot(slot, item.getItem());
    }

    @SuppressWarnings("unchecked")
    default boolean canItemGoInAccessorySlot(@NonNull T slot, @Nullable IItemConvertible item) {
        if (item == null) {
            return false;
        } else {
            Item realItem = item.asItem();
            if (!(realItem instanceof IAccessoryItem<?> accessory)) {
                return false;
            } else {
                return ((IAccessoryItem<T>) accessory).fitsInShape(slot);
            }
        }
    }
}
