package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.WILDCARD_1_SLOT;

public class ItemIronBubble extends ItemAccessory{
    public ItemIronBubble(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                && slotId - player.inventory.mainInventory.length >= WILDCARD_1_SLOT
        ) {
            return;
        }
        player.airSupply = 300;
    }
}
