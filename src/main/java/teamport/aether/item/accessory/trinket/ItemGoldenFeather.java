package teamport.aether.item.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemGoldenFeather extends ItemTrinket {
    public ItemGoldenFeather(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (slotId < player.inventory.mainInventory.length
            || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
            || player.gamemode.hasInvulnerablePlayer()
            || player.gamemode.hasPlayerFlight()) {
            return;
        }

        if (!player.onGround && !player.isInWater() && !player.isInLava() && player.yd < -0.225 && !player.isSneaking()) {
            player.yd *= 0.8;
            player.fallDistance = 0.0F;
        }
    }
}
