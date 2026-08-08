package teamport.aether.gui.machine.freezer;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ContainerListener;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.entity.TileEntityFreezer;

public class MenuFreezer extends MenuAbstract {
    private final TileEntityFreezer freezer;
    private int currentProcessTime = 0;
    private int currentEnergyTime = 0;
    private int maxProcessTime = 0;
    private int maxEnergyTime = 0;

    public MenuFreezer(ContainerInventory inventory, TileEntityFreezer tileEntityFreezer) {
        this.freezer = tileEntityFreezer;
        this.addSlot(new Slot(tileEntityFreezer, 0, 56, 17));
        this.addSlot(new Slot(tileEntityFreezer, 1, 56, 53));
        this.addSlot(new SlotFreezer(tileEntityFreezer, 2, 116, 35));
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
    public IntList getMoveSlots(@NonNull InventoryAction inventoryAction, @NonNull Slot slot, int target, Player player) {
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
    public IntList getTargetSlots(@NonNull InventoryAction inventoryAction, @NonNull Slot slot, int target, Player player) {
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
            return IntLists.EMPTY_LIST;
        }
    }

    @Override
    @SuppressWarnings("java:S131")
    public void setData(int id, int value) {
        switch (id) {
            case 0:
                this.freezer.setCurrentProcessTime(value);
                break;
            case 1:
                this.freezer.setCurrentEnergyTime(value);
                break;
            case 2:
                this.freezer.setMaxProcessTime(value);
                break;
            case 3:
                this.freezer.setMaxEnergyTime(value);
        }
    }


    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        for (ContainerListener crafter : this.containerListeners) {
            if (this.currentProcessTime != this.freezer.getCurrentProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 0, this.freezer.getCurrentProcessTime());
            }

            if (this.currentEnergyTime != this.freezer.getCurrentEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 1, this.freezer.getCurrentEnergyTime());
            }

            if (this.maxProcessTime != this.freezer.getMaxProcessTime()) {
                crafter.updateCraftingInventoryInfo(this, 2, this.freezer.getMaxProcessTime());
            }

            if (this.maxEnergyTime != this.freezer.getMaxEnergyTime()) {
                crafter.updateCraftingInventoryInfo(this, 3, this.freezer.getMaxEnergyTime());
            }
        }
        this.currentProcessTime = this.freezer.getCurrentProcessTime();
        this.currentEnergyTime = this.freezer.getCurrentEnergyTime();
        this.maxProcessTime = this.freezer.getMaxProcessTime();
        this.maxEnergyTime = this.freezer.getMaxEnergyTime();
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return this.freezer.stillValid(player);
    }
}
