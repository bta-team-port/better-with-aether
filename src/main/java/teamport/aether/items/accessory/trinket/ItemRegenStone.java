package teamport.aether.items.accessory.trinket;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.items.accessory.IAccessoryEffects;
import teamport.aether.items.accessory.ItemTrinket;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemRegenStone extends ItemTrinket implements IAccessoryEffects {
    public int defaultTime = 150;

    public ItemRegenStone(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }


    // TODO change it to write to nbt tag
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        CompoundTag tag = itemstack.getData();
        if (
                slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                || player.gamemode.isPlayerInvulnerable()
        ) {
            tag.putInt("time", 0);
            return;
        }

        int time = tag.getInteger("time");
        tag.putInt("time", ++time);
        if (time > 150) {
            tag.putInt("time", 0);
            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
            }
        }
    }


    @Override
    public void onAccessoryAdded(Player player, ItemStack accessory) {
        accessory.setMetadata(0);
    }
}
