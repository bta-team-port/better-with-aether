package teamport.aether.item.accessory.trinket;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryEffects;
import teamport.aether.item.accessory.ItemAccessory;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemIronBubble extends ItemAccessory<HumanAccessoryShape> implements IAccessoryEffects {

    public ItemIronBubble(String translationKey, String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, HumanAccessoryShape.TRINKET_1);
        this.withTags(AetherItemTags.tags(AetherItemTags.TRINKET));
    }

    @Override
    public boolean fitsInShape(@NonNull HumanAccessoryShape shape) {
        return shape == HumanAccessoryShape.TRINKET_1 || shape == HumanAccessoryShape.TRINKET_2;
    }

    @Override
    public void inventoryTick(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
            slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
        ) {
            return;
        }
        player.airSupply = 0;
    }

    @Override
    public void removeEffect(@NonNull Player player, ItemStack accessory) {
        player.airSupply = 300;
    }

}
