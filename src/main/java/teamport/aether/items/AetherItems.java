package teamport.aether.items;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.*;
import net.minecraft.core.item.tag.ItemTags;
import teamport.aether.AetherConfig;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.itemtool.*;
import teamport.aether.items.itemtool.ItemToolGravitite.ItemToolAxeGravitite;
import teamport.aether.items.itemtool.ItemToolGravitite.ItemToolPickaxeGravitite;
import teamport.aether.items.itemtool.ItemToolGravitite.ItemToolShovelGravitite;
import teamport.aether.items.itemtool.ItemToolGravitite.ItemToolSwordGravitite;
import teamport.aether.items.itemtool.ItemToolHolystone.ItemToolAxeHolystone;
import teamport.aether.items.itemtool.ItemToolHolystone.ItemToolPickaxeHolystone;
import teamport.aether.items.itemtool.ItemToolHolystone.ItemToolShovelHolystone;
import teamport.aether.items.itemtool.ItemToolHolystone.ItemToolSwordHolystone;
import teamport.aether.items.itemtool.ItemToolZanite.ItemToolAxeZanite;
import teamport.aether.items.itemtool.ItemToolZanite.ItemToolPickaxeZanite;
import teamport.aether.items.itemtool.ItemToolZanite.ItemToolShovelZanite;
import teamport.aether.items.itemtool.ItemToolZanite.ItemToolSwordZanite;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.aether.AetherMod.MOD_ID;

public final class AetherItems {

    public static int itemID =  AetherConfig.itemIDs;

    public static int itemID(String itemName) {
        try {
            return AetherConfig.cfg.getInt(AetherConfig.itemIDs + "." + itemName);
        } catch (NullPointerException e) {
            AetherConfig.properties.addEntry(AetherConfig.itemIDs + "." + itemName, itemID);
            return itemID++;
        }
    }

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
    public static Item AMBER;

    public static Item PETAL_AECHOR;
    public static Item STICK_SKYROOT;
    public static Item AMMO_DART_GOLDEN;

    public static Item AMMO_DART_POISON;
    public static Item AMMO_DART_ENCHANTED;
    public static Item TOOL_SHOOTER;

    public static Item AMBROSIUM;

    public static Item ZANITE;
    public static Item BUCKET_SKYROOT;

    public static Item BUCKET_SKYROOT_WATER;
    public static Item BUCKET_SKYROOT_MILK;
    public static Item BUCKET_SKYROOT_POISON;
    public static Item BUCKET_SKYROOT_REMEDY;
    public static Item BUCKET_SKYROOT_ICECREAM;
    public static Item FOOD_HEALING_STONE;

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
    public static Item TOOL_SHIELD_REPULSION;
    public static Item ARMOR_GLOVES_LEATHER;

    public static Item ARMOR_GLOVES_CHAIN;
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

    public static Item ARMOR_TALISMAN_CHAIN;
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
    public static Item ARMOR_CAPE_RED;

    public static Item ARMOR_CAPE_YELLOW;
    public static Item ARMOR_CAPE_BLUE;
    public static Item ARMOR_CAPE_WHITE;
    public static Item FOOD_GUMMY_BLUE;

    public static Item FOOD_GUMMY_GOLD;
    public static Item PARACHUTE_CLOUD;
    public static Item PARACHUTE_CLOUD_GOLD;
    public static Item LIFESHARD;
    public static Item LANTERN_FIREFLY_SILVER;
    public static Item DOOR_SKYROOT;
    public static Item DOOR_GLASS_AMBROSIUM;
    public static Item AMMO_WINDBALL;

    public static Item PROJECTILE_FIRE;

    public static Item PROJECTILE_ICE;
    public static Item PROJECTILE_LIGHTNING;
    private static boolean hasInit = false;

    public static void init(){
        if(!hasInit){
            hasInit = true;
            initializeItems();
        }
    }

    public static String itemKey(String string) {
        return MOD_ID + ":item/" + string;
    }

