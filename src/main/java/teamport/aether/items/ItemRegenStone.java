package teamport.aether.items;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemRegenStone extends Item {

    public ItemRegenStone(String translationKey, String namespaceId, int id) {
        super(translationKey, namespaceId, id);
    }

    public static int ticks_per_half_heart_heal = 14 * 20;

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        itemstack.setMetadata(itemstack.getMetadata() + 1);
        if (itemstack.getMetadata() > ticks_per_half_heart_heal) {
            itemstack.setMetadata(0);
            // only heal if it's the first equipped, since in the OG mod effect doesn't stack.
            // also don't heal when player has max health, since that causes hearts flash
            // Not sure if this is supposed to heal life crystal hearts, so I added that. - Cookie
        }
    }

}
