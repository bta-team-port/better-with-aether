package bta.aether.container;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class ContainerLoreBook extends Container {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
    public InventoryPlayer playerInv;
    public ContainerLoreBook(InventoryPlayer inventory) {
        int i;
        this.playerInv = inventory;
        this.addSlot(new Slot(this.craftMatrix, 0, 82, 66));
        for (i = 0; i < 3; ++i) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inventory, k1 + (i + 1) * 9, 48 + k1 * 18, 113 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 48 + i * 18, 171));
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    @Override
    public void onCraftGuiClosed(EntityPlayer player) {
        super.onCraftGuiClosed(player);
        ItemStack stack = craftMatrix.getStackInSlot(0);
        if (stack != null){
            craftMatrix.setInventorySlotContents(0, null);
            storeOrDropItem(player, stack);
            player.world.playSoundAtEntity(player, "random.insert", 0.1f, 1.0f);
        }
    }
    @Override
    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public java.util.List<Integer> getMoveSlots(InventoryAction action, Slot slot, int target, EntityPlayer player) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, EntityPlayer player) {
        return this.getSlots(9, 36, false);
    }
}
