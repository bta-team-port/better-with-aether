package teamport.aether.gui;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class MenuLorebook extends MenuAbstract {
    public final ContainerLorebook loreContainer;

    public MenuLorebook(ContainerInventory playerInventory) {
        this.loreContainer = new ContainerLorebook(this);

        addSlot(new Slot(loreContainer, 0, 82, 66));

        for (int y = 0; y < 3; ++y)
            for (int x = 0; x < 9; ++x)
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 48 + x * 18, 113 + y * 18));

        for (int x = 0; x < 9; ++x)
            addSlot(new Slot(playerInventory, x, 48 + x * 18, 171));
    }

    @Override
    public void onCraftGuiClosed(Player player) {
        super.onCraftGuiClosed(player);
        ItemStack stack = loreContainer.getItem(0);
        if (stack != null) {
            loreContainer.setItem(0, null);
            storeOrDropItem(player, stack);
            player.world.playSoundAtEntity(null, player, "random.insert", 0.1F, 1.0F);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int target, Player player) {
        return slot.index == 0 ? getSlots(0, 1, false) : getSlots(1, 36, false);
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, Player player) {
        if (slot.index >= 1) {
            return target == 1 ? getSlots(0, 1, false) : getSlots(1, 36, false);
        }
        return getSlots(1, 36, false);
    }
}
