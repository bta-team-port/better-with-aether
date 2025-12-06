package teamport.aether.blocks.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTree;

import java.util.Random;

public class BlockLogicSaplingSkyroot extends BlockLogicSaplingBaseAether {

    public BlockLogicSaplingSkyroot(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random random) {
        world.setBlockWithNotify(x, y, z, 0);
        WorldFeature tree = new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
        if (!tree.place(world, random, x, y, z)) {
            world.setBlockWithNotify(x, y, z, this.id());
        }
    }

}
