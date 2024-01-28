package bta.aether.accessory;


import bta.aether.accessory.API.Accessory;

import bta.aether.accessory.API.AccessoryTypeRegistry;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;


public class AccessorySlot extends Slot {

    String type_key;

    public AccessorySlot(IInventory inventory, int id, int x, int y, String type_key) {
        super(inventory, id, x, y);
        this.type_key = type_key;
    }

    @Override
    public boolean canPutStackInSlot(ItemStack item) {
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
    public int getBackgroundIconIndex() {
        return AccessoryTypeRegistry.getSlotIconTextureIndex(this.type_key);
    }

    public void onPickupFromSlot(ItemStack itemstack) {
        if (itemstack.getItem() instanceof Accessory && this.inventory instanceof InventoryPlayer) {
            Accessory accessory = (Accessory) itemstack.getItem();
            EntityPlayer player = ((InventoryPlayer) this.inventory).player;
            accessory.onAccessoryRemoved(player, itemstack);
        }
        super.onPickupFromSlot(itemstack);
    }
}
