package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;

import java.util.Random;

public class BlockLogicSaplingSkyroot extends BlockLogicSaplingBaseAether {

    public BlockLogicSaplingSkyroot(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random random) {
        WorldFeature treeSmall = new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
        world.setBlockType(tilePos, Blocks.AIR);
        if (!treeSmall.place(world, random, tilePos.x(), tilePos.y(), tilePos.z())) {
            world.setBlockType(tilePos, this.block);
        }
    }

}
