package teamport.aether.entity.tile;

import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class TileEntityMimic extends TileEntityChest {
    @Override
    public void dropContents(World world, int x, int y, int z) {}

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        return null;
    }
}
