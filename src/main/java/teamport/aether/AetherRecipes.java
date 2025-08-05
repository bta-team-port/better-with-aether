package teamport.aether;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;
import teamport.aether.recipe.RecipeEntryAetherMachine;
import teamport.aether.recipe.RecipeEntryIncubator;
import teamport.aether.recipe.RecipeGroupAetherMachine;
import teamport.aether.recipe.RecipeGroupIncubator;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherRecipes implements RecipeEntrypoint {
    public static  RecipeNamespace AETHER;
    public static  RecipeGroupAetherMachine ENCHANTER;
    public static  RecipeGroupAetherMachine FREEZER;
    public static  RecipeGroupIncubator INCUBATOR;

    @Override
    public void onRecipesReady() {
        AetherRecipes.workbenchRecipes();
        AetherRecipes.furnaceRecipes();
        AetherRecipes.blastFurnaceRecipes();
        AetherRecipes.trommelRecipes();
        AetherRecipes.aetherMachinesRecipes();
    }




    public static void aetherMachinesRecipes() {
        // registered recipe types
        Registries.RECIPE_TYPES.register("aether:machine", RecipeEntryAetherMachine.class);
        Registries.RECIPE_TYPES.register("aether:incubator", RecipeEntryIncubator.class);

        DataLoader.loadRecipesFromFile("/assets/aether/recipes/freezer.json");
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/incubator.json");
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/enchanter.json");
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/workbench.json");
    }

    @Override
    public void initNamespaces() {
        RecipeBuilder.initNameSpace(MOD_ID);
        AETHER = RecipeBuilder.getRecipeNamespace(MOD_ID);
        RecipeBuilder.getRecipeNamespace(MOD_ID);
        AetherRecipes.extendVanillaGroups();
        AetherRecipes.oreGemGroups();
        AetherRecipes.enchanterGroups();
        AetherRecipes.freezerGroups();
        AetherRecipes.trommelGroups();

        ENCHANTER = new RecipeGroupAetherMachine(new RecipeSymbol(new ItemStack(AetherBlocks.ENCHANTER_ACTIVE.getDefaultStack())));
        FREEZER = new RecipeGroupAetherMachine(new RecipeSymbol(new ItemStack(AetherBlocks.FREEZER_ACTIVE.getDefaultStack())));
        INCUBATOR = new RecipeGroupIncubator(new RecipeSymbol(new ItemStack(AetherBlocks.INCUBATOR_ACTIVE.getDefaultStack())));

        // register groups
        AETHER.register("enchanter", ENCHANTER);
        AETHER.register("freezer", FREEZER);
        AETHER.register("incubator", INCUBATOR);


    }
    public static void oreGemGroups() {
        Registries.ITEM_GROUPS.register("aether:gems", Registries.stackListOf(AetherBlocks.BLOCK_GRAVITITE, Items.DIAMOND));
        Registries.ITEM_GROUPS.register("aether:sticks", Registries.stackListOf(AetherItems.STICK_SKYROOT, Items.STICK));
        Registries.ITEM_GROUPS.register("aether:ambrosium_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, 1)));
        Registries.ITEM_GROUPS.register("aether:zanite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_ZANITE_HOLYSTONE, 1)));
        Registries.ITEM_GROUPS.register("aether:gravitite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 1)));
    }
    public static void extendVanillaGroups() {
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("jukebox");
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("ladder");
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("sponge_to_wet_sponge");
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.LOG_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.LOG_OAK_GOLDEN.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:planks").add(AetherBlocks.PLANKS_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.LEAVES_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.LEAVES_OAK_GOLDEN.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:stones").add(AetherBlocks.HOLYSTONE.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:cobblestones").add(AetherBlocks.COBBLE_HOLYSTONE.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:grasses").add(AetherBlocks.GRASS_AETHER.getDefaultStack());
    }
    public static void freezerGroups(){
        Registries.ITEM_GROUPS.register("aether:water_buckets", Registries.stackListOf(
             new ItemStack(Items.BUCKET_WATER),
             new ItemStack(AetherItems.BUCKET_SKYROOT_WATER)
        ));

        Registries.ITEM_GROUPS.register("aether:stones", Registries.stackListOf(
             new ItemStack(Blocks.BASALT),
             new ItemStack(Blocks.GRANITE),
             new ItemStack(Blocks.LIMESTONE),
             new ItemStack(Blocks.STONE),
             new ItemStack(Blocks.MARBLE),
             new ItemStack(Blocks.SLATE)
        ));

        Registries.ITEM_GROUPS.register("aether:cobblestones", Registries.stackListOf(
                new ItemStack(Blocks.COBBLE_BASALT),
                new ItemStack(Blocks.COBBLE_GRANITE),
                new ItemStack(Blocks.COBBLE_LIMESTONE),
                new ItemStack(Blocks.COBBLE_STONE)
        ));

    }
    public static void enchanterGroups() {
        Registries.ITEM_GROUPS.register("aether:saplings", Registries.stackListOf(
                new ItemStack(Blocks.SAPLING_BIRCH),
                new ItemStack(Blocks.SAPLING_CACAO),
                new ItemStack(Blocks.SAPLING_CHERRY),
                new ItemStack(Blocks.SAPLING_OAK),
                new ItemStack(Blocks.SAPLING_EUCALYPTUS),
                new ItemStack(Blocks.SAPLING_OAK_RETRO),
                new ItemStack(Blocks.SAPLING_PALM),
                new ItemStack(Blocks.SAPLING_PINE),
                new ItemStack(Blocks.SAPLING_SHRUB),
                new ItemStack(Blocks.SAPLING_THORN)
        ));

        Registries.ITEM_GROUPS.register("aether:record_aether", Registries.stackListOf(
                new ItemStack(Items.RECORD_13),
                new ItemStack(Items.RECORD_BLOCKS),
                new ItemStack(Items.RECORD_CAT),
                new ItemStack(Items.RECORD_CHIRP)));

        Registries.ITEM_GROUPS.register("aether:record_dawn", Registries.stackListOf(
                new ItemStack(Items.RECORD_DOG),
                new ItemStack(Items.RECORD_STRAD),
                new ItemStack(Items.RECORD_WAIT),
                new ItemStack(Items.RECORD_WARD)));

        Registries.ITEM_GROUPS.register("aether:record_morning", Registries.stackListOf(
                new ItemStack(Items.RECORD_FAR),
                new ItemStack(Items.RECORD_MALL),
                new ItemStack(Items.RECORD_MELLOHI),
                new ItemStack(Items.RECORD_STAL)));
    }
    public static void trommelGroups() {
        Registries.ITEM_GROUPS.register("aether:dirts", Registries.stackListOf(
                new ItemStack((AetherBlocks.DIRT_AETHER)),
                new ItemStack((AetherBlocks.PATH_DIRT_AETHER)),
                new ItemStack((AetherBlocks.GRASS_AETHER))
        ));
    }

    public static void workbenchRecipes() {
        AetherRecipes.plankRecipes();
        AetherRecipes.aetherMachineRecipes();
        AetherRecipes.quickGlassRecipes();
        AetherRecipes.slabRecipes();
        AetherRecipes.stairRecipes();
        AetherRecipes.capeRecipes();
        AetherRecipes.dartAmmoRecipes();
        AetherRecipes.toolsRecipes();
        AetherRecipes.armorRecipes();
        AetherRecipes.glovesRecipes();
        AetherRecipes.pendantRecipes();

        // these did not fit anywhere specific

        RecipeBuilder.Shaped(MOD_ID, "XXX", "XXX", "XXX")
                .addInput('X', AetherItems.ZANITE)
                .create("block_of_zanite", new ItemStack(AetherBlocks.BLOCK_ZANITE, 1));

        RecipeBuilder.Shaped(MOD_ID, "S", "B")
                .addInput('S', Blocks.SPONGE_DRY)
                .addInput('B', "aether:water_buckets")
                .create("sponge_to_wet_sponge", new ItemStack(Blocks.SPONGE_WET, 1));

        RecipeBuilderShaped templateItemtoFuelBlock = new RecipeBuilderShaped(MOD_ID, "XXX", "X X", "XXX");
        templateItemtoFuelBlock.addInput('X', AetherItems.AMBROSIUM).create("block_of_ambrosium", new ItemStack(AetherBlocks.BLOCK_AMBROSIUM, 1));
        templateItemtoFuelBlock.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_chest", new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT, 1));

        RecipeBuilderShaped templateBlocktoItem = new RecipeBuilderShaped(MOD_ID, "X");
        templateBlocktoItem.addInput('X', AetherBlocks.BLOCK_ZANITE).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.ZANITE, 9));
        templateBlocktoItem.addInput('X', AetherBlocks.BLOCK_AMBROSIUM).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.AMBROSIUM, 8));

        RecipeBuilderShaped templateFlowertoDye = new RecipeBuilderShaped(MOD_ID, "X");
        templateFlowertoDye.addInput('X', AetherBlocks.FLOWER_WHITE).create("flower_white_to_dye", new ItemStack(Items.DYE, 2, 7));
        templateFlowertoDye.addInput('X', AetherBlocks.FLOWER_PURPLE).create("flower_purple_to_dye", new ItemStack(Items.DYE, 2, 5));

        RecipeBuilder.Shaped(MOD_ID, "XX", "XX")
                .addInput('X', AetherItems.ZANITE)
                .create("zanite_bricks", new ItemStack(AetherBlocks.BRICK_ZANITE, 4));

        RecipeBuilder.Shaped(MOD_ID, "XX", "XX")
                .addInput('X', AetherBlocks.HOLYSTONE)
                .create("holystone_bricks", new ItemStack(AetherBlocks.BRICK_HOLYSTONE, 4));

        RecipeBuilder.Shaped(MOD_ID, "X", "S")
                .addInput('X', AetherItems.AMBROSIUM)
                .addInput('S', AetherItems.STICK_SKYROOT)
                .create("ambrosium_torch", new ItemStack(AetherBlocks.TORCH_AMBROSIUM, 2));


        RecipeBuilder.Shaped(MOD_ID, " C ", "BSB", " M ")
                .addInput('C', Items.FOOD_CHERRY)
                .addInput('B', new ItemStack(Items.DYE, 1, 3))
                .addInput('S', Items.AMMO_SNOWBALL)
                .addInput('M', AetherItems.BUCKET_SKYROOT_MILK)
                .create("skyroot_bucket_icecream", new ItemStack(AetherItems.BUCKET_SKYROOT_ICECREAM, 1));


        RecipeBuilderShaped Clouds = new RecipeBuilderShaped(MOD_ID, "XX", "XX");
        Clouds.addInput('X', AetherBlocks.AERCLOUD_WHITE).create("cloud_parachute", new ItemStack(AetherItems.PARACHUTE_CLOUD, 1));
        Clouds.addInput('X', AetherBlocks.AERCLOUD_GOLD).create("cloud_parachute_gold", new ItemStack(AetherItems.PARACHUTE_CLOUD_GOLD, 1));

        RecipeBuilder.Shaped(MOD_ID, "Z", "S")
                .addInput('S', AetherItems.STICK_SKYROOT)
                .addInput('Z', AetherItems.ZANITE)
                .create("nature_staff", new ItemStack(AetherItems.TOOL_STAFF_NATURE, 1));
    }

    public static void quickGlassRecipes() {
        RecipeBuilder.Shaped(MOD_ID, "PP", "PP", "PP")
                .addInput('P', AetherBlocks.GLASS_QUICKSOIL)
                .create("quicksoil_glass_door", new ItemStack(AetherItems.DOOR_GLASS_AMBROSIUM, 2));

        RecipeBuilder.Shaped(MOD_ID, "PPP", "PPP")
                .addInput('P', AetherBlocks.GLASS_QUICKSOIL)
                .create("quicksoil_glass_trapdoor", new ItemStack(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, 6));
    }
    public static void dartAmmoRecipes() {
        RecipeBuilderShaped Shooter = new RecipeBuilderShaped(MOD_ID, " X ", " X ", " S ");
        Shooter.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.ZANITE).create("dart_shooter", new ItemStack(AetherItems.TOOL_SHOOTER, 1));

        RecipeBuilder.Shaped(MOD_ID, " A ", " S ", " F ")
                .addInput('S', AetherItems.STICK_SKYROOT)
                .addInput('A', AetherItems.AMBER)
                .addInput('F', Items.FEATHER_CHICKEN)
                .create("dart_golden", new ItemStack(AetherItems.AMMO_DART_GOLDEN, 4));

        RecipeBuilder.Shaped(MOD_ID, " D ", "DPD", " D ")
                .addInput('D', AetherItems.AMMO_DART_GOLDEN)
                .addInput('P', AetherItems.PETAL_AECHOR)
                .create("dart_poison", new ItemStack(AetherItems.AMMO_DART_POISON, 4));
    }
    public static void capeRecipes() {
        RecipeBuilderShaped Capes = new RecipeBuilderShaped(MOD_ID, "CC", "WW", "WW");
        Capes.addInput('C', Items.CLOTH).addInput( 'W', Blocks.WOOL, 0).create("cape_white", new ItemStack(AetherItems.ARMOR_CAPE_WHITE, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 1).create("cape_orange", new ItemStack(AetherItems.ARMOR_CAPE_ORANGE, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 2).create("cape_magenta", new ItemStack(AetherItems.ARMOR_CAPE_MAGENTA, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 3).create("cape_lightblue", new ItemStack(AetherItems.ARMOR_CAPE_LIGHTBLUE, 1));
        Capes.addInput('C', Items.CLOTH).addInput( 'W', Blocks.WOOL, 4).create("cape_yellow", new ItemStack(AetherItems.ARMOR_CAPE_YELLOW, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 5).create("cape_lime", new ItemStack(AetherItems.ARMOR_CAPE_LIME, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 6).create("cape_pink", new ItemStack(AetherItems.ARMOR_CAPE_PINK, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 7).create("cape_gray", new ItemStack(AetherItems.ARMOR_CAPE_GRAY, 1));
        Capes.addInput('C', Items.CLOTH).addInput( 'W', Blocks.WOOL, 8).create("cape_silver", new ItemStack(AetherItems.ARMOR_CAPE_SILVER, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 9).create("cape_cyan", new ItemStack(AetherItems.ARMOR_CAPE_CYAN, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 10).create("cape_purple", new ItemStack(AetherItems.ARMOR_CAPE_PURPLE, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 11).create("cape_blue", new ItemStack(AetherItems.ARMOR_CAPE_BLUE, 1));
        Capes.addInput('C', Items.CLOTH).addInput( 'W', Blocks.WOOL, 12).create("cape_brown", new ItemStack(AetherItems.ARMOR_CAPE_BROWN, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 13).create("cape_green", new ItemStack(AetherItems.ARMOR_CAPE_GREEN, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 14).create("cape_red", new ItemStack(AetherItems.ARMOR_CAPE_RED, 1));
        Capes.addInput('C', Items.CLOTH).addInput('W', Blocks.WOOL, 15).create("cape_black", new ItemStack(AetherItems.ARMOR_CAPE_BLACK, 1));
    }
    public static void stairRecipes() {
        RecipeBuilderShaped templateStairs = new RecipeBuilderShaped(MOD_ID, "X ", "XX ", "XXX");
        templateStairs.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).create("holystone_stairs", new ItemStack(AetherBlocks.STAIRS_COBBLE_HOLYSTONE, 6));
        templateStairs.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_wooden_stairs", new ItemStack(AetherBlocks.STAIRS_PLANKS_SKYROOT, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_STONE).create("carved_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_STONE, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_ANGELIC).create("angelic_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_ANGELIC, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_HELLFIRE).create("hellfire_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_HELLFIRE, 6));
        templateStairs.addInput('X', AetherBlocks.BRICK_ZANITE).create("zanite_brick_stairs", new ItemStack(AetherBlocks.STAIRS_BRICK_ZANITE, 6));
        templateStairs.addInput('X', AetherBlocks.BRICK_HOLYSTONE).create("holystone_brick_stairs", new ItemStack(AetherBlocks.STAIRS_BRICK_HOLYSTONE, 6));

    }
    public static void aetherMachineRecipes() {
        RecipeBuilder.Shaped(MOD_ID, "HHH", "HZH", "HHH")
                .addInput('H', (AetherBlocks.COBBLE_HOLYSTONE))
                .addInput('Z', (AetherItems.ZANITE))
                .create("enchanter", new ItemStack(AetherBlocks.ENCHANTER_IDLE, 1));

        RecipeBuilder.Shaped(MOD_ID, "HHH", "HIH", "SSS")
                .addInput('H', (AetherBlocks.COBBLE_HOLYSTONE))
                .addInput('I', (AetherBlocks.ICESTONE))
                .addInput('S', (AetherBlocks.PLANKS_SKYROOT))
                .create("freezer", new ItemStack(AetherBlocks.FREEZER_IDLE, 1));

        RecipeBuilder.Shaped(MOD_ID, "HHH", "HTH", "HHH")
                .addInput('H', (AetherBlocks.COBBLE_HOLYSTONE))
                .addInput('T', (AetherBlocks.TORCH_AMBROSIUM))
                .create("incubator", new ItemStack(AetherBlocks.INCUBATOR_IDLE, 1));
    }
    public static void plankRecipes() {
        RecipeBuilder.Shaped(MOD_ID, "XXX", "XGX", "XXX")
                .addInput('X', "minecraft:planks")
                .addInput('G', "aether:gems")
                .create("jukebox", new ItemStack(Blocks.JUKEBOX, 1));

        RecipeBuilder.Shaped(MOD_ID, "X X", "XXX", "X X")
                .addInput('X', "aether:sticks")
                .create("ladder", new ItemStack(Blocks.LADDER_OAK, 2));

        RecipeBuilderShaped templateLogtoPlank = new RecipeBuilderShaped(MOD_ID, "X");
        templateLogtoPlank.addInput('X', AetherBlocks.LOG_SKYROOT).create("skyroot_log_to_skyroot_planks", new ItemStack(AetherBlocks.PLANKS_SKYROOT, 4));
        templateLogtoPlank.addInput('X', AetherBlocks.LOG_OAK_GOLDEN).create("golden_oak_log_to_yellow_wooden_planks", new ItemStack(Blocks.PLANKS_OAK_PAINTED, 4, 4));

        RecipeBuilder.Shaped(MOD_ID, "PSP", "PSP")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .addInput('S', AetherItems.STICK_SKYROOT)
                .create("skyroot_fence", new ItemStack(AetherBlocks.FENCE_PLANKS_SKYROOT, 6));

        RecipeBuilder.Shaped(MOD_ID, "SPS", "SPS")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .addInput('S', AetherItems.STICK_SKYROOT)
                .create("skyroot_fencegate", new ItemStack(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, 3));

        RecipeBuilder.Shaped(MOD_ID, "PX", "XP")
                .addInput('P', (Items.AMMO_PEBBLE))
                .addInput('X', (AetherBlocks.AERCLOUD_WHITE))
                .create("pebbles_to_holystone", new ItemStack(AetherBlocks.COBBLE_HOLYSTONE, 2));

        RecipeBuilder.Shaped(MOD_ID, "PP", "PP", "PP")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_door", new ItemStack(AetherItems.DOOR_SKYROOT, 2));

        RecipeBuilder.Shaped(MOD_ID, "PPP", "PPP")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_trapdoor", new ItemStack(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT, 6));

        RecipeBuilder.Shaped(MOD_ID, "P")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_button", new ItemStack(AetherBlocks.BUTTON_PLANKS_SKYROOT, 4));

        RecipeBuilder.Shaped(MOD_ID, "PP")
                .addInput('P', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_pressure_plate", new ItemStack(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT, 1));

        RecipeBuilder.Shaped(MOD_ID, "X", "X")
                .addInput('X', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_sticks", new ItemStack(AetherItems.STICK_SKYROOT, 4));

        RecipeBuilder.Shaped(MOD_ID, "X X", " X ")
                .addInput('X', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_bucket", new ItemStack(AetherItems.BUCKET_SKYROOT, 1));
    }
    public static void slabRecipes() {
        RecipeBuilderShaped templateSlab = new RecipeBuilderShaped(MOD_ID, "XXX");
        templateSlab.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).create("holystone_slab", new ItemStack(AetherBlocks.SLAB_COBBLE_HOLYSTONE, 6));
        templateSlab.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_wooden_slab", new ItemStack(AetherBlocks.SLAB_PLANKS_SKYROOT, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_STONE).create("carved_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_STONE, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_ANGELIC).create("angelic_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_ANGELIC, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_HELLFIRE).create("hellfire_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_HELLFIRE, 6));
        templateSlab.addInput('X', AetherBlocks.BRICK_ZANITE).create("zanite_brick_slab", new ItemStack(AetherBlocks.SLAB_BRICK_ZANITE, 6));
        templateSlab.addInput('X', AetherBlocks.BRICK_HOLYSTONE).create("holystone_brick_slab", new ItemStack(AetherBlocks.SLAB_BRICK_HOLYSTONE, 6));

    }
    public static void armorRecipes() {
        RecipeBuilderShaped Helmet = new RecipeBuilderShaped(MOD_ID, "XXX", "X X");
        Helmet.addInput('X', AetherItems.ZANITE).create("zanite_helmet", new ItemStack(AetherItems.ARMOR_HELMET_ZANITE, 1));
        Helmet.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_helmet", new ItemStack(AetherItems.ARMOR_HELMET_GRAVITITE, 1));

        RecipeBuilderShaped Chestplate = new RecipeBuilderShaped(MOD_ID, "X X", "XXX", "XXX");
        Chestplate.addInput('X', AetherItems.ZANITE).create("zanite_chestplate", new ItemStack(AetherItems.ARMOR_CHESTPLATE_ZANITE, 1));
        Chestplate.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_chestplate", new ItemStack(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, 1));

        RecipeBuilderShaped Leggings = new RecipeBuilderShaped(MOD_ID, "XXX", "X X", "X X");
        Leggings.addInput('X', AetherItems.ZANITE).create("zanite_leggings", new ItemStack(AetherItems.ARMOR_LEGGINGS_ZANITE, 1));
        Leggings.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_leggings", new ItemStack(AetherItems.ARMOR_LEGGINGS_GRAVITITE, 1));

        RecipeBuilderShaped Boots = new RecipeBuilderShaped(MOD_ID, "X X", "X X");
        Boots.addInput('X', AetherItems.ZANITE).create("zanite_boots", new ItemStack(AetherItems.ARMOR_BOOTS_ZANITE, 1));
        Boots.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_boots", new ItemStack(AetherItems.ARMOR_BOOTS_GRAVITITE, 1));
    }
    public static void toolsRecipes() {
        RecipeBuilderShaped Sword = new RecipeBuilderShaped(MOD_ID, " X ", " X ", " S ");
        Sword.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.STICK_SKYROOT).create("skyroot_sword", new ItemStack(AetherItems.TOOL_SWORD_SKYROOT, 1));
        Sword.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).addInput('S', AetherItems.STICK_SKYROOT).create("holystone_sword", new ItemStack(AetherItems.TOOL_SWORD_HOLYSTONE, 1));
        Sword.addInput('X', AetherItems.ZANITE).addInput('S', AetherItems.STICK_SKYROOT).create("zanite_sword", new ItemStack(AetherItems.TOOL_SWORD_ZANITE, 1));
        Sword.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', AetherItems.STICK_SKYROOT).create("gravitite_sword", new ItemStack(AetherItems.TOOL_SWORD_GRAVITITE, 1));

        RecipeBuilderShaped Pick = new RecipeBuilderShaped(MOD_ID, "XXX", " S ", " S ");
        Pick.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.STICK_SKYROOT).create("skyroot_pick", new ItemStack(AetherItems.TOOL_PICKAXE_SKYROOT, 1));
        Pick.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).addInput('S', AetherItems.STICK_SKYROOT).create("holystone_pick", new ItemStack(AetherItems.TOOL_PICKAXE_HOLYSTONE, 1));
        Pick.addInput('X', AetherItems.ZANITE).addInput('S', AetherItems.STICK_SKYROOT).create("zanite_pick", new ItemStack(AetherItems.TOOL_PICKAXE_ZANITE, 1));
        Pick.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', AetherItems.STICK_SKYROOT).create("gravitite_pick", new ItemStack(AetherItems.TOOL_PICKAXE_GRAVITITE, 1));

        RecipeBuilderShaped Shovel = new RecipeBuilderShaped(MOD_ID, " X ", " S ", " S ");
        Shovel.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.STICK_SKYROOT).create("skyroot_shovel", new ItemStack(AetherItems.TOOL_SHOVEL_SKYROOT, 1));
        Shovel.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).addInput('S', AetherItems.STICK_SKYROOT).create("holystone_shovel", new ItemStack(AetherItems.TOOL_SHOVEL_HOLYSTONE, 1));
        Shovel.addInput('X', AetherItems.ZANITE).addInput('S', AetherItems.STICK_SKYROOT).create("zanite_shovel", new ItemStack(AetherItems.TOOL_SHOVEL_ZANITE, 1));
        Shovel.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', AetherItems.STICK_SKYROOT).create("gravitite_shovel", new ItemStack(AetherItems.TOOL_SHOVEL_GRAVITITE, 1));

        RecipeBuilderShaped Axe = new RecipeBuilderShaped(MOD_ID, "XX ", "XS ", " S ");
        Axe.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.STICK_SKYROOT).create("skyroot_axe", new ItemStack(AetherItems.TOOL_AXE_SKYROOT, 1));
        Axe.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).addInput('S', AetherItems.STICK_SKYROOT).create("holystone_axe", new ItemStack(AetherItems.TOOL_AXE_HOLYSTONE, 1));
        Axe.addInput('X', AetherItems.ZANITE).addInput('S', AetherItems.STICK_SKYROOT).create("zanite_axe", new ItemStack(AetherItems.TOOL_AXE_ZANITE, 1));
        Axe.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', AetherItems.STICK_SKYROOT).create("gravitite_axe", new ItemStack(AetherItems.TOOL_AXE_GRAVITITE, 1));
    }
    public static void glovesRecipes() {
        RecipeBuilderShaped Gloves = new RecipeBuilderShaped(MOD_ID, "X X");
        Gloves.addInput('X', Items.LEATHER).create("leather_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_LEATHER, 1));
        Gloves.addInput('X', Items.INGOT_IRON).create("iron_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_IRON, 1));
        Gloves.addInput('X', Items.INGOT_GOLD).create("gold_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_GOLD, 1));
        Gloves.addInput('X', Items.DIAMOND).create("diamond_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_DIAMOND, 1));
        Gloves.addInput('X', Items.INGOT_STEEL).create("steel_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_STEEL, 1));
        Gloves.addInput('X', AetherItems.ZANITE).create("zanite_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_ZANITE, 1));
        Gloves.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_GRAVITITE, 1));
    }
    public static void pendantRecipes() {
        RecipeBuilderShaped Pendant = new RecipeBuilderShaped(MOD_ID, "SSS", "S S", " X ");
        Pendant.addInput('X', Items.LEATHER).addInput('S', Items.STRING).create("leather_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_LEATHER, 1));
        Pendant.addInput('X', Items.INGOT_IRON).addInput('S', Items.STRING).create("iron_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_IRON, 1));
        Pendant.addInput('X', Items.INGOT_GOLD).addInput('S', Items.STRING).create("gold_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_GOLD, 1));
        Pendant.addInput('X', Items.DIAMOND).addInput('S', Items.STRING).create("diamond_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_DIAMOND, 1));
        Pendant.addInput('X', Items.INGOT_STEEL).addInput('S', Items.STRING).create("steel_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_STEEL, 1));
        Pendant.addInput('X', AetherItems.ZANITE).addInput('S', Items.STRING).create("zanite_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_ZANITE, 1));
        Pendant.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', Items.STRING).create("gravitite_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_GRAVITITE, 1));
    }

    public static void furnaceRecipes(){
        RecipeBuilder.Furnace(MOD_ID)
                .setInput(AetherBlocks.COBBLE_HOLYSTONE)
                .create("cobble_holystone_to_holystone", AetherBlocks.HOLYSTONE.getDefaultStack());
    }

    public static void blastFurnaceRecipes() {
        RecipeBuilder.BlastFurnace(MOD_ID)
                .setInput(AetherBlocks.COBBLE_HOLYSTONE)
                .create("cobble_holystone_to_holystone", AetherBlocks.HOLYSTONE.getDefaultStack());
    }


    public static void trommelRecipes() {
        RecipeBuilder.Trommel(MOD_ID)
                .setInput("aether:dirts")
                .addEntry(new WeightedRandomLootObject(Items.AMMO_PEBBLE.getDefaultStack(), 1, 3), 60.24)
                .addEntry(new WeightedRandomLootObject(Items.CLAY.getDefaultStack(), 1, 5), 24.10)
                .addEntry(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 1, 3), 12.05)
                .addEntry(new WeightedRandomLootObject(Items.SULPHUR.getDefaultStack(), 1), 2.41)
                .addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1), 0.60)
                .addEntry(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(AetherItems.STICK_SKYROOT.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_dirt");

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.QUICKSOIL)
                .addEntry(new WeightedRandomLootObject(AetherItems.AMBROSIUM.getDefaultStack(), 1, 2), 36.76)
                .addEntry(new WeightedRandomLootObject(AetherItems.AMBER.getDefaultStack(), 4, 8), 22.06)
                .addEntry(new WeightedRandomLootObject(Items.AMMO_PEBBLE.getDefaultStack(), 1, 5), 18.38)
                .addEntry(new WeightedRandomLootObject(Items.SULPHUR.getDefaultStack(), 1), 3.68)
                .addEntry(new WeightedRandomLootObject(AetherItems.PETAL_AECHOR.getDefaultStack(), 1), 0.74)
                .addEntry(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 1, 3), 7.35)
                .addEntry(new WeightedRandomLootObject(AetherItems.STICK_SKYROOT.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_quicksoil");
    }
}
