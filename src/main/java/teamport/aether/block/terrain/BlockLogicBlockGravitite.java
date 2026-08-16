package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.BlockLogicFloatingBlock;

public class BlockLogicBlockGravitite extends BlockLogicFloatingBlock {

    public BlockLogicBlockGravitite(Block<?> block, Material material) {
        super(block, material);
    }

    @Override
    public void onPlacedByWorld(@NonNull World world, @NonNull TilePosc tilePos) {
        if (isPowered(world, tilePos)) {
            world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
        }
    }

    @Override
    public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
        if (isPowered(world, tilePos)) {
            world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
        }
    }

    @Override
    public void tryToFloat(@NonNull World world, @NonNull TilePosc tilePos) {
        if (isPowered(world, tilePos)) {
            super.tryToFloat(world, tilePos);
        }
    }

    private boolean isPowered(@NonNull World world, @NonNull TilePosc tilePos) {
        if (world.hasDirectSignal(tilePos) || world.hasNeighborSignal(tilePos)) {
            return true;
        }

        TilePos queryPos = new TilePos();
        for (Direction search : Direction.all) {
            queryPos.set(tilePos).add(search);
            if (world.hasSignal(queryPos, search.side())) {
                return true;
            }
        }

        return false;
    }
}
