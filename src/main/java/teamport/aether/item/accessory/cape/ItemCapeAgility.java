package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.IAccessoryEffects;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemCapeAgility extends ItemCape implements IAccessoryEffects {
    public ItemCapeAgility(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (slotId > player.inventory.mainInventory.length
            && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            player.footSize = 1.0f;
            return;
        }
        player.footSize = 0.5f;
    }

    @Override
    public void removeEffect(@NonNull Player player, ItemStack accessory) {
        player.footSize = 0.5F;
    }

}
