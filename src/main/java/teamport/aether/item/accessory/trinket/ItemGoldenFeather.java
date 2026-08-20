package teamport.aether.item.accessory.trinket;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;

public class ItemGoldenFeather extends ItemTrinket {
    public ItemGoldenFeather(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        if (player.hasNoPhysics()) {
            return;
        }

        if (!player.onGround && !player.isInWater() && !player.isInLava() && player.yd < -0.225 && !player.isSneaking()) {
            player.yd *= 0.8;
            player.fallDistance = 0.0F;
        }
    }
}
