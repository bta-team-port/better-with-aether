package teamport.aether.items.accessory;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.WILDCARD_1_SLOT;

public class ItemGoldenFeather extends ItemAccessory {
    public ItemGoldenFeather(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < WILDCARD_1_SLOT
                || player.gamemode.isPlayerInvulnerable()
        ) {
            return;
        }
        if(player.gamemode.canPlayerFly()){
            return;
        }
        player.fallDistance = 0.0f;
        if (!player.onGround && !player.isInWater() && !player.isInLava() && player.yd < 0.0) {
            player.yd *= 0.8f;
        }
    }
}
