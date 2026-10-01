package teamport.aether.world.feature.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.block.AetherBlocks;

import java.util.Random;

public abstract class WorldFeatureAetherTree extends WorldFeature implements WorldFeatureInterface {
    protected final Block<?> leaves;
    protected final Block<?> log;
    protected final int heightMod;
    protected int treeHeight;


    protected WorldFeatureAetherTree(Block<?> leaves, Block<?> log, int heightMod) {
        this.leaves = leaves;
        this.log = log;
        this.heightMod = heightMod;
    }

    @Override
    public final boolean place(World world, Random random, int x, int y, int z) {
        return this.place(world, random, new TilePos(x, y, z));
    }

    @Override
    public boolean place(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc) {
        this.treeHeight = this.setTreeHeight(world, random, tilePosc);
        if(this.canPlace(world, random, tilePosc)){
            return this.placeFeature(world, random, tilePosc);
        }
        this.treeHeight = 0;
        return false;
    }

    protected abstract int setTreeHeight(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc);
    public abstract boolean canPlace(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc);
    public abstract boolean placeFeature(@NotNull World world, @NotNull Random random, @NotNull TilePosc tilePosc);

    public final void changeDirtBlockBelow(@NotNull World world, TilePosc tilePosc) {
        Block<?> block = world.getBlockType(tilePosc);
        if (block == Blocks.GRASS || block == Blocks.GRASS_RETRO) {
            world.setBlockTypeNotify(tilePosc, Blocks.DIRT);
            return;
        }
        if(block == Blocks.GRASS_SCORCHED){
            world.setBlockTypeNotify(tilePosc, Blocks.DIRT_SCORCHED);
            return;
        }
        if(block == AetherBlocks.GRASS_AETHER){
            world.setBlockTypeNotify(tilePosc, AetherBlocks.DIRT_AETHER);
        }
    }

    public final boolean canPlaceTree(@NotNull World world, @NotNull TilePosc tilePosc){
        TilePos tileBelow = new TilePos(tilePosc).down();
        Block<?> blockBelow = world.getBlockType(tileBelow);
        return tilePosc.y() >= 1
            && tilePosc.y() + this.treeHeight + 1 <= world.getHeightBlocks()
            && tilePosc.y() < (world.getHeightBlocks() - this.treeHeight - 1)
            && (blockBelow.hasTag(BlockTags.GROWS_TREES) || blockBelow.hasTag(AetherBlockTags.GROWS_AETHER_TREES));
    }

    protected boolean canTrunkBePlaced(Block<?> block) {
        return block == Blocks.AIR
            || block.hasTag(BlockTags.PLACE_OVERWRITES)
            || block.getLogic() instanceof BlockLogicLeavesBase
            || block.getLogic() instanceof BlockLogicLog;
    }

    protected boolean canLeavesBePlaced(Block<?> block) {
        return block == Blocks.AIR
            || block.getLogic() instanceof BlockLogicLeavesBase
            || block.hasTag(BlockTags.PLACE_OVERWRITES)
            || block.getMaterial().isReplaceable();
    }

}
