package teamport.aether.world.chunk.extended;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.TerrainGeneratorLerp;
import net.minecraft.core.world.type.WorldType;

public class TerrainGeneratorAetherExtended extends TerrainGeneratorLerp {
    public final DensityGenerator densityGenerator;

    public TerrainGeneratorAetherExtended(World world) {
        super(world);
        this.densityGenerator = new DensityGeneratorAetherExtended(world);
    }

    @Override
    public DensityGenerator getDensityGenerator() {
        return this.densityGenerator;
    }

    @Override
    public int getBlockAt(int x, int y, int z, double density) {
        WorldType type = this.world.getWorldType();
        return density > 4.0 ? type.getFillerBlockId() : 0;
    }
}
