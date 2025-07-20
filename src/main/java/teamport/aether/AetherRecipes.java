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
import teamport.aether.recipe.RecipeGroupAether;
import teamport.aether.recipe.RecipeGroupIncubator;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherRecipes implements RecipeEntrypoint {
    public static final RecipeNamespace AETHER = RecipeBuilder.getRecipeNamespace(MOD_ID);
    public static final RecipeGroupAether ENCHANTER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.ENCHANTER_ACTIVE.getDefaultStack())));
    public static final RecipeGroupAether FREEZER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.FREEZER_ACTIVE.getDefaultStack())));
    public static final RecipeGroupIncubator INCUBATOR = new RecipeGroupIncubator(new RecipeSymbol(new ItemStack(AetherBlocks.INCUBATOR_ACTIVE.getDefaultStack())));


    @Override
    public void onRecipesReady() {
        AETHER.register("enchanter", ENCHANTER);
        AETHER.register("freezer", FREEZER);
        AETHER.register("incubator", INCUBATOR);
        Registries.RECIPE_TYPES.register("aether:machine", RecipeEntryAetherMachine.class);
        Registries.RECIPE_TYPES.register("aether:incubator", RecipeEntryIncubator.class);
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/enchanter.json");
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/freezer.json");
        DataLoader.loadRecipesFromFile("/assets/aether/recipes/incubator.json");
        initializeRecipes();
    }

    @Override
    public void initNamespaces() {
        RecipeBuilder.initNameSpace(MOD_ID);
        RecipeBuilder.getRecipeNamespace(MOD_ID);


        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("jukebox");
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("ladder");

        // Recipe Groups
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.LOG_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.LOG_OAK_GOLDEN.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:planks").add(AetherBlocks.PLANKS_SKYROOT.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.LEAVES_SKYROOT.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.LEAVES_OAK_GOLDEN.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:cobblestones").add(AetherBlocks.COBBLE_HOLYSTONE.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:grasses").add(AetherBlocks.GRASS_AETHER.getDefaultStack());

        Registries.ITEM_GROUPS.register("aether:gems", Registries.stackListOf(AetherBlocks.BLOCK_GRAVITITE, Items.DIAMOND));
        Registries.ITEM_GROUPS.register("aether:sticks", Registries.stackListOf(AetherItems.STICK_SKYROOT, Items.STICK));

        Registries.ITEM_GROUPS.register("aether:ambrosium_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, 1)));
        Registries.ITEM_GROUPS.register("aether:zanite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_ZANITE_HOLYSTONE, 1)));
        Registries.ITEM_GROUPS.register("aether:gravitite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 1)));

        Registries.ITEM_GROUPS.register("aether:blue_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 11), new ItemStack(Blocks.WOOL, 1, 3), new ItemStack(Blocks.WOOL, 1, 9)));
        Registries.ITEM_GROUPS.register("aether:white_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 0), new ItemStack(Blocks.WOOL, 1, 8)));
        Registries.ITEM_GROUPS.register("aether:red_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 14), new ItemStack(Blocks.WOOL, 1, 6)));
        Registries.ITEM_GROUPS.register("aether:green_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 5), new ItemStack(Blocks.WOOL, 1, 13)));
        Registries.ITEM_GROUPS.register("aether:purple_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 2), new ItemStack(Blocks.WOOL, 1, 10)));
        Registries.ITEM_GROUPS.register("aether:orange_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 1), new ItemStack(Blocks.WOOL, 1, 4), new ItemStack(Blocks.WOOL, 1, 12)));
        Registries.ITEM_GROUPS.register("aether:black_wool", Registries.stackListOf(new ItemStack(Blocks.WOOL, 1, 7), new ItemStack(Blocks.WOOL, 1, 15)));

    }

    public void initializeRecipes() {

        // Crafting Recipes Blocks

        RecipeBuilderShaped jukebox = new RecipeBuilderShaped(MOD_ID, "XXX", "XGX", "XXX");
        jukebox.addInput('X', "minecraft:planks").addInput('G', "aether:gems").create("jukebox", new ItemStack(Blocks.JUKEBOX, 1));

        RecipeBuilderShaped sticks = new RecipeBuilderShaped(MOD_ID, "X X", "XXX", "X X");
        sticks.addInput('X', "aether:sticks").create("ladder", new ItemStack(Blocks.LADDER_OAK, 2));

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

        RecipeBuilderShaped templateItemtoBlock = new RecipeBuilderShaped(MOD_ID, "XXX", "XXX", "XXX");
        templateItemtoBlock.addInput('X', AetherItems.ZANITE).create("block_of_zanite", new ItemStack(AetherBlocks.BLOCK_ZANITE, 1));

        RecipeBuilderShaped templateItemtoFuelBlock = new RecipeBuilderShaped(MOD_ID, "XXX", "X X", "XXX");
        templateItemtoFuelBlock.addInput('X', AetherItems.AMBROSIUM).create("block_of_ambrosium", new ItemStack(AetherBlocks.BLOCK_AMBROSIUM, 1));
        templateItemtoFuelBlock.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_chest", new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT, 1));

        RecipeBuilderShaped templateBlocktoItem = new RecipeBuilderShaped(MOD_ID, "X");
        templateBlocktoItem.addInput('X', AetherBlocks.BLOCK_ZANITE).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.ZANITE, 9));
        templateBlocktoItem.addInput('X', AetherBlocks.BLOCK_AMBROSIUM).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.AMBROSIUM, 8));

        RecipeBuilderShaped templateFlowertoDye = new RecipeBuilderShaped(MOD_ID, "X");
        templateFlowertoDye.addInput('X', AetherBlocks.FLOWER_WHITE).create("flower_white_to_dye", new ItemStack(Items.DYE, 2, 7));
        templateFlowertoDye.addInput('X', AetherBlocks.FLOWER_PURPLE).create("flower_purple_to_dye", new ItemStack(Items.DYE, 2, 5));

        RecipeBuilderShaped templateBricks = new RecipeBuilderShaped(MOD_ID, "XX", "XX");
        templateBricks.addInput('X', AetherItems.ZANITE).create("zanite_bricks", new ItemStack(AetherBlocks.BRICK_ZANITE, 4));

        RecipeBuilder.Shaped(MOD_ID, "PP", "PP", "PP")
                .addInput('P', AetherBlocks.GLASS_QUICKSOIL)
                .create("quicksoil_glass_door", new ItemStack(AetherItems.DOOR_GLASS_AMBROSIUM, 2));

        RecipeBuilder.Shaped(MOD_ID, "PPP", "PPP")
                .addInput('P', AetherBlocks.GLASS_QUICKSOIL)
                .create("quicksoil_glass_trapdoor", new ItemStack(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, 6));


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


        RecipeBuilderShaped templateStacked = new RecipeBuilderShaped(MOD_ID, "X", "S");
        templateStacked.addInput('X', AetherItems.AMBROSIUM).addInput('S', AetherItems.STICK_SKYROOT).create("ambrosium_torch", new ItemStack(AetherBlocks.TORCH_AMBROSIUM, 2));

        RecipeBuilderShaped templateSlab = new RecipeBuilderShaped(MOD_ID, "XXX");
        templateSlab.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).create("holystone_slab", new ItemStack(AetherBlocks.SLAB_COBBLE_HOLYSTONE, 6));
        templateSlab.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_wooden_slab", new ItemStack(AetherBlocks.SLAB_PLANKS_SKYROOT, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_STONE).create("carved_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_STONE, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_ANGELIC).create("angelic_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_ANGELIC, 6));
        templateSlab.addInput('X', AetherBlocks.CARVED_HELLFIRE).create("hellfire_stone_slab", new ItemStack(AetherBlocks.SLAB_CARVED_HELLFIRE, 6));
        templateSlab.addInput('X', AetherBlocks.BRICK_ZANITE).create("zanite_brick_slab", new ItemStack(AetherBlocks.SLAB_BRICK_ZANITE, 6));

        RecipeBuilderShaped templateStairs = new RecipeBuilderShaped(MOD_ID, "X ", "XX ", "XXX");
        templateStairs.addInput('X', AetherBlocks.COBBLE_HOLYSTONE).create("holystone_stairs", new ItemStack(AetherBlocks.STAIRS_COBBLE_HOLYSTONE, 6));
        templateStairs.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_wooden_stairs", new ItemStack(AetherBlocks.STAIRS_PLANKS_SKYROOT, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_STONE).create("carved_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_STONE, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_ANGELIC).create("angelic_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_ANGELIC, 6));
        templateStairs.addInput('X', AetherBlocks.CARVED_HELLFIRE).create("hellfire_stone_stairs", new ItemStack(AetherBlocks.STAIRS_CARVED_HELLFIRE, 6));
        templateStairs.addInput('X', AetherBlocks.BRICK_ZANITE).create("zanite_brick_stairs", new ItemStack(AetherBlocks.STAIRS_BRICK_ZANITE, 6));


        // Crafting Recipes Items

        RecipeBuilderShaped templateStack = new RecipeBuilderShaped(MOD_ID, "X", "X");
        templateStack.addInput('X', AetherBlocks.PLANKS_SKYROOT).create("skyroot_sticks", new ItemStack(AetherItems.STICK_SKYROOT, 4));

        RecipeBuilder.Shaped(MOD_ID, "X  ", " X ", "  X")
                .addInput('X', AetherBlocks.PLANKS_SKYROOT)
                .create("skyroot_bucket", new ItemStack(AetherItems.BUCKET_SKYROOT, 1));

        RecipeBuilder.Shaped(MOD_ID, " C ", "BSB", " M ")
                .addInput('C', Items.FOOD_CHERRY)
                .addInput('B', new ItemStack(Items.DYE, 1, 3))
                .addInput('S', Items.AMMO_SNOWBALL)
                .addInput('M', AetherItems.BUCKET_SKYROOT_MILK)
                .create("skyroot_bucket_icecream", new ItemStack(AetherItems.BUCKET_SKYROOT_ICECREAM, 1));

        RecipeBuilderShaped Capes = new RecipeBuilderShaped(MOD_ID, "WW", "WW", "WW");
        Capes.addInput('W', "aether:white_wool").create("cape_white", new ItemStack(AetherItems.ARMOR_CAPE_WHITE, 1));
        Capes.addInput('W', "aether:blue_wool").create("cape_blue", new ItemStack(AetherItems.ARMOR_CAPE_BLUE, 1));
        Capes.addInput('W', "aether:orange_wool").create("cape_yellow", new ItemStack(AetherItems.ARMOR_CAPE_YELLOW, 1));
        Capes.addInput('W', "aether:red_wool").create("cape_red", new ItemStack(AetherItems.ARMOR_CAPE_RED, 1));

        RecipeBuilderShaped Shooter = new RecipeBuilderShaped(MOD_ID, " X ", " X ", " S ");
        Shooter.addInput('X', AetherBlocks.PLANKS_SKYROOT).addInput('S', AetherItems.ZANITE).create("dart_shooter", new ItemStack(AetherItems.TOOL_SHOOTER, 1));

        RecipeBuilder.Shaped(MOD_ID, " A ", " S ", " F ")
                .addInput('S', Items.STICK)
                .addInput('A', AetherItems.AMBER)
                .addInput('F', Items.FEATHER_CHICKEN)
                .create("dart_golden", new ItemStack(AetherItems.AMMO_DART_GOLDEN, 4));

        RecipeBuilder.Shaped(MOD_ID, " D ", "DPD", " D ")
                .addInput('D', AetherItems.AMMO_DART_GOLDEN)
                .addInput('P', AetherItems.PETAL_AECHOR)
                .create("dart_poison", new ItemStack(AetherItems.AMMO_DART_POISON, 4));

        RecipeBuilder.Shaped(MOD_ID, "Z", "S")
                .addInput('S', Items.STICK)
                .addInput('Z', AetherItems.ZANITE)
                .create("nature_staff", new ItemStack(AetherItems.TOOL_STAFF_NATURE, 1));

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

        RecipeBuilderShaped Gloves = new RecipeBuilderShaped(MOD_ID, "X X");
        Gloves.addInput('X', Items.LEATHER).create("leather_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_LEATHER, 1));
        Gloves.addInput('X', Items.INGOT_IRON).create("iron_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_IRON, 1));
        Gloves.addInput('X', Items.INGOT_GOLD).create("gold_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_GOLD, 1));
        Gloves.addInput('X', Items.DIAMOND).create("diamond_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_DIAMOND, 1));
        Gloves.addInput('X', Items.INGOT_STEEL).create("steel_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_STEEL, 1));
        Gloves.addInput('X', AetherItems.ZANITE).create("zanite_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_ZANITE, 1));
        Gloves.addInput('X', AetherBlocks.BLOCK_GRAVITITE).create("gravitite_gloves", new ItemStack(AetherItems.ARMOR_GLOVES_GRAVITITE, 1));

        RecipeBuilderShaped Pendant = new RecipeBuilderShaped(MOD_ID, "SSS", "S S", " X ");
        Pendant.addInput('X', Items.LEATHER).addInput('S', Items.STRING).create("leather_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_LEATHER, 1));
        Pendant.addInput('X', Items.INGOT_IRON).addInput('S', Items.STRING).create("iron_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_IRON, 1));
        Pendant.addInput('X', Items.INGOT_GOLD).addInput('S', Items.STRING).create("gold_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_GOLD, 1));
        Pendant.addInput('X', Items.DIAMOND).addInput('S', Items.STRING).create("diamond_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_DIAMOND, 1));
        Pendant.addInput('X', Items.INGOT_STEEL).addInput('S', Items.STRING).create("steel_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_STEEL, 1));
        Pendant.addInput('X', AetherItems.ZANITE).addInput('S', Items.STRING).create("zanite_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_ZANITE, 1));
        Pendant.addInput('X', AetherBlocks.BLOCK_GRAVITITE).addInput('S', Items.STRING).create("gravitite_pendant", new ItemStack(AetherItems.ARMOR_TALISMAN_GRAVITITE, 1));

        RecipeBuilderShaped Clouds = new RecipeBuilderShaped(MOD_ID, "XX", "XX");
        Clouds.addInput('X', AetherBlocks.AERCLOUD_WHITE).create("cloud_parachute", new ItemStack(AetherItems.PARACHUTE_CLOUD, 1));
        Clouds.addInput('X', AetherBlocks.AERCLOUD_GOLD).create("cloud_parachute_gold", new ItemStack(AetherItems.PARACHUTE_CLOUD_GOLD, 1));

        RecipeBuilder.Shaped(MOD_ID, "Z", "S")
                .addInput('S', Items.STICK)
                .addInput('Z', AetherItems.ZANITE)
                .create("nature_staff", new ItemStack(AetherItems.TOOL_STAFF_NATURE, 1));

        // Furnace Recipes

        RecipeBuilder.Furnace(MOD_ID)
                .setInput(AetherBlocks.COBBLE_HOLYSTONE)
                .create("cobble_holystone_to_holystone", AetherBlocks.HOLYSTONE.getDefaultStack());

        RecipeBuilder.BlastFurnace(MOD_ID)
                .setInput(AetherBlocks.COBBLE_HOLYSTONE)
                .create("cobble_holystone_to_holystone", AetherBlocks.HOLYSTONE.getDefaultStack());

        // Trommel Recipes

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.GRASS_AETHER)
                .addEntry(new WeightedRandomLootObject(Items.AMMO_PEBBLE.getDefaultStack(), 1, 3), 60.24)
                .addEntry(new WeightedRandomLootObject(Items.CLAY.getDefaultStack(), 1, 5), 24.10)
                .addEntry(new WeightedRandomLootObject(Items.FLINT.getDefaultStack(), 1, 3), 12.05)
                .addEntry(new WeightedRandomLootObject(Items.SULPHUR.getDefaultStack(), 1), 2.41)
                .addEntry(new WeightedRandomLootObject(AetherItems.ZANITE.getDefaultStack(), 1), 0.60)
                .addEntry(new WeightedRandomLootObject(Items.BONE.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(AetherItems.STICK_SKYROOT.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_grass");

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.DIRT_AETHER)
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
