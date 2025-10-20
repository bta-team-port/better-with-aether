package teamport.aether.world.chunk.extended;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import teamport.aether.world.chunk.ChunkDecoratorAether;
import teamport.aether.world.chunk.MapGenCavesAether;
import teamport.aether.world.chunk.SurfaceGeneratorAether;

public class ChunkGeneratorAetherExtended extends ChunkGeneratorPerlin {
    public ChunkGeneratorAetherExtended(World world) {
        super(world, new ChunkDecoratorAether(world), new TerrainGeneratorAetherExtended(world), new SurfaceGeneratorAether(world), new LargeFeature[]{new MapGenCavesAether()});
    }
}
