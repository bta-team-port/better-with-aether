package teamport.aether.item.accessory.cape;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.item.accessory.IAccessoryEffects;

public class ItemCapeSwet extends ItemCape implements IAccessoryEffects {
    public ItemCapeSwet(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
        AetherEffects.add(player, AetherEffects.swetty, 1);
    }

    @Override
    public void removeEffect(Player player, ItemStack accessory) {
        ((IHasEffects<?>) player).getContainer().remove(AetherEffects.swetty);
    }
}
