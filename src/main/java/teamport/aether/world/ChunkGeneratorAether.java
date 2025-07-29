package teamport.aether.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;

public class ChunkGeneratorAether extends ChunkGeneratorPerlin {
    public ChunkGeneratorAether(World world) {
        super(world, new ChunkDecoratorOverworld(world), new TerrainGeneratorAether(world), new SurfaceGeneratorAether(world), new LargeFeature[]{new MapGenCavesAether()});
    }
}
