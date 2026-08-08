package teamport.aether.block.terrain;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
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
       new WorldFeatureAetherTree(AetherBlocks.LEAVES_SKYROOT.id(), AetherBlocks.LOG_SKYROOT.id(), 4);
    }

}
