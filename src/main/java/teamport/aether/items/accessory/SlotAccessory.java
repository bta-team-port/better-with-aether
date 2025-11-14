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
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.items.AetherItemTags;

public class SlotAccessory extends Slot {
    // armorType
    public static final byte GLOVES_SLOT = 4;
    public static final byte CAPE_SLOT = 5;       // cape, quiver
    public static final byte TRINKET_1_SLOT = 6; // pendant, healing stone, compass, clock, calendar, etc.
    public static final byte TRINKET_2_SLOT = 7; // pendant, healing stone, compass, clock, calendar, etc.

    // empty slot equipment
    private static final String[] ACCESSORY_OUTLINE = new String[]{
        "aether:item/armor_gloves_outline",
        "aether:item/armor_capes_outline",
        "aether:item/armor_wildcard_outline",
        "aether:item/armor_wildcard_outline",
    };

    public final MenuInventory menu;
    public final int armorType;

    public SlotAccessory(MenuInventory menu, Container container, int index, int x, int y, int armorType) {
        super(container, index, x, y);
        this.menu = menu;
        this.armorType = armorType;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if (item instanceof ItemAccessoryArmor) {
            return ((ItemAccessoryArmor) item).getSlotID() == this.armorType;
        }
        if ((item instanceof ItemQuiverEndless || item instanceof ItemQuiver) && this.armorType == CAPE_SLOT) {
            return true;
        }
        return item.hasTag(AetherItemTags.TRINKET) && this.armorType >= TRINKET_1_SLOT;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        int count = 0;

        for (int i = 0; i < this.menu.slots.size(); ++i) {
            if (this.menu.slots.get(i) instanceof SlotAccessory) {
                ItemStack stack = this.menu.slots.get(i).getItemStack();
                if (stack != null) {
                    ++count;
                }
            }
        }

        if (count == 4) {
            this.menu.inventory.player.triggerAchievement(AetherAchievements.ALL_ACCESSORY_TYPES);
        }

        if (this.getItemStack() != null && this.container instanceof ContainerInventory) {
            Player player = ((ContainerInventory) this.container).player;
            if (player.world != null) player.world.playSoundAtEntity(player, player, "random.equip", 2.0F, 1.0F);
        }

    }

    // cause of the armor offset
    @Override
    public String getItemIcon() {
        return ACCESSORY_OUTLINE[this.armorType - 4];
    }

}
