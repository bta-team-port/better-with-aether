package teamport.aether.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class BlockLogicFloatingBlock extends BlockLogic {
    public static boolean fallInstantly = false;

    public BlockLogicFloatingBlock(@NonNull Block<?> block, @NonNull Material material) {
        super(block, material);
    }

    @Override
    public void onPlacedByWorld(@NonNull World world, @NonNull TilePosc tilePos) {
        world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
    }

    @Override
    public void onNeighborChanged(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Block<?> block) {
        world.scheduleBlockUpdate(tilePos, this.block, this.tickDelay());
    }

    @Override
    public void updateTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand, boolean isRandomTick) {
        this.tryToFloat(world, tilePos);
    }

    public void tryToFloat(@NonNull World world, @NonNull TilePosc tilePos) {
        TilePos queryPos = new TilePos();
        if (canFloatAbove(world, tilePos.up(queryPos)) && tilePos.y() < world.getHeightBlocks()) {
            byte radius = 32;
            if (!fallInstantly && world.areBlocksLoaded(tilePos.add(-radius, -radius, -radius, new TilePos()), tilePos.add(radius, radius, radius, new TilePos()))) {
                EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, (double)tilePos.x() + (double)0.5F, (double)tilePos.y() + (double)0.5F, (double)tilePos.z() + (double)0.5F, this.block.id(), 0, null);
                world.entityJoinedWorld(entityFallingBlock);
            } else {
                world.setBlockTypeNotify(tilePos, Blocks.AIR);
                TilePos check = tilePos.up(new TilePos());

                while(canFloatAbove(world, check) && check.y < world.getHeightBlocks()) {
                    check.up(new TilePos());
                }

                if (check.y < world.getHeightBlocks()) {
                    world.setBlockTypeNotify(check, this.block);
                }
            }
        }

    }

    @Override
    public int tickDelay() {
        return 3;
    }

    public static boolean canFloatAbove(@NonNull World world, @NonNull TilePos tilePos) {
        Block<?> block = world.getBlockType(tilePos);
        return block == Blocks.AIR || block.hasTag(BlockTags.PLACE_OVERWRITES);
    }
}
