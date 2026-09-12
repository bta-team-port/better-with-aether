package teamport.aether;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.item.*;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;

import java.util.List;

public final class AetherCreativeContents {
    private static CreativeInventoryRegistry REG = CreativeInventoryRegistry.INSTANCE;

    public AetherCreativeContents() {
    }

    public static void init() {
        AetherCreativeContents.addDirtAndClouds();
        AetherCreativeContents.addStones();
        AetherCreativeContents.addSkyroot();
        AetherCreativeContents.addFurniture();
        AetherCreativeContents.addOreAndCo();
        AetherCreativeContents.addDungeonBlocks();
        AetherCreativeContents.addTools();
        AetherCreativeContents.addVanillaArmors();
        AetherCreativeContents.addTrinkets();
        AetherCreativeContents.addArmor();
        AetherCreativeContents.addDungeonTools();
        AetherCreativeContents.addFood();
        AetherCreativeContents.addMisc();
        AetherCreativeContents.addRecords();
    }

    private static void addDirtAndClouds() {
        CreativeInventoryPlacement.After after = placeAfter(Blocks.DIRT_SCORCHED_RICH);
        REG.register(AetherBlocks.GRASS_AETHER, after);
        REG.register(AetherBlocks.DIRT_AETHER, after);
        REG.register(AetherBlocks.PATH_DIRT_AETHER, after);
        REG.register(AetherBlocks.ICESTONE, placeAfter(Blocks.PERMAICE));
        REG.register(AetherBlocks.AEROGEL, placeAfter(Blocks.OBSIDIAN));
        after = placeAfter(Blocks.EMBER);
        REG.register(AetherBlocks.QUICKSOIL, after);
        REG.register(AetherBlocks.AERCLOUD_WHITE, after);
        REG.register(AetherBlocks.AERCLOUD_BLUE, after);
        REG.register(AetherBlocks.AERCLOUD_GOLD, after);
    }

    private static void addStones() {
        CreativeInventoryPlacement.After after = placeAfter(Blocks.PRESSURE_PLATE_MARBLE);
        REG.register(AetherBlocks.HOLYSTONE, after);
        REG.register(AetherBlocks.HOLYSTONE_MOSSY, after);
        REG.register(AetherBlocks.COBBLE_HOLYSTONE, after);
        REG.register(AetherBlocks.STAIRS_COBBLE_HOLYSTONE, after);
        REG.register(AetherBlocks.SLAB_COBBLE_HOLYSTONE, after);
        REG.register(AetherBlocks.COBBLE_HOLYSTONE_MOSSY, after);
        REG.register(AetherBlocks.BRICK_HOLYSTONE, after);
        REG.register(AetherBlocks.STAIRS_BRICK_HOLYSTONE, after);
        REG.register(AetherBlocks.SLAB_BRICK_HOLYSTONE, after);
        REG.register(AetherBlocks.HOLYSTONE_POLISHED, after);
        REG.register(AetherBlocks.HOLYSTONE_CARVED, after);
        REG.register(AetherBlocks.SLAB_HOLYSTONE_POLISHED, after);
        REG.register(AetherItems.STATUE_HOLYSTONE, after);
        REG.register(AetherBlocks.BUTTON_HOLYSTONE, after);
        REG.register(AetherBlocks.PRESSURE_PLATE_HOLYSTONE, after);
        REG.register(AetherBlocks.PRESSURE_PLATE_COBBLE_HOLYSTONE, after);
    }

    private static void addSkyroot() {
        CreativeInventoryPlacement.After after = placeAfter(Items.SIGN);
        REG.register(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.BUTTON_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.STAIRS_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.SLAB_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.FENCE_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.FENCEGATE_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT, after);
        REG.register(AetherBlocks.CHEST_PLANKS_SKYROOT, after);
        REG.register(AetherItems.DOOR_SKYROOT, after);
        REG.register(AetherItems.SIGN_SKYROOT, after);
        after = placeAfter(Blocks.LOG_SCORCHED);
        REG.register(AetherBlocks.LOG_SKYROOT, after);
        REG.register(AetherBlocks.LOG_OAK_GOLDEN, after);
        REG.register(AetherBlocks.LOG_AETHER_SCORCHED, after);
        after = placeAfter(Blocks.LEAVES_CACAO);
        REG.register(AetherBlocks.LEAVES_SKYROOT, after);
        REG.register(AetherBlocks.LEAVES_OAK_GOLDEN, after);
        after = placeAfter(Blocks.SAPLING_CACAO);
        REG.register(AetherBlocks.SAPLING_SKYROOT, after);
        REG.register(AetherBlocks.SAPLING_OAK_GOLDEN, after);
    }

