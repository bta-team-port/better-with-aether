package teamport.aether.gui.machine.freezer;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.slot.Slot;

public class SlotFreezer extends Slot {
    private Player thePlayer;

    public SlotFreezer(Player theplayer, Container container, int index, int x, int y) {
        super(container, index, x, y);
        this.thePlayer = theplayer;
    }

    @Override
    public void onTake(ItemStack itemstack){
        // check for achievement
    }

    // prevent item to be put in the output slot
    @Override
    public boolean mayPlace(ItemStack itemstack)    {return false;}
    @Override
    public boolean enableDragAndPickup()            {return false;}
    @Override
    public boolean allowItemInteraction()           {return false;}
}