    private static void initializeItems() {
        // TODO maybe moving some item initialization in their own method
        AetherItems.registerArmor();
        AetherItems.registerTool();
        AetherItems.registerFood();
        AetherItems.registerOther();




        MEDAL_VICTORY = new ItemBuilder(MOD_ID).build(new Item("medal.victory", itemKey("medal_victory"), itemID("MEDAL_VICTORY")));

        KEY_BRONZE = new ItemBuilder(MOD_ID).build(new Item("key.bronze", itemKey("key_bronze"), itemID("KEY_BRONZE")));
        KEY_SILVER = new ItemBuilder(MOD_ID).build(new Item("key.silver", itemKey("key_silver"), itemID("KEY_SILVER")));
        KEY_GOLD = new ItemBuilder(MOD_ID).build(new Item("key.gold", itemKey("key_gold"), itemID("KEY_GOLD")));

        EGG_MOA_BLUE = new ItemBuilder(MOD_ID).build(new Item("egg.moa.blue", itemKey("egg_moa_blue"), itemID("EGG_MOA_BLUE")));
        EGG_MOA_WHITE = new ItemBuilder(MOD_ID).build(new Item("egg.moa.white", itemKey("egg_moa_white"), itemID("EGG_MOA_WHITE")));
        EGG_MOA_BLACK = new ItemBuilder(MOD_ID).build(new Item("egg.moa.black", itemKey("egg_moa_black"), itemID("EGG_MOA_BLACK")));

        RECORD_AETHER = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.aether", itemKey("record_aether"), itemID("RECORD_AETHER"), "aether:aether.tune", "Emile van Kriken"));
        RECORD_MORNING = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.morning", itemKey("record_morning"), itemID("RECORD_MORNING"), "aether:a.morning.wish", "Emile van Kriken"));
        RECORD_DAWN = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.dawn", itemKey("record_dawn"), itemID("RECORD_DAWN"), "aether:ascending.dawn", "Emile van Kriken"));

        AMBER = new ItemBuilder(MOD_ID).build(new Item("amber", itemKey("amber"), itemID("AMBER")));
        PETAL_AECHOR = new ItemBuilder(MOD_ID).build(new Item("petal.aechor", itemKey("petal_aechor"), itemID("PETAL_AECHOR")));
        STICK_SKYROOT = new ItemBuilder(MOD_ID).build(new Item("stick.skyroot", itemKey("stick_skyroot"), itemID("STICK_SKYROOT")));

        AMBROSIUM = new ItemBuilder(MOD_ID).build(new ItemFood("ambrosium", itemKey("ambrosium"), itemID("AMBROSIUM"), 1, 20, false, 64));
        ZANITE = new ItemBuilder(MOD_ID).build(new Item("zanite", itemKey("zanite"), itemID("ZANITE")));

        BUCKET_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemBucketSkyrootEmpty("bucket.skyroot", itemKey("bucket_skyroot"), itemID("BUCKET_SKYROOT")));
        BUCKET_SKYROOT_WATER = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.water", itemKey("bucket_skyroot_water"), itemID("BUCKET_SKYROOT_WATER"), Blocks.FLUID_WATER_FLOWING)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_MILK = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.milk", itemKey("bucket_skyroot_milk"), itemID("BUCKET_SKYROOT_MILK"), null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_REMEDY = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.remedy", itemKey("bucket_skyroot_remedy"), itemID("BUCKET_SKYROOT_REMEDY"), null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_POISON = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.poison", itemKey("bucket_skyroot_poison"), itemID("BUCKET_SKYROOT_POISON"), null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_ICECREAM = new ItemBuilder(MOD_ID).build(new ItemBucketSkyrootIceCream("bucket.skyroot.icecream", itemKey("bucket_skyroot_icecream"), itemID("BUCKET_SKYROOT_ICECREAM"), 10, 4)).setContainerItem(BUCKET_SKYROOT);
        //TODO Poison and remedy buckets need to be drinkable and give effects


        /**
         SUGGESTION all tool do extremely poorly outside of aether, maybe scale them down to a specific tool tier?
         An example can be found in ItemToolAxeZanite
         */
        TOOL_SWORD_SKYROOT = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.skyroot", itemKey("tool_sword_skyroot"), itemID("TOOL_SWORD_SKYROOT"), AetherToolMaterial.SKYROOT));
        TOOL_SHOVEL_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.skyroot", itemKey("tool_shovel_skyroot"), itemID("TOOL_SHOVEL_SKYROOT"), AetherToolMaterial.SKYROOT));
        TOOL_PICKAXE_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.skyroot", itemKey("tool_pickaxe_skyroot"), itemID("TOOL_PICKAXE_SKYROOT"), AetherToolMaterial.SKYROOT));
        TOOL_AXE_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.skyroot", itemKey("tool_axe_skyroot"), itemID("TOOL_AXE_SKYROOT"), AetherToolMaterial.SKYROOT));

