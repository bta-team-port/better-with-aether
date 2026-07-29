package teamport.aether.gui.machine.incubator;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ContainerListener;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.aether.block.entity.TileEntityIncubator;

import java.util.Collections;
import java.util.List;

public class MenuIncubator extends MenuAbstract {
    private final TileEntityIncubator incubator;
    private int currentProcessTime = 0;
    private int currentEnergyTime = 0;
    private int maxProcessTime = 0;
    private int maxEnergyTime = 0;

    public MenuIncubator(ContainerInventory inventory, TileEntityIncubator tileEntityIncubator) {
        this.incubator = tileEntityIncubator;
        this.addSlot(new Slot(tileEntityIncubator, 0, 73, 17));
        this.addSlot(new SlotIncubatorFuel(tileEntityIncubator, 1, 73, 53));
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
    public it.unimi.dsi.fastutil.ints.IntList getMoveSlots(InventoryAction inventoryAction, Slot slot, int target, Player player) {
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
    public it.unimi.dsi.fastutil.ints.IntList getTargetSlots(InventoryAction inventoryAction, Slot slot, int target, Player player) {
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
            return it.unimi.dsi.fastutil.ints.IntLists.EMPTY_LIST;
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        for (ContainerListener crafter : this.containerListeners) {
            if (this.currentProcessTime != this.incubator.getCurrentProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 0, this.incubator.getCurrentProcessTime());
            }

            if (this.currentEnergyTime != this.incubator.getCurrentEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 1, this.incubator.getCurrentEnergyTime());
            }

            if (this.maxProcessTime != this.incubator.getMaxProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 2, this.incubator.getMaxProcessTime());
            }

            if (this.maxEnergyTime != this.incubator.getMaxEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 3, this.incubator.getMaxEnergyTime());
            }
        }
        this.currentProcessTime = this.incubator.getCurrentProcessTime();
        this.currentEnergyTime = this.incubator.getCurrentEnergyTime();
        this.maxProcessTime = this.incubator.getMaxProcessTime();
        this.maxEnergyTime = this.incubator.getMaxEnergyTime();
    }

    @Override
    @SuppressWarnings("java:S131")
    public void setData(int id, int value) {
        switch (id) {
            case 0:
                this.incubator.setCurrentProcessTime(value);
                break;
            case 1:
                this.incubator.setCurrentEnergyTime(value);
                break;
            case 2:
                this.incubator.setMaxProcessTime(value);
                break;
            case 3:
                this.incubator.setMaxEnergyTime(value);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.incubator.stillValid(player);
    }
}
