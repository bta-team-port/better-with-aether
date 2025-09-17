package teamport.aether.world.generate.chunk;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;

public class ChunkGeneratorAether extends ChunkGeneratorPerlin {
    public ChunkGeneratorAether(World world) {
        super(world, new ChunkDecoratorAether(world), new TerrainGeneratorAether(world), new SurfaceGeneratorAether(world), new LargeFeature[]{new MapGenCavesAether()});
    }
}