    private static void addFurniture() {
        CreativeInventoryPlacement.After after = placeAfter(Blocks.TROMMEL_IDLE);
        REG.register(AetherBlocks.ENCHANTER_IDLE, after);
        REG.register(AetherBlocks.FREEZER_IDLE, after);
        REG.register(AetherBlocks.INCUBATOR_IDLE, after);
        REG.register(AetherBlocks.TORCH_AMBROSIUM, placeAfter(Blocks.TORCH_COAL));
        after = placeAfter(Blocks.GLASS_STEEL);
        REG.register(AetherBlocks.GLASS_QUICKSOIL, after);
        REG.register(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, after);
    }

    private static void addOreAndCo() {
        CreativeInventoryPlacement.After after = placeAfter(Blocks.ORE_NETHERCOAL_GLOOMSTONE);
        REG.register(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, after);
        REG.register(AetherBlocks.ORE_ZANITE_HOLYSTONE, after);
        REG.register(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, after);
        after = placeAfter(Blocks.BLOCK_QUARTZ);
        REG.register(AetherBlocks.BLOCK_AMBER, after);
        REG.register(AetherBlocks.BLOCK_ZANITE, after);
        REG.register(AetherBlocks.BLOCK_GRAVITITE, after);
        REG.register(AetherBlocks.BLOCK_AMBROSIUM, placeAfter(Blocks.BLOCK_OLIVINE));
        after = placeAfter(Blocks.SLAB_BRICK_RUBYGLASS);
        REG.register(AetherBlocks.BRICK_ZANITE, after);
        REG.register(AetherBlocks.STAIRS_BRICK_ZANITE, after);
        REG.register(AetherBlocks.SLAB_BRICK_ZANITE, after);
        REG.register(AetherBlocks.BRICK_GRAVITITE, after);
        REG.register(AetherBlocks.STAIRS_BRICK_GRAVITITE, after);
        REG.register(AetherBlocks.SLAB_BRICK_GRAVITITE, after);
    }

    private static void addDungeonBlocks() {
        CreativeInventoryPlacement.After after = placeAfter(Blocks.SLAB_BRICK_RUBYGLASS);
        REG.register(AetherBlocks.CARVED_STONE, after);
        REG.register(AetherBlocks.STAIRS_CARVED_STONE, after);
        REG.register(AetherBlocks.SLAB_CARVED_STONE, after);
        REG.register(AetherBlocks.CARVED_STONE_LIGHT, after);
        REG.register(AetherBlocks.CHEST_DUNGEON_BRONZE, after);
        REG.register(AetherItems.DOOR_DUNGEON_BRONZE, after);

        REG.register(AetherBlocks.CARVED_ANGELIC, after);
        REG.register(AetherBlocks.STAIRS_CARVED_ANGELIC, after);
        REG.register(AetherBlocks.SLAB_CARVED_ANGELIC, after);
        REG.register(AetherBlocks.CARVED_ANGELIC_LIGHT, after);
        REG.register(AetherBlocks.CHEST_DUNGEON_SILVER, after);
        REG.register(AetherItems.DOOR_DUNGEON_SILVER, after);

        REG.register(AetherBlocks.CARVED_HELLFIRE, after);
        REG.register(AetherBlocks.STAIRS_CARVED_HELLFIRE, after);
        REG.register(AetherBlocks.SLAB_CARVED_HELLFIRE, after);
        REG.register(AetherBlocks.CARVED_HELLFIRE_LIGHT, after);
        REG.register(AetherBlocks.CHEST_DUNGEON_GOLD, after);
        REG.register(AetherItems.DOOR_DUNGEON_GOLD, after);

        REG.register(AetherBlocks.PILLAR, after);
        REG.register(AetherBlocks.PILLAR_CAPSTONE, after);
    }

    private static void addTools() {
        CreativeInventoryPlacement.Category after = placeCategory(CreativeInventoryCategory.TOOLS);
        REG.register(AetherItems.TOOL_SHOVEL_SKYROOT, after);
        REG.register(AetherItems.TOOL_PICKAXE_SKYROOT, after);
        REG.register(AetherItems.TOOL_AXE_SKYROOT, after);
        REG.register(AetherItems.TOOL_SWORD_SKYROOT, after);
        REG.register(AetherItems.TOOL_SHOVEL_HOLYSTONE, after);
        REG.register(AetherItems.TOOL_PICKAXE_HOLYSTONE, after);
        REG.register(AetherItems.TOOL_AXE_HOLYSTONE, after);
        REG.register(AetherItems.TOOL_AXE_SKYROOT, after);
        REG.register(AetherItems.TOOL_SWORD_HOLYSTONE, after);
        REG.register(AetherItems.TOOL_SHOVEL_ZANITE, after);
        REG.register(AetherItems.TOOL_PICKAXE_ZANITE, after);
        REG.register(AetherItems.TOOL_AXE_ZANITE, after);
        REG.register(AetherItems.TOOL_SWORD_ZANITE, after);
        REG.register(AetherItems.TOOL_SHOVEL_GRAVITITE, after);
        REG.register(AetherItems.TOOL_PICKAXE_GRAVITITE, after);
        REG.register(AetherItems.TOOL_AXE_GRAVITITE, after);
        REG.register(AetherItems.TOOL_SWORD_GRAVITITE, after);
        REG.register(AetherItems.TOOL_SHOVEL_VALKYRIE, after);
        REG.register(AetherItems.TOOL_PICKAXE_VALKYRIE, after);
        REG.register(AetherItems.TOOL_AXE_VALKYRIE, after);
        REG.register(AetherItems.TOOL_SWORD_VALKYRIE, after);
    }

