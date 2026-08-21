package teamport.aether.item.accessory.trinket;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.accessory.IAccessoryEffects;

public class ItemRegenStone extends ItemTrinket implements IAccessoryEffects {
    public ItemRegenStone(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        CompoundTag tag = stack.getData();
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
    public void removeEffect(Player player, @NonNull ItemStack accessory) {
        CompoundTag tag = accessory.getData();
        tag.putInt("time", 0);
    }
}
