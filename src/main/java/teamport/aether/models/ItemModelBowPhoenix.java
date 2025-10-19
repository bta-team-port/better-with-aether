package teamport.aether.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerRemote;
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import teamport.aether.items.AetherItems;

@Environment(EnvType.CLIENT)
public class ItemModelBowPhoenix extends ItemModelBow {
    public ItemModelBowPhoenix(Item item, String namespace) {
        super(item, namespace);
    }

    public Item getNextArrow(Player player) {
        if (player instanceof PlayerRemote) {
            int id = player.getArrowId();
            return id >= 0 && id < Item.itemsList.length ? Item.itemsList[id] : null;
        } else {
            return AetherItems.AMMO_ARROW_FLAMING;
        }
    }

}
