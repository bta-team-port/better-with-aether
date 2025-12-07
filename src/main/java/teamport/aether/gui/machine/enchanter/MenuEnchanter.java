package teamport.aether.gui.machine.enchanter;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ContainerListener;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.aether.block.entity.TileEntityEnchanter;

import java.util.Collections;
import java.util.List;

public class MenuEnchanter extends MenuAbstract {
    private final TileEntityEnchanter enchanter;
    private int currentProcessTime = 0;
    private int currentEnergyTime = 0;
    private int maxProcessTime = 0;
    private int maxEnergyTime = 0;

    public MenuEnchanter(ContainerInventory inventory, TileEntityEnchanter tileEntityEnchanter) {
        this.enchanter = tileEntityEnchanter;
        this.addSlot(new Slot(tileEntityEnchanter, 0, 56, 17));
        this.addSlot(new Slot(tileEntityEnchanter, 1, 56, 53));
        this.addSlot(new SlotEnchanter(inventory.player, tileEntityEnchanter, 2, 116, 35));
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int target, Player player) {
        if (slot.index >= 0 && slot.index <= 3) {
            return this.getSlots(slot.index, 1, false);
        } else {
            if (inventoryAction == InventoryAction.MOVE_ALL) {
                if (slot.index >= 3 && slot.index <= 30) {
                    return this.getSlots(3, 27, false);
                }

                if (slot.index >= 30 && slot.index <= 38) {
                    return this.getSlots(30, 9, false);
                }
            }

            return slot.index >= 3 && slot.index <= 38 ? this.getSlots(3, 36, false) : null;
        }
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int target, Player player) {
        if (slot.index >= 3 && slot.index <= 39) {
            if (inventoryAction != InventoryAction.MOVE_ALL) {
                if (target == 1) {
                    return this.getSlots(0, 1, false);
                }

                if (target == 2) {
                    return this.getSlots(1, 1, false);
                }
            }

            if (slot.index <= 29) {
                return this.getSlots(30, 9, false);
            }

            if (slot.index >= 31 && slot.index <= 38) {
                return this.getSlots(3, 27, false);
            }
        }

        if (slot.index >= 0 && slot.index <= 2) {
            return slot.index == 2 ? this.getSlots(3, 36, true) : this.getSlots(3, 36, false);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        for (ContainerListener crafter : this.containerListeners) {
            if (this.currentProcessTime != this.enchanter.getCurrentProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 0, this.enchanter.getCurrentProcessTime());
            }

            if (this.currentEnergyTime != this.enchanter.getCurrentEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 1, this.enchanter.getCurrentEnergyTime());
            }

            if (this.maxProcessTime != this.enchanter.getMaxProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 2, this.enchanter.getMaxProcessTime());
            }

            if (this.maxEnergyTime != this.enchanter.getMaxEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 3, this.enchanter.getMaxEnergyTime());
            }
        }
        this.currentProcessTime = this.enchanter.getCurrentProcessTime();
        this.currentEnergyTime = this.enchanter.getCurrentEnergyTime();
        this.maxProcessTime = this.enchanter.getMaxProcessTime();
        this.maxEnergyTime = this.enchanter.getMaxEnergyTime();
    }

    @SuppressWarnings("java:S131")
    @Override
    public void setData(int id, int value) {
        switch (id) {
            case 0:
                this.enchanter.setCurrentProcessTime(value);
                break;
            case 1:
                this.enchanter.setCurrentEnergyTime(value);
                break;
            case 2:
                this.enchanter.setMaxProcessTime(value);
                break;
            case 3:
                this.enchanter.setMaxEnergyTime(value);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.enchanter.stillValid(player);
    }
}
