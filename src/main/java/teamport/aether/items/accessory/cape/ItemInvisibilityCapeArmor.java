package teamport.aether.items.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.IAccessoryEffects;
import teamport.aether.items.accessory.IAetherInvisibility;
import teamport.aether.items.accessory.ItemAccessoryArmor;

import static teamport.aether.items.accessory.SlotAccessory.CAPE_SLOT;

public class ItemInvisibilityCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {

    public ItemInvisibilityCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    // TODO make the player visible when the item is dragged away from the armor slot
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId > player.inventory.mainInventory.length
                        && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            ((IAetherInvisibility) player).aether$setInvisible(true);
            return;
        }
        ((IAetherInvisibility) player).aether$setInvisible(false);
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((IAetherInvisibility) player).aether$setInvisible(false);
    }


}
