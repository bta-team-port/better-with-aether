package bta.aether.item;

import bta.aether.AetherConfig;
import bta.aether.block.AetherBlocks;
import bta.aether.entity.projectiles.EntityEnchantedDart;
import bta.aether.entity.projectiles.EntityGoldenDart;
import bta.aether.entity.projectiles.EntityPoisonDart;
import bta.aether.item.Accessories.*;
import bta.aether.item.Accessories.base.*;
import bta.aether.item.tool.ItemToolAxeZanite;
import bta.aether.item.tool.ItemToolPickaxeZanite;
import bta.aether.item.tool.ItemToolShovelZanite;
import bta.aether.item.tool.base.ItemToolAetherAxe;
import bta.aether.item.tool.base.ItemToolAetherPickaxe;
import bta.aether.item.tool.base.ItemToolAetherShovel;
import bta.aether.world.AetherDimension;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.projectile.EntityProjectile;
import net.minecraft.core.item.*;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ItemHelper;

import static bta.aether.AetherMod.MOD_ID;
import static net.minecraft.core.block.Block.fluidWaterFlowing;

@SuppressWarnings({"unused", "unchecked"})
public class AetherItems {
    public int itemID(String itemName) {
        return AetherConfig.cfg.getInt("Item IDs." + itemName);
    }

    public static Item victoryMedal;

    public static Item keyBronze;
    public static Item keySilver;
    public static Item keyGold;

    public static Item bookLoreOverworld;
    public static Item bookLoreNether;
    public static Item bookLoreAether;
    public static Item bookLoreParadise;

    public static Item eggMoaBlue;
    public static Item eggMoaWhite;
    public static Item eggMoaBlack;

    public static Item recordBlue;
    public static Item recordSilver;
    public static Item recordPink;

    public static Item amberGolden;
    public static Item petalAechor;
    public static Item stickSkyroot;

    public static Item dartGolden;
    public static Item dartPoison;
    public static Item dartEnchanted;

    public static Item dartShooter;
    public static Item dartShooterPoison;
    public static Item dartShooterEnchanted;

    public static Item ambrosium;
    public static Item zanite;

    public static Item bucketSkyroot;
    public static Item bucketSkyrootWater;
    public static Item bucketSkyrootMilk;
    public static Item bucketSkyrootPoison;
    public static Item bucketSkyrootRemedy;
    public static Item bucketSkyrootIcecream;

    public static Item healingStone;

    public static Item toolPickaxeSkyroot;
    public static Item toolShovelSkyroot;
    public static Item toolAxeSkyroot;
    public static Item toolSwordSkyroot;

    public static Item toolPickaxeHolystone;
    public static Item toolShovelHolystone;
    public static Item toolAxeHolystone;
    public static Item toolSwordHolystone;

    public static Item toolPickaxeZanite;
    public static Item toolShovelZanite;
    public static Item toolAxeZanite;
    public static Item toolSwordZanite;

    public static Item toolPickaxeGravitite;
    public static Item toolShovelGravitite;
    public static Item toolAxeGravitite;
    public static Item toolSwordGravitite;

    public static Item toolPickaxeValkyrie;
    public static Item toolShovelValkyrie;
    public static Item toolAxeValkyrie;
    public static Item toolSwordValkyrie;

    public static Item armorHelmetZanite;
    public static Item armorChestplateZanite;
    public static Item armorLeggingsZanite;
    public static Item armorBootsZanite;

    public static Item armorHelmetGravitite;
    public static Item armorChestplateGravitite;
    public static Item armorLeggingsGravitite;
    public static Item armorBootsGravitite;

    public static Item armorHelmetPhoenix;
    public static Item armorChestplatePhoenix;
    public static Item armorLeggingsPhoenix;
    public static Item armorBootsPhoenix;

    public static Item armorHelmetObsidian;
    public static Item armorChestplateObsidian;
    public static Item armorLeggingsObsidian;
    public static Item armorBootsObsidian;

    public static Item armorHelmetNeptune;
    public static Item armorChestplateNeptune;
    public static Item armorLeggingsNeptune;
    public static Item armorBootsNeptune;

    public static Item toolSwordPig;
    public static Item toolSwordVampire;

    public static Item toolSwordFlaming;
    public static Item toolSwordHoly;
    public static Item toolSwordLightning;

    public static Item toolStaffNature;
    public static Item toolStaffCloud;

    public static Item toolKnifeLightning;
    public static Item toolHammerNotch;
    public static Item toolBowPhoenix;

