package bta.aether.container;

import bta.aether.tile.TileEntityIncubator;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class ContainerIncubator extends Container {

    private TileEntityIncubator tile;

    public ContainerIncubator(InventoryPlayer inventoryPlayer, TileEntityIncubator tile){
        this.tile = tile;

        this.addSlot(new Slot(tile, 0, 73, 17));
        this.addSlot(new Slot(tile, 1, 73, 53));

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(inventoryPlayer, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }
        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(inventoryPlayer, k, 8 + k * 18, 142));
        }
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return tile.canInteractWith(entityPlayer);
    }
}

