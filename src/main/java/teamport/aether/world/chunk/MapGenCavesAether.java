package teamport.aether.world.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import org.jspecify.annotations.NonNull;

import java.util.Random;

public class MapGenCavesAether extends LargeFeature {
    @Override
    protected void doGeneration(@NonNull World world, @NonNull Random random, int chunkX, int chunkZ, int baseChunkX, int baseChunkZ, @NonNull ChunkGeneratorResult result) {
        // The Aether doesn't have caves.
    }
}
