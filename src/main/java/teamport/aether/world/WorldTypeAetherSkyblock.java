package teamport.aether.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.nether.WorldTypeNether;

public class WorldTypeAetherSkyblock extends WorldTypeAether {
    public WorldTypeAetherSkyblock(WorldType.Properties properties) {
        super(properties);
    }

    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorSkyblockAether(world);
    }
}
