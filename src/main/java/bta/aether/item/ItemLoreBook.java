package bta.aether.item;

import bta.aether.AetherAchievements;
import bta.aether.gui.IAetherGuis;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemLoreBook extends Item {
    private final String loreId;
    public ItemLoreBook(String name, int id, String loreId) {
        super(name, id);
        this.loreId = loreId;
    }
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        ((IAetherGuis)entityplayer).aether$displayGUILoreBook(loreId);
        entityplayer.addStat(AetherAchievements.LORE, 1);
        return super.onItemRightClick(itemstack, world, entityplayer);
    }
}
