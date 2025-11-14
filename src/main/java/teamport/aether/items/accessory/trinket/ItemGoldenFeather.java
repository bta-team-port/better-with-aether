package teamport.aether.items.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.ItemTrinket;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemGoldenFeather extends ItemTrinket {
    public ItemGoldenFeather(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
            slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                || player.gamemode.isPlayerInvulnerable()
        ) {
            return;
        }
        if (player.gamemode.canPlayerFly()) {
            return;
        }
        player.fallDistance = 0.0f;
        if (!player.onGround && !player.isInWater() && !player.isInLava() && player.yd < 0.0 && !player.isSneaking()) {
            player.yd *= 0.8f;
        }
    }
}
