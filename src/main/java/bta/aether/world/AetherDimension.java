package bta.aether.world;

import bta.aether.block.AetherBlocks;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;

public class AetherDimension implements PreLaunchEntrypoint {
    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether());
    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));
    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, AetherBlocks.portalAether.id).setDefaultWorldType(worldTypeAether);
    @Override
    public void onPreLaunch() {
        // This is here so that the dimension is created and added to the dimension list before the Server even launches, it'll crash otherwise
        biomeAether.topBlock = (short) AetherBlocks.grassAether.id;
        biomeAether.fillerBlock = (short) AetherBlocks.dirtAether.id;
        Dimension.registerDimension(3, dimensionAether);
    }
}
