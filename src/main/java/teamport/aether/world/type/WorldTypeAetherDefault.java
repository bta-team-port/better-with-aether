package teamport.aether.world.type;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.type.WorldType;
import teamport.aether.world.generate.chunk.defaultt.ChunkGeneratorDefaultAether;

public class WorldTypeAetherDefault extends WorldTypeAether {
    public WorldTypeAetherDefault(WorldType.Properties properties) {
        super(properties);
    }

    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorDefaultAether(world);
    }
}
