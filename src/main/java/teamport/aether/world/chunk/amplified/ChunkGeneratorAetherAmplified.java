package teamport.aether.world.chunk.amplified;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import teamport.aether.world.chunk.ChunkDecoratorAether;
import teamport.aether.world.chunk.MapGenCavesAether;
import teamport.aether.world.chunk.SurfaceGeneratorAether;

public class ChunkGeneratorAetherAmplified extends ChunkGeneratorPerlin {
    public ChunkGeneratorAetherAmplified(World world) {
        super(world, new ChunkDecoratorAether(world), new TerrainGeneratorAetherAmplified(world), new SurfaceGeneratorAether(world), new LargeFeature[]{new MapGenCavesAether()});
    }
}
