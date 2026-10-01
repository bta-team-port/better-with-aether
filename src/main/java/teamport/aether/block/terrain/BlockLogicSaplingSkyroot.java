package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureInterface;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;
import teamport.aether.world.feature.terrain.WorldFeatureSkyrootTree;

import java.util.Random;

public class BlockLogicSaplingSkyroot extends BlockLogicSaplingBaseAether {

    public BlockLogicSaplingSkyroot(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random random) {
        WorldFeatureAetherTree treeSmall = new WorldFeatureSkyrootTree(AetherBlocks.LEAVES_SKYROOT, AetherBlocks.LOG_SKYROOT, 4);
        world.setBlockType(tilePos, Blocks.AIR);
        if (!treeSmall.place(world, random, tilePos)) {
            world.setBlockType(tilePos, this.block);
        }
    }

}
