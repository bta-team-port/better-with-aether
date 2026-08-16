package teamport.aether.item;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.*;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.helper.DamageType;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.vehicle.parachute.EntityParachute;
import teamport.aether.entity.vehicle.parachute.EntityParachuteGold;
import teamport.aether.item.accessory.ItemAccessoryArmor;
import teamport.aether.item.accessory.ItemGloves;
import teamport.aether.item.accessory.ItemTrinket;
import teamport.aether.item.accessory.cape.ItemAgilityCapeArmor;
import teamport.aether.item.accessory.cape.ItemInvisibilityCapeArmor;
import teamport.aether.item.accessory.cape.ItemSwetCapeArmor;
import teamport.aether.item.accessory.gloves.ItemGlovesGravitite;
import teamport.aether.item.accessory.gloves.ItemGlovesNeptune;
import teamport.aether.item.accessory.gloves.ItemGlovesObsidian;
import teamport.aether.item.accessory.gloves.ItemGlovesPhoenix;
import teamport.aether.item.accessory.pendant.ItemCombatPendant;
import teamport.aether.item.accessory.pendant.ItemGravititePendant;
import teamport.aether.item.accessory.pendant.ItemIcePendant;
import teamport.aether.item.accessory.pendant.ItemPendant;
import teamport.aether.item.accessory.trinket.ItemGoldenFeather;
import teamport.aether.item.accessory.trinket.ItemIronBubble;
import teamport.aether.item.accessory.trinket.ItemRegenStone;
import teamport.aether.item.accessory.trinket.ItemRepulsionShield;
import teamport.aether.item.item_tool.*;
import teamport.aether.item.item_tool.item_tool_gravitite.ItemToolAxeGravitite;
import teamport.aether.item.item_tool.item_tool_gravitite.ItemToolPickaxeGravitite;
import teamport.aether.item.item_tool.item_tool_gravitite.ItemToolShovelGravitite;
import teamport.aether.item.item_tool.item_tool_gravitite.ItemToolSwordGravitite;
import teamport.aether.item.item_tool.item_tool_holystone.ItemToolAxeHolystone;
import teamport.aether.item.item_tool.item_tool_holystone.ItemToolPickaxeHolystone;
import teamport.aether.item.item_tool.item_tool_holystone.ItemToolShovelHolystone;
import teamport.aether.item.item_tool.item_tool_holystone.ItemToolSwordHolystone;
import teamport.aether.item.item_tool.item_tool_valkyrie.ItemToolAxeValkyrie;
import teamport.aether.item.item_tool.item_tool_valkyrie.ItemToolPickaxeValkyrie;
import teamport.aether.item.item_tool.item_tool_valkyrie.ItemToolShovelValkyrie;
import teamport.aether.item.item_tool.item_tool_valkyrie.ItemToolSwordValkyrie;
import teamport.aether.item.item_tool.item_tool_zanite.ItemToolAxeZanite;
import teamport.aether.item.item_tool.item_tool_zanite.ItemToolPickaxeZanite;
import teamport.aether.item.item_tool.item_tool_zanite.ItemToolShovelZanite;
import teamport.aether.item.item_tool.item_tool_zanite.ItemToolSwordZanite;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;

import static teamport.aether.AetherConfig.itemID;
import static teamport.aether.AetherMod.MOD_ID;

@SuppressWarnings({"java:S1104", "java:S1444", "java:S3008"})
public final class AetherItems {
    public static Item MEDAL_VICTORY;

    public static Item KEY_BRONZE;
    public static Item KEY_SILVER;
    public static Item KEY_GOLD;

    public static Item EGG_MOA_BLUE;
    public static Item EGG_MOA_WHITE;
    public static Item EGG_MOA_BLACK;

    public static Item RECORD_AETHER;
    public static Item RECORD_MORNING;
    public static Item RECORD_DAWN;
    public static Item RECORD_NETHER;

    public static Item AMBER;
    public static Item PETAL_AECHOR;
    public static Item STICK_SKYROOT;

    public static Item AMMO_DART_GOLDEN;
    public static Item AMMO_DART_POISON;
    public static Item AMMO_DART_ENCHANTED;
    public static Item TOOL_SHOOTER;

    public static Item AMMO_ARROW_FLAMING;

    public static Item AMBROSIUM;
    public static Item ZANITE;
    public static Item ORE_RAW_GRAVITITE;

    public static Item BUCKET_SKYROOT;
    public static Item BUCKET_SKYROOT_WATER;
    public static Item BUCKET_SKYROOT_MILK;
    public static Item BUCKET_SKYROOT_POISON;
    public static Item BUCKET_SKYROOT_REMEDY;
    public static Item BUCKET_SKYROOT_ICECREAM;

    public static Item FOOD_HEALING_STONE;

    public static Item TOOL_DUNGEON_COMPASS;

    public static Item TOOL_PICKAXE_SKYROOT;
    public static Item TOOL_SHOVEL_SKYROOT;
    public static Item TOOL_AXE_SKYROOT;
    public static Item TOOL_SWORD_SKYROOT;

    public static Item TOOL_PICKAXE_HOLYSTONE;
    public static Item TOOL_SHOVEL_HOLYSTONE;
    public static Item TOOL_AXE_HOLYSTONE;
    public static Item TOOL_SWORD_HOLYSTONE;

    public static Item TOOL_PICKAXE_ZANITE;
    public static Item TOOL_SHOVEL_ZANITE;
    public static Item TOOL_AXE_ZANITE;
    public static Item TOOL_SWORD_ZANITE;

    public static Item TOOL_PICKAXE_GRAVITITE;
    public static Item TOOL_SHOVEL_GRAVITITE;
    public static Item TOOL_AXE_GRAVITITE;
    public static Item TOOL_SWORD_GRAVITITE;

