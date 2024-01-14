package bta.aether.world.generate.chunk.perlin.aether;

import bta.aether.compat.terrainapi.ChunkDecoratorAetherAPI;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.MapGenCaves;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import net.minecraft.core.world.generate.chunk.perlin.paradise.TerrainGeneratorParadise;

public class ChunkGeneratorAether  extends ChunkGeneratorPerlin {
    public ChunkGeneratorAether(World world) {
        super(world, new ChunkDecoratorAetherAPI(world), new TerrainGeneratorAether(world), new SurfaceGeneratorAether(world), new MapGenCaves(false));
    }
}
