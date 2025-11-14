package teamport.aether.helper;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public class ContainerHelper {

    public static int countArmorPiecesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        int count = 0;
        for (int i = 0; i < inventory.armorInventory.length; ++i) {
            ItemStack itemStack = inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            if (armor.getArmorPiece() != i) {
                continue;
            }
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (armorMaterial == null || !armorMaterial.equals(material)) {
                continue;
            }
            count++;
        }
        return count;
    }

    public static int countAccessoriesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        int count = 0;
        for (int i = 6; i < inventory.armorInventory.length; ++i) {
            ItemStack itemStack = inventory.armorInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof IArmorItem)) {
                continue;
            }
            IArmorItem armor = (IArmorItem) itemStack.getItem();
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if (armorMaterial == null || !armorMaterial.equals(material)) {
                continue;
            }
            count++;
        }
        return count;
    }

}
