package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;

import java.util.Random;

public class BlockLogicSaplingOakGolden extends BlockLogicSaplingBaseAether {

    public BlockLogicSaplingOakGolden(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(@NonNull World world, @NonNull TilePosc tilePos, @NonNull Random random) {
        WorldFeatureAetherTree treeBig = new WorldFeatureAetherTreeGoldenOak(AetherBlocks.LEAVES_OAK_GOLDEN, AetherBlocks.LOG_OAK_GOLDEN, 6);
        world.setBlockType(tilePos, Blocks.AIR);
        if (!treeBig.place(world, random, tilePos.x(), tilePos.y(), tilePos.z())) {
            world.setBlockType(tilePos, this.block);
        }
    }

}
