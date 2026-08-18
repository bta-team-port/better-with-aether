package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.item.accessory.IAccessoryEffects;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemCapeSwet extends ItemCape implements IAccessoryEffects {
    public ItemCapeSwet(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
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
            AetherEffects.add(player, AetherEffects.swetty, 1);
        }
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((IHasEffects<?>) player).getContainer().remove(AetherEffects.swetty);
    }
}
