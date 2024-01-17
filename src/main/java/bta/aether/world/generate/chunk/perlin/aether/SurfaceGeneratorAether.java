package bta.aether.world.generate.chunk.perlin.aether;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import net.minecraft.core.world.noise.PerlinNoise;
import net.minecraft.core.world.noise.RetroPerlinNoise;

public class SurfaceGeneratorAether extends SurfaceGeneratorOverworld {
    public SurfaceGeneratorAether(World world) {
        super(world, new PerlinNoise(world.getRandomSeed(), 4, 40), new PerlinNoise(world.getRandomSeed(), 4, 44), new PerlinNoise(world.getRandomSeed(), 8, 32), false);
    }
}
