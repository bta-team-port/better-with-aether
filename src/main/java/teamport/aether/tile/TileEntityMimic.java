package teamport.aether.tile;

import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;

import javax.annotation.Nullable;

public class TileEntityMimic extends TileEntityChest {
    // cant remove from mimic's inventory
    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount){
        return null;
    }
}
