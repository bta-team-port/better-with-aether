package teamport.aether.world.type;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import teamport.aether.world.generate.chunk.skyblock.ChunkGeneratorSkyblockAether;

public class WorldTypeAetherSkyblock extends WorldTypeAether {
    public WorldTypeAetherSkyblock(WorldType.Properties properties) {
        super(properties);
    }

    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorSkyblockAether(world);
    }
}
