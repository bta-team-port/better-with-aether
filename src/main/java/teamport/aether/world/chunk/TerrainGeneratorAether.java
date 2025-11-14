package teamport.aether.world.chunk;

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
    public int getBlockAt(int x, int y, int z, double density) {
        WorldType type = this.world.getWorldType();
        return density > 1.0 ? type.getFillerBlockId() : 0;
    }
}
