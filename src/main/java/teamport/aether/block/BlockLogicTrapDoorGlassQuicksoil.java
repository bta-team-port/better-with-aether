package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import static teamport.aether.AetherConfig.QUICK_SOIL_SPEED_CAP;

public class BlockLogicTrapDoorGlassQuicksoil extends BlockLogicTrapDoor {
	public BlockLogicTrapDoorGlassQuicksoil(Block<?> block, Material material) {
		super(block, material);
		double f = 0.5;
		double f1 = 1.0;
		this.setBlockBounds(0.5 - f, 0.0, 0.5 - f, 0.5 + f, f1, 0.5 + f);
		block.friction = 1.05F;
	}

    @Override
    public void onEntityWalkedOn(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Entity walker) {
        walker.xd = Math.max(Math.min(walker.xd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        walker.zd = Math.max(Math.min(walker.zd, QUICK_SOIL_SPEED_CAP), -QUICK_SOIL_SPEED_CAP);
        super.onEntityWalkedOn(world, tilePos, walker);
    }
}
