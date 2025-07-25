package teamport.aether.gui.machine.incubator;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.slot.Slot;

public class SlotIncubatorFuel extends Slot {
    public SlotIncubatorFuel(Container container, int index, int x, int y) {super(container, index, x, y);}
    public String getItemIcon() {return "aether:item/incubator_fuel_outline";}
}