        TOOL_SWORD_HOLYSTONE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordHolystone("tool.sword.holystone", itemKey("tool_sword_holystone"), itemID("TOOL_SWORD_HOLYSTONE"), AetherToolMaterial.HOLYSTONE));
        TOOL_SHOVEL_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolShovelHolystone("tool.shovel.holystone", itemKey("tool_shovel_holystone"), itemID("TOOL_SHOVEL_HOLYSTONE"), AetherToolMaterial.HOLYSTONE));
        TOOL_PICKAXE_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeHolystone("tool.pickaxe.holystone", itemKey("tool_pickaxe_holystone"), itemID("TOOL_PICKAXE_HOLYSTONE"), AetherToolMaterial.HOLYSTONE));
        TOOL_AXE_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolAxeHolystone("tool.axe.holystone", itemKey("tool_axe_holystone"), itemID("TOOL_AXE_HOLYSTONE"), AetherToolMaterial.HOLYSTONE) );

        TOOL_SWORD_ZANITE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordZanite("tool.sword.zanite", itemKey("tool_sword_zanite"), itemID("TOOL_SWORD_ZANITE"), AetherToolMaterial.ZANITE));
        TOOL_SHOVEL_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolShovelZanite("tool.shovel.zanite", itemKey("tool_shovel_zanite"), itemID("TOOL_SHOVEL_ZANITE"), AetherToolMaterial.ZANITE));
        TOOL_PICKAXE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemKey("tool_pickaxe_zanite"), itemID("TOOL_PICKAXE_ZANITE"), AetherToolMaterial.ZANITE));
        TOOL_AXE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolAxeZanite("tool.axe.zanite", itemKey("tool_axe_zanite"), itemID("TOOL_AXE_ZANITE"), AetherToolMaterial.ZANITE));

        TOOL_SWORD_GRAVITITE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordGravitite("tool.sword.gravitite", itemKey("tool_sword_gravitite"), itemID("TOOL_SWORD_GRAVITITE"), AetherToolMaterial.GRAVITITE));
        TOOL_SHOVEL_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolShovelGravitite("tool.shovel.gravitite", itemKey("tool_shovel_gravitite"), itemID("TOOL_SHOVEL_GRAVITITE"), AetherToolMaterial.GRAVITITE));
        TOOL_PICKAXE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeGravitite("tool.pickaxe.gravitite", itemKey("tool_pickaxe_gravitite"), itemID("TOOL_PICKAXE_GRAVITITE"), AetherToolMaterial.GRAVITITE));
        TOOL_AXE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolAxeGravitite("tool.axe.gravitite", itemKey("tool_axe_gravitite"), itemID("TOOL_AXE_GRAVITITE"), AetherToolMaterial.GRAVITITE));

        TOOL_SWORD_VALKYRIE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.valkyrie", itemKey("tool_sword_valkyrie"), itemID("TOOL_SWORD_VALKYRIE"), AetherToolMaterial.VALKYRIE));
        TOOL_SHOVEL_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.valkyrie", itemKey("tool_shovel_valkyrie"), itemID("TOOL_SHOVEL_VALKYRIE"), AetherToolMaterial.VALKYRIE));
        TOOL_PICKAXE_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.valkyrie", itemKey("tool_pickaxe_valkyrie"), itemID("TOOL_PICKAXE_VALKYRIE"), AetherToolMaterial.VALKYRIE));
        TOOL_AXE_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.valkyrie", itemKey("tool_axe_valkyrie"), itemID("TOOL_AXE_VALKYRIE"), AetherToolMaterial.VALKYRIE));

        TOOL_KNIFE_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolKnifeLightning("tool.knife.lightning", itemKey("tool_knife_lightning"), itemID("TOOL_KNIFE_LIGHTNING")));
        //TODO Lightning knife model is a bit broken
        AMMO_HAMMER_HEAD = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("ammo.hammer.head", itemKey("ammo_hammer_head"), itemID("AMMO_HAMMER_HEAD")));
        TOOL_HAMMER_NOTCH = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolHammerNotch("tool.hammer.notch", itemKey("tool_hammer_notch"), itemID("TOOL_HAMMER_NOTCH"), AetherToolMaterial.SPECIAL));

        TOOL_BOW_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemBowPhoenix("tool.bow.phoenix", itemKey("tool_bow_phoenix"), itemID("TOOL_BOW_PHOENIX")));
        //TODO potentially add flaming arrow versions of golden arrows/purple arrows

        AMMO_DART_GOLDEN = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.golden", itemKey("ammo_dart_golden"), itemID("AMMO_DART_GOLDEN")));
        AMMO_DART_POISON = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.poison", itemKey("ammo_dart_poison"), itemID("AMMO_DART_POISON")));
        AMMO_DART_ENCHANTED = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.enchanted", itemKey("ammo_dart_enchanted"), itemID("AMMO_DART_ENCHANTED")));
        //TODO Poison darts need to be added and given their effect when they hit something, enchanted darts need their ability to be more accurate and do more damage

        TOOL_SHOOTER = new ItemBuilder(MOD_ID).build(new ItemShooter("tool.shooter", itemKey("tool_shooter"), itemID("TOOL_SHOOTER")));

        TOOL_SHIELD_REPULSION = new ItemBuilder(MOD_ID).build(new Item("tool.shield.repulsion", itemKey("tool_shield_repulsion"), itemID("TOOL_SHIELD_REPULSION")));
        //TODO Shield needs its ability to activate when held, blocks all projectiles from hitting you


        ARMOR_HELMET_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.zanite", itemKey("armor_helmet_zanite"), itemID("ARMOR_HELMET_ZANITE"), AetherArmorMaterial.ZANITE, 3));
        ARMOR_CHESTPLATE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.zanite", itemKey("armor_chestplate_zanite"), itemID("ARMOR_CHESTPLATE_ZANITE"), AetherArmorMaterial.ZANITE, 2));
        ARMOR_LEGGINGS_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.zanite", itemKey("armor_leggings_zanite"), itemID("ARMOR_LEGGINGS_ZANITE"), AetherArmorMaterial.ZANITE, 1));
        ARMOR_BOOTS_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.zanite", itemKey("armor_boots_zanite"), itemID("ARMOR_BOOTS_ZANITE"), AetherArmorMaterial.ZANITE, 0));

        ARMOR_HELMET_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.gravitite", itemKey("armor_helmet_gravitite"), itemID("ARMOR_HELMET_GRAVITITE"), AetherArmorMaterial.GRAVITITE, 3));
        ARMOR_CHESTPLATE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.gravitite", itemKey("armor_chestplate_gravitite"), itemID("ARMOR_CHESTPLATE_GRAVITITE"), AetherArmorMaterial.GRAVITITE, 2));
        ARMOR_LEGGINGS_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.gravitite", itemKey("armor_leggings_gravitite"), itemID("ARMOR_LEGGINGS_GRAVITITE"), AetherArmorMaterial.GRAVITITE, 1));
        ARMOR_BOOTS_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.gravitite", itemKey("armor_boots_gravitite"), itemID("ARMOR_BOOTS_GRAVITITE"), AetherArmorMaterial.GRAVITITE, 0));

        ARMOR_HELMET_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.obsidian", itemKey("armor_helmet_obsidian"), itemID("ARMOR_HELMET_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, 3));
        ARMOR_CHESTPLATE_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.obsidian", itemKey("armor_chestplate_obsidian"), itemID("ARMOR_CHESTPLATE_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, 2));
        ARMOR_LEGGINGS_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.obsidian", itemKey("armor_leggings_obsidian"), itemID("ARMOR_LEGGINGS_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, 1));
        ARMOR_BOOTS_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.obsidian", itemKey("armor_boots_obsidian"), itemID("ARMOR_BOOTS_OBSIDIAN"), AetherArmorMaterial.OBSIDIAN, 0));

        ARMOR_HELMET_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.phoenix", itemKey("armor_helmet_phoenix"), itemID("ARMOR_HELMET_PHOENIX"), AetherArmorMaterial.PHOENIX, 3));
        ARMOR_CHESTPLATE_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.phoenix", itemKey("armor_chestplate_phoenix"), itemID("ARMOR_CHESTPLATE_PHOENIX"), AetherArmorMaterial.PHOENIX, 2));
        ARMOR_LEGGINGS_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.phoenix", itemKey("armor_leggings_phoenix"), itemID("ARMOR_LEGGINGS_PHOENIX"), AetherArmorMaterial.PHOENIX, 1));
        ARMOR_BOOTS_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.phoenix", itemKey("armor_boots_phoenix"), itemID("ARMOR_BOOTS_PHOENIX"), AetherArmorMaterial.PHOENIX, 0));
        // TODO obsidian armor is craftable by freezing phoenix armor in a freezer

        ARMOR_HELMET_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.neptune", itemKey("armor_helmet_neptune"), itemID("ARMOR_HELMET_NEPTUNE"), AetherArmorMaterial.NEPTUNE, 3));
        ARMOR_CHESTPLATE_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.neptune", itemKey("armor_chestplate_neptune"), itemID("ARMOR_CHESTPLATE_NEPTUNE"), AetherArmorMaterial.NEPTUNE, 2));
        ARMOR_LEGGINGS_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.neptune", itemKey("armor_leggings_neptune"), itemID("ARMOR_LEGGINGS_NEPTUNE"), AetherArmorMaterial.NEPTUNE, 1));
        ARMOR_BOOTS_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.neptune", itemKey("armor_boots_neptune"), itemID("ARMOR_BOOTS_NEPTUNE"), AetherArmorMaterial.NEPTUNE, 0));

        ARMOR_GLOVES_LEATHER = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.leather", itemKey("armor_gloves_leather"), itemID("ARMOR_GLOVES_LEATHER")));
        ARMOR_GLOVES_CHAIN = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.chain", itemKey("armor_gloves_chain"), itemID("ARMOR_GLOVES_CHAIN")));
        ARMOR_GLOVES_IRON = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.iron", itemKey("armor_gloves_iron"), itemID("ARMOR_GLOVES_IRON")));
        ARMOR_GLOVES_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.gold", itemKey("armor_gloves_gold"), itemID("ARMOR_GLOVES_GOLD")));
        ARMOR_GLOVES_DIAMOND = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.diamond", itemKey("armor_gloves_diamond"), itemID("ARMOR_GLOVES_DIAMOND")));
        ARMOR_GLOVES_STEEL = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.steel", itemKey("armor_gloves_steel"), itemID("ARMOR_GLOVES_STEEL")));
        ARMOR_GLOVES_ZANITE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.zanite", itemKey("armor_gloves_zanite"), itemID("ARMOR_GLOVES_ZANITE")));
        ARMOR_GLOVES_GRAVITITE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.gravitite", itemKey("armor_gloves_gravitite"), itemID("ARMOR_GLOVES_GRAVITITE")));
        ARMOR_GLOVES_OBSIDIAN = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.obsidian", itemKey("armor_gloves_obsidian"), itemID("ARMOR_GLOVES_OBSIDIAN")));
        ARMOR_GLOVES_PHOENIX = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.phoenix", itemKey("armor_gloves_phoenix"), itemID("ARMOR_GLOVES_PHOENIX")));
        ARMOR_GLOVES_NEPTUNE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.neptune", itemKey("armor_gloves_neptune"), itemID("ARMOR_GLOVES_NEPTUNE")));
        //TODO Gloves need their system added and be wearable


        TOOL_SWORD_PIG = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordPig("tool.sword.pig", itemKey("tool_sword_pig"), itemID("TOOL_SWORD_PIG"), AetherToolMaterial.SPECIAL));
        TOOL_SWORD_VAMPIRE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordVampire("tool.sword.vampire", itemKey("tool_sword_vampire"), itemID("TOOL_SWORD_VAMPIRE"), AetherToolMaterial.SPECIAL));

        TOOL_SWORD_FLAME = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordFire("tool.sword.flame", itemKey("tool_sword_flame"), itemID("TOOL_SWORD_FLAME"), AetherToolMaterial.SPECIAL));
        TOOL_SWORD_HOLY = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordHoly("tool.sword.holy", itemKey("tool_sword_holy"), itemID("TOOL_SWORD_HOLY"), AetherToolMaterial.SPECIAL));
        TOOL_SWORD_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordLightning("tool.sword.lightning", itemKey("tool_sword_lightning"), itemID("TOOL_SWORD_LIGHTNING"), AetherToolMaterial.SPECIAL));

        TOOL_STAFF_NATURE = new ItemBuilder(MOD_ID).build(new Item("tool.staff.nature", itemKey("tool_staff_nature"), itemID("TOOL_STAFF_NATURE")));
        TOOL_STAFF_CLOUD = new ItemBuilder(MOD_ID).build(new Item("tool.staff.cloud", itemKey("tool_staff_cloud"), itemID("TOOL_STAFF_CLOUD")));
        //TODO Cloud staff when used should spawn 2 cloud allies on your shoulders that attack enemies with ice projectiles,
        //TODO Nature staff should either be removed or reworked, og use was luring moas but we have a system built into bta for that now


        ARMOR_TALISMAN_REGEN = new ItemBuilder(MOD_ID).build(new ItemRegenStone("armor.talisman.regen", itemKey("armor_talisman_regen"), itemID("ARMOR_TALISMAN_REGEN")));
        ARMOR_TALISMAN_BUBBLE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.bubble", itemKey("armor_talisman_bubble"), itemID("ARMOR_TALISMAN_BUBBLE")));
        ARMOR_TALISMAN_FEATHER_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.feather.gold", itemKey("armor_talisman_feather_gold"), itemID("ARMOR_TALISMAN_FEATHER_GOLD")));
        //TODO all talismans need their effects and also work with the new armor system, regen stone heals half a heart every 5 seconds, iron bubble gives water breathing, feather gold makes you fall like a chicken

        ARMOR_TALISMAN_LEATHER = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.leather", itemKey("armor_talisman_leather"), itemID("ARMOR_TALISMAN_LEATHER")));
        ARMOR_TALISMAN_CHAIN = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.chain", itemKey("armor_talisman_chain"), itemID("ARMOR_TALISMAN_CHAIN")));
        ARMOR_TALISMAN_IRON = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.iron", itemKey("armor_talisman_iron"), itemID("ARMOR_TALISMAN_IRON")));
        ARMOR_TALISMAN_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.gold", itemKey("armor_talisman_gold"), itemID("ARMOR_TALISMAN_GOLD")));
        ARMOR_TALISMAN_DIAMOND = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.diamond", itemKey("armor_talisman_diamond"), itemID("ARMOR_TALISMAN_DIAMOND")));
        ARMOR_TALISMAN_STEEL = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.steel", itemKey("armor_talisman_steel"), itemID("ARMOR_TALISMAN_STEEL")));
        ARMOR_TALISMAN_ZANITE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.zanite", itemKey("armor_talisman_zanite"), itemID("ARMOR_TALISMAN_ZANITE")));
        ARMOR_TALISMAN_GRAVITITE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.gravitite", itemKey("armor_talisman_gravitite"), itemID("ARMOR_TALISMAN_GRAVITITE")));
        ARMOR_TALISMAN_ICE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.ice", itemKey("armor_talisman_ice"), itemID("ARMOR_TALISMAN_ICE")));
        //TODO all necklaces can be equipped in ? talisman slot, ice one freezes water and lava into ice and obsidian as you walk over them, zanite one increases mining speed as you mine and its durability goes down
        //TODO gold one will grant silk touch effect to you and lose durability as you mine, other ones can be cosmetic if uses cant be thought of

        ARMOR_CAPE_AGILITY = new ItemBuilder(MOD_ID).build(new Item("armor.cape.agility", itemKey("armor_cape_agility"), itemID("ARMOR_CAPE_AGILITY")));
        ARMOR_CAPE_SWET = new ItemBuilder(MOD_ID).build(new Item("armor.cape.swet", itemKey("armor_cape_swet"), itemID("ARMOR_CAPE_SWET")));
        ARMOR_CAPE_INVISIBILITY = new ItemBuilder(MOD_ID).build(new Item("armor.cape.invisibility", itemKey("armor_cape_invisibility"), itemID("ARMOR_CAPE_INVISIBILITY")));
        //TODO Capes are equipped in the cape slot, agility cape lets you walk up 1 block without jumping, swet cape is decorative, invisibility cape turns you invisible and enemies wont aggro unless hit, like creative

        ARMOR_CAPE_WHITE = new ItemBuilder(MOD_ID).build(new Item("armor.cape.white", itemKey("armor_cape_white"), itemID("ARMOR_CAPE_WHITE")));
        ARMOR_CAPE_RED = new ItemBuilder(MOD_ID).build(new Item("armor.cape.red", itemKey("armor_cape_red"), itemID("ARMOR_CAPE_RED")));
        ARMOR_CAPE_YELLOW = new ItemBuilder(MOD_ID).build(new Item("armor.cape.yellow", itemKey("armor_cape_yellow"), itemID("ARMOR_CAPE_YELLOW")));
        ARMOR_CAPE_BLUE = new ItemBuilder(MOD_ID).build(new Item("armor.cape.blue", itemKey("armor_cape_blue"), itemID("ARMOR_CAPE_BLUE")));
        //TODO Decorative capes, potential to add cape color for each wool color,


        FOOD_HEALING_STONE = new ItemBuilder(MOD_ID).build(new ItemFood("food.healing.stone", itemKey("food_healing_stone"), itemID("FOOD_HEALING_STONE"), 4, 1, false, 16));

        FOOD_GUMMY_BLUE = new ItemBuilder(MOD_ID).build(new ItemFood("food.gummy.blue", itemKey("food_gummy_blue"), itemID("FOOD_GUMMY_BLUE"), 20, 1, false, 64));
        FOOD_GUMMY_GOLD = new ItemBuilder(MOD_ID).build(new ItemFood("food.gummy.gold", itemKey("food_gummy_gold"), itemID("FOOD_GUMMY_GOLD"), 40, 1, false, 64));

        LIFESHARD = new ItemBuilder(MOD_ID).build(new ItemLifeShard("food.lifeshard", itemKey("food_lifeshard"), itemID("LIFESHARD")).setMaxStackSize(10));

        PARACHUTE_CLOUD = new ItemBuilder(MOD_ID).build(new Item("parachute.cloud", itemKey("parachute_cloud"), itemID("PARACHUTE_CLOUD")));
        PARACHUTE_CLOUD_GOLD = new ItemBuilder(MOD_ID).build(new Item("parachute.cloud.gold", itemKey("parachute_cloud_gold"), itemID("PARACHUTE_CLOUD_GOLD")));
        //TODO Parachutes on use spawn a cloud entity below you that makes you fall slowly, gold one has 20 uses, when you land it goes away

        LANTERN_FIREFLY_SILVER = new ItemBuilder(MOD_ID).build(new ItemPlaceable("lantern.firefly.silver", itemKey("lantern_firefly_silver"), itemID("LANTERN_FIREFLY_SILVER"), AetherBlocks.LANTERN_FIREFLY_SILVER));
        DOOR_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemDoor("door.skyroot", itemKey("door_skyroot"), itemID("DOOR_SKYROOT"), AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM, AetherBlocks.DOOR_PLANKS_SKYROOT_TOP));
        DOOR_GLASS_AMBROSIUM = new ItemBuilder(MOD_ID).build(new ItemDoor("door.glass.ambrosium", itemKey("door_glass_ambrosium"), itemID("DOOR_GLASS_AMBROSIUM"), AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP));

        AMMO_WINDBALL = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("ammo.windball", itemKey("ammo_windball"), itemID("AMMO_WINDBALL")));
        PROJECTILE_FIRE = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.fire", itemKey("projectile_fire"), itemID("PROJECTILE_FIRE")));
        PROJECTILE_ICE = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.ice", itemKey("projectile_ice"), itemID("PROJECTILE_ICE")));
        PROJECTILE_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.lightning", itemKey("projectile_lightning"), itemID("PROJECTILE_LIGHTNING")));
        //TODO These are to be used as the textures for the projectile the zephyrs shoot (windball), and the other 3 as the boss projectiles that bounce off walls and can be deflected back

//        BLANK = new ItemBuilder(MOD_ID)
//                .build(new Item("BLANK", "aether:item/blank", ID));

    }

    // maybe for later when cleanign up
    private static void registerOther() {}
    private static void registerTool() {}
    private static void registerFood() {}
    private static void registerArmor() {}

}
