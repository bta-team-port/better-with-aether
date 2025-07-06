package teamport.aether.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.aether.accessory.api.Accessory;
import teamport.aether.accessory.api.AccessoryTypeRegistry;

public class AccessorySlot extends Slot {

    String type_key;

    public AccessorySlot(Container inventory, int id, int x, int y, String type_key) {
        super(inventory, id, x, y);
        this.type_key = type_key;
    }

    @Override
    public boolean mayPlace(ItemStack item) {
        if (item.getItem() instanceof Accessory) {
            for (String type : ((Accessory) item.getItem()).getAccessoryTypes(item)) {
                if (type.equals(this.type_key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return AccessoryTypeRegistry.getSlotIconTextureIndex(this.type_key);
    }

    public void onTake(ItemStack itemstack) {
        if (itemstack.getItem() instanceof Accessory && this.container instanceof ContainerInventory) {
            Accessory accessory = (Accessory) itemstack.getItem();
            Player player = ((ContainerInventory) this.container).player;
            accessory.onAccessoryRemoved(player, itemstack);
        }
        super.onTake(itemstack);
    }
}
