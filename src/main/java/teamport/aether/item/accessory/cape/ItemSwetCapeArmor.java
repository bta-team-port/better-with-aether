package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.item.accessory.AetherStatus;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemAccessoryArmor;

import static teamport.aether.item.accessory.SlotAccessory.CAPE_SLOT;

public class ItemSwetCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {
    public ItemSwetCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    public static boolean  isSwetFriendly(Player player, double distance) {
        if (player instanceof AetherStatus) {
            AetherStatus potentialInvisiblePlayer = (AetherStatus) player;
            return potentialInvisiblePlayer.aether$isSwetFriendly();
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
            ((AetherStatus) player).aether$setSwetFriendly(true);
            return;
        }
        ((AetherStatus) player).aether$setSwetFriendly(false);
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((AetherStatus) player).aether$setSwetFriendly(false);
    }

//    @Override
//    public void inventoryTick(ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
//        if(!(entity instanceof Player)){ return;}
//        Player player = (Player) entity;
//        List<MobSwet> list = world.getEntitiesWithinAABB(MobSwet.class, entity.bb.grow(6.0D, 3.0D, 6.0D));
//
//        if (slotId > player.inventory.mainInventory.length && slotId - player.inventory.mainInventory.length == CAPE_SLOT) {
//            for (MobSwet swet : list) {
//                swet.setFriendly(true);
//            }
//            return;
//        }
//        for (MobSwet swet : list) {
//            swet.setFriendly(false);
//        }
//    }
//
//    @Override
//    public void removeEffect(Player player, ItemStack accessory) {
//        World world = player.world;
//        if (world == null) return;
//        List<MobSwet> list = world.getEntitiesWithinAABB(MobSwet.class, player.bb.grow(6.0D, 3.0D, 6.0D));
//        for (MobSwet swet : list) {
//            swet.setFriendly(false);
//        }
//    }
}
