package teamport.aether.item.accessory.gloves;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;

public interface ItemGloveTool {
	default boolean canHarvestBlockEQ(Mob mob, ItemStack itemStack, Block<?> block){
		return false;
	}

	default float getStrVsBlockEQ(ItemStack itemstack, Block<?> block) {
		return 1.0F;
	}

	default int getDamageVsEntityEQ(Entity entity, ItemStack is) {
		return 1;
	}

	default boolean hitEntityEQ(ItemStack gloves, Mob target, Mob attacker) {
		return false;
	}

	@SuppressWarnings("java:S107")
	default boolean onBlockDestroyedEQ(World world, ItemStack itemstack, Block<?> block, TilePosc tilePosc, Side side, Mob mob) {
		return false;
	}
}
