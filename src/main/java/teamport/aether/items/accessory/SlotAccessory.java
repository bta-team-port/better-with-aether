package teamport.aether.items.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class SlotAccessory extends Slot {

    // 4 -> gloves
    // 5 -> cape:       cape, quiver
    // 6 -> wildcard:   pendant, healing stone, compass, clock, calendar, etc.
    // 7 -> wildcard:   pendant, healing stone, compass, clock, calendar, etc.

    // empty slot equipment
    private static final String[] accessoryOutline = new String[]{
            "aether:item/armor_gloves_outline",
            "aether:item/armor_capes_outline",
            "aether:item/armor_wildcard_outline",
            "aether:item/armor_wildcard_outline",
    };

    // TODO add inventory equipment achievements
    private final MenuInventory menu;
    private final int armorType;

    public SlotAccessory(MenuInventory menu, Container container, int index, int x, int y, int armorType) {
        super(container, index, x, y);
        this.menu = menu;
        this.armorType = armorType;
    }

    public int getMaxStackSize() {
        return 1;
    }

    public boolean mayPlace(ItemStack itemstack) {
        // we use 6 & 7 for wildcard, we allow anything here
        if (this.armorType == 7 || this.armorType == 6) {
            return true;
        }
        // allow quiver to be placed in the cape slot
        Item item = itemstack.getItem();
        if (this.armorType == 5 && (item instanceof ItemQuiverEndless || item instanceof ItemQuiver)) {
            return true;
        }
        return item instanceof Accessory && ((Accessory) item).getAccessoryTypes() == this.armorType;
    }

    // TODO figure out what to do here
    public void setChanged() {
        super.setChanged();
        if (this.getItemStack() != null && this.container instanceof ContainerInventory) {
            Player player = ((ContainerInventory)this.container).player;
            player.world.playSoundAtEntity(player, player, "random.equip", 2.0F, 1.0F);
        }
    }

    public void set(@Nullable ItemStack itemstack) {
        super.set(itemstack);
    }

    public String getItemIcon() {
        return accessoryOutline[this.armorType - 4];
    }

}
