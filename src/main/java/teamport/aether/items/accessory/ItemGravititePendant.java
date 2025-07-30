package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemGravititePendant extends ItemAccessoryTrinket {


    public ItemGravititePendant(String translationKey, String namespaceId, int id, ArmorMaterial name) {
        super(translationKey, namespaceId, id, name);
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (slotId < player.inventory.mainInventory.length || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT) {
            return;
        }

        if (!player.getGamemode().isPlayerInvulnerable()) {
            player.fallDistance = player.fallDistance - 0.05f;
            player.yd += 0.025F;
        }
    }

}
