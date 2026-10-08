package teamport.aether.item.item_tool.phoenix;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public interface PhoenixAdjustBlockDropped extends AdjustBlockDropped {

	@Override
	default ItemStack[] adjustBreakingResults(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, Player player, ItemStack[] drops){
		if(dropCause == EnumDropCause.IMPROPER_TOOL){
			return drops;
		}
		return PhoenixHelper.smeltItemResults(player, drops);
	}
}