    public static Item armorGlovesLeather;
    public static Item armorGlovesChain;
    public static Item armorGlovesIron;
    public static Item armorGlovesGold;
    public static Item armorGlovesDiamond;
    public static Item armorGlovesSteel;
    public static Item armorGlovesZanite;
    public static Item armorGlovesGravitite;
    public static Item armorGlovesPhoenix;
    public static Item armorGlovesObsidian;
    public static Item armorGlovesNeptune;

    public static Item armorRingIron;
    public static Item armorRingGold;
    public static Item armorRingZanite;
    public static Item armorRingIce;

    public static Item armorPendantLeather;
    public static Item armorPendantChain;
    public static Item armorPendantIron;
    public static Item armorPendantGold;
    public static Item armorPendantDiamond;
    public static Item armorPendantSteel;
    public static Item armorPendantZanite;
    public static Item armorPendantGravitite;
    public static Item armorPendantIce;

    public static Item armorTalismanIronBubble;
    public static Item armorTalismanGoldenFeather;
    public static Item armorTalismanRegenStone;

    public static Item armorShieldRepulsion;

    public static Item armorCapeSwet;
    public static Item armorCapeInvisibility;
    public static Item armorCapeAgility;

    public static Item armorCapeWhite;
    public static Item armorCapeRed;
    public static Item armorCapeYellow;
    public static Item armorCapeBlue;

    public static Item foodGummyBlue;
    public static Item foodGummyGold;

    public static Item cloudParachute;
    public static Item cloudParachuteGold;

    public static Item lifeshard;

    public static Item lanternAether;


    // tags
    public static Tag<Item> aetherTool = Tag.of("aether_tool");
    public static Tag<Item> aetherdungeonKey = Tag.of("aether_key");
    public static Tag<Item> aetheregg = Tag.of("aether_egg");


//    public static final Item devStick = ItemHelper.createItem(MOD_ID, new ItemDevStick("dev.stick", itemID++), "stick_skyroot.png").setMaxStackSize(1);


