package teamport.aether.items.accessory.pendant;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemGravititePendant extends ItemPendant {
    public final String name;

    public ItemGravititePendant(String translationKey, String namespaceId, int id, String name, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
        this.name = name;
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                        || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                        || player.gamemode.isPlayerInvulnerable()
                        || player.isInWater()
                        || player.isSneaking()
                        || player.isPassenger()
                        || player.passenger != null
        ) {
            return;
        }
        player.yd += 0.025F;
    }
}
