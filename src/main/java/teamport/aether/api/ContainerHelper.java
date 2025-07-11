package teamport.aether.api;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public class ContainerHelper{

    // TODO move damageArmor function used in immunities here
    public static int countArmorPiecesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        return ((ICountArmor)inventory).aether$countArmorPiecesOfMaterial(material);
    }

    public static float getTotalEquippedArmorProtection(ContainerInventory inventory, ArmorMaterial material){
        float totalProtection  = 0;
        for(int i = 0; i < inventory.armorInventory.length; ++i){
            ItemStack itemStack = inventory.armorInventory[i];
            if(itemStack == null || !(itemStack.getItem() instanceof IArmorItem)){
                continue;
            }
            IArmorItem armor = (IArmorItem)itemStack.getItem();
            if(armor.getArmorPiece() != i){
                continue;
            }
            ArmorMaterial armorMaterial = armor.getArmorMaterial();
            if(armorMaterial == null || !armorMaterial.equals(material)){
                continue;
            }
            totalProtection  += armor.getArmorPieceProtectionPercentage();
        }
        return totalProtection ;
    }
}
