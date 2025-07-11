package teamport.aether.items;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.*;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.AetherBlocks;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherItems {

    public static String itemKey(String string) {
        return MOD_ID + ":item/" + string;
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

    public void initializeItems() {

        MEDAL_VICTORY = new ItemBuilder(MOD_ID).build(new Item("medal.victory", itemKey("medal_victory"), 17000));

        KEY_BRONZE = new ItemBuilder(MOD_ID).build(new Item("key.bronze", itemKey("key_bronze"), 17001));
        KEY_SILVER = new ItemBuilder(MOD_ID).build(new Item("key.silver", itemKey("key_silver"), 17002));
        KEY_GOLD = new ItemBuilder(MOD_ID).build(new Item("key.gold", itemKey("key_gold"), 17003));

        EGG_MOA_BLUE = new ItemBuilder(MOD_ID).build(new Item("egg.moa.blue", itemKey("egg_moa_blue"), 17005));
        EGG_MOA_WHITE = new ItemBuilder(MOD_ID).build(new Item("egg.moa.white", itemKey("egg_moa_white"), 17006));
        EGG_MOA_BLACK = new ItemBuilder(MOD_ID).build(new Item("egg.moa.black", itemKey("egg_moa_black"), 17007));

        RECORD_AETHER = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.aether", itemKey("record_aether"), 17010, "aether:aether.tune", "Emile van Kriken"));
        RECORD_MORNING = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.morning", itemKey("record_morning"), 17011, "aether:a.morning.wish", "Emile van Kriken"));
        RECORD_DAWN = new ItemBuilder(MOD_ID).build(new ItemDiscMusic("record.dawn", itemKey("record_dawn"), 17012, "aether:ascending.dawn", "Emile van Kriken"));

        AMBER = new ItemBuilder(MOD_ID).build(new Item("amber", itemKey("amber"), 17015));
        PETAL_AECHOR = new ItemBuilder(MOD_ID).build(new Item("petal.aechor", itemKey("petal_aechor"), 17016));
        STICK_SKYROOT = new ItemBuilder(MOD_ID).build(new Item("stick.skyroot", itemKey("stick_skyroot"), 17017));

        AMBROSIUM = new ItemBuilder(MOD_ID).build(new ItemFood("ambrosium", itemKey("ambrosium"), 17020, 1, 20, false, 64));
        ZANITE = new ItemBuilder(MOD_ID).build(new Item("zanite", itemKey("zanite"), 17021));

        BUCKET_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemBucketSkyrootEmpty("bucket.skyroot", itemKey("bucket_skyroot"), 17030));
        BUCKET_SKYROOT_WATER = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.water", itemKey("bucket_skyroot_water"), 17031, Blocks.FLUID_WATER_FLOWING)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_MILK = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.milk", itemKey("bucket_skyroot_milk"), 17032, null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_REMEDY = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.remedy", itemKey("bucket_skyroot_remedy"), 17033, null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_POISON = new ItemBuilder(MOD_ID).build(new ItemBucketSkyroot("bucket.skyroot.poison", itemKey("bucket_skyroot_poison"), 17034, null)).setContainerItem(BUCKET_SKYROOT);
        BUCKET_SKYROOT_ICECREAM = new ItemBuilder(MOD_ID).build(new ItemBucketSkyrootIceCream("bucket.skyroot.icecream", itemKey("bucket_skyroot_icecream"), 17035, 10, 4)).setContainerItem(BUCKET_SKYROOT);
        //TODO Poison and remedy buckets need to be drinkable and give effects


        TOOL_SWORD_SKYROOT = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.skyroot", itemKey("tool_sword_skyroot"), 17050, AetherToolMaterial.SKYROOT));
        // TODO 7.2 port: skyroot sword drops fewer item
        TOOL_SHOVEL_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.skyroot", itemKey("tool_shovel_skyroot"), 17051, AetherToolMaterial.SKYROOT));
        TOOL_PICKAXE_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.skyroot", itemKey("tool_pickaxe_skyroot"), 17052, AetherToolMaterial.SKYROOT));
        TOOL_AXE_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.skyroot", itemKey("tool_axe_skyroot"), 17053, AetherToolMaterial.SKYROOT));
        // TODO skyroot

        TOOL_SWORD_HOLYSTONE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.holystone", itemKey("tool_sword_holystone"), 17054, AetherToolMaterial.HOLYSTONE) {
            public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
                if (itemRand.nextInt(16) == 0) {
                    target.dropItem(AMBROSIUM.id, 1);
                }
                itemstack.damageItem(1, attacker);
                return true;
            }
        });
        TOOL_SHOVEL_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.holystone", itemKey("tool_shovel_holystone"), 17055, AetherToolMaterial.HOLYSTONE) {
            public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int x, int y, int z, Side side, Mob mob) {
                Block<?> block = Blocks.blocksList[i];
                if (block != null && (block.getHardness() > 0.0F || this.isSilkTouch())) {
                    itemstack.damageItem(1, mob);
                }
                if (itemRand.nextInt(32) == 0) {
                    world.dropItem(x, y, z, new ItemStack(AMBROSIUM, 1));
                }
                return true;
            }
        });
        TOOL_PICKAXE_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.holystone", itemKey("tool_pickaxe_holystone"), 17056, AetherToolMaterial.HOLYSTONE) {
            public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int x, int y, int z, Side side, Mob mob) {
                Block<?> block = Blocks.blocksList[i];
                if (block != null && (block.getHardness() > 0.0F || this.isSilkTouch())) {
                    itemstack.damageItem(1, mob);
                }
                if (itemRand.nextInt(32) == 0) {
                    world.dropItem(x, y, z, new ItemStack(AMBROSIUM, 1));
                }
                return true;
            }
        });
        TOOL_AXE_HOLYSTONE = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.holystone", itemKey("tool_axe_holystone"), 17057, AetherToolMaterial.HOLYSTONE) {
            public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int x, int y, int z, Side side, Mob mob) {
                Block<?> block = Blocks.blocksList[i];
                if (block != null && (block.getHardness() > 0.0F || this.isSilkTouch())) {
                    itemstack.damageItem(1, mob);
                }
                if (itemRand.nextInt(32) == 0) {
                    world.dropItem(x, y, z, new ItemStack(AMBROSIUM, 1));
                }
                return true;
            }
        });
        TOOL_SWORD_ZANITE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.zanite", itemKey("tool_sword_zanite"), 17058, AetherToolMaterial.ZANITE){
            public int getDamageVsEntity(Entity entity, ItemStack is) {
                // to keep it consistent with other tools
                float factor = AetherToolMaterial.ZANITE.getEfficiency(true ) / AetherToolMaterial.ZANITE.getEfficiency(false);

                // we will 'lerp' between the starting damage and starting damage time ration of efficiency
                float durability_progress = (float) is.getMetadata() / this.getMaxDamage();
                float starting_damage = (float) super.getDamageVsEntity(entity, is);
                float ending_damage = starting_damage * factor;
                return Math.round(starting_damage * (1.0F - durability_progress) + (ending_damage * durability_progress));
            }
        });
        TOOL_SHOVEL_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.zanite", itemKey("tool_shovel_zanite"), 17059, AetherToolMaterial.ZANITE){
            public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
                if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL)) return 1.0F;
                float durability_progress = (float) itemstack.getMetadata() / this.getMaxDamage();

                // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
                float starting_efficiency = this.material.getEfficiency(false);
                float ending_efficiency = this.material.getEfficiency(true);
                return (float) (starting_efficiency * (1.0 - durability_progress) + (ending_efficiency * durability_progress));
            }
        });
        TOOL_PICKAXE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.zanite", itemKey("tool_pickaxe_zanite"), 17060, AetherToolMaterial.ZANITE){
            public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
                if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)) return 1.0F;
                float durability_progress = (float) itemstack.getMetadata() / this.getMaxDamage();

                // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
                float starting_efficiency = this.material.getEfficiency(false);
                float ending_efficiency = this.material.getEfficiency(true);

                return (float) (starting_efficiency * (1.0 - durability_progress) + (ending_efficiency * durability_progress));
            }
        });
        TOOL_AXE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.zanite", itemKey("tool_axe_zanite"), 17061, AetherToolMaterial.ZANITE){
            public float getStrVsBlock(ItemStack itemstack, Block<?> block) {
                if (!block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE)) return 1.0F;
                float durability_progress = (float) itemstack.getMetadata() / this.getMaxDamage();

                // we will 'lerp' between the starting efficiency and the unused 'haste' efficiency of tools
                float starting_efficiency = this.material.getEfficiency(false);
                float ending_efficiency = this.material.getEfficiency(true);

                return (float) (starting_efficiency * (1.0 - durability_progress) + (ending_efficiency * durability_progress));
            }
        });

        TOOL_SWORD_GRAVITITE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordGravitite("tool.sword.gravitite", itemKey("tool_sword_gravitite"), 17062, AetherToolMaterial.GRAVITITE));
        TOOL_SHOVEL_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.gravitite", itemKey("tool_shovel_gravitite"), 17063, AetherToolMaterial.GRAVITITE));
        TOOL_PICKAXE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.gravitite", itemKey("tool_pickaxe_gravitite"), 17064, AetherToolMaterial.GRAVITITE));
        TOOL_AXE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.gravitite", itemKey("tool_axe_gravitite"), 17065, AetherToolMaterial.GRAVITITE));
        //TODO Gravitite tools need their ability to make blocks that you right click float up, blocks only float up if you are using the right tool and it uses durability

        TOOL_SWORD_VALKYRIE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordAether("tool.sword.valkyrie", itemKey("tool_sword_valkyrie"), 17066, AetherToolMaterial.VALKYRIE));
        TOOL_SHOVEL_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolShovelAether("tool.shovel.valkyrie", itemKey("tool_shovel_valkyrie"), 17067, AetherToolMaterial.VALKYRIE));
        TOOL_PICKAXE_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolPickaxeAether("tool.pickaxe.valkyrie", itemKey("tool_pickaxe_valkyrie"), 17068, AetherToolMaterial.VALKYRIE));
        TOOL_AXE_VALKYRIE = new ItemBuilder(MOD_ID).build(new ItemToolAxeAether("tool.axe.valkyrie", itemKey("tool_axe_valkyrie"), 17069, AetherToolMaterial.VALKYRIE));

        TOOL_KNIFE_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolKnifeLightning("tool.knife.lightning", itemKey("tool_knife_lightning"), 17037));
        //TODO Lightning knife model is a bit broken
        AMMO_HAMMER_HEAD = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("ammo.hammer.head", itemKey("ammo_hammer_head"), 17038));
        TOOL_HAMMER_NOTCH = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolHammerNotch("tool.hammer.notch", itemKey("tool_hammer_notch"), 17039, AetherToolMaterial.SPECIAL));

        TOOL_BOW_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemBowPhoenix("tool.bow.phoenix", itemKey("tool_bow_phoenix"), 17040));
        //TODO potentially add flaming arrow versions of golden arrows/purple arrows

        AMMO_DART_GOLDEN = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.golden", itemKey("ammo_dart_golden"), 17041));
        AMMO_DART_POISON = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.poison", itemKey("ammo_dart_poison"), 17042));
        AMMO_DART_ENCHANTED = new ItemBuilder(MOD_ID).build(new Item("ammo.dart.enchanted", itemKey("ammo_dart_enchanted"), 17043));
        //TODO Poison darts need to be added and given their effect when they hit something, enchanted darts need their ability to be more accurate and do more damage

        TOOL_SHOOTER = new ItemBuilder(MOD_ID).build(new ItemShooter("tool.shooter", itemKey("tool_shooter"), 17044));

        TOOL_SHIELD_REPULSION = new ItemBuilder(MOD_ID).build(new Item("tool.shield.repulsion", itemKey("tool_shield_repulsion"), 17045));
        //TODO Shield needs its ability to activate when held, blocks all projectiles from hitting you


        ARMOR_HELMET_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.zanite", itemKey("armor_helmet_zanite"), 17070, AetherArmorMaterial.ZANITE, 3));
        ARMOR_CHESTPLATE_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.zanite", itemKey("armor_chestplate_zanite"), 17071, AetherArmorMaterial.ZANITE, 2));
        ARMOR_LEGGINGS_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.zanite", itemKey("armor_leggings_zanite"), 17072, AetherArmorMaterial.ZANITE, 1));
        ARMOR_BOOTS_ZANITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.zanite", itemKey("armor_boots_zanite"), 17073, AetherArmorMaterial.ZANITE, 0));

        ARMOR_HELMET_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.gravitite", itemKey("armor_helmet_gravitite"), 17074, AetherArmorMaterial.GRAVITITE, 3));
        ARMOR_CHESTPLATE_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.gravitite", itemKey("armor_chestplate_gravitite"), 17075, AetherArmorMaterial.GRAVITITE, 2));
        ARMOR_LEGGINGS_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.gravitite", itemKey("armor_leggings_gravitite"), 17076, AetherArmorMaterial.GRAVITITE, 1));
        ARMOR_BOOTS_GRAVITITE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.gravitite", itemKey("armor_boots_gravitite"), 17077, AetherArmorMaterial.GRAVITITE, 0));

        ARMOR_HELMET_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.obsidian", itemKey("armor_helmet_obsidian"), 17078, AetherArmorMaterial.OBSIDIAN, 3));
        ARMOR_CHESTPLATE_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.obsidian", itemKey("armor_chestplate_obsidian"), 17079, AetherArmorMaterial.OBSIDIAN, 2));
        ARMOR_LEGGINGS_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.obsidian", itemKey("armor_leggings_obsidian"), 17080, AetherArmorMaterial.OBSIDIAN, 1));
        ARMOR_BOOTS_OBSIDIAN = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.obsidian", itemKey("armor_boots_obsidian"), 17081, AetherArmorMaterial.OBSIDIAN, 0));
        // TODO Give obsidian armor ability to negate knockback damage

        ARMOR_HELMET_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.phoenix", itemKey("armor_helmet_phoenix"), 17082, AetherArmorMaterial.PHOENIX, 3));
        ARMOR_CHESTPLATE_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.phoenix", itemKey("armor_chestplate_phoenix"), 17083, AetherArmorMaterial.PHOENIX, 2));
        ARMOR_LEGGINGS_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.phoenix", itemKey("armor_leggings_phoenix"), 17084, AetherArmorMaterial.PHOENIX, 1));
        ARMOR_BOOTS_PHOENIX = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.phoenix", itemKey("armor_boots_phoenix"), 17085, AetherArmorMaterial.PHOENIX, 0));
        //TODO Phoenix armor should be damaged in water and if a piece of armor breaks while in water it turns into a piece of obsidian armor

        ARMOR_HELMET_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.helmet.neptune", itemKey("armor_helmet_neptune"), 17086, AetherArmorMaterial.NEPTUNE, 3));
        ARMOR_CHESTPLATE_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.chestplate.neptune", itemKey("armor_chestplate_neptune"), 17087, AetherArmorMaterial.NEPTUNE, 2));
        ARMOR_LEGGINGS_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.leggings.neptune", itemKey("armor_leggings_neptune"), 17088, AetherArmorMaterial.NEPTUNE, 1));
        ARMOR_BOOTS_NEPTUNE = new ItemBuilder(MOD_ID).build(new ItemArmor("armor.boots.neptune", itemKey("armor_boots_neptune"), 17089, AetherArmorMaterial.NEPTUNE, 0));
        //TODO Neptune armor needs its ability to full mining speed

        ARMOR_GLOVES_LEATHER = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.leather", itemKey("armor_gloves_leather"), 17150));
        ARMOR_GLOVES_CHAIN = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.chain", itemKey("armor_gloves_chain"), 17151));
        ARMOR_GLOVES_IRON = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.iron", itemKey("armor_gloves_iron"), 17152));
        ARMOR_GLOVES_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.gold", itemKey("armor_gloves_gold"), 17153));
        ARMOR_GLOVES_DIAMOND = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.diamond", itemKey("armor_gloves_diamond"), 17154));
        ARMOR_GLOVES_STEEL = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.steel", itemKey("armor_gloves_steel"), 17155));
        ARMOR_GLOVES_ZANITE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.zanite", itemKey("armor_gloves_zanite"), 17156));
        ARMOR_GLOVES_GRAVITITE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.gravitite", itemKey("armor_gloves_gravitite"), 17157));
        ARMOR_GLOVES_OBSIDIAN = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.obsidian", itemKey("armor_gloves_obsidian"), 17158));
        ARMOR_GLOVES_PHOENIX = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.phoenix", itemKey("armor_gloves_phoenix"), 17159));
        ARMOR_GLOVES_NEPTUNE = new ItemBuilder(MOD_ID).build(new Item("armor.gloves.neptune", itemKey("armor_gloves_neptune"), 17160));
        //TODO Gloves need their system added and be wearable


        TOOL_SWORD_PIG = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordPig("tool.sword.pig", itemKey("tool_sword_pig"), 17090, AetherToolMaterial.SPECIAL));
        TOOL_SWORD_VAMPIRE = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordVampire("tool.sword.vampire", itemKey("tool_sword_vampire"), 17091, AetherToolMaterial.SPECIAL));

        TOOL_SWORD_FLAME = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordFire("tool.sword.flame", itemKey("tool_sword_flame"), 17092, AetherToolMaterial.SPECIAL));
        TOOL_SWORD_HOLY = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordHoly("tool.sword.holy", itemKey("tool_sword_holy"), 17093, AetherToolMaterial.SPECIAL));
        TOOL_SWORD_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.PREVENT_CREATIVE_MINING).build(new ItemToolSwordLightning("tool.sword.lightning", itemKey("tool_sword_lightning"), 17094, AetherToolMaterial.SPECIAL));

        TOOL_STAFF_NATURE = new ItemBuilder(MOD_ID).build(new Item("tool.staff.nature", itemKey("tool_staff_nature"), 17095));
        TOOL_STAFF_CLOUD = new ItemBuilder(MOD_ID).build(new Item("tool.staff.cloud", itemKey("tool_staff_cloud"), 17096));
        //TODO Cloud staff when used should spawn 2 cloud allies on your shoulders that attack enemies with ice projectiles,
        //TODO Nature staff should either be removed or reworked, og use was luring moas but we have a system built into bta for that now


        ARMOR_TALISMAN_REGEN = new ItemBuilder(MOD_ID).build(new ItemRegenStone("armor.talisman.regen", itemKey("armor_talisman_regen"), 17120));
        ARMOR_TALISMAN_BUBBLE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.bubble", itemKey("armor_talisman_bubble"), 17121));
        ARMOR_TALISMAN_FEATHER_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.feather.gold", itemKey("armor_talisman_feather_gold"), 17122));
        //TODO all talismans need their effects and also work with the new armor system, regen stone heals half a heart every 5 seconds, iron bubble gives water breathing, feather gold makes you fall like a chicken

        ARMOR_TALISMAN_LEATHER = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.leather", itemKey("armor_talisman_leather"), 17125));
        ARMOR_TALISMAN_CHAIN = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.chain", itemKey("armor_talisman_chain"), 17126));
        ARMOR_TALISMAN_IRON = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.iron", itemKey("armor_talisman_iron"), 17127));
        ARMOR_TALISMAN_GOLD = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.gold", itemKey("armor_talisman_gold"), 17128));
        ARMOR_TALISMAN_DIAMOND = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.diamond", itemKey("armor_talisman_diamond"), 17129));
        ARMOR_TALISMAN_STEEL = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.steel", itemKey("armor_talisman_steel"), 17130));
        ARMOR_TALISMAN_ZANITE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.zanite", itemKey("armor_talisman_zanite"), 17131));
        ARMOR_TALISMAN_GRAVITITE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.gravitite", itemKey("armor_talisman_gravitite"), 17132));
        ARMOR_TALISMAN_ICE = new ItemBuilder(MOD_ID).build(new Item("armor.talisman.ice", itemKey("armor_talisman_ice"), 17133));
        //TODO all necklaces can be equipped in ? talisman slot, ice one freezes water and lava into ice and obsidian as you walk over them, zanite one increases mining speed as you mine and its durability goes down
        //TODO gold one will grant silk touch effect to you and lose durability as you mine, other ones can be cosmetic if uses cant be thought of

        ARMOR_CAPE_AGILITY = new ItemBuilder(MOD_ID).build(new Item("armor.cape.agility", itemKey("armor_cape_agility"), 17134));
        ARMOR_CAPE_SWET = new ItemBuilder(MOD_ID).build(new Item("armor.cape.swet", itemKey("armor_cape_swet"), 17135));
        ARMOR_CAPE_INVISIBILITY = new ItemBuilder(MOD_ID).build(new Item("armor.cape.invisibility", itemKey("armor_cape_invisibility"), 17136));
        //TODO Capes are equipped in the cape slot, agility cape lets you walk up 1 block without jumping, swet cape is decorative, invisibility cape turns you invisible and enemies wont aggro unless hit, like creative

        ARMOR_CAPE_WHITE = new ItemBuilder(MOD_ID).build(new Item("armor.cape.white", itemKey("armor_cape_white"), 17137));
        ARMOR_CAPE_RED = new ItemBuilder(MOD_ID).build(new Item("armor.cape.red", itemKey("armor_cape_red"), 17138));
        ARMOR_CAPE_YELLOW = new ItemBuilder(MOD_ID).build(new Item("armor.cape.yellow", itemKey("armor_cape_yellow"), 17139));
        ARMOR_CAPE_BLUE = new ItemBuilder(MOD_ID).build(new Item("armor.cape.blue", itemKey("armor_cape_blue"), 17140));
        //TODO Decorative capes, potential to add cape color for each wool color,



        FOOD_HEALING_STONE = new ItemBuilder(MOD_ID).build(new ItemFood("food.healing.stone", itemKey("food_healing_stone"), 17100, 4, 1, false, 16));

        FOOD_GUMMY_BLUE = new ItemBuilder(MOD_ID).build(new ItemFood("food.gummy.blue", itemKey("food_gummy_blue"), 17101, 20, 1, false, 64));
        FOOD_GUMMY_GOLD = new ItemBuilder(MOD_ID).build(new ItemFood("food.gummy.gold", itemKey("food_gummy_gold"), 17102, 40, 1, false, 64));

        LIFESHARD = new ItemBuilder(MOD_ID).build(new ItemLifeShard("food.lifeshard", itemKey("food_lifeshard"), 17105).setMaxStackSize(10));
        //TODO hud need to be adjusted so it wont overlap with oxygenmeter

        PARACHUTE_CLOUD = new ItemBuilder(MOD_ID).build(new Item("parachute.cloud", itemKey("parachute_cloud"), 17106));
        PARACHUTE_CLOUD_GOLD = new ItemBuilder(MOD_ID).build(new Item("parachute.cloud.gold", itemKey("parachute_cloud_gold"), 17107));
        //TODO Parachutes on use spawn a cloud entity below you that makes you fall slowly, gold one has 20 uses, when you land it goes away

        LANTERN_FIREFLY_SILVER = new ItemBuilder(MOD_ID).build(new ItemPlaceable("lantern.firefly.silver", itemKey("lantern_firefly_silver"), 17110, AetherBlocks.LANTERN_FIREFLY_SILVER));
        DOOR_SKYROOT = new ItemBuilder(MOD_ID).build(new ItemDoor("door.skyroot", itemKey("door_skyroot"), 17111, AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM, AetherBlocks.DOOR_PLANKS_SKYROOT_TOP));
        DOOR_GLASS_AMBROSIUM = new ItemBuilder(MOD_ID).build(new ItemDoor("door.glass.ambrosium", itemKey("door_glass_ambrosium"), 17112, AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP));

        AMMO_WINDBALL = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("ammo.windball", itemKey("ammo_windball"), 17115));
        PROJECTILE_FIRE = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.fire", itemKey("projectile_fire"), 17116));
        PROJECTILE_ICE = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.ice", itemKey("projectile_ice"), 17117));
        PROJECTILE_LIGHTNING = new ItemBuilder(MOD_ID).setTags(ItemTags.NOT_IN_CREATIVE_MENU).build(new Item("projectile.lightning", itemKey("projectile_lightning"), 17118));
        //TODO These are to be used as the textures for the projectile the zephyrs shoot (windball), and the other 3 as the boss projectiles that bounce off walls and can be deflected back

//        BLANK = new ItemBuilder(MOD_ID)
//                .build(new Item("BLANK", "aether:item/blank", ID));

    }

}
