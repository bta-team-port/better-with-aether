package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.WILDCARD_1_SLOT;

public class ItemRegenStone extends ItemAccessory{

    public ItemRegenStone(String translationKey, String namespaceId, int id, String name, int accessorySlot) {
        super(translationKey, namespaceId, id, name, accessorySlot);
    }


    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < WILDCARD_1_SLOT
                || player.gamemode.isPlayerInvulnerable()
        ) {
            itemstack.setMetadata(0);
            return;
        }
        itemstack.setMetadata(itemstack.getMetadata() + 1);
        if (itemstack.getMetadata() > 150) {
            itemstack.setMetadata(0);
            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
            }
        }
    }

    @Override
    public void onAccessoryAdded(Player player, ItemStack accessory) {
        accessory.setMetadata(0);
    }
}
