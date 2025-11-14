package teamport.aether.world.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;

public class MapGenCavesAether extends LargeFeature {
    @Override
    public void generate(World world, int baseChunkX, int baseChunkZ, ChunkGeneratorResult result) {
        // The Aether doesn't have caves.
    }
}
