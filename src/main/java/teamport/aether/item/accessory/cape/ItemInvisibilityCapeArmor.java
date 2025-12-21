package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.item.accessory.AetherStatus;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemAccessoryArmor;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemInvisibilityCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {

    public ItemInvisibilityCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public static boolean  isInvisible(Player player, double distance) {
        if (player instanceof AetherStatus) {
            AetherStatus potentialInvisiblePlayer = (AetherStatus) player;
            return potentialInvisiblePlayer.aether$isInvisible() && distance > 2.0f;
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        if(!(entity instanceof Player)){ return;}
        Player player = (Player) entity;
        if (
            slotId > player.inventory.mainInventory.length
                && slotId - player.inventory.mainInventory.length == CAPE_SLOT
        ) {
            ((AetherStatus) player).aether$setInvisible(true);
            return;
        }
        ((AetherStatus) player).aether$setInvisible(false);
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((AetherStatus) player).aether$setInvisible(false);
    }


}