    private static void addVanillaArmors() {
        CreativeInventoryPlacement.After after = placeAfter(Items.ARMOR_BOOTS_LEATHER);
        REG.register(AetherItems.ARMOR_GLOVES_LEATHER, after);
        REG.register(AetherItems.ARMOR_TALISMAN_LEATHER, after);
        after = placeAfter(Items.ARMOR_BOOTS_CHAINMAIL);
        REG.register(AetherItems.ARMOR_GLOVES_CHAINMAIL, after);
        REG.register(AetherItems.ARMOR_TALISMAN_CHAINMAIL, after);
        after = placeAfter(Items.ARMOR_BOOTS_IRON);
        REG.register(AetherItems.ARMOR_GLOVES_IRON, after);
        REG.register(AetherItems.ARMOR_TALISMAN_IRON, after);
        after = placeAfter(Items.ARMOR_BOOTS_GOLD);
        REG.register(AetherItems.ARMOR_GLOVES_GOLD, after);
        REG.register(AetherItems.ARMOR_TALISMAN_GOLD, after);
        after = placeAfter(Items.ARMOR_BOOTS_DIAMOND);
        REG.register(AetherItems.ARMOR_GLOVES_DIAMOND, after);
        REG.register(AetherItems.ARMOR_TALISMAN_DIAMOND, after);
        after = placeAfter(Items.ARMOR_BOOTS_STEEL);
        REG.register(AetherItems.ARMOR_GLOVES_STEEL, after);
        REG.register(AetherItems.ARMOR_TALISMAN_STEEL, after);
    }

    private static void addArmor() {
        CreativeInventoryPlacement.Category after = placeCategory(CreativeInventoryCategory.ARMOR);
        REG.register(AetherItems.ARMOR_HELMET_ZANITE, after);
        REG.register(AetherItems.ARMOR_CHESTPLATE_ZANITE, after);
        REG.register(AetherItems.ARMOR_LEGGINGS_ZANITE, after);
        REG.register(AetherItems.ARMOR_BOOTS_ZANITE, after);
        REG.register(AetherItems.ARMOR_GLOVES_ZANITE, after);
        REG.register(AetherItems.ARMOR_TALISMAN_ZANITE, after);
        REG.register(AetherItems.ARMOR_WOLF_ZANITE, after);

        REG.register(AetherItems.ARMOR_HELMET_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_CHESTPLATE_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_LEGGINGS_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_BOOTS_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_GLOVES_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_TALISMAN_GRAVITITE, after);
        REG.register(AetherItems.ARMOR_WOLF_GRAVITITE, after);

        REG.register(AetherItems.ARMOR_HELMET_OBSIDIAN, after);
        REG.register(AetherItems.ARMOR_CHESTPLATE_OBSIDIAN, after);
        REG.register(AetherItems.ARMOR_LEGGINGS_OBSIDIAN, after);
        REG.register(AetherItems.ARMOR_BOOTS_OBSIDIAN, after);
        REG.register(AetherItems.ARMOR_GLOVES_ZANITE, after);
        REG.register(AetherItems.ARMOR_GLOVES_OBSIDIAN, after);
        REG.register(AetherItems.ARMOR_WOLF_OBSIDIAN, after);

        REG.register(AetherItems.ARMOR_HELMET_PHOENIX, after);
        REG.register(AetherItems.ARMOR_CHESTPLATE_PHOENIX, after);
        REG.register(AetherItems.ARMOR_LEGGINGS_PHOENIX, after);
        REG.register(AetherItems.ARMOR_BOOTS_PHOENIX, after);
        REG.register(AetherItems.ARMOR_GLOVES_PHOENIX, after);
        REG.register(AetherItems.ARMOR_WOLF_PHOENIX, after);

        REG.register(AetherItems.ARMOR_HELMET_NEPTUNE, after);
        REG.register(AetherItems.ARMOR_CHESTPLATE_NEPTUNE, after);
        REG.register(AetherItems.ARMOR_LEGGINGS_NEPTUNE, after);
        REG.register(AetherItems.ARMOR_BOOTS_NEPTUNE, after);
        REG.register(AetherItems.ARMOR_GLOVES_NEPTUNE, after);
        REG.register(AetherItems.ARMOR_WOLF_NEPTUNE, after);
    }

