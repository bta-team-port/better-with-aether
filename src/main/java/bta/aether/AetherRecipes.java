package bta.aether;

import bta.aether.block.AetherBlocks;
import bta.aether.item.AetherItems;
import bta.aether.recipe.RecipeEntryAetherMachine;
import bta.aether.recipe.RecipeGroupAether;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static bta.aether.Aether.MOD_ID;

public class AetherRecipes implements RecipeEntrypoint {
    public static final RecipeNamespace AETHER = new RecipeNamespace();
    public static final RecipeGroupAether ENCHANTER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.enchanter)));
    public static final RecipeGroupAether FREEZER = new RecipeGroupAether(new RecipeSymbol(new ItemStack(AetherBlocks.freezer)));

    @Override
    public void onRecipesReady() {
        AETHER.register("enchanter",ENCHANTER);
        AETHER.register("freezer",FREEZER);
        Registries.RECIPES.register("aether", AETHER);
        Registries.RECIPE_TYPES.register("aether:machine",RecipeEntryAetherMachine.class);

        DataLoader.loadRecipes("/assets/aether/recipes/enchanter.json");
        DataLoader.loadRecipes("/assets/aether/recipes/freezer.json");
    }

    public void initializeRecipes() {

        // Recipe Groups
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logSkyroot.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:logs").add(AetherBlocks.logOakGolden.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:planks").add(AetherBlocks.planksSkyroot.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.leavesSkyroot.getDefaultStack());
        Registries.ITEM_GROUPS.getItem("minecraft:leaves").add(AetherBlocks.leavesOakGolden.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:cobblestones").add(AetherBlocks.holystone.getDefaultStack());

        Registries.ITEM_GROUPS.getItem("minecraft:grasses").add(AetherBlocks.grassAether.getDefaultStack());

        Registries.ITEM_GROUPS.register("aether:gems", Registries.stackListOf(AetherBlocks.gravititeEnchanted, Item.diamond));
        Registries.ITEM_GROUPS.register("aether:sticks", Registries.stackListOf(AetherItems.stickSkyroot, Item.stick));

        Registries.ITEM_GROUPS.register("aether:ambrosium_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreAmbrosiumHolystone, 1)));
        Registries.ITEM_GROUPS.register("aether:zanite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreZaniteHolystone, 1)));
        Registries.ITEM_GROUPS.register("aether:gravitite_ores", Registries.stackListOf(new ItemStack(AetherBlocks.oreGravititeHolystone, 1)));

        Registries.ITEM_GROUPS.register("aether:blue_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 11), new ItemStack(Block.wool, 1, 3), new ItemStack(Block.wool, 1, 9)));
        Registries.ITEM_GROUPS.register("aether:white_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 0), new ItemStack(Block.wool, 1, 8)));
        Registries.ITEM_GROUPS.register("aether:red_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 14), new ItemStack(Block.wool, 1, 6)));
        Registries.ITEM_GROUPS.register("aether:green_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 5), new ItemStack(Block.wool, 1, 13)));
        Registries.ITEM_GROUPS.register("aether:purple_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 2), new ItemStack(Block.wool, 1, 10)));
        Registries.ITEM_GROUPS.register("aether:orange_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 1), new ItemStack(Block.wool, 1, 4), new ItemStack(Block.wool, 1, 12)));
        Registries.ITEM_GROUPS.register("aether:black_wool", Registries.stackListOf(new ItemStack(Block.wool, 1, 7), new ItemStack(Block.wool, 1, 15)));


        // Crafting Recipes Blocks

        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("jukebox");
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("ladder");

        RecipeBuilderShaped jukebox = new RecipeBuilderShaped(MOD_ID, "XXX", "XGX", "XXX");
        jukebox.addInput('X', "minecraft:planks").addInput('G', "aether:gems").create("ladder", new ItemStack(Block.jukebox, 1));

        RecipeBuilderShaped sticks = new RecipeBuilderShaped(MOD_ID, "X X", "XXX", "X X");
        sticks.addInput('X', "aether:sticks").create("jukebox", new ItemStack(Block.ladderOak, 2));

        RecipeBuilderShaped templateLogtoPlank = new RecipeBuilderShaped(MOD_ID, "X");
        templateLogtoPlank.addInput('X', AetherBlocks.logSkyroot).create("skyroot_log_to_skyroot_planks", new ItemStack(AetherBlocks.planksSkyroot, 4, 14));
        templateLogtoPlank.addInput('X', AetherBlocks.logOakGolden).create("golden_oak_log_to_yellow_wooden_planks", new ItemStack(Block.planksOakPainted, 4, 4));

        RecipeBuilder.Shaped(MOD_ID, "PSP", "PSP")
                .addInput('P', AetherBlocks.planksSkyroot)
                .addInput('S', AetherItems.stickSkyroot)
                .create("skyroot_fence", new ItemStack(AetherBlocks.fencePlanksSkyroot, 6));

        RecipeBuilder.Shaped(MOD_ID, "SPS", "SPS")
                .addInput('P', AetherBlocks.planksSkyroot)
                .addInput('S', AetherItems.stickSkyroot)
                .create("skyroot_fencegate", new ItemStack(AetherBlocks.fenceGatePlanksSkyroot, 3));

        RecipeBuilder.Shaped(MOD_ID, "PX", "XP")
                .addInput('P', (Item.ammoPebble))
                .addInput('X', (AetherBlocks.aercloudWhite))
                .create("pebbles_to_holystone", new ItemStack(AetherBlocks.holystone, 2));

        RecipeBuilderShaped templateItemtoBlock = new RecipeBuilderShaped(MOD_ID, "XXX", "XXX", "XXX");
        templateItemtoBlock.addInput('X', AetherItems.gemZanite).create("block_of_zanite", new ItemStack(AetherBlocks.blockZanite, 1));

        RecipeBuilderShaped templateItemtoFuelBlock = new RecipeBuilderShaped(MOD_ID, "XXX", "X X", "XXX");
        templateItemtoFuelBlock.addInput('X', AetherItems.ambrosium).create("block_of_ambrosium", new ItemStack(AetherBlocks.blockAmbrosium, 1));
        templateItemtoFuelBlock.addInput('X', AetherBlocks.planksSkyroot).create("skyroot_chest", new ItemStack(AetherBlocks.chestSkyroot, 1));

        RecipeBuilderShaped templateBlocktoItem = new RecipeBuilderShaped(MOD_ID, "X");
        templateBlocktoItem.addInput('X', AetherBlocks.blockZanite).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.gemZanite, 9));
        templateBlocktoItem.addInput('X', AetherBlocks.blockAmbrosium).create("block_of_zanite_to_zanite", new ItemStack(AetherItems.ambrosium, 8));

        RecipeBuilderShaped templateFlowertoDye = new RecipeBuilderShaped(MOD_ID, "X");
        templateFlowertoDye.addInput('X', AetherBlocks.flowerWhite).create("flower_white_to_dye", new ItemStack(Item.dye, 2, 7));
        templateFlowertoDye.addInput('X', AetherBlocks.flowerPurple).create("flower_purple_to_dye", new ItemStack(Item.dye, 2, 5));

        RecipeBuilderShaped templateBricks = new RecipeBuilderShaped(MOD_ID, "XX", "XX");
//        templateBricks.addInput('X', AetherItems.gemZanite).create("zanite_bricks", new ItemStack(AetherBlocks.brickZanite, 4));
//        templateBricks.addInput('X', AetherBlocks.gravititeEnchanted).create("zanite_bricks", new ItemStack(AetherBlocks.brickGravitite, 4));


        RecipeBuilder.Shaped(MOD_ID, "HHH", "HZH", "HHH")
                .addInput('H', (AetherBlocks.holystone))
                .addInput('Z', (AetherItems.gemZanite))
                .create("enchanter", new ItemStack(AetherBlocks.enchanter, 1));

        RecipeBuilder.Shaped(MOD_ID, "HHH", "HIH", "SSS")
                .addInput('H', (AetherBlocks.holystone))
                .addInput('I', (AetherBlocks.icestone))
                .addInput('S', (AetherBlocks.planksSkyroot))
                .create("freezer", new ItemStack(AetherBlocks.freezer, 1));

        RecipeBuilder.Shaped(MOD_ID, "HHH", "HTH", "HHH")
                .addInput('H', (AetherBlocks.holystone))
                .addInput('T', (AetherBlocks.torchAmbrosium))
                .create("incubator", new ItemStack(AetherBlocks.incubator, 1));


        RecipeBuilderShaped templateStacked = new RecipeBuilderShaped(MOD_ID, "X", "S");
        templateStacked.addInput('X', AetherItems.ambrosium).addInput('S', AetherItems.stickSkyroot).create("ambrosium_torch", new ItemStack(AetherBlocks.torchAmbrosium, 2));

        RecipeBuilderShaped templateSlab = new RecipeBuilderShaped(MOD_ID, "XXX");
        templateSlab.addInput('X', AetherBlocks.holystone).create("holystone_slab", new ItemStack(AetherBlocks.slabHolystone, 6));
        templateSlab.addInput('X', AetherBlocks.planksSkyroot).create("skyroot_wooden_slab", new ItemStack(AetherBlocks.slabPlanksSkyroot, 6));
        templateSlab.addInput('X', AetherBlocks.stoneCarved).create("carved_stone_slab", new ItemStack(AetherBlocks.slabStoneCarved, 6));
        templateSlab.addInput('X', AetherBlocks.stoneAngelic).create("angelic_stone_slab", new ItemStack(AetherBlocks.slabStoneAngelic, 6));
        templateSlab.addInput('X', AetherBlocks.stoneHellfire).create("hellfire_stone_slab", new ItemStack(AetherBlocks.slabStoneHellfire, 6));

        RecipeBuilderShaped templateStairs = new RecipeBuilderShaped(MOD_ID, "X ", "XX ", "XXX");
        templateStairs.addInput('X', AetherBlocks.holystone).create("holystone_stairs", new ItemStack(AetherBlocks.stairsHolystone, 6));
        templateStairs.addInput('X', AetherBlocks.planksSkyroot).create("skyroot_wooden_stairs", new ItemStack(AetherBlocks.stairsPlanksSkyroot, 6));
        templateStairs.addInput('X', AetherBlocks.stoneCarved).create("carved_stone_stairs", new ItemStack(AetherBlocks.stairsStoneCarved, 6));
        templateStairs.addInput('X', AetherBlocks.stoneAngelic).create("angelic_stone_stairs", new ItemStack(AetherBlocks.stairsStoneAngelic, 6));
        templateStairs.addInput('X', AetherBlocks.stoneHellfire).create("hellfire_stone_stairs", new ItemStack(AetherBlocks.stairsStoneHellfire, 6));




        // Crafting Recipes Items

        RecipeBuilderShaped templateStack = new RecipeBuilderShaped(MOD_ID, "X", "X");
        templateStack.addInput('X', AetherBlocks.planksSkyroot).create("skyroot_sticks", new ItemStack(AetherItems.stickSkyroot, 4));

        RecipeBuilder.Shaped(MOD_ID, "X  ", " X ", "  X")
                .addInput('X', AetherBlocks.planksSkyroot)
                .create("skyroot_bucket", new ItemStack(AetherItems.bucketSkyroot, 1));

        RecipeBuilder.Shaped(MOD_ID, " C ", "BSB", " M ")
                .addInput('C', Item.cherry)
                .addInput('B', new ItemStack(Item.dye, 1, 3))
                .addInput('S', Item.ammoSnowball)
                .addInput('M', AetherItems.bucketSkyrootMilk)
                .create("skyroot_bucket_icecream", new ItemStack(AetherItems.bucketSkyrootIcecream, 1));

        RecipeBuilderShaped Capes = new RecipeBuilderShaped(MOD_ID, "WW", "WW", "WW");
        Capes.addInput('W', "aether:white_wool").create("cape_white", new ItemStack(AetherItems.armorCapeWhite, 1));
        Capes.addInput('W', "aether:blue_wool").create("cape_blue", new ItemStack(AetherItems.armorCapeBlue, 1));
        Capes.addInput('W', "aether:orange_wool").create("cape_yellow", new ItemStack(AetherItems.armorCapeYellow, 1));
        Capes.addInput('W', "aether:red_wool").create("cape_red", new ItemStack(AetherItems.armorCapeRed, 1));

        RecipeBuilderShaped Shooter = new RecipeBuilderShaped(MOD_ID, " X ", " X ", " S ");
        Shooter.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.gemZanite).create("dart_shooter", new ItemStack(AetherItems.dartShooter, 1));
        Shooter.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.petalAechor).create("dart_shooter_poison", new ItemStack(AetherItems.dartShooterPoison, 1));

        RecipeBuilder.Shaped(MOD_ID, " A ", " S ", " F ")
                .addInput('S', Item.stick)
                .addInput('A', AetherItems.amberGolden)
                .addInput('F', Item.featherChicken)
                .create("dart_golden", new ItemStack(AetherItems.dartGolden, 4));

        RecipeBuilder.Shaped(MOD_ID, " D ", "DPD", " D ")
                .addInput('D', AetherItems.dartGolden)
                .addInput('P', AetherItems.bucketSkyrootPoison)
                .create("dart_poison", new ItemStack(AetherItems.dartPoison, 4));

        RecipeBuilder.Shaped(MOD_ID, "Z", "S")
                .addInput('S', Item.stick)
                .addInput('Z', AetherItems.gemZanite)
                .create("nature_staff", new ItemStack(AetherItems.toolStaffNature, 1));

        RecipeBuilderShaped Sword = new RecipeBuilderShaped(MOD_ID, " X ", " X ", " S ");
        Sword.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.stickSkyroot).create("skyroot_sword", new ItemStack(AetherItems.toolSwordSkyroot, 1));
        Sword.addInput('X', AetherBlocks.holystone).addInput('S', AetherItems.stickSkyroot).create("holystone_sword", new ItemStack(AetherItems.toolSwordHolystone, 1));
        Sword.addInput('X', AetherItems.gemZanite).addInput('S', AetherItems.stickSkyroot).create("zanite_sword", new ItemStack(AetherItems.toolSwordZanite, 1));
        Sword.addInput('X', AetherBlocks.gravititeEnchanted).addInput('S', AetherItems.stickSkyroot).create("gravitite_sword", new ItemStack(AetherItems.toolSwordGravitite, 1));

        RecipeBuilderShaped Pick = new RecipeBuilderShaped(MOD_ID, "XXX", " S ", " S ");
        Pick.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.stickSkyroot).create("skyroot_pick", new ItemStack(AetherItems.toolPickaxeSkyroot, 1));
        Pick.addInput('X', AetherBlocks.holystone).addInput('S', AetherItems.stickSkyroot).create("holystone_pick", new ItemStack(AetherItems.toolPickaxeHolystone, 1));
        Pick.addInput('X', AetherItems.gemZanite).addInput('S', AetherItems.stickSkyroot).create("zanite_pick", new ItemStack(AetherItems.toolPickaxeZanite, 1));
        Pick.addInput('X', AetherBlocks.gravititeEnchanted).addInput('S', AetherItems.stickSkyroot).create("gravitite_pick", new ItemStack(AetherItems.toolPickaxeGravitite, 1));

        RecipeBuilderShaped Shovel = new RecipeBuilderShaped(MOD_ID, " X ", " S ", " S ");
        Shovel.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.stickSkyroot).create("skyroot_shovel", new ItemStack(AetherItems.toolShovelSkyroot, 1));
        Shovel.addInput('X', AetherBlocks.holystone).addInput('S', AetherItems.stickSkyroot).create("holystone_shovel", new ItemStack(AetherItems.toolShovelHolystone, 1));
        Shovel.addInput('X', AetherItems.gemZanite).addInput('S', AetherItems.stickSkyroot).create("zanite_shovel", new ItemStack(AetherItems.toolShovelZanite, 1));
        Shovel.addInput('X', AetherBlocks.gravititeEnchanted).addInput('S', AetherItems.stickSkyroot).create("gravitite_shovel", new ItemStack(AetherItems.toolShovelGravitite, 1));

        RecipeBuilderShaped Axe = new RecipeBuilderShaped(MOD_ID, "XX ", "XS ", " S ");
        Axe.addInput('X', AetherBlocks.planksSkyroot).addInput('S', AetherItems.stickSkyroot).create("skyroot_axe", new ItemStack(AetherItems.toolAxeSkyroot, 1));
        Axe.addInput('X', AetherBlocks.holystone).addInput('S', AetherItems.stickSkyroot).create("holystone_axe", new ItemStack(AetherItems.toolAxeHolystone, 1));
        Axe.addInput('X', AetherItems.gemZanite).addInput('S', AetherItems.stickSkyroot).create("zanite_axe", new ItemStack(AetherItems.toolAxeZanite, 1));
        Axe.addInput('X', AetherBlocks.gravititeEnchanted).addInput('S', AetherItems.stickSkyroot).create("gravitite_axe", new ItemStack(AetherItems.toolAxeGravitite, 1));

        RecipeBuilderShaped Helmet = new RecipeBuilderShaped(MOD_ID, "XXX", "X X");
        Helmet.addInput('X', AetherItems.gemZanite).create("zanite_helmet", new ItemStack(AetherItems.armorHelmetZanite, 1));
        Helmet.addInput('X', AetherBlocks.gravititeEnchanted).create("gravitite_helmet", new ItemStack(AetherItems.armorHelmetGravitite, 1));

        RecipeBuilderShaped Chestplate = new RecipeBuilderShaped(MOD_ID, "X X", "XXX", "XXX");
        Chestplate.addInput('X', AetherItems.gemZanite).create("zanite_chestplate", new ItemStack(AetherItems.armorChestplateZanite, 1));
        Chestplate.addInput('X', AetherBlocks.gravititeEnchanted).create("gravitite_chestplate", new ItemStack(AetherItems.armorChestplateGravitite, 1));

        RecipeBuilderShaped Leggings = new RecipeBuilderShaped(MOD_ID, "XXX", "X X", "X X");
        Leggings.addInput('X', AetherItems.gemZanite).create("zanite_leggings", new ItemStack(AetherItems.armorLeggingsZanite, 1));
        Leggings.addInput('X', AetherBlocks.gravititeEnchanted).create("gravitite_leggings", new ItemStack(AetherItems.armorLeggingsGravitite, 1));

        RecipeBuilderShaped Boots = new RecipeBuilderShaped(MOD_ID, "X X", "X X");
        Boots.addInput('X', AetherItems.gemZanite).create("zanite_boots", new ItemStack(AetherItems.armorBootsZanite, 1));
        Boots.addInput('X', AetherBlocks.gravititeEnchanted).create("gravitite_boots", new ItemStack(AetherItems.armorBootsGravitite, 1));

        RecipeBuilderShaped Gloves = new RecipeBuilderShaped(MOD_ID, "X X");
        Gloves.addInput('X', Item.leather).create("leather_gloves", new ItemStack(AetherItems.armorGlovesLeather, 1));
        Gloves.addInput('X', Item.ingotIron).create("iron_gloves", new ItemStack(AetherItems.armorGlovesIron, 1));
        Gloves.addInput('X', Item.ingotGold).create("gold_gloves", new ItemStack(AetherItems.armorGlovesGold, 1));
        Gloves.addInput('X', Item.diamond).create("diamond_gloves", new ItemStack(AetherItems.armorGlovesDiamond, 1));
        Gloves.addInput('X', Item.ingotSteel).create("steel_gloves", new ItemStack(AetherItems.armorGlovesSteel, 1));
        Gloves.addInput('X', AetherItems.gemZanite).create("zanite_gloves", new ItemStack(AetherItems.armorGlovesZanite, 1));
        Gloves.addInput('X', AetherBlocks.gravititeEnchanted).create("gravitite_gloves", new ItemStack(AetherItems.armorGlovesGravitite, 1));

        RecipeBuilderShaped Pendant = new RecipeBuilderShaped(MOD_ID, "SSS", "S S", " X ");
        Pendant.addInput('X', Item.leather).addInput('S', Item.string).create("leather_pendant", new ItemStack(AetherItems.armorPendantLeather, 1));
        Pendant.addInput('X', Item.ingotIron).addInput('S', Item.string).create("iron_pendant", new ItemStack(AetherItems.armorPendantIron, 1));
        Pendant.addInput('X', Item.ingotGold).addInput('S', Item.string).create("gold_pendant", new ItemStack(AetherItems.armorPendantGold, 1));
        Pendant.addInput('X', Item.diamond).addInput('S', Item.string).create("diamond_pendant", new ItemStack(AetherItems.armorPendantDiamond, 1));
        Pendant.addInput('X', Item.ingotSteel).addInput('S', Item.string).create("steel_pendant", new ItemStack(AetherItems.armorPendantSteel, 1));
        Pendant.addInput('X', AetherItems.gemZanite).addInput('S', Item.string).create("zanite_pendant", new ItemStack(AetherItems.armorPendantZanite, 1));
        Pendant.addInput('X', AetherBlocks.gravititeEnchanted).addInput('S', Item.string).create("gravitite_pendant", new ItemStack(AetherItems.armorPendantGravitite, 1));

        RecipeBuilderShaped Rings = new RecipeBuilderShaped(MOD_ID, " X ", "X X", " X ");
        Rings.addInput('X', AetherItems.gemZanite).create("zanite_ring", new ItemStack(AetherItems.armorRingZanite, 1));
        Rings.addInput('X', Item.ingotIron).create("iron_ring", new ItemStack(AetherItems.armorRingIron, 1));
        Rings.addInput('X', Item.ingotGold).create("gold_ring", new ItemStack(AetherItems.armorRingGold, 1));

        RecipeBuilderShaped Clouds = new RecipeBuilderShaped(MOD_ID, "XX", "XX");
        Clouds.addInput('X', AetherBlocks.aercloudWhite).create("cloud_parachute", new ItemStack(AetherItems.cloudParachute, 1));
        Clouds.addInput('X', AetherBlocks.aercloudGold).create("cloud_parachute_gold", new ItemStack(AetherItems.cloudParachuteGold, 1));

        RecipeBuilder.Shaped(MOD_ID, "Z", "S")
                .addInput('S', Item.stick)
                .addInput('Z', AetherItems.gemZanite)
                .create("nature_staff", new ItemStack(AetherItems.toolStaffNature, 1));

        // Furnace Recipes



        // Trommel Recipes

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.grassAether)
                .addEntry(new WeightedRandomLootObject(Item.ammoPebble.getDefaultStack(), 1, 3), 60.24)
                .addEntry(new WeightedRandomLootObject(Item.clay.getDefaultStack(), 1, 5), 24.10)
                .addEntry(new WeightedRandomLootObject(Item.flint.getDefaultStack(), 1, 3), 12.05)
                .addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 1), 2.41)
                .addEntry(new WeightedRandomLootObject(AetherItems.gemZanite.getDefaultStack(), 1), 0.60)
                .addEntry(new WeightedRandomLootObject(Item.bone.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(AetherItems.stickSkyroot.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_grass");

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.dirtAether)
                .addEntry(new WeightedRandomLootObject(Item.ammoPebble.getDefaultStack(), 1, 3), 60.24)
                .addEntry(new WeightedRandomLootObject(Item.clay.getDefaultStack(), 1, 5), 24.10)
                .addEntry(new WeightedRandomLootObject(Item.flint.getDefaultStack(), 1, 3), 12.05)
                .addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 1), 2.41)
                .addEntry(new WeightedRandomLootObject(AetherItems.gemZanite.getDefaultStack(), 1), 0.60)
                .addEntry(new WeightedRandomLootObject(Item.bone.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(AetherItems.stickSkyroot.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_dirt");

        RecipeBuilder.Trommel(MOD_ID)
                .setInput(AetherBlocks.quicksoil)
                .addEntry(new WeightedRandomLootObject(AetherItems.ambrosium.getDefaultStack(), 1, 2), 36.76)
                .addEntry(new WeightedRandomLootObject(AetherItems.amberGolden.getDefaultStack(), 4, 8), 22.06)
                .addEntry(new WeightedRandomLootObject(Item.ammoPebble.getDefaultStack(), 1, 5), 18.38)
                .addEntry(new WeightedRandomLootObject(Item.sulphur.getDefaultStack(), 1), 3.68)
                .addEntry(new WeightedRandomLootObject(AetherItems.petalAechor.getDefaultStack(), 1), 0.74)
                .addEntry(new WeightedRandomLootObject(Item.bone.getDefaultStack(), 1, 3), 7.35)
                .addEntry(new WeightedRandomLootObject(AetherItems.stickSkyroot.getDefaultStack(), 1), 0.30)
                .addEntry(new WeightedRandomLootObject(Item.flint.getDefaultStack(), 1), 0.30)
                .create("trommel_aether_quicksoil");

    }
}