package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.item_tool.item_tool_phoenix.AdjustBlockDropped;

@Mixin(value = {TreecapitatorHelper.class}, remap = false)
public abstract class TreeCapitatorMixinPhoenixAxe {
	@WrapOperation(
		method = {"breakBlock"},
		at = {@At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/block/Block;getBreakResult(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)[Lnet/minecraft/core/item/ItemStack;"
		)}
	)
	private ItemStack[] modifyBlockResults(
		Block<?> instance,
		World world, EnumDropCause dropCause,
		int x, int y, int z, int meta, TileEntity tileEntity, Operation<ItemStack[]> original
	) {
		Player player = ((TreecapitatorHelper) (Object) this).player;
		ItemStack[] drops = original.call(instance, world, dropCause, x, y, z, meta, tileEntity);
		if(player == null) {
			return drops;
		}
		ItemStack heldItem = player.getHeldItem();
		if(heldItem != null && heldItem.getItem() instanceof AdjustBlockDropped){
			Item item = heldItem.getItem();
			return ((AdjustBlockDropped)item).adjustBreakingResults(world, dropCause, x, y, z, meta, tileEntity, player, drops);
		}else{
			return drops;
		}
	}
}

