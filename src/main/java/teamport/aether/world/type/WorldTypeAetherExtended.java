package teamport.aether.world.type;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import teamport.aether.world.chunk.extended.ChunkGeneratorAetherExtended;

public class WorldTypeAetherExtended extends WorldTypeAether {
    public WorldTypeAetherExtended(WorldType.Properties properties) {
        super(properties);
    }

    @Override
    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorAetherExtended(world);
    }
}
