package teamport.aether.item.accessory.trinket;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemTrinket;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemRegenStone extends ItemTrinket implements IAccessoryEffects {
    public ItemRegenStone(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }


    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        CompoundTag tag = itemstack.getData();
        if (
            slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                || player.gamemode.hasInvulnerablePlayer()
        ) {
            tag.putInt("time", 0);
            return;
        }

        int time = tag.getInteger("time");
        tag.putInt("time", ++time);
        if (time > 200) {
            tag.putInt("time", 0);
            if (player.getHealth() < player.getMaxHealth()) {
                player.heal(1);
                world.playSoundAtEntity(player, player, "aether:heal", 0.5f, itemRand.nextFloat() * 0.4F + 0.8F);
                ParticleMaker.spawnHeartParticles(world, player.x, player.y, player.z, player.bbHeight, player.bbWidth);
            }
        }
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        CompoundTag tag = accessory.getData();
        tag.putInt("time", 0);
    }
}
