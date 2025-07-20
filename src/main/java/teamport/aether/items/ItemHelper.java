package teamport.aether.items;

import net.minecraft.core.item.*;
import net.minecraft.core.item.tool.ItemTool;

public class ItemHelper {
    public static boolean isRepairable(ItemStack toProcess) {
        Item item = toProcess.getItem();
        return item instanceof ItemTool
                || item instanceof ItemArmor
                || item instanceof ItemFireStriker
                || item instanceof ItemBow;
    }
}