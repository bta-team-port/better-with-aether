package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.pos.TilePosc;
import teamport.aether.world.feature.terrain.WorldFeatureAetherTreeGoldenOak;

import java.util.Random;

public class BlockLogicSaplingOakGolden extends BlockLogicSaplingBaseAether {

    public BlockLogicSaplingOakGolden(Block<?> block) {
        super(block);
    }

    @Override
    public void growTree(World world, TilePosc pos, Random random) {
        int x = pos.x();
        int y = pos.y();
        int z = pos.z();
        world.setBlockWithNotify(x, y, z, 0);
        WorldFeature tree = new WorldFeatureAetherTreeGoldenOak();
        if (!tree.place(world, random, x, y, z)) {
            world.setBlockWithNotify(x, y, z, this.id());
        }
    }

}