    public void initializeItems(){
        victoryMedal = new ItemBuilder(MOD_ID)
                .setIcon("item/medal")
                .setStackSize(10)
                .build(new Item("victorymedal", itemID("victoryMedal")));

        // See BlockChestLocked.java before using any of these.
        keyBronze = new ItemBuilder(MOD_ID)
                .setIcon("item/key_bronze")
                .setStackSize(1)
                .build(new Item("key.bronze", itemID("keyBronze")));

        keySilver = new ItemBuilder(MOD_ID)
                .setIcon("item/key_silver")
                .setStackSize(1)
                .build(new Item("key.silver", itemID("keySilver")));

        keyGold = new ItemBuilder(MOD_ID)
                .setIcon("item/key_gold")
                .setStackSize(1)
                .build(new Item("key.gold", itemID("keyGold")));


        bookLoreOverworld = new ItemBuilder(MOD_ID)
                .setIcon("item/book_overworld")
                .setStackSize(1)
                .build(new ItemLoreBook("book.lore.overworld", itemID("bookLoreOverworld"), Dimension.overworld.languageKey));

        bookLoreNether = new ItemBuilder(MOD_ID)
                .setIcon("item/book_nether")
                .setStackSize(1)
                .build(new ItemLoreBook("book.lore.nether", itemID("bookLoreNether"), Dimension.nether.languageKey));

        bookLoreAether = new ItemBuilder(MOD_ID)
                .setIcon("item/book_aether")
                .setStackSize(1)
                .build(new ItemLoreBook("book.lore.aether", itemID("bookLoreAether"), AetherDimension.dimensionAether.languageKey));

        bookLoreParadise = new ItemBuilder(MOD_ID)
                .setIcon("item/book_paradise")
                .setStackSize(1)
                .build(new ItemLoreBook("book.lore.paradise", itemID("bookLoreParadise"), Dimension.paradise.languageKey));


        eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item("egg.moa.blue", itemID++), "moa_egg_blue.png").withTags(aetheregg);
        eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item("egg.moa.white", itemID++), "moa_egg_white.png").withTags(aetheregg);
        eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item("egg.moa.black", itemID++), "moa_egg_black.png").withTags(aetheregg);

        recordBlue = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_aether")
                .setStackSize(1)
                .build(new ItemRecord("record.blue", itemID++, "AetherTune", "Noisestorm"));

        recordSilver = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_wish")
                .setStackSize(1)
                .build(new ItemRecord("record.silver", itemID++, "AMorningWish", "Emile van Krieken"));

        recordPink = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_dawn")
                .setStackSize(1)
                .build(new ItemRecord("record.pink", itemID++, "AscendingDawn", "Emile van Krieken"));

        amberGolden = ItemHelper.createItem(MOD_ID, new Item("goldenamber", itemID++), "amber.png");
        petalAechor = ItemHelper.createItem(MOD_ID, new Item("aechorpetal", itemID++), "petal.png");
        stickSkyroot = ItemHelper.createItem(MOD_ID, new Item("stick.skyroot", itemID++), "stick_skyroot.png");

        dartGolden = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.gold", itemID++),"dart_golden.png");
        dartPoison = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.poison", itemID++), "dart_poison.png");
        dartEnchanted = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.enchanted",  itemID++), "dart_enchanted.png");

        dartShooter = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter", itemID++, AetherItems.dartGolden.id){
            @Override
            public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityGoldenDart(world, entityPlayer, true);
            }
        }, "shooter_gold.png");
        dartShooterPoison = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.poison", itemID++, AetherItems.dartPoison.id){
            @Override
            public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityPoisonDart(world, entityPlayer, true);
            }
        }, "shooter_poison.png");
        dartShooterEnchanted = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.enchanted", itemID++, AetherItems.dartEnchanted.id){
            @Override
            public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityEnchantedDart(world, entityPlayer, true);
            }
        }, "shooter_enchanted.png");


        ambrosium = ItemHelper.createItem(MOD_ID, new ItemFood("ambrosium", itemID++, 1, false, 64), "ambrosium.png");
        zanite = ItemHelper.createItem(MOD_ID, new Item("zanite", itemID++), "zanite.png");

        bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty("bucket.skyroot", itemID++), "bucket_skyroot.png");
        bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.water", itemID++, fluidWaterFlowing, 0), "bucket_skyroot_water.png").setContainerItem(bucketSkyroot);
        bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.milk", itemID++, null, 1), "bucket_skyroot_milk.png").setContainerItem(bucketSkyroot);
        bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.poison", itemID++, null, 2), "bucket_skyroot_poison.png").setContainerItem(bucketSkyroot);
        bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.remedy", itemID++, null, 3), "bucket_skyroot_remedy.png").setContainerItem(bucketSkyroot);
        bucketSkyrootIcecream = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketIceCream("bucket.skyroot.icecream", itemID++, 10), "bucket_skyroot_icecream.png").setContainerItem(bucketSkyroot);

        healingStone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", itemID++, 4, false), "food_stone.png");

        toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_pickaxe_skyroot.png").withTags(aetherTool);
        toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_shovel_skyroot.png").withTags(aetherTool);
        toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_axe_skyroot.png").withTags(aetherTool);
        toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_sword_skyroot.png").withTags(ItemTags.preventCreativeMining, aetherTool);

        toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_pickaxe_holystone.png").withTags(aetherTool);
        toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_shovel_holystone.png").withTags(aetherTool);
        toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_axe_holystone.png").withTags(aetherTool);
        toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_sword_holystone.png").withTags(ItemTags.preventCreativeMining, aetherTool);

        toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_pickaxe_zanite.png").withTags(aetherTool);
        toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovelZanite("tool.shovel.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_shovel_zanite.png").withTags(aetherTool);
        toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxeZanite("tool.axe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_axe_zanite.png").withTags(aetherTool);
        toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_sword_zanite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

        toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_pickaxe_gravitite.png").withTags(aetherTool);
        toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_shovel_gravitite.png").withTags(aetherTool);
        toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_axe_gravitite.png").withTags(aetherTool);
        toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_sword_gravitite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

        toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_pickaxe_valk.png").withTags(aetherTool);
        toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_shovel_valk.png").withTags(aetherTool);
        toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_axe_valk.png").withTags(aetherTool);
        toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_sword_valk.png").withTags(ItemTags.preventCreativeMining, aetherTool);


        // --- ARMOR ---
        armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.zanite", itemID++, AetherArmorMaterial.ZANITE, 0), "armor_helmet_zanite.png");
        armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.zanite", itemID++, AetherArmorMaterial.ZANITE, 1), "armor_chestplate_zanite.png");
        armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.zanite", itemID++, AetherArmorMaterial.ZANITE, 2), "armor_leggings_zanite.png");
        armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.zanite", itemID++, AetherArmorMaterial.ZANITE, 3), "armor_boots_zanite.png");

        armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 0), "armor_helmet_gravitite.png");
        armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 1), "armor_chestplate_gravitite.png");
        armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 2), "armor_leggings_gravitite.png");
        armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 3), "armor_boots_gravitite.png");

        armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 0), "armor_helmet_phoenix.png");
        armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 1), "armor_chestplate_phoenix.png");
        armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 2), "armor_leggings_phoenix.png");
        armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 3), "armor_boots_phoenix.png");

        armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 0), "armor_helmet_obsidian.png");
        armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 1), "armor_chestplate_obsidian.png");
        armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 2), "armor_leggings_obsidian.png");
        armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 3), "armor_boots_obsidian.png");

        armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 0), "armor_helmet_neptune.png");
        armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 1), "armor_chestplate_neptune.png");
        armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 2), "armor_leggings_neptune.png");
        armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 3), "armor_boots_neptune.png");


        toolSwordPig = ItemHelper.createItem(MOD_ID, new ItemPigSlayer("tool.sword.pig", itemID++), "tool_knife_pig.png").withTags(ItemTags.preventCreativeMining);
        toolSwordVampire = ItemHelper.createItem(MOD_ID, new ItemVampireSword("tool.sword.vampire", itemID++, ToolMaterial.diamond), "tool_sword_vampire.png").withTags(ItemTags.preventCreativeMining);

        toolSwordFlaming = ItemHelper.createItem(MOD_ID, new ItemSwordFlaming("tool.sword.flaming", itemID++, AetherToolMaterial.SWORD_SPECIAL), "tool_sword_element_fire.png").withTags(ItemTags.preventCreativeMining);
        toolSwordHoly = ItemHelper.createItem(MOD_ID, new ItemSwordHoly("tool.sword.holy", itemID++, AetherToolMaterial.SWORD_HOLY), "tool_sword_element_holy.png").withTags(ItemTags.preventCreativeMining);
        toolSwordLightning = ItemHelper.createItem(MOD_ID, new ItemSwordLightning("tool.sword.lightning", itemID++, AetherToolMaterial.SWORD_SPECIAL), "tool_sword_element_lightning.png").withTags(ItemTags.preventCreativeMining);

        toolStaffNature = ItemHelper.createItem(MOD_ID, new Item("tool.staff.nature", itemID++), "staff_nature.png");
        toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item("tool.staff.cloud", itemID++), "staff_cloud.png");

        toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife("tool.knife.lightning", itemID++), "tool_knife_lightning.png").withTags(ItemTags.preventCreativeMining);
        toolHammerNotch = ItemHelper.createItem(MOD_ID, new ItemHammerNotch("tool.hammer.notch", itemID++, ToolMaterial.diamond), "tool_hammer_notch.png").withTags(ItemTags.preventCreativeMining);
        toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow("tool.bow.phoenix", itemID++), "tool_bow_phoenix.png");

        armorGlovesLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.leather", itemID++, "/assets/aether/armor/leather_pendant_and_gloves.png", ArmorMaterial.LEATHER), "armor_gloves_leather.png");
        armorGlovesChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.chain", itemID++, "/assets/aether/armor/chain_pendant_and_gloves.png", ArmorMaterial.CHAINMAIL), "armor_gloves_chain.png");
        armorGlovesIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.iron", itemID++, "/assets/aether/armor/Accessories.png", ArmorMaterial.IRON), "armor_gloves_iron.png");
        armorGlovesGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gold", itemID++, "/assets/aether/armor/gold_pendant_and_gloves.png", ArmorMaterial.GOLD), "armor_gloves_gold.png");
        armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.diamond", itemID++, "/assets/aether/armor/diamond_pendant_and_gloves.png", ArmorMaterial.DIAMOND), "armor_gloves_diamond.png");
        armorGlovesSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.steel", itemID++, "/assets/aether/armor/steel_pendant_and_gloves.png", ArmorMaterial.STEEL), "armor_gloves_steel.png");
        armorGlovesZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.zanite", itemID++, "/assets/aether/armor/zanite_pendant_and_gloves.png", AetherArmorMaterial.ZANITE), "armor_gloves_zanite.png");
        armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gravitite", itemID++, "/assets/aether/armor/gravitite_pendant_and_gloves.png", AetherArmorMaterial.GRAVITITE), "armor_gloves_gravitite.png");
        armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.phoenix", itemID++, "/assets/aether/armor/Phoenix.png", AetherArmorMaterial.PHOENIX), "armor_gloves_phoenix.png");
        armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.obsidian", itemID++, "/assets/aether/armor/obsidian_pendant_and_gloves.png", AetherArmorMaterial.OBSIDIAN), "armor_gloves_obsidian.png");
        armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.neptune", itemID++, "/assets/aether/armor/neptune_pendant_and_gloves.png", AetherArmorMaterial.NEPTUNE), "armor_gloves_neptune.png");

        armorRingIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing("armor.ring.iron", itemID++), "armor_ring_iron.png");
        armorRingGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldRing("armor.ring.gold", itemID++), "armor_ring_gold.png");
        armorRingZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZaniteRing("armor.ring.zanite", itemID++), "armor_ring_zanite.png");
        armorRingIce = ItemHelper.createItem(MOD_ID, new ItemRingIce("armor.ring.ice", itemID++), "armor_ring_ice.png");

        armorPendantLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.leather", itemID++, "/assets/aether/armor/leather_pendant_and_gloves.png"), "armor_pendant_leather.png");
        armorPendantChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.chain", itemID++, "/assets/aether/armor/chain_pendant_and_gloves.png"), "armor_pendant_chain.png");
        armorPendantIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.iron", itemID++, "/assets/aether/armor/Accessories.png"), "armor_pendant_iron.png");
        armorPendantGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldPendant("armor.pendant.gold", itemID++, "/assets/aether/armor/gold_pendant_and_gloves.png"), "armor_pendant_gold.png");
        armorPendantDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.diamond", itemID++, "/assets/aether/armor/diamond_pendant_and_gloves.png"), "armor_pendant_diamond.png");
        armorPendantSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.steel", itemID++, "/assets/aether/armor/steel_pendant_and_gloves.png"), "armor_pendant_steel.png");
        armorPendantZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZanitePendant("armor.pendant.zanite", itemID++, "/assets/aether/armor/zanite_pendant_and_gloves.png"), "armor_pendant_zanite.png");
        armorPendantGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZanitePendant("armor.pendant.gravitite", itemID++, "/assets/aether/armor/gravitite_pendant_and_gloves.png"), "armor_pendant_gravitite.png");
        armorPendantIce = ItemHelper.createItem(MOD_ID, new ItemPendantIce("armor.pendant.ice", itemID++, "/assets/aether/armor/ice_pendant_and_gloves.png"), "armor_pendant_ice.png");

        armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new ItemAccessoryIronBubble("armor.talisman.ironbubble", itemID++), "accessory_bubble.png");
        armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldenFeather("armor.talisman.goldenfeather", itemID++), "accessory_feather.png");
        armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new ItemAccessoryRegenStone("armor.talisman.regenstone", itemID++), "accessory_healing.png");

        armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new ItemAccessoryShield("armor.shield.repulsion", itemID++), "tool_shield_repulsion.png");

        armorCapeSwet = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.swet", itemID++, "/assets/aether/other/AetherCape.png"), "cape_swet.png");
        armorCapeInvisibility = ItemHelper.createItem(MOD_ID, new ItemAccessoryInvisibilityCloak("armor.cape.invisibility", itemID++, "/assets/aether/other/InvisCape.png"), "cape_invis.png");
        armorCapeAgility = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.agility", itemID++, "/assets/aether/other/AgilityCape.png"), "cape_agility.png");

        armorCapeWhite = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.white", itemID++, "/assets/aether/other/WhiteCape.png"), "cape_white.png");
        armorCapeRed = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.red", itemID++, "/assets/aether/other/RedCape.png"), "cape_red.png");
        armorCapeYellow = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.yellow", itemID++, "/assets/aether/other/YellowCape.png"), "cape_yellow.png");
        armorCapeBlue = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.blue", itemID++, "/assets/aether/other/BlueCape.png"), "cape_blue.png");

        foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFood("food.gummy.blue", itemID++, 20, false, 64), "food_swet_blue.png");
        foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFood("food.gummy.gold", itemID++, 40, false, 64), "food_swet_gold.png");

        cloudParachute = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute", itemID++), "parachute.png").setMaxStackSize(1);
        cloudParachuteGold = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute.gold", itemID++), "parachute_gold.png").setMaxStackSize(1);

        lifeshard = ItemHelper.createItem(MOD_ID, new ItemLifeShard("food.lifeshard", itemID++), "food_lifeshard.png");
        lanternAether = ItemHelper.createItem(MOD_ID, new ItemPlaceable("lantern.firefly.silver", itemID++, AetherBlocks.lanternFireflyAether), "lantern_firefly_silver.png");

    }

}