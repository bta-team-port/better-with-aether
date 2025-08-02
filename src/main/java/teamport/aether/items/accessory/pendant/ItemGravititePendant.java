package teamport.aether.items.accessory.pendant;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;

public class ItemGravititePendant extends ItemPendant implements IArmorItem {
    private final ArmorMaterial material;

    public ItemGravititePendant(String translationKey, String namespaceId, int id, ArmorMaterial material) {
        super(translationKey, namespaceId, id, material);
        this.material = material;
    }

    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int slotId, boolean flag) {
        Player player = (Player) entity;
        if (
                slotId < player.inventory.mainInventory.length
                || slotId - player.inventory.mainInventory.length < TRINKET_1_SLOT
                || player.gamemode.isPlayerInvulnerable()
                || player.isInWater()
                || player.isSneaking()

                //TODO Fix this so that when the player is riding and/or has a passenger talisman doesnt work
//                || player.getPassenger() == entity
        ) {
            return;
        }
        player.yd += 0.025F;
    }

    @Override public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }
    @Override public int armorPieceProtection() {
        return 0;
    }
    @Override public float getArmorPieceProtectionPercentage() {
        return 0F;
    }
    @Override public int getArmorPiece() {
        return 0;
    }
}
