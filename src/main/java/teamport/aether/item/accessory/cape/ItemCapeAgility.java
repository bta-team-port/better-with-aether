package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.accessory.IAccessoryEffects;

public class ItemCapeAgility extends ItemCape implements IAccessoryEffects {
    public ItemCapeAgility(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        player.footSize = 1.0f;
    }

    @Override
    public void removeEffect(@NonNull Player player, ItemStack accessory) {
        player.footSize = 0.5F;
    }

}
