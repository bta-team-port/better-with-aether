package teamport.aether.item.item_tool.item_tool_phoenix;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public interface AdjustBlockDropped {
	default ItemStack[] adjustBreakingResults(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, Player player, ItemStack[] drops){
		return drops;
	}
}
