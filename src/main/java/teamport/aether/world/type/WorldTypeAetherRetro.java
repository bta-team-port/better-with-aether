package teamport.aether.world.type;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.type.WorldType;
import org.jspecify.annotations.NonNull;
import teamport.aether.world.biome.AetherBiomes;

public class WorldTypeAetherRetro extends WorldTypeAether {
    public WorldTypeAetherRetro(WorldType.Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderSingleBiome(world, AetherBiomes.AETHER_PLAINS, 1.0F, 1.0F, 1.0F);
    }

}
