package teamport.aether.mixin.accessory.gloves.ultimate_fist;

;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import teamport.aether.item.accessory.IAccessoryWearing;
import teamport.aether.item.accessory.SlotAccessory;
import teamport.aether.item.accessory.gloves.ItemGloveTool;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerGlovesMixin {
	@Unique
    Player asThis = (Player) (Object) this;


	@WrapMethod(method = "canHarvestBlock")
	private boolean canHarvestFist(Block<?> block, Operation<Boolean> original) {
		boolean canHarvest = original.call(block);
		if(block == null || canHarvest){
			return canHarvest;
		}
		ItemStack current = asThis.getCurrentEquippedItem();
		ItemStack gloves = ((IAccessoryWearing<?>)asThis).better_with_aether$getAccessoryInSlot(SlotAccessory.GLOVES_SLOT);
		if (current != null || gloves == null || !(gloves.getItem() instanceof ItemGloveTool toolGloves)) {
			return false;
		}
		return toolGloves.canHarvestBlockEQ(asThis, gloves, block);
	}

	@WrapMethod(method = "getCurrentPlayerStrVsBlock")
	private float getStrVsBlock(Block<?> block, Operation<Float> original) {
		ItemStack current = asThis.getCurrentEquippedItem();
		ItemStack gloves = ((IAccessoryWearing<?>)asThis).better_with_aether$getAccessoryInSlot(SlotAccessory.GLOVES_SLOT);
		if (current != null || gloves == null || !(gloves.getItem() instanceof ItemGloveTool toolGloves)) {
			return original.call(block);
		}
		return toolGloves.getStrVsBlockEQ(gloves, block);
	}
}
