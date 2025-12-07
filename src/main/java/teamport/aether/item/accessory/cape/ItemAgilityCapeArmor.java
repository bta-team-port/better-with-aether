package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemAccessoryArmor;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemAgilityCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {

    public ItemAgilityCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
            slotId > player.inventory.mainInventory.length
                && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            player.footSize = 1.0f;
            return;
        }
        player.footSize = 0.5f;
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        player.footSize = 0.5F;
    }

}
