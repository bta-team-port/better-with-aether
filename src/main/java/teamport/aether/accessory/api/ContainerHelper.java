package teamport.aether.accessory.api;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;

public class ContainerHelper{

    public static int countArmorPiecesOfMaterial(ContainerInventory inventory, ArmorMaterial material) {
        return ((ICountArmor)inventory).aether$countArmorPiecesOfMaterial(material);
    }
}
