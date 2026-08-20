package teamport.aether.item.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherArmorMaterial;

public interface IAccessoryWearing<T extends IAccessoryShape> {
    @Nullable ItemStack getAccessoryInSlot(int slotIndex);

    void setAccessoryInSlot(int slotIndex, @Nullable ItemStack stack);

    int getNumAccessorySlots();

    @Nullable T getSlotShape(int slotIndex);

    default float getTotalAccessoryProtectionAmount(@NonNull DamageType damageType) {
        float protectionPercentage = 0.0F;

        for (int i = 0; i < this.getNumAccessorySlots(); ++i) {
            ItemStack itemStack = this.getAccessoryInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof IAccessoryItem<?> accessory) {
                ArmorMaterial material = accessory.getArmorMaterial();
                if (material != null) {
                    float materialProtection = material.getProtection(damageType);

                    if (material == AetherArmorMaterial.ZANITE && itemStack.isItemStackDamageable()) {
                        float durabilityProgress = (float) itemStack.getMetadata() / (float) itemStack.getMaxDamage();
                        materialProtection = MathHelper.lerp(materialProtection, AetherArmorMaterial.ZANITE_BROKEN.getProtection(damageType), durabilityProgress);
                    }

                    protectionPercentage += materialProtection * accessory.getArmorPieceProtectionPercentage();
                }
            }
        }
        return protectionPercentage;
    }

    default void damageAccessories(int damage, int slotIndex) {
        ItemStack itemStack = this.getAccessoryInSlot(slotIndex);
        if (itemStack != null && itemStack.getItem() instanceof IAccessoryItem<?> accessory) {
            if (!accessory.takesArmorDamage()) return;

            if (accessory.getArmorMaterial() != null) {
                Entity entity = (this instanceof Entity e) ? e : null;
                itemStack.damageItem(damage, entity);

                if (itemStack.stackSize <= 0) {
                    this.setAccessoryInSlot(slotIndex, null);
                }
            }
        }
    }

    default void damageAccessories(int damage) {
        for (int i = 0; i < this.getNumAccessorySlots(); ++i) {
            this.damageAccessories(damage, i);
        }
    }

    default boolean canItemGoInAccessorySlot(int slotIndex, @Nullable ItemStack item) {
        return item == null || this.canItemGoInAccessorySlot(slotIndex, item.getItem());
    }

    @SuppressWarnings("unchecked")
    default boolean canItemGoInAccessorySlot(int slotIndex, @Nullable IItemConvertible item) {
        if (item == null || !(item.asItem() instanceof IAccessoryItem<?> accessory)) {
            return false;
        }
        T slotShape = this.getSlotShape(slotIndex);
        return slotShape != null && ((IAccessoryItem<T>) accessory).fitsInShape(slotShape);
    }
}
