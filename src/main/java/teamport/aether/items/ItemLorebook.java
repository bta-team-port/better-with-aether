package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.gui.AetherScreens;

public class ItemLorebook extends Item {
    private final String loreId;

    public ItemLorebook(String key, String namespace, int id, String loreId) {
        super(key, namespace, id);
        this.loreId = loreId;
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onUseItem(ItemStack stack, World world, Player player) {
        if (!world.isClientSide) {
            ((AetherScreens) player).aether$displayLorebookScreen(loreId);
            player.addStat(AetherAchievements.AMBROSIUM, 1);
        }
        return stack;
    }
}
