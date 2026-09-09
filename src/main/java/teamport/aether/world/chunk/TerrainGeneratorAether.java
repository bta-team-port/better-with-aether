package teamport.aether.world.chunk;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.TerrainGeneratorLerp;
import net.minecraft.core.world.type.WorldType;
import org.jspecify.annotations.NonNull;

public class TerrainGeneratorAether extends TerrainGeneratorLerp {
    private final @NonNull DensityGenerator densityGenerator;

    public TerrainGeneratorAether(@NonNull World world) {
        super(world);
        this.densityGenerator = new DensityGeneratorAether(world);
    }

    public @NonNull DensityGenerator getDensityGenerator() {
        return this.densityGenerator;
    }

    @Override
    protected int getBlockAt(@NonNull Chunk chunk, int x, int y, int z, double density) {
        WorldType type = this.world.getWorldType();
        return density > (double) 1.0F ? type.getFillerBlockId() : Blocks.AIR.id();
    }
}
