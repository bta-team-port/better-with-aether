package teamport.aether.entity.tile;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;

public class TileEntityMimic extends TileEntityChest implements Container {

    @Override
    public void dropContents(World world, int x, int y, int z) {
    }

    @Override
    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        return null;
    }

    public String getNameTranslationKey() {
        return "aether.container.chest.trapped.name";
    }
}
