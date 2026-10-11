package teamport.aether.mixin.accessory.gloves.ultimate_fist;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.controller.PlayerControllerMP;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.item.accessory.IAccessoryWearing;
import teamport.aether.item.accessory.SlotAccessory;
import teamport.aether.item.accessory.gloves.ItemGloveTool;

@Mixin(value = PlayerControllerMP.class, remap = false)
public class PlayerControllerMPMixin {

	@Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;getCurrentEquippedItem()Lnet/minecraft/core/item/ItemStack;"))
	private void onBlockDestroyed(
        TilePosc tilePos, Side side, CallbackInfoReturnable<Boolean> cir, @Local Block<?> block
	) {
		Player thePlayer = Minecraft.getMinecraft().thePlayer;
		ItemStack gloves = ((IAccessoryWearing<?>)thePlayer).better_with_aether$getAccessoryInSlot(SlotAccessory.GLOVES_SLOT);
		ItemStack item = thePlayer.getCurrentEquippedItem();
		if (item == null && gloves != null && gloves.getItem() instanceof ItemGloveTool toolGloves) {
            toolGloves.onBlockDestroyedEQ(Minecraft.getMinecraft().currentWorld, gloves, block, tilePos, side, thePlayer);
		}
	}


}
