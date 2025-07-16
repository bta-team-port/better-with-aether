package teamport.aether.player;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.aether.tile.TileEntityEnchanter;

import java.util.List;

// TODO implement this
public class MenuEnchanter extends MenuAbstract {


    public final TileEntityEnchanter enchanter;

    public MenuEnchanter(ContainerInventory inventory, TileEntityEnchanter tileEntityEnchanter){
        this.enchanter = tileEntityEnchanter;
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, Player player) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
