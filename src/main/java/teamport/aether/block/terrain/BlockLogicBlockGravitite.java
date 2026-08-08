package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.BlockLogicFloatingBlock;

public class BlockLogicBlockGravitite extends BlockLogicFloatingBlock {

    public BlockLogicBlockGravitite(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
        boolean hasSignal = world.hasNeighborSignal(tilePos);
        if (hasSignal) {
            tryToFloat(world, tilePos);
        }
    }

}
