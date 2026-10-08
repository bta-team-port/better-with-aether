package teamport.aether.mixin.accessory.gloves.ultimate_fist;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.server.world.ServerPlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.accessory.SlotAccessory;
import teamport.aether.item.accessory.gloves.ItemGloveTool;
import teamport.aether.mixin.accessors.ServerPlayerControllerAccessor;

@Mixin(value = ServerPlayerController.class, remap = false)
public class ServerPlayerControllerMixin {

	ServerPlayerController asThis = (ServerPlayerController) (Object) this;

	@Inject(method = "mineBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Blocks;getBlock(I)Lnet/minecraft/core/block/Block;"))
	private void onBlockDestroyed(
		int x, int y, int z,
		Side side, CallbackInfoReturnable<Boolean> cir,
		@Local(name = "id") int id
	) {
		ItemStack gloves = asThis.player.inventory.armorInventory[SlotAccessory.GLOVES_SLOT];
		ItemStack item = asThis.player.getCurrentEquippedItem();
		if (item == null && gloves != null && gloves.getItem() instanceof ItemGloveTool toolGloves) {
            toolGloves.onBlockDestroyedEQ(((ServerPlayerControllerAccessor)asThis).getThisWorld(), gloves, Blocks.getBlock(id), new TilePos(x, y, z), side, asThis.player);
		}
	}
}
