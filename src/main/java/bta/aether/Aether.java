package bta.aether;

import bta.aether.block.*;
import bta.aether.item.*;
import bta.aether.tile.TileEntityEnchanter;
import bta.aether.tile.TileEntityFreezer;
import bta.aether.tile.TileEntityIncubator;
import bta.aether.tile.TileEntityTreasureChest;
import bta.aether.world.BiomeAether;
import bta.aether.world.WorldTypeAetherDefault;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.*;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;

import static net.minecraft.core.block.Block.*;


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













    static {
    }

    @Override
    public void onInitialize() {
        new AetherBlocks().initializeBlocks();
        new AetherItems().initializeItems();

        LOGGER.info("Aether initialized. Welcome to a hostile paradise.");


        //Tiles

        EntityHelper.createTileEntity(TileEntityEnchanter.class,"Enchanter");
        EntityHelper.createTileEntity(TileEntityFreezer.class,"Freezer");
        EntityHelper.createTileEntity(TileEntityIncubator.class,"Incubator");
        EntityHelper.createTileEntity(TileEntityTreasureChest.class,"Treasure Chest");


        //Crafting Recipes Blocks

//        RecipeHelper.Crafting.createRecipe(Block.workbench, 1, new Object[]{"PP", "PP", 'P' , Aether.planksSkyroot});
//        RecipeHelper.Crafting.createRecipe(Aether.incubator, 1, new Object[]{"PPP", "PTP", "PPP", 'P' , Aether.planksSkyroot, 'T', Aether.torchAmbrosium});
//        RecipeHelper.Crafting.createRecipe(Aether.freezer, 1, new Object[]{"PPP", "PTP", "XXX", 'P' , Aether.planksSkyroot, 'T', Aether.icestone, 'X', Aether.planksSkyroot});
//        RecipeHelper.Crafting.createRecipe(Aether.enchanter, 1, new Object[]{"PPP", "PTP", "PPP", 'P' , Aether.holystone, 'T', Aether.gemZanite});
//
//        RecipeHelper.Crafting.createRecipe(chestPlanksOak, 1, new Object[]{"PPP", "P P", "PPP", 'P' , Aether.planksSkyroot});
//
//        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(Item.dye, 2, 7), new Object[]{"P", 'P' , Aether.flowerWhite});
//        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(Item.dye, 2, 5), new Object[]{"P", 'P' , Aether.flowerPurple});
//
//        RecipeHelper.Crafting.createRecipe(Aether.planksSkyroot, 4, new Object[]{"P", 'P' , Aether.logSkyroot});
//        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(planksOakPainted, 4, 4), new Object[]{"P", 'P' , Aether.logOakGolden});
//
//        RecipeHelper.Crafting.createRecipe(Aether.blockZanite, 1, new Object[]{"PP ", "PP ", 'P' , Aether.gemZanite});
//
//        RecipeHelper.Crafting.createRecipe(jukebox, 1, new Object[]{"PPP", "PGP", "PPP", 'P' , Aether.planksSkyroot, 'G', gravititeEnchanted});
//
//        Crafting Recipes Items
//
//        RecipeHelper.Crafting.createRecipe(Item.doorOak, 1, new Object[]{"SS ", "SS ", "SS ", 'S', Aether.planksSkyroot});
//        RecipeHelper.Crafting.createRecipe(ladderOak, 4, new Object[]{"S S", "SSS", "S S", 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(fencePlanksOak, 6, new Object[]{"PSP", "PSP", 'P', Aether.planksSkyroot, 'S', stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(fencegatePlanksOak, 3, new Object[]{"PSP", "PSP", 'S', Aether.planksSkyroot, 'P', stickSkyroot});
//
//
//        RecipeHelper.Crafting.createRecipe(Aether.stickSkyroot, 4, new Object[]{" P ", " P ", 'P' , Aether.planksSkyroot});
//        RecipeHelper.Crafting.createRecipe(Aether.torchAmbrosium, 2, new Object[]{" A ", " P ", 'P' , Aether.stickSkyroot, 'A', ambrosium});
//
//        RecipeHelper.Crafting.createRecipe(Aether.dartGolden, 1, new Object[]{" G ", " P ", " F ", 'P' , Aether.stickSkyroot, 'G', Aether.amberGolden, 'F', Item.featherChicken});
//        RecipeHelper.Crafting.createRecipe(Aether.dartPoison, 8, new Object[]{"GGG", "GBG", "GGG", 'G' , Aether.dartGolden, 'B', Aether.bucketSkyrootPoison});
//
//        RecipeHelper.Crafting.createRecipe(Aether.dartshooter, 1, new Object[]{"P  ", "P  ", "Z  ", 'P' , Aether.planksSkyroot, 'Z', gemZanite});
//        RecipeHelper.Crafting.createRecipe(Aether.dartshooterPoison, 1, new Object[]{"DP ", 'D' , Aether.dartshooter, 'P', petalAechor});
//
//
//
//        RecipeHelper.Crafting.createRecipe(Aether.gemZanite, 4, new Object[]{"P", 'P' , Aether.blockZanite});
//
//        RecipeHelper.Crafting.createRecipe(Aether.toolStaffNature, 1, new Object[]{"Z ", "S ", 'S' , Aether.stickSkyroot, 'Z', gemZanite});
//
//        RecipeHelper.Crafting.createRecipe(Aether.cloudparachute, 1, new Object[]{"PP ", "PP ", 'P' , Aether.aercloudWhite});
//        RecipeHelper.Crafting.createRecipe(Aether.cloudparachuteGold, 1, new Object[]{"PP ", "PP ", 'P' , Aether.aercloudGold});
//
//        RecipeHelper.Crafting.createRecipe(Item.saddle, 1, new Object[]{"LLL", "LSL", 'L' , Item.leather, 'S', Item.string});
//
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeWhite, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , Block.wool});
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeRed, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 14))});
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeYellow, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 4))});
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 11))});
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 3))});
//        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 9))});
//
//        RecipeHelper.Crafting.createRecipe(Aether.bucketSkyroot, 1, new Object[]{"P P", " P ", 'P' , Aether.planksSkyroot});
//
//        RecipeHelper.Crafting.createRecipe(toolAxeSkyroot, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.planksSkyroot, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolPickaxeSkyroot, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.planksSkyroot, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolShovelSkyroot, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.planksSkyroot, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolSwordSkyroot, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.planksSkyroot, 'S', Aether.stickSkyroot});
//
//        RecipeHelper.Crafting.createRecipe(toolAxeHolystone, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.holystone, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolPickaxeHolystone, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.holystone, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolShovelHolystone, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.holystone, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolSwordHolystone, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.holystone, 'S', Aether.stickSkyroot});
//
//        RecipeHelper.Crafting.createRecipe(toolAxeZanite, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.gemZanite, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolPickaxeZanite, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.gemZanite, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolShovelZanite, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.gemZanite, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolSwordZanite, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.gemZanite, 'S', Aether.stickSkyroot});
//
//        RecipeHelper.Crafting.createRecipe(toolAxeGravitite, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.gravititeEnchanted, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolPickaxeGravitite, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.gravititeEnchanted, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolShovelGravitite, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.gravititeEnchanted, 'S', Aether.stickSkyroot});
//        RecipeHelper.Crafting.createRecipe(toolSwordGravitite, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.gravititeEnchanted, 'S', Aether.stickSkyroot});
//
//        RecipeHelper.Crafting.createRecipe(armorHelmetZanite, 1, new Object[]{"GGG", "G G", 'G' , Aether.gemZanite});
//        RecipeHelper.Crafting.createRecipe(armorChestplateZanite, 1, new Object[]{"G G", "GGG", "GGG", 'G' , Aether.gemZanite});
//        RecipeHelper.Crafting.createRecipe(armorLeggingsZanite, 1, new Object[]{"GGG", "G G", "G G", 'G' , Aether.gemZanite});
//        RecipeHelper.Crafting.createRecipe(armorBootsZanite, 1, new Object[]{"G G", "G G", 'G' , Aether.gemZanite});
//
//        RecipeHelper.Crafting.createRecipe(armorHelmetGravitite, 1, new Object[]{"GGG", "G G", 'G' , Aether.gravititeEnchanted});
//        RecipeHelper.Crafting.createRecipe(armorChestplateGravitite, 1, new Object[]{"G G", "GGG", "GGG", 'G' , Aether.gravititeEnchanted});
//        RecipeHelper.Crafting.createRecipe(armorLeggingsGravitite, 1, new Object[]{"GGG", "G G", "G G", 'G' , Aether.gravititeEnchanted});
//        RecipeHelper.Crafting.createRecipe(armorBootsGravitite, 1, new Object[]{"G G", "G G", 'G' , Aether.gravititeEnchanted});
//
//        RecipeHelper.Crafting.createRecipe(armorGlovesLeather, 1, new Object[]{"G G", 'G' , Item.leather});
//        RecipeHelper.Crafting.createRecipe(armorGlovesIron, 1, new Object[]{"G G", 'G' , Item.ingotIron});
//        RecipeHelper.Crafting.createRecipe(armorGlovesGold, 1, new Object[]{"G G", 'G' , Item.ingotGold});
//        RecipeHelper.Crafting.createRecipe(armorGlovesDiamond, 1, new Object[]{"G G", 'G' , Item.diamond});
//        RecipeHelper.Crafting.createRecipe(armorGlovesZanite, 1, new Object[]{"G G", 'G' , Aether.gemZanite});
//        RecipeHelper.Crafting.createRecipe(armorGlovesGravitite, 1, new Object[]{"G G", 'G' , Aether.gravititeEnchanted});
//
//        RecipeHelper.Crafting.createRecipe(armorRingIron, 1, new Object[]{" G ", "G G", " G ", 'G' , Item.ingotIron});
//        RecipeHelper.Crafting.createRecipe(armorRingGold, 1, new Object[]{" G ", "G G", " G ", 'G' , Item.ingotGold});
//        RecipeHelper.Crafting.createRecipe(armorRingZanite, 1, new Object[]{" G ", "G G", " G ", 'G' , Aether.gemZanite});
//
//        RecipeHelper.Crafting.createRecipe(armorPendantIron, 1, new Object[]{"SSS", "S S", " G ", 'S', Item.string, 'G' , Item.ingotIron});
//        RecipeHelper.Crafting.createRecipe(armorPendantGold, 1, new Object[]{"SSS", "S S", " G ", 'S', Item.string, 'G' , Item.ingotGold});
//        RecipeHelper.Crafting.createRecipe(armorPendantZanite, 1, new Object[]{"SSS", "S S", " G ", 'S', Item.string, 'G' , Aether.gemZanite});



        //Furnace Recipes

        RecipeHelper.smeltingManager.addSmelting(AetherBlocks.logSkyroot.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.smeltingManager.addSmelting(AetherBlocks.logOakGolden.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.blastingManager.addSmelting(AetherBlocks.logSkyroot.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.blastingManager.addSmelting(AetherBlocks.logOakGolden.id, new ItemStack(Item.coal, 1, 1));

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.logOakGolden.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.planksSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.stickSkyroot.id, 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolPickaxeSkyroot.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolShovelSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolAxeSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.toolSwordSkyroot.id, 600);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.bucketSkyroot.id, 600);

    }
}