    private static void addTrinkets() {
        CreativeInventoryPlacement.After after = placeAfter(Items.ARMOR_BOOTS_ICESKATES);
        REG.register(AetherItems.ARMOR_TALISMAN_ICE, after);
    }

    private static void addRecords() {
        CreativeInventoryPlacement.Category after = placeCategory(CreativeInventoryCategory.RECORDS);
        REG.register(AetherItems.RECORD_AETHER, after);
        REG.register(AetherItems.RECORD_MORNING, after);
        REG.register(AetherItems.RECORD_DAWN, after);
        REG.register(AetherItems.RECORD_NETHER, after);
    }

    private static void addDungeonTools() {
        CreativeInventoryPlacement.After after = placeAfter(Items.MAP);
        REG.register(AetherItems.TOOL_DUNGEON_COMPASS, after);

        REG.register(AetherItems.TOOL_SWORD_PIG, after);
        REG.register(AetherItems.TOOL_SWORD_VAMPIRE, after);
        REG.register(AetherItems.TOOL_SWORD_FLAME, after);
        REG.register(AetherItems.TOOL_SWORD_HOLY, after);
        REG.register(AetherItems.TOOL_SWORD_LIGHTNING, after);
        REG.register(AetherItems.TOOL_KNIFE_LIGHTNING, after);
        REG.register(AetherItems.TOOL_STAFF_NATURE, after);
        REG.register(AetherItems.TOOL_STAFF_CLOUD, after);
        REG.register(AetherItems.TOOL_HAMMER_NOTCH, after);
        REG.register(AetherItems.TOOL_BOW_PHOENIX, placeAfter(Items.TOOL_BOW));

        after = placeAfter(Items.AMMO_FIREBALL);
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            REG.register(AetherItems.AMMO_WINDBALL, after);
            REG.register(AetherItems.PROJECTILE_FIRE, after);
            REG.register(AetherItems.PROJECTILE_ICE, after);
            REG.register(AetherItems.PROJECTILE_LIGHTNING, after);
        }
        REG.register(AetherItems.TOOL_SHOOTER, after);
        REG.register(AetherItems.AMMO_DART_GOLDEN, after);
        REG.register(AetherItems.AMMO_DART_POISON, after);
        REG.register(AetherItems.AMMO_DART_ENCHANTED, after);
    }

    private static void addFood() {
        CreativeInventoryPlacement.After after = placeAfter(Items.EGG_CHICKEN);
        REG.register(AetherItems.EGG_MOA_BLUE, after);
        REG.register(AetherItems.EGG_MOA_WHITE, after);
        REG.register(AetherItems.EGG_MOA_BLACK, after);
        after = placeAfter(Items.FOOD_STEW_MUSHROOM);
        REG.register(AetherItems.FOOD_HEALING_STONE, after);
        REG.register(AetherItems.FOOD_GUMMY_BLUE, after);
        REG.register(AetherItems.FOOD_GUMMY_GOLD, after);
        REG.register(AetherItems.LIFESHARD, after);
        REG.register(AetherItems.AMBROSIUM, placeAfter(Items.OLIVINE));
    }

    private static void addMisc() {
        REG.register(AetherBlocks.TALLGRASS_AETHER, placeAfter(Blocks.TALLGRASS));
        REG.register(AetherBlocks.DEADBUSH_AETHER, placeAfter(Blocks.DEADBUSH));

        REG.register(AetherItems.DOOR_GLASS_AMBROSIUM, placeAfter(Items.DOOR_STEEL));
        REG.register(AetherItems.LANTERN_FIREFLY_SILVER, placeAfter(Items.LANTERN_FIREFLY_RED));
        CreativeInventoryPlacement.After after = placeAfter(Items.DUST_GLOWSTONE);
        REG.register(AetherItems.AMBER, after);
        REG.register(AetherItems.ZANITE, after);
        REG.register(AetherItems.ORE_RAW_GRAVITITE, after);
        after = placeAfter(Items.LEATHER);
        REG.register(AetherItems.STICK_SKYROOT, after);
        REG.register(AetherItems.PETAL_AECHOR, after);
        REG.register(AetherItems.MEDAL_VICTORY, after);
        REG.register(AetherItems.KEY_BRONZE, after);
        REG.register(AetherItems.KEY_SILVER, after);
        REG.register(AetherItems.KEY_GOLD, after);
    }

    private static CreativeInventoryPlacement.After placeAfter(IItemConvertible item) {
        return new CreativeInventoryPlacement.After(() -> item);
    }

    private static CreativeInventoryPlacement.Category placeCategory(CreativeInventoryCategory category) {
        return new CreativeInventoryPlacement.Category(category);
    }
}
