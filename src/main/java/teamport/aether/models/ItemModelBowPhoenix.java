package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import teamport.aether.item.AetherItems;

@Environment(EnvType.CLIENT)
public class ItemModelBowPhoenix extends ItemModelBow {
    public ItemModelBowPhoenix(Item item, String namespace) {
        super(item, namespace);
    }

    @Override
    public Item getNextArrow(Player player) {
        if (player instanceof PlayerRemote) {
            int id = player.getArrowId();
            return id >= 0 && id < Item.itemsList.length ? AetherItems.AMMO_ARROW_FLAMING : null;
        } else {
            ItemStack quiverSlot = player.inventory.armorItemInSlot(2);
            ItemStack capeSlot = player.inventory.armorItemInSlot(5);
            if (quiverSlot != null && (quiverSlot.itemID == Items.ARMOR_QUIVER.id && quiverSlot.getMetadata() < quiverSlot.getMaxDamage()) ||
                (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) || capeSlot != null && (capeSlot.itemID == Items.ARMOR_QUIVER.id && capeSlot.getMetadata() < capeSlot.getMaxDamage()) ||
                (capeSlot != null && capeSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) ||
                player.hasItem(Items.AMMO_ARROW_GOLD) ||
                player.hasItem(Items.AMMO_ARROW)) {
                return AetherItems.AMMO_ARROW_FLAMING;
            }
            return null;
        }
    }
}
