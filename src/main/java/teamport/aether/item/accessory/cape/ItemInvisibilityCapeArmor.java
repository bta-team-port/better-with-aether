package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemAccessoryArmor;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemInvisibilityCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {

    public ItemInvisibilityCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (slotId > player.inventory.mainInventory.length
            && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            AetherEffects.add(player, AetherEffects.invisibility, 1);
        }
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((IHasEffects<?>) player).getContainer().remove(AetherEffects.invisibility);
    }

}
