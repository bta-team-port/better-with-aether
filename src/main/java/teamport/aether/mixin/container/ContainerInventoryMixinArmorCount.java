package teamport.aether.mixin.container;

import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.api.ICountArmor;

@Mixin(value = ContainerInventory.class, remap = false)
public class ContainerInventoryMixinArmorCount  implements ICountArmor {
    @Shadow
    public ItemStack[] armorInventory;

    @Unique
    public int aether$countArmorPiecesOfMaterial(ArmorMaterial material) {
        int count = 0;
        for (int i = 0; i < this.armorInventory.length; ++i) {
            ItemStack itemStack = this.armorInventory[i];
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
}