    public static Item TOOL_PICKAXE_VALKYRIE;
    public static Item TOOL_SHOVEL_VALKYRIE;
    public static Item TOOL_AXE_VALKYRIE;
    public static Item TOOL_SWORD_VALKYRIE;

    public static Item ARMOR_HELMET_ZANITE;
    public static Item ARMOR_CHESTPLATE_ZANITE;
    public static Item ARMOR_LEGGINGS_ZANITE;
    public static Item ARMOR_BOOTS_ZANITE;

    public static Item ARMOR_HELMET_GRAVITITE;
    public static Item ARMOR_CHESTPLATE_GRAVITITE;
    public static Item ARMOR_LEGGINGS_GRAVITITE;
    public static Item ARMOR_BOOTS_GRAVITITE;

    public static Item ARMOR_HELMET_OBSIDIAN;
    public static Item ARMOR_CHESTPLATE_OBSIDIAN;
    public static Item ARMOR_LEGGINGS_OBSIDIAN;
    public static Item ARMOR_BOOTS_OBSIDIAN;

    public static Item ARMOR_HELMET_PHOENIX;
    public static Item ARMOR_CHESTPLATE_PHOENIX;
    public static Item ARMOR_LEGGINGS_PHOENIX;
    public static Item ARMOR_BOOTS_PHOENIX;

    public static Item ARMOR_HELMET_NEPTUNE;
    public static Item ARMOR_CHESTPLATE_NEPTUNE;
    public static Item ARMOR_LEGGINGS_NEPTUNE;
    public static Item ARMOR_BOOTS_NEPTUNE;

    public static Item TOOL_SWORD_PIG;
    public static Item TOOL_SWORD_VAMPIRE;

    public static Item TOOL_SWORD_FLAME;
    public static Item TOOL_SWORD_HOLY;
    public static Item TOOL_SWORD_LIGHTNING;

    public static Item TOOL_STAFF_NATURE;
    public static Item TOOL_STAFF_CLOUD;

    public static Item TOOL_KNIFE_LIGHTNING;
    public static Item TOOL_HAMMER_NOTCH;
    public static Item AMMO_HAMMER_HEAD;
    public static Item TOOL_BOW_PHOENIX;

    public static Item ARMOR_SHIELD_REPULSION;

    public static Item ARMOR_GLOVES_LEATHER;
    public static Item ARMOR_GLOVES_CHAINMAIL;
    public static Item ARMOR_GLOVES_IRON;
    public static Item ARMOR_GLOVES_GOLD;
    public static Item ARMOR_GLOVES_DIAMOND;
    public static Item ARMOR_GLOVES_STEEL;
    public static Item ARMOR_GLOVES_ZANITE;
    public static Item ARMOR_GLOVES_GRAVITITE;
    public static Item ARMOR_GLOVES_OBSIDIAN;
    public static Item ARMOR_GLOVES_PHOENIX;
    public static Item ARMOR_GLOVES_NEPTUNE;

    public static Item ARMOR_TALISMAN_LEATHER;
    public static Item ARMOR_TALISMAN_CHAINMAIL;
    public static Item ARMOR_TALISMAN_IRON;
    public static Item ARMOR_TALISMAN_GOLD;
    public static Item ARMOR_TALISMAN_DIAMOND;
    public static Item ARMOR_TALISMAN_STEEL;
    public static Item ARMOR_TALISMAN_ZANITE;
    public static Item ARMOR_TALISMAN_GRAVITITE;
    public static Item ARMOR_TALISMAN_ICE;

    public static Item ARMOR_TALISMAN_BUBBLE;
    public static Item ARMOR_TALISMAN_FEATHER_GOLD;
    public static Item ARMOR_TALISMAN_REGEN;

    public static Item ARMOR_CAPE_SWET;
    public static Item ARMOR_CAPE_INVISIBILITY;
    public static Item ARMOR_CAPE_AGILITY;

    public static Item ARMOR_CAPE_BLACK;
    public static Item ARMOR_CAPE_RED;
    public static Item ARMOR_CAPE_GREEN;
    public static Item ARMOR_CAPE_BROWN;
    public static Item ARMOR_CAPE_BLUE;
    public static Item ARMOR_CAPE_PURPLE;
    public static Item ARMOR_CAPE_CYAN;
    public static Item ARMOR_CAPE_SILVER;
    public static Item ARMOR_CAPE_GRAY;
    public static Item ARMOR_CAPE_PINK;
    public static Item ARMOR_CAPE_LIME;
    public static Item ARMOR_CAPE_YELLOW;
    public static Item ARMOR_CAPE_LIGHTBLUE;
    public static Item ARMOR_CAPE_MAGENTA;
    public static Item ARMOR_CAPE_ORANGE;
    public static Item ARMOR_CAPE_WHITE;

    public static Item FOOD_GUMMY_BLUE;
    public static Item FOOD_GUMMY_GOLD;

    public static Item PARACHUTE_CLOUD;
    public static Item PARACHUTE_CLOUD_GOLD;

    public static Item LIFESHARD;

    public static Item LANTERN_FIREFLY_SILVER;

    public static Item DOOR_SKYROOT;
    public static Item DOOR_SKYROOT_PAINTED;

    public static Item DOOR_GLASS_AMBROSIUM;
    public static Item DOOR_DUNGEON_BRONZE;
    public static Item DOOR_DUNGEON_SILVER;
    public static Item DOOR_DUNGEON_GOLD;

    public static Item SIGN_SKYROOT;
    public static Item SIGN_SKYROOT_PAINTED;

    public static Item AMMO_WINDBALL;

    public static Item PROJECTILE_FIRE;
    public static Item PROJECTILE_ICE;
    public static Item PROJECTILE_LIGHTNING;

