package bta.aether.world.generate.chunk.perlin.aether;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.TerrainGeneratorLerp;
import net.minecraft.core.world.type.WorldType;

public class TerrainGeneratorAether extends TerrainGeneratorLerp {
    private final DensityGenerator densityGenerator;

    public TerrainGeneratorAether(World world) {
        super(world);
        this.densityGenerator = new DensityGeneratorAether(world);
    }

    @Override
    public DensityGenerator getDensityGenerator() {
        return this.densityGenerator;
    }

    @Override
    protected int getBlockAt(int x, int y, int z, double density) {
        WorldType type = this.world.getWorldType();
        if (density > 0.0) {
            return type.getFillerBlock();
        }
        return 0;
    }
}

