package teamport.aether.items.accessory.cape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.items.accessory.IAccessoryEffects;
import teamport.aether.items.accessory.ItemAccessoryArmor;

import java.util.List;

import static teamport.aether.items.accessory.SlotAccessory.CAPE_SLOT;

public class ItemSwetCapeArmor extends ItemAccessoryArmor implements IAccessoryEffects {
    public ItemSwetCapeArmor(String translationKey, String namespaceId, int id, String name, int accessoryPiece) {
        super(translationKey, namespaceId, id, name, accessoryPiece);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        List<MobSwet> list = world.getEntitiesWithinAABB(MobSwet.class, entity.bb.grow(6.0D, 3.0D, 6.0D));

        if (slotId > player.inventory.mainInventory.length && slotId - player.inventory.mainInventory.length == CAPE_SLOT) {
            for (MobSwet swet : list) {
                swet.setFriendly(true);
            }
            return;
        }
        for (MobSwet swet : list) {
            swet.setFriendly(false);
        }
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        World world = player.world;
        if (world == null) return;
        List<MobSwet> list = world.getEntitiesWithinAABB(MobSwet.class, player.bb.grow(6.0D, 3.0D, 6.0D));
        for (MobSwet swet : list) {
            swet.setFriendly(false);
        }
    }
}