    private static boolean hasInit = false;

    private AetherItems(){}

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeItems();
        }
    }

    public static String itemKey(String string) {
        return MOD_ID + ":item/" + string;
    }

    private static @NonNull ItemBuilder itemBuilder(CreativeInventoryCategory category) {
        return new ItemBuilder(MOD_ID)
            .setCreativeInventoryPlacement(new CreativeInventoryPlacement.Category(category));
    }

    public static void initializeItems() {
        AetherItems.registerArmor();
        AetherItems.registerTool();
        AetherItems.registerOther();
    }

    public static void registerOther() {
        MEDAL_VICTORY = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .build(new Item("medal.victory", itemKey("medal_victory"), itemID("MEDAL_VICTORY"))).setMaxStackSize(10);


        KEY_BRONZE = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE, AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new Item("key.bronze", itemKey("key_bronze"), itemID("KEY_BRONZE")));

        KEY_SILVER = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE, AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new Item("key.silver", itemKey("key_silver"), itemID("KEY_SILVER")));

        KEY_GOLD = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE, AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new Item("key.gold", itemKey("key_gold"), itemID("KEY_GOLD")));


        EGG_MOA_BLUE = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new Item("egg.moa.blue", itemKey("egg_moa_blue"), itemID("EGG_MOA_BLUE"))).setMaxStackSize(16);

        EGG_MOA_WHITE = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new Item("egg.moa.white", itemKey("egg_moa_white"), itemID("EGG_MOA_WHITE"))).setMaxStackSize(16);

        EGG_MOA_BLACK = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new Item("egg.moa.black", itemKey("egg_moa_black"), itemID("EGG_MOA_BLACK"))).setMaxStackSize(16);


        RECORD_AETHER = itemBuilder(CreativeInventoryCategory.RECORDS)
            .build(new ItemDiscMusic("record.aether", itemKey("record_aether"), itemID("RECORD_AETHER"), "aether:aether_tune", "Noisestorm"));

        RECORD_MORNING = itemBuilder(CreativeInventoryCategory.RECORDS)
            .build(new ItemDiscMusic("record.morning", itemKey("record_morning"), itemID("RECORD_MORNING"), "aether:a_morning_wish", "Emile van Kriken"));

        RECORD_DAWN = itemBuilder(CreativeInventoryCategory.RECORDS)
            .build(new ItemDiscMusic("record.dawn", itemKey("record_dawn"), itemID("RECORD_DAWN"), "aether:ascending_dawn", "Emile van Kriken"));

        RECORD_NETHER = itemBuilder(CreativeInventoryCategory.RECORDS)
            .build(new ItemDiscMusic("record.nether", itemKey("record_nether"), itemID("RECORD_NETHER"), "aether:nether_menu", "Emile van Kriken"));


        AMBER = itemBuilder(CreativeInventoryCategory.ORE_PRODUCTS)
            .build(new Item("amber", itemKey("amber"), itemID("AMBER")));

        PETAL_AECHOR = itemBuilder(CreativeInventoryCategory.DROPS)
            .setTags(AetherItemTags.MOAS_FAVOURITE_ITEM)
            .build(new Item("petal.aechor", itemKey("petal_aechor"), itemID("PETAL_AECHOR")));

        STICK_SKYROOT = itemBuilder(CreativeInventoryCategory.BASICS)
            .build(new Item("stick.skyroot", itemKey("stick_skyroot"), itemID("STICK_SKYROOT")));


        AMBROSIUM = itemBuilder(CreativeInventoryCategory.ORE_PRODUCTS)
            .build(new ItemAmbrosium("ambrosium", itemKey("ambrosium"), itemID("AMBROSIUM"), 1, 20, false));

        ZANITE = itemBuilder(CreativeInventoryCategory.ORE_PRODUCTS)
            .build(new Item("zanite", itemKey("zanite"), itemID("ZANITE")));

        ORE_RAW_GRAVITITE = itemBuilder(CreativeInventoryCategory.ORE_PRODUCTS)
            .setTags(AetherItemTags.FALLS_UPWARDS)
            .build(new Item("ore.raw.gravitite", itemKey("ore_raw_gravitite"), itemID("ORE_RAW_GRAVITITE")));


        FOOD_HEALING_STONE = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new ItemFood("food.healing.stone", itemKey("food_healing_stone"), itemID("FOOD_HEALING_STONE"), 4, 10, false, 4));


        FOOD_GUMMY_BLUE = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new ItemFood("food.gummy.blue", itemKey("food_gummy_blue"), itemID("FOOD_GUMMY_BLUE"), 15, 2, false, 4));

        FOOD_GUMMY_GOLD = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new ItemFood("food.gummy.gold", itemKey("food_gummy_gold"), itemID("FOOD_GUMMY_GOLD"), 30, 2, false, 2));


        LIFESHARD = itemBuilder(CreativeInventoryCategory.FOOD)
            .build(new ItemLifeShard("food.lifeshard", itemKey("food_lifeshard"), itemID("LIFESHARD")).setMaxStackSize(10));


        PARACHUTE_CLOUD = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .setMaxDamage(1)
            .build(new ItemParachute("parachute.cloud", itemKey("parachute_cloud"), itemID("PARACHUTE_CLOUD"), EntityParachute.class));


        PARACHUTE_CLOUD_GOLD = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .setMaxDamage(20)
            .build(new ItemParachute("parachute.cloud.gold", itemKey("parachute_cloud_gold"), itemID("PARACHUTE_CLOUD_GOLD"), EntityParachuteGold.class));


        LANTERN_FIREFLY_SILVER = itemBuilder(CreativeInventoryCategory.PLACEABLES)
            .build(new ItemPlaceable("lantern.firefly.silver", itemKey("lantern_firefly_silver"), itemID("LANTERN_FIREFLY_SILVER"), AetherBlocks.LANTERN_FIREFLY_SILVER));

        DOOR_SKYROOT = itemBuilder(CreativeInventoryCategory.WOOD)
            .build(new ItemDoor("door.skyroot", itemKey("door_skyroot"), itemID("DOOR_SKYROOT"), AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM, AetherBlocks.DOOR_PLANKS_SKYROOT_TOP));

        DOOR_SKYROOT_PAINTED = itemBuilder(CreativeInventoryCategory.WOOD)
            .build(new ItemDoorPainted("door.skyroot.painted", itemKey("door_skyroot_painted"), itemID("DOOR_SKYROOT_PAINTED"), AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP));


        DOOR_GLASS_AMBROSIUM = itemBuilder(CreativeInventoryCategory.PLACEABLES)
            .build(new ItemDoor("door.glass.ambrosium", itemKey("door_glass_ambrosium"), itemID("DOOR_GLASS_AMBROSIUM"), AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP));

        DOOR_DUNGEON_BRONZE = itemBuilder(CreativeInventoryCategory.PLACEABLES)
            .build(new ItemDoorDungeon("door.dungeon.bronze", itemKey("door_dungeon_bronze"), itemID("DOOR_DUNGEON_BRONZE"), AetherBlocks.DOOR_DUNGEON_BRONZE, ItemDoorDungeon.DoorType.BRONZE));
        DOOR_DUNGEON_SILVER = itemBuilder(CreativeInventoryCategory.PLACEABLES)
            .build(new ItemDoorDungeon("door.dungeon.silver", itemKey("door_dungeon_silver"), itemID("DOOR_DUNGEON_SILVER"), AetherBlocks.DOOR_DUNGEON_SILVER, ItemDoorDungeon.DoorType.SILVER));
        DOOR_DUNGEON_GOLD = itemBuilder(CreativeInventoryCategory.PLACEABLES)
            .build(new ItemDoorDungeon("door.dungeon.gold", itemKey("door_dungeon_gold"), itemID("DOOR_DUNGEON_GOLD"), AetherBlocks.DOOR_DUNGEON_GOLD, ItemDoorDungeon.DoorType.GOLD));

        SIGN_SKYROOT = itemBuilder(CreativeInventoryCategory.WOOD)
            .build(new ItemSignSkyroot("sign.skyroot", itemKey("sign_skyroot"), itemID("SIGN_SKYROOT"), false));
        SIGN_SKYROOT_PAINTED = itemBuilder(CreativeInventoryCategory.WOOD)
            .build(new ItemSignSkyroot("sign.skyroot.painted", itemKey("sign_skyroot_painted"), itemID("SIGN_SKYROOT_PAINTED"), true));

        AMMO_WINDBALL = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU)
            .build(new Item("ammo.windball", itemKey("ammo_windball"), itemID("AMMO_WINDBALL")));

        PROJECTILE_FIRE = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU, AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new Item("projectile.fire", itemKey("projectile_fire"), itemID("PROJECTILE_FIRE")));

        PROJECTILE_ICE = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU)
            .build(new Item("projectile.ice", itemKey("projectile_ice"), itemID("PROJECTILE_ICE")));

        PROJECTILE_LIGHTNING = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU)
            .build(new Item("projectile.lightning", itemKey("projectile_lightning"), itemID("PROJECTILE_LIGHTNING")));
    }

    public static void registerTool() {
        BUCKET_SKYROOT = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyrootEmpty("bucket.skyroot", itemKey("bucket_skyroot"), itemID("BUCKET_SKYROOT")));

        BUCKET_SKYROOT_WATER = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyroot("bucket.skyroot.water", itemKey("bucket_skyroot_water"), itemID("BUCKET_SKYROOT_WATER"), Blocks.FLUID_WATER_FLOWING))
            .setContainerItem(BUCKET_SKYROOT);

        BUCKET_SKYROOT_MILK = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyroot("bucket.skyroot.milk", itemKey("bucket_skyroot_milk"), itemID("BUCKET_SKYROOT_MILK"), null))
            .setContainerItem(BUCKET_SKYROOT);

        BUCKET_SKYROOT_REMEDY = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyrootRemedy("bucket.skyroot.remedy", itemKey("bucket_skyroot_remedy"), itemID("BUCKET_SKYROOT_REMEDY")))
            .setContainerItem(BUCKET_SKYROOT);

        BUCKET_SKYROOT_POISON = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyrootPoison("bucket.skyroot.poison", itemKey("bucket_skyroot_poison"), itemID("BUCKET_SKYROOT_POISON")))
            .setContainerItem(BUCKET_SKYROOT);

        BUCKET_SKYROOT_ICECREAM = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemBucketSkyrootIceCream("bucket.skyroot.icecream", itemKey("bucket_skyroot_icecream"), itemID("BUCKET_SKYROOT_ICECREAM"), 10, 4))
            .setContainerItem(BUCKET_SKYROOT);


        TOOL_SWORD_SKYROOT = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordAether("tool.sword.skyroot", itemKey("tool_sword_skyroot"), itemID("TOOL_SWORD_SKYROOT"), AetherToolMaterial.skyroot));

        TOOL_SHOVEL_SKYROOT = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolShovelAether("tool.shovel.skyroot", itemKey("tool_shovel_skyroot"), itemID("TOOL_SHOVEL_SKYROOT"), AetherToolMaterial.skyroot));

        TOOL_PICKAXE_SKYROOT = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolPickaxeAether("tool.pickaxe.skyroot", itemKey("tool_pickaxe_skyroot"), itemID("TOOL_PICKAXE_SKYROOT"), AetherToolMaterial.skyroot));

        TOOL_AXE_SKYROOT = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolAxeAether("tool.axe.skyroot", itemKey("tool_axe_skyroot"), itemID("TOOL_AXE_SKYROOT"), AetherToolMaterial.skyroot));


        TOOL_SWORD_HOLYSTONE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordHolystone("tool.sword.holystone", itemKey("tool_sword_holystone"), itemID("TOOL_SWORD_HOLYSTONE"), AetherToolMaterial.holystone));

        TOOL_SHOVEL_HOLYSTONE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolShovelHolystone("tool.shovel.holystone", itemKey("tool_shovel_holystone"), itemID("TOOL_SHOVEL_HOLYSTONE"), AetherToolMaterial.holystone));

        TOOL_PICKAXE_HOLYSTONE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolPickaxeHolystone("tool.pickaxe.holystone", itemKey("tool_pickaxe_holystone"), itemID("TOOL_PICKAXE_HOLYSTONE"), AetherToolMaterial.holystone));

        TOOL_AXE_HOLYSTONE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolAxeHolystone("tool.axe.holystone", itemKey("tool_axe_holystone"), itemID("TOOL_AXE_HOLYSTONE"), AetherToolMaterial.holystone));


        TOOL_SWORD_ZANITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordZanite("tool.sword.zanite", itemKey("tool_sword_zanite"), itemID("TOOL_SWORD_ZANITE"), AetherToolMaterial.zanite));

        TOOL_SHOVEL_ZANITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolShovelZanite("tool.shovel.zanite", itemKey("tool_shovel_zanite"), itemID("TOOL_SHOVEL_ZANITE"), AetherToolMaterial.zanite));

        TOOL_PICKAXE_ZANITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemKey("tool_pickaxe_zanite"), itemID("TOOL_PICKAXE_ZANITE"), AetherToolMaterial.zanite));

        TOOL_AXE_ZANITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolAxeZanite("tool.axe.zanite", itemKey("tool_axe_zanite"), itemID("TOOL_AXE_ZANITE"), AetherToolMaterial.zanite));


        TOOL_SWORD_GRAVITITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordGravitite("tool.sword.gravitite", itemKey("tool_sword_gravitite"), itemID("TOOL_SWORD_GRAVITITE"), AetherToolMaterial.gravitite));

        TOOL_SHOVEL_GRAVITITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolShovelGravitite("tool.shovel.gravitite", itemKey("tool_shovel_gravitite"), itemID("TOOL_SHOVEL_GRAVITITE"), AetherToolMaterial.gravitite));

        TOOL_PICKAXE_GRAVITITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolPickaxeGravitite("tool.pickaxe.gravitite", itemKey("tool_pickaxe_gravitite"), itemID("TOOL_PICKAXE_GRAVITITE"), AetherToolMaterial.gravitite));

        TOOL_AXE_GRAVITITE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolAxeGravitite("tool.axe.gravitite", itemKey("tool_axe_gravitite"), itemID("TOOL_AXE_GRAVITITE"), AetherToolMaterial.gravitite));


        TOOL_SWORD_VALKYRIE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordValkyrie("tool.sword.valkyrie", itemKey("tool_sword_valkyrie"), itemID("TOOL_SWORD_VALKYRIE"), AetherToolMaterial.valkyrie));

        TOOL_SHOVEL_VALKYRIE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolShovelValkyrie("tool.shovel.valkyrie", itemKey("tool_shovel_valkyrie"), itemID("TOOL_SHOVEL_VALKYRIE"), AetherToolMaterial.valkyrie));

        TOOL_PICKAXE_VALKYRIE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolPickaxeValkyrie("tool.pickaxe.valkyrie", itemKey("tool_pickaxe_valkyrie"), itemID("TOOL_PICKAXE_VALKYRIE"), AetherToolMaterial.valkyrie));

        TOOL_AXE_VALKYRIE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .build(new ItemToolAxeValkyrie("tool.axe.valkyrie", itemKey("tool_axe_valkyrie"), itemID("TOOL_AXE_VALKYRIE"), AetherToolMaterial.valkyrie));


        TOOL_KNIFE_LIGHTNING = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolKnifeLightning("tool.knife.lightning", itemKey("tool_knife_lightning"), itemID("TOOL_KNIFE_LIGHTNING")))
            .setMaxStackSize(32);


        AMMO_HAMMER_HEAD = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU)
            .build(new Item("ammo.hammer.head", itemKey("ammo_hammer_head"), itemID("AMMO_HAMMER_HEAD")));

        TOOL_HAMMER_NOTCH = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolHammerNotch("tool.hammer.notch", itemKey("tool_hammer_notch"), itemID("TOOL_HAMMER_NOTCH"), AetherToolMaterial.special));


        TOOL_BOW_PHOENIX = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new ItemBowPhoenix("tool.bow.phoenix", itemKey("tool_bow_phoenix"), itemID("TOOL_BOW_PHOENIX")));


        AMMO_DART_GOLDEN = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemDart("ammo.dart.golden", itemKey("ammo_dart_golden"), itemID("AMMO_DART_GOLDEN"), 0));

        AMMO_DART_POISON = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemDart("ammo.dart.poison", itemKey("ammo_dart_poison"), itemID("AMMO_DART_POISON"), 1));

        AMMO_DART_ENCHANTED = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemDart("ammo.dart.enchanted", itemKey("ammo_dart_enchanted"), itemID("AMMO_DART_ENCHANTED"), 2));


        TOOL_SHOOTER = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemShooter("tool.shooter", itemKey("tool_shooter"), itemID("TOOL_SHOOTER")));


        AMMO_ARROW_FLAMING = itemBuilder(CreativeInventoryCategory.MISCELLANEOUS)
            .setTags(ItemTags.NOT_IN_CREATIVE_MENU)
            .build(new Item("ammo.arrow.flaming", itemKey("ammo_arrow_flaming"), itemID("AMMO_ARROW_FLAMING")));


        TOOL_SWORD_PIG = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordPig("tool.sword.pig", itemKey("tool_sword_pig"), itemID("TOOL_SWORD_PIG"), AetherToolMaterial.special));

        TOOL_SWORD_VAMPIRE = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordVampire("tool.sword.vampire", itemKey("tool_sword_vampire"), itemID("TOOL_SWORD_VAMPIRE"), AetherToolMaterial.special));

        TOOL_SWORD_FLAME = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordFire("tool.sword.flame", itemKey("tool_sword_flame"), itemID("TOOL_SWORD_FLAME"), AetherToolMaterial.special));

        TOOL_SWORD_HOLY = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordHoly("tool.sword.holy", itemKey("tool_sword_holy"), itemID("TOOL_SWORD_HOLY"), AetherToolMaterial.special));

        TOOL_SWORD_LIGHTNING = itemBuilder(CreativeInventoryCategory.TOOLS)
            .setTags(ItemTags.PREVENT_CREATIVE_MINING)
            .build(new ItemToolSwordLightning("tool.sword.lightning", itemKey("tool_sword_lightning"), itemID("TOOL_SWORD_LIGHTNING"), AetherToolMaterial.special));


        TOOL_STAFF_NATURE = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .setTags(ItemTags.CHICKENS_FAVOURITE_ITEM, ItemTags.COWS_FAVOURITE_ITEM, AetherItemTags.NATURE_STAFF_FOLLOW, AetherItemTags.MOAS_FAVOURITE_ITEM)
            .setStackSize(1)
            .build(new Item("tool.staff.nature", itemKey("tool_staff_nature"), itemID("TOOL_STAFF_NATURE")));

        TOOL_STAFF_CLOUD = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemStaffCloud("tool.staff.cloud", itemKey("tool_staff_cloud"), itemID("TOOL_STAFF_CLOUD")));


        TOOL_DUNGEON_COMPASS = itemBuilder(CreativeInventoryCategory.MISC_TOOLS)
            .build(new ItemTrinket("tool.dungeon_compass", itemKey("tool_dungeon_compass"), itemID("TOOL_DUNGEON_COMPASS"), "dungeon_compass", "aether:item/trinket/armor_dungeon_compass_outline"));

    }

    public static void registerArmor() {

        ARMOR_HELMET_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.helmet.zanite", itemKey("armor_helmet_zanite"), itemID("ARMOR_HELMET_ZANITE"), AetherArmorMaterial.ZANITE, HumanArmorShape.HEAD));

        ARMOR_CHESTPLATE_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.chestplate.zanite", itemKey("armor_chestplate_zanite"), itemID("ARMOR_CHESTPLATE_ZANITE"), AetherArmorMaterial.ZANITE, HumanArmorShape.CHEST));

        ARMOR_LEGGINGS_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.leggings.zanite", itemKey("armor_leggings_zanite"), itemID("ARMOR_LEGGINGS_ZANITE"), AetherArmorMaterial.ZANITE, HumanArmorShape.LEGS));

        ARMOR_BOOTS_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.boots.zanite", itemKey("armor_boots_zanite"), itemID("ARMOR_BOOTS_ZANITE"), AetherArmorMaterial.ZANITE, HumanArmorShape.BOOTS));


        ARMOR_HELMET_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.helmet.gravitite", itemKey("armor_helmet_gravitite"), itemID("ARMOR_HELMET_GRAVITITE"), AetherArmorMaterial.GRAVITITE, HumanArmorShape.HEAD));

        ARMOR_CHESTPLATE_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.chestplate.gravitite", itemKey("armor_chestplate_gravitite"), itemID("ARMOR_CHESTPLATE_GRAVITITE"), AetherArmorMaterial.GRAVITITE, HumanArmorShape.CHEST));

        ARMOR_LEGGINGS_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.leggings.gravitite", itemKey("armor_leggings_gravitite"), itemID("ARMOR_LEGGINGS_GRAVITITE"), AetherArmorMaterial.GRAVITITE, HumanArmorShape.LEGS));

        ARMOR_BOOTS_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.boots.gravitite", itemKey("armor_boots_gravitite"), itemID("ARMOR_BOOTS_GRAVITITE"), AetherArmorMaterial.GRAVITITE, HumanArmorShape.BOOTS));


        ARMOR_HELMET_OBSIDIAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new ItemArmor<>("armor.helmet.obsidian", itemKey("armor_helmet_obsidian"), itemID("ARMOR_HELMET_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, HumanArmorShape.HEAD));

        ARMOR_CHESTPLATE_OBSIDIAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new ItemArmor<>("armor.chestplate.obsidian", itemKey("armor_chestplate_obsidian"), itemID("ARMOR_CHESTPLATE_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, HumanArmorShape.CHEST));

        ARMOR_LEGGINGS_OBSIDIAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new ItemArmor<>("armor.leggings.obsidian", itemKey("armor_leggings_obsidian"), itemID("ARMOR_LEGGINGS_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, HumanArmorShape.LEGS));

        ARMOR_BOOTS_OBSIDIAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_BLAST_DAMAGE)
            .build(new ItemArmor<>("armor.boots.obsidian", itemKey("armor_boots_obsidian"), itemID("ARMOR_BOOTS_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, HumanArmorShape.BOOTS));


        ARMOR_HELMET_PHOENIX = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new ItemArmor<>("armor.helmet.phoenix", itemKey("armor_helmet_phoenix"), itemID("ARMOR_HELMET_PHOENIX"), AetherArmorMaterial.PHOENIX, HumanArmorShape.HEAD));

        ARMOR_CHESTPLATE_PHOENIX = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new ItemArmor<>("armor.chestplate.phoenix", itemKey("armor_chestplate_phoenix"), itemID("ARMOR_CHESTPLATE_PHOENIX"), AetherArmorMaterial.PHOENIX, HumanArmorShape.CHEST));

        ARMOR_LEGGINGS_PHOENIX = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new ItemArmor<>("armor.leggings.phoenix", itemKey("armor_leggings_phoenix"), itemID("ARMOR_LEGGINGS_PHOENIX"), AetherArmorMaterial.PHOENIX, HumanArmorShape.LEGS));

        ARMOR_BOOTS_PHOENIX = itemBuilder(CreativeInventoryCategory.ARMOR)
            .addTags(AetherItemTags.IMMUNE_TO_FIRE_DAMAGE)
            .build(new ItemArmor<>("armor.boots.phoenix", itemKey("armor_boots_phoenix"), itemID("ARMOR_BOOTS_PHOENIX"), AetherArmorMaterial.PHOENIX, HumanArmorShape.BOOTS));


        ARMOR_HELMET_NEPTUNE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.helmet.neptune", itemKey("armor_helmet_neptune"), itemID("ARMOR_HELMET_NEPTUNE"), AetherArmorMaterial.NEPTUNE, HumanArmorShape.HEAD));

        ARMOR_CHESTPLATE_NEPTUNE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.chestplate.neptune", itemKey("armor_chestplate_neptune"), itemID("ARMOR_CHESTPLATE_NEPTUNE"), AetherArmorMaterial.NEPTUNE, HumanArmorShape.CHEST));

        ARMOR_LEGGINGS_NEPTUNE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.leggings.neptune", itemKey("armor_leggings_neptune"), itemID("ARMOR_LEGGINGS_NEPTUNE"), AetherArmorMaterial.NEPTUNE, HumanArmorShape.LEGS));

        ARMOR_BOOTS_NEPTUNE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemArmor<>("armor.boots.neptune", itemKey("armor_boots_neptune"), itemID("ARMOR_BOOTS_NEPTUNE"), AetherArmorMaterial.NEPTUNE, HumanArmorShape.BOOTS));


        ARMOR_GLOVES_LEATHER = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.leather", itemKey("armor_gloves_leather"), itemID("ARMOR_GLOVES_LEATHER"), ArmorMaterial.LEATHER, 4).setDamage(2));

        ARMOR_GLOVES_CHAINMAIL = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.chainmail", itemKey("armor_gloves_chainmail"), itemID("ARMOR_GLOVES_CHAINMAIL"), ArmorMaterial.CHAINMAIL, 4).setDamage(2));

        ARMOR_GLOVES_IRON = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.iron", itemKey("armor_gloves_iron"), itemID("ARMOR_GLOVES_IRON"), ArmorMaterial.IRON, 4).setDamage(2));

        ARMOR_GLOVES_GOLD = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.gold", itemKey("armor_gloves_gold"), itemID("ARMOR_GLOVES_GOLD"), ArmorMaterial.GOLD, 4).setDamage(2));

        ARMOR_GLOVES_DIAMOND = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.diamond", itemKey("armor_gloves_diamond"), itemID("ARMOR_GLOVES_DIAMOND"), ArmorMaterial.DIAMOND, 4).setDamage(4));

        ARMOR_GLOVES_STEEL = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.steel", itemKey("armor_gloves_steel"), itemID("ARMOR_GLOVES_STEEL"), ArmorMaterial.STEEL, 4).setDamage(3));

        ARMOR_GLOVES_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGloves("armor.gloves.zanite", itemKey("armor_gloves_zanite"), itemID("ARMOR_GLOVES_ZANITE"), AetherArmorMaterial.ZANITE, 4).setDamage(2));

        ARMOR_GLOVES_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGlovesGravitite("armor.gloves.gravitite", itemKey("armor_gloves_gravitite"), itemID("ARMOR_GLOVES_GRAVITITE"), AetherArmorMaterial.GRAVITITE, 4).setDamage(3).setDamageType(DamageType.FALL));

        ARMOR_GLOVES_OBSIDIAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGlovesObsidian("armor.gloves.obsidian", itemKey("armor_gloves_obsidian"), itemID("ARMOR_GLOVES_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, 4).setDamage(3).setDamageType(DamageType.BLAST));

        ARMOR_GLOVES_PHOENIX = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGlovesPhoenix("armor.gloves.phoenix", itemKey("armor_gloves_phoenix"), itemID("ARMOR_GLOVES_PHOENIX"), AetherArmorMaterial.PHOENIX, 4).setDamage(3).setDamageType(DamageType.FIRE));

        ARMOR_GLOVES_NEPTUNE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGlovesNeptune("armor.gloves.neptune", itemKey("armor_gloves_neptune"), itemID("ARMOR_GLOVES_NEPTUNE"), AetherArmorMaterial.NEPTUNE, 4).setDamage(3).setDamageType(DamageType.DROWN));


        ARMOR_TALISMAN_REGEN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemRegenStone("armor.talisman.regen", itemKey("armor_talisman_regen"), itemID("ARMOR_TALISMAN_REGEN"), "regen_trinket"));

        ARMOR_TALISMAN_BUBBLE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemIronBubble("armor.talisman.bubble", itemKey("armor_talisman_bubble"), itemID("ARMOR_TALISMAN_BUBBLE"), "bubble"));

        ARMOR_TALISMAN_FEATHER_GOLD = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGoldenFeather("armor.talisman.feather.gold", itemKey("armor_talisman_feather_gold"), itemID("ARMOR_TALISMAN_FEATHER_GOLD"), "feather_gold"));


        ARMOR_SHIELD_REPULSION = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemRepulsionShield("armor.shield.repulsion", itemKey("armor_shield_repulsion"), itemID("ARMOR_SHIELD_REPULSION"), "shield"));


        ARMOR_TALISMAN_LEATHER = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.leather", itemKey("armor_talisman_leather"), itemID("ARMOR_TALISMAN_LEATHER"), "cloth", ArmorMaterial.LEATHER));

        ARMOR_TALISMAN_CHAINMAIL = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemCombatPendant("armor.talisman.chainmail", itemKey("armor_talisman_chainmail"), itemID("ARMOR_TALISMAN_CHAINMAIL"), ArmorMaterial.CHAINMAIL));

        ARMOR_TALISMAN_IRON = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.iron", itemKey("armor_talisman_iron"), itemID("ARMOR_TALISMAN_IRON"), "iron", ArmorMaterial.IRON));

        ///  we treat gold differently when harvesting as such we don't make it take damage when harvesting the way other pendant might
        ARMOR_TALISMAN_GOLD = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.gold", itemKey("armor_talisman_gold"), itemID("ARMOR_TALISMAN_GOLD"), "gold", ArmorMaterial.GOLD));

        ARMOR_TALISMAN_DIAMOND = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.diamond", itemKey("armor_talisman_diamond"), itemID("ARMOR_TALISMAN_DIAMOND"), "diamond", ArmorMaterial.DIAMOND)
                .setHarvestDamageable()
            );

        ARMOR_TALISMAN_STEEL = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.steel", itemKey("armor_talisman_steel"), itemID("ARMOR_TALISMAN_STEEL"), "steel", ArmorMaterial.STEEL));

        ARMOR_TALISMAN_ZANITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemPendant("armor.talisman.zanite", itemKey("armor_talisman_zanite"), itemID("ARMOR_TALISMAN_ZANITE"), "zanite", AetherArmorMaterial.ZANITE)
                .setHarvestDamageable()
            );

        ARMOR_TALISMAN_GRAVITITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemGravititePendant("armor.talisman.gravitite", itemKey("armor_talisman_gravitite"), itemID("ARMOR_TALISMAN_GRAVITITE"), AetherArmorMaterial.GRAVITITE));

        ARMOR_TALISMAN_ICE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemIcePendant("armor.talisman.ice", itemKey("armor_talisman_ice"), itemID("ARMOR_TALISMAN_ICE"), "ice"));


        ARMOR_CAPE_AGILITY = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAgilityCapeArmor("armor.cape.agility", itemKey("armor_cape_agility"), itemID("ARMOR_CAPE_AGILITY"), "agility", 5));

        ARMOR_CAPE_SWET = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemSwetCapeArmor("armor.cape.swet", itemKey("armor_cape_swet"), itemID("ARMOR_CAPE_SWET"), "swet", 5));

        ARMOR_CAPE_INVISIBILITY = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemInvisibilityCapeArmor("armor.cape.invisibility", itemKey("armor_cape_invisibility"), itemID("ARMOR_CAPE_INVISIBILITY"), "invisibility", 5));


        ARMOR_CAPE_BLACK = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.black", itemKey("armor_cape_black"), itemID("ARMOR_CAPE_BLACK"), "black", 5));

        ARMOR_CAPE_RED = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.red", itemKey("armor_cape_red"), itemID("ARMOR_CAPE_RED"), "red", 5));

        ARMOR_CAPE_GREEN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.green", itemKey("armor_cape_green"), itemID("ARMOR_CAPE_GREEN"), "green", 5));

        ARMOR_CAPE_BROWN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.brown", itemKey("armor_cape_brown"), itemID("ARMOR_CAPE_BROWN"), "brown", 5));

        ARMOR_CAPE_BLUE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.blue", itemKey("armor_cape_blue"), itemID("ARMOR_CAPE_BLUE"), "blue", 5));

        ARMOR_CAPE_PURPLE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.purple", itemKey("armor_cape_purple"), itemID("ARMOR_CAPE_PURPLE"), "purple", 5));

        ARMOR_CAPE_CYAN = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.cyan", itemKey("armor_cape_cyan"), itemID("ARMOR_CAPE_CYAN"), "cyan", 5));

        ARMOR_CAPE_SILVER = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.silver", itemKey("armor_cape_silver"), itemID("ARMOR_CAPE_SILVER"), "silver", 5));

        ARMOR_CAPE_GRAY = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.gray", itemKey("armor_cape_gray"), itemID("ARMOR_CAPE_GRAY"), "gray", 5));

        ARMOR_CAPE_PINK = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.pink", itemKey("armor_cape_pink"), itemID("ARMOR_CAPE_PINK"), "pink", 5));

        ARMOR_CAPE_LIME = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.lime", itemKey("armor_cape_lime"), itemID("ARMOR_CAPE_LIME"), "lime", 5));

        ARMOR_CAPE_YELLOW = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.yellow", itemKey("armor_cape_yellow"), itemID("ARMOR_CAPE_YELLOW"), "yellow", 5));

        ARMOR_CAPE_LIGHTBLUE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.lightblue", itemKey("armor_cape_lightblue"), itemID("ARMOR_CAPE_LIGHTBLUE"), "lightblue", 5));

        ARMOR_CAPE_MAGENTA = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.magenta", itemKey("armor_cape_magenta"), itemID("ARMOR_CAPE_MAGENTA"), "magenta", 5));

        ARMOR_CAPE_ORANGE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.orange", itemKey("armor_cape_orange"), itemID("ARMOR_CAPE_ORANGE"), "orange", 5));

        ARMOR_CAPE_WHITE = itemBuilder(CreativeInventoryCategory.ARMOR)
            .build(new ItemAccessoryArmor("armor.cape.white", itemKey("armor_cape_white"), itemID("ARMOR_CAPE_WHITE"), "white", 5));

    }

}
