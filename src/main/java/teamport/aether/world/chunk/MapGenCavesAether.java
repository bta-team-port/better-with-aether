package teamport.aether.world.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;

import java.util.Random;

public class MapGenCavesAether extends LargeFeature {
    @Override
    protected void doGeneration(World world, Random random, int chunkX, int chunkZ, int baseChunkX, int baseChunkZ, ChunkGeneratorResult result) {
        // The Aether doesn't have caves.
    }
}
