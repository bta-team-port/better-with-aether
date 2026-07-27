package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherItems;

@Environment(EnvType.CLIENT)
public class ItemModelBowPhoenix extends ItemModelBow {
    public ItemModelBowPhoenix(Item item, boolean handheld) {
        super(item, handheld);
    }

    @Override
    public Item getNextArrow(Player player) {
        if (player instanceof PlayerRemote) {
            int id = player.getArrowId();
            if (id < 0 || id > Item.highestItemId) return null;
            Item arrow = Item.getItem(id);
            return arrow == Items.AMMO_ARROW || arrow == Items.AMMO_ARROW_GOLD || arrow == AetherItems.AMMO_ARROW_FLAMING
                ? AetherItems.AMMO_ARROW_FLAMING
                : null;
        } else {
            if (PlayerUtil.getActiveQuiver(player) != null || player.hasItem(Items.AMMO_ARROW_GOLD) ||
                player.hasItem(Items.AMMO_ARROW)) {
                return AetherItems.AMMO_ARROW_FLAMING;
            }
            return null;
        }
    }
}
