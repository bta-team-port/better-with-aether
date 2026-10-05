package teamport.aether.item.accessory.trinket;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.IAccessoryEffects;

public class ItemIronBubble extends ItemTrinket implements IAccessoryEffects {
    public ItemIronBubble(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        player.airSupply = 0;
    }

    @Override
    public void removeEffect(@NonNull Player player, ItemStack accessory) {
        player.airSupply = 300;
    }

}
