package teamport.aether.gui.machine.enchanter;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.slot.Slot;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.item.AetherItems;

public class SlotEnchanter extends Slot {
    private final Player thePlayer;

    public SlotEnchanter(Player theplayer, Container container, int index, int x, int y) {
        super(container, index, x, y);
        this.thePlayer = theplayer;
    }

    @Override
    public void onTake(ItemStack itemstack) {
        if (itemstack.itemID == AetherItems.FOOD_HEALING_STONE.id) {
            this.thePlayer.addStat(AetherAchievements.HEALING_STONE, 1);
        }
        super.onTake(itemstack);
    }

    // prevent item to be put in the output slot
    @Override
    public boolean mayPlace(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean enableDragAndPickup() {
        return false;
    }

    @Override
    public boolean allowItemInteraction() {
        return false;
    }
}
