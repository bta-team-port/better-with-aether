package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.*;
import bta.aether.item.AetherItems;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.tile.TileEntityTreasureChest;
import bta.aether.world.BiomeAether;
import bta.aether.world.WorldTypeAetherDefault;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.entity.FallingSandRenderer;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class Aether implements GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void beforeGameStart() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();
        new AetherEntities().initializeEntities();

        MobInfoRegistry.register(EntitySentry.class, "aether.sentry.name", "aether.sentry.desc",
                20, 0, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarved),
                        0.66f * 0.8f, 1 ,2), new MobInfoRegistry.MobDrop(new ItemStack(AetherBlocks.stoneCarvedLight),
                        0.66f * 0.2f, 1, 2)});


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

    @Override
    public void afterGameStart() {

    }

    @Override
    public void beforeClientStart() {
        EntityHelper.Client.assignEntityRenderer(EntityFallingGravitite.class, new FallingSandRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityArrowFlaming.class, new ArrowFlamingRenderer());
        EntityHelper.Client.assignEntityRenderer(EntityGoldenDart.class, new GoldenDartRenderer());
        new AetherEntities().initializeModels();
    }

    @Override
    public void afterClientStart() {

    }
}
