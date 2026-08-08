package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public abstract class BlockLogicSaplingBaseAether extends BlockLogicSaplingBase {

    protected BlockLogicSaplingBaseAether(Block<?> block) {
        super(block);
    }

    @Override
    public boolean mayPlaceOn(@NonNull Block<?> block) {
        int blockId = block.id();
        return (block.hasTag(BlockTags.GROWS_FLOWERS)
            || blockId == AetherBlocks.QUICKSOIL.id()
            || blockId == Blocks.SAND.id()
            || block.hasTag(BlockTags.GROWS_TREES)
            || block.hasTag(AetherBlockTags.GROWS_AETHER_FLOWERS)
            || block.hasTag(AetherBlockTags.GROWS_AETHER_TREES)
            || super.mayPlaceOn(block));
    }

    @Override
    public void updateTick(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random rand, boolean isRandomTick) {
        if (!world.isClientSide) {
            TilePos queryPos = new TilePos();
            if (!this.canGrowOnSand && world.getBlockType(tilePos.down(queryPos)) == Blocks.SAND || world.getBlockType(tilePos.down(queryPos)) == AetherBlocks.QUICKSOIL) {
                world.setBlockTypeNotify(tilePos, AetherBlocks.DEADBUSH_AETHER);
            }

            super.updateTick(world, tilePos, rand, isRandomTick);
            int growthRate = 30;
            if (world.getSeasonManager().getCurrentSeason() != null) {
                growthRate = MathHelper.floor_float((float) growthRate / world.getSeasonManager().getCurrentSeason().cropGrowthFactor);
            }

            if (world.getBlockLightValue(tilePos.up(queryPos)) >= 9 && rand.nextInt(growthRate) == 0) {
                int l = world.getBlockData(tilePos);
                if ((l & 8) == 0) {
                    world.setBlockData(tilePos, l | 8);
                } else {
                    this.growTree(world, tilePos, rand);
                }
            }

        }
    }

}
