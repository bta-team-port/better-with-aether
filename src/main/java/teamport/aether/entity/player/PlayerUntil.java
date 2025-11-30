package teamport.aether.entity.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.items.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.items.accessory.SlotAccessory.TRINKET_2_SLOT;

public class PlayerUntil {
    private PlayerUntil(){/* no need to initiate*/}


    public static boolean isSilkTouch(Player player) {
        ItemStack trinketOne = player.inventory.armorInventory[TRINKET_1_SLOT];
        ItemStack trinketTwo = player.inventory.armorInventory[TRINKET_2_SLOT];
        return trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id
            || trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
    }

    /// Y pos on the server counted from the player's foot height but on the client it is counted from the player's head height
    /// We want count the player pos from his feet
    public static double getY(Player player){
        if(EnvironmentHelper.isSinglePlayer()){
            return player.y - player.bbHeight;
        }
        return player.y;
    }

    public static double getHeadY(Player player){
        if(EnvironmentHelper.isSinglePlayer()){
            return player.y;
        }
        return player.y - player.bbHeight;
    }
}
