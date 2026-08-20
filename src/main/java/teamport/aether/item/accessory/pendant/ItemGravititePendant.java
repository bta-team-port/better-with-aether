package teamport.aether.item.accessory.pendant;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class ItemGravititePendant extends ItemPendant {
    public ItemGravititePendant(@NonNull String translationKey, @NonNull String namespaceId, int id, @NonNull ArmorMaterial material, String name) {
        super(translationKey, namespaceId, id, material, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        if (player.hasNoPhysics() || player.isInWater() || player.isSneaking() || player.isPassenger() || player.passenger != null) {
            return;
        }

        player.yd += 0.025F;
    }

}
