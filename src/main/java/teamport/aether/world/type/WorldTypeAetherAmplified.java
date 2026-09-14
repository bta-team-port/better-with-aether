package teamport.aether.world.type;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import teamport.aether.world.chunk.amplified.ChunkGeneratorAetherAmplified;

public class WorldTypeAetherAmplified extends WorldTypeAether {
    public WorldTypeAetherAmplified(WorldType.Properties properties) {
        super(properties);
    }

    @Override
    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorAetherAmplified(world);
    }
}
