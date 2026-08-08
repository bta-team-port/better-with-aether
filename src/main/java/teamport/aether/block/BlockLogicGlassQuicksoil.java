package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import static teamport.aether.AetherConfig.QUICK_SOIL_SPEED_CAP;

public class BlockLogicGlassQuicksoil extends BlockLogicTransparent {
	public BlockLogicGlassQuicksoil(Block<?> block) {
		super(block, Materials.GLASS);
		block.friction = 1.05f;
	}

    @Override
    public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity walker) {
        walker.xd = Math.max(Math.min(walker.xd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        walker.zd = Math.max(Math.min(walker.zd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        super.onEntityWalkedOn(world, tilePos, walker);
    }

	@Override
	public ItemStack[] getBreakResult(@NonNull World world, @NonNull EnumDropCause dropCause, int meta, TileEntity tileEntity) {
		return switch (dropCause) {
			case SILK_TOUCH, PICK_BLOCK -> new ItemStack[]{new ItemStack(this)};
			default -> null;
		};
	}
}
