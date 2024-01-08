package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.tile.TileEntityTreasureChest;
import bta.aether.world.BiomeAether;
import bta.aether.world.WorldTypeAetherDefault;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;


public class Aether implements ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether());
    static
    {
        biomeAether.topBlock = (short) AetherBlocks.grassAether.id;
        biomeAether.fillerBlock = (short) AetherBlocks.dirtAether.id;
    }
    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));

    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, AetherBlocks.portalAether.id).setDefaultWorldType(worldTypeAether);
    static
    {
        Dimension.registerDimension(3, dimensionAether);
    }

    @Override
    public void onInitialize() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();

        //Tiles
        EntityHelper.Core.createTileEntity(TileEntityEnchanter.class,"Enchanter");
        EntityHelper.Core.createTileEntity(TileEntityFreezer.class,"Freezer");
        EntityHelper.Core.createTileEntity(TileEntityIncubator.class,"Incubator");
        EntityHelper.Core.createTileEntity(TileEntityTreasureChest.class,"Treasure Chest");

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logOakGolden.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.planksSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.stickSkyroot.id, 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolPickaxeSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolShovelSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolAxeSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolSwordSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.bucketSkyroot.id, 600);

        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");
    }
}
