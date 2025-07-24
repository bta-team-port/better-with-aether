package teamport.aether.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jetbrains.annotations.Nullable;
import teamport.aether.accessory.api.Accessory;

public class SlotAccessory extends Slot {

    // 0 -> gloves
    // 1 -> cape
    // 2 -> accessory:  pendant, healing stone, etc.
    // 3 -> wildcard:   compass, clock, calendar

    // empty slot equipment
    private static final String[] accessoryOutline = new String[]{
            "aether:item/armor_gloves_outline",
            "aether:item/armor_capes_outline",
            "aether:item/armor_accessory_outline",
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
        // we use 3 for wildcard, we allow anything here
        if (this.armorType == 3) {
            return true;
        } else {
            return itemstack.getItem() instanceof Accessory && ((Accessory)itemstack.getItem()).getAccessoryTypes() == this.armorType;
        }
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
        return accessoryOutline[this.armorType];
    }

}
