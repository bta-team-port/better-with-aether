package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.item.Accessories.ItemAccessoryGoldenFeather;
import bta.aether.item.Accessories.ItemAccessoryInvisibilityCloak;
import bta.aether.item.Accessories.ItemAccessoryIronBubble;
import bta.aether.item.Accessories.ItemAccessoryRegenStone;
import bta.aether.item.Accessories.base.*;
import bta.aether.world.AetherDimension;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.*;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.Dimension;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemHelper;

import static net.minecraft.core.block.Block.fluidWaterFlowing;
import static bta.aether.Aether.MOD_ID;

public class AetherItems {
    private static int itemID = 17000;

    // tags
    public static Tag<Item> aetherTool = Tag.of("aether_tool");
    public static Tag<Item> aetherdungeonKey = Tag.of("aether_key");
    public static Tag<Item> aetheregg = Tag.of("aether_egg");

    public static final Item victoryMedal = ItemHelper.createItem(MOD_ID, new Item(itemID++), "victorymedal", "VictoryMedal.png").setMaxStackSize(10);

    // See BlockChestLocked.java before using any of these.
    public static final Item keyBronze = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.bronze", "BronzeKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keySilver = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.silver", "SilverKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.gold", "GoldKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);

    public static final Item bookLoreOverworld = ItemHelper.createItem(MOD_ID, new ItemLoreBook(itemID++, Dimension.overworld.languageKey), "book.lore.overworld", "OverworldBook.png");
    public static final Item bookLoreNether = ItemHelper.createItem(MOD_ID, new ItemLoreBook(itemID++, Dimension.nether.languageKey), "book.lore.nether", "NetherBook.png");
    public static final Item bookLoreAether = ItemHelper.createItem(MOD_ID, new ItemLoreBook(itemID++, AetherDimension.dimensionAether.languageKey), "book.lore.aether", "AetherBook.png");
    public static final Item bookLoreParadise = ItemHelper.createItem(MOD_ID, new ItemLoreBook(itemID++, Dimension.paradise.languageKey), "book.lore.paradise", "ParadiseBook.png");

    public static final Item eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.blue", "BlueMoaEgg.png").withTags(aetheregg);
    public static final Item eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.black", "BlackMoaEgg.png").withTags(aetheregg);
    public static final Item eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.white", "WhiteMoaEgg.png").withTags(aetheregg);

    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor(MOD_ID, 18400, "AetherTune", "Noisestorm"), "record.blue", "BlueMusicDisk.png").setMaxStackSize(1);
    public static final Item recordSilver = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor(MOD_ID, 18401, "AMorningWish", "Emile van Krieken"), "record.silver", "SilverMusicDisk.png").setMaxStackSize(1);
    public static final Item recordPink = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor(MOD_ID, 18402, "AscendingDawn", "Emile van Krieken"), "record.pink", "PinkMusicDisk.png").setMaxStackSize(1);

    public static final Item amberGolden = ItemHelper.createItem(MOD_ID, new Item(itemID++), "goldenamber", "GoldenAmber.png");
    public static final Item petalAechor = ItemHelper.createItem(MOD_ID, new Item(itemID++), "aechorpetal", "AechorPetal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item(itemID++), "stick.skyroot", "Stick.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.gold", "DartGolden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.poison", "DartPoison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.enchanted", "DartEnchanted.png");

    public static final Item dartShooter = ItemHelper.createItem(MOD_ID, new ItemShooter(itemID++), "tool.dart.shooter", "DartShooter.png");
    public static final Item dartShooterPoison = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.dart.shooter.poison", "DartShooterPoison.png");
    public static final Item dartShooterEnchanted = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.dart.shooter.enchanted", "DartShooterEnchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("ambrosium", itemID++, 1, false, 64), "ambrosium", "AmbrosiumShard.png");
    public static final Item gemZanite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "zanite", "Zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty(itemID++), "bucket.skyroot", "Bucket.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, fluidWaterFlowing, 0), "bucket.skyroot.water", "BucketWater.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 1), "bucket.skyroot.milk", "BucketMilk.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 2), "bucket.skyroot.poison", "BucketPoison.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 3), "bucket.skyroot.remedy", "BucketRemedy.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootIcecream = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketIceCream(itemID++, 10), "bucket.skyroot.icecream", "BucketIceCream.png").setContainerItem(bucketSkyroot);

    public static final Item healingStone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", itemID++, 4, false), "food.healingstone", "HealingStone.png");

    public static final ToolMaterial toolskyroot = new ToolMaterialAether().setDropMultipier(2).setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0).setBlockHitDelay(0);
    public static final ToolMaterial toolholystone = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1).setBlockHitDelay(0);
    public static final ToolMaterial toolzanite = new ToolMaterial().setDurability(256).setEfficiency(6.0F, 8.0F).setMiningLevel(2).setBlockHitDelay(0);
    public static final ToolMaterial toolgravitite = new ToolMaterial().setDurability(1536).setEfficiency(8.0F, 25.0F).setMiningLevel(3).setBlockHitDelay(2);
    public static final ToolMaterial toolvalkyrie = new ToolMaterial().setDurability(1536).setEfficiency(10.0f, 35.0f).setMiningLevel(3).setBlockHitDelay(3);
    public static final ToolMaterial swordSpecialMaterial = new ToolMaterial().setDurability(32).setEfficiency(2.0f, 4.0f).setMiningLevel(0);
    public static final ToolMaterial swordHolyMaterial = new ToolMaterial().setDurability(128).setEfficiency(2.0f, 4.0f).setMiningLevel(0);

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.skyroot",itemID++, toolskyroot), "tool.pickaxe.skyroot", "PickSkyroot.png").withTags(aetherTool);
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.skyroot",itemID++, toolskyroot), "tool.shovel.skyroot", "ShovelSkyroot.png").withTags(aetherTool);
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.skyroot", itemID++, toolskyroot), "tool.axe.skyroot", "AxeSkyroot.png").withTags(aetherTool);
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", itemID++, toolskyroot), "tool.sword.skyroot", "SwordSkyroot.png").withTags(ItemTags.preventCreativeMining, aetherTool);;

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.holystone",itemID++, toolholystone), "tool.pickaxe.holystone", "PickHolystone.png").withTags(aetherTool);
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.holystone",itemID++, toolholystone), "tool.shovel.holystone", "ShovelHolystone.png").withTags(aetherTool);
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.holystone",itemID++, toolholystone), "tool.axe.holystone", "AxeHolystone.png").withTags(aetherTool);
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", itemID++, toolholystone), "tool.sword.holystone", "SwordHolystone.png").withTags(ItemTags.preventCreativeMining, aetherTool);;

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.zanite", itemID++, toolzanite), "tool.pickaxe.zanite", "PickZanite.png").withTags(aetherTool);
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.zanite", itemID++, toolzanite), "tool.shovel.zanite", "ShovelZanite.png").withTags(aetherTool);
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.zanite", itemID++, toolzanite), "tool.axe.zanite", "AxeZanite.png").withTags(aetherTool);
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", itemID++, toolzanite), "tool.sword.zanite", "SwordZanite.png").withTags(ItemTags.preventCreativeMining, aetherTool);;

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.gravitite", itemID++, toolgravitite), "tool.pickaxe.gravitite", "PickGravitite.png").withTags(aetherTool);
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.gravitite", itemID++, toolgravitite), "tool.shovel.gravitite", "ShovelGravitite.png").withTags(aetherTool);
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.gravitite", itemID++, toolgravitite), "tool.axe.gravitite", "AxeGravitite.png").withTags(aetherTool);
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", itemID++, toolgravitite), "tool.sword.gravitite", "SwordGravitite.png").withTags(ItemTags.preventCreativeMining, aetherTool);;

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.valkyrie", itemID++, toolvalkyrie), "tool.pickaxe.valkyrie", "ValkyriePickaxe.png").withTags(aetherTool);
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.valkyrie", itemID++, toolvalkyrie), "tool.shovel.valkyrie", "ValkyrieShovel.png").withTags(aetherTool);
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.valkyrie", itemID++, toolvalkyrie), "tool.axe.valkyrie", "ValkyrieAxe.png").withTags(aetherTool);
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", itemID++, toolvalkyrie), "tool.sword.valkyrie", "Lance.png").withTags(ItemTags.preventCreativeMining, aetherTool);;

    //Armor
    public static final ArmorMaterial armorZanite = ArmorHelper.createArmorMaterial("Zanite", 200, 0f, 0f, 0f, 0f); // all zeros are intended, uses custom protection values
    public static final ArmorMaterial armorGravitite = ArmorHelper.createArmorMaterial("Gravitite", 800, 35f, 35f, 35f, 150f);
    public static final ArmorMaterial armorPhoenix = ArmorHelper.createArmorMaterial("Phoenix", 800, 80f, 20f, 150f, 45f);
    public static final ArmorMaterial armorObsidian = ArmorHelper.createArmorMaterial("Obsidian", 1200, 35f, 150f, 65f, 0f);
    public static final ArmorMaterial armorNeptune = ArmorHelper.createArmorMaterial("Neptune", 800, 150f, 100f, 0f, 55f).withProtectionPercentage(DamageType.DROWN, 50f);

    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Helmet", itemID++, armorZanite, 0), "armor.helmet.zanite", "ZaniteHelmet.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Chestplate", itemID++, armorZanite, 1), "armor.chestplate.zanite", "ZaniteChestplate.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Leggings", itemID++, armorZanite, 2), "armor.leggings.zanite", "ZaniteLeggings.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Boots", itemID++, armorZanite, 3), "armor.boots.zanite", "ZaniteBoots.png");
    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Helmet", itemID++, armorGravitite, 0), "armor.helmet.gravitite", "GravititeHelmet.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Chestplate", itemID++, armorGravitite, 1), "armor.chestplate.gravitite", "GravititeChestplate.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Leggings", itemID++, armorGravitite, 2), "armor.leggings.gravitite", "GravititeLeggings.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Boots", itemID++, armorGravitite, 3), "armor.boots.gravitite", "GravititeBoots.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Helmet", itemID++, armorPhoenix, 0), "armor.helmet.phoenix", "PhoenixHelmet.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Chestplate", itemID++, armorPhoenix, 1), "armor.chestplate.phoenix", "PhoenixChestplate.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Leggings", itemID++, armorPhoenix, 2), "armor.leggings.phoenix", "PhoenixLeggings.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Boots", itemID++, armorPhoenix, 3), "armor.boots.phoenix", "PhoenixBoots.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Helmet", itemID++, armorObsidian, 0), "armor.helmet.obsidian", "ObsidianHelmet.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Chestplate", itemID++, armorObsidian, 1), "armor.chestplate.obsidian", "ObsidianChestplate.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Leggings", itemID++, armorObsidian, 2), "armor.leggings.obsidian", "ObsidianLeggings.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Boots", itemID++, armorObsidian, 3), "armor.boots.obsidian", "ObsidianBoots.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Helmet", itemID++, armorNeptune, 0), "armor.helmet.neptune", "NeptuneHelmet.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Chestplate", itemID++, armorNeptune, 1), "armor.chestplate.neptune", "NeptuneChestplate.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Leggings", itemID++, armorNeptune, 2), "armor.leggings.neptune", "NeptuneLeggings.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Boots", itemID++, armorNeptune, 3), "armor.boots.neptune", "NeptuneBoots.png");
   

    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new ItemPigSlayer(itemID++), "tool.sword.pig", "PigSlayer.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new ItemVampireSword("tool.sword.vampire", itemID++, ToolMaterial.diamond), "tool.sword.vampire", "VampireBlade.png").withTags(ItemTags.preventCreativeMining);

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new ItemSwordFlaming("tool.sword.flaming", itemID++, swordSpecialMaterial), "tool.sword.flaming", "FlameSword.png").withTags(ItemTags.preventCreativeMining);;
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new ItemSwordHoly("tool.sword.holy", itemID++, swordHolyMaterial), "tool.sword.holy", "HolySword.png").withTags(ItemTags.preventCreativeMining);;
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new ItemSwordLightning("tool.sword.lightning", itemID++, swordSpecialMaterial), "tool.sword.lightning", "LightningSword.png").withTags(ItemTags.preventCreativeMining);;

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.staff.nature", "NatureStaff.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.staff.cloud", "CloudStaff.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife(itemID++), "tool.knife.lightning", "LightningKnife.png").withTags(ItemTags.preventCreativeMining);;
    public static final Item toolHammerNotch = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.hammer.notch", "HammerNotch.png").withTags(ItemTags.preventCreativeMining);;
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow(itemID++), "tool.bow.phoenix", "PhoenixBow.png");

    public static final Item armorGlovesLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/LeatherAccessories.png"), "armor.gloves.leather", "LeatherGloves.png");
    public static final Item armorGlovesChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/ChainAccessories.png"), "armor.gloves.chain", "GloveChain.png");
    public static final Item armorGlovesIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/Accessories.png"), "armor.gloves.iron", "IronGloves.png");
    public static final Item armorGlovesGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/GoldAccessories.png"), "armor.gloves.gold", "GoldGloves.png");
    public static final Item armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/DiamondAccessories.png"), "armor.gloves.diamond", "DiamondGloves.png");
    public static final Item armorGlovesSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/SteelAccessories.png"), "armor.gloves.steel", "SteelGloves.png");
    public static final Item armorGlovesZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/ZaniteAccessories.png"), "armor.gloves.zanite", "ZaniteGloves.png");
    public static final Item armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/GravititeAccessories.png"), "armor.gloves.gravitite", "GravititeGloves.png");
    public static final Item armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/Phoenix.png"), "armor.gloves.phoenix", "PhoenixGloves.png");
    public static final Item armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/ObsidianAccessories.png"), "armor.gloves.obsidian", "ObsidianGloves.png");
    public static final Item armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves(itemID++, "/armor/NeptuneAccessories.png"), "armor.gloves.neptune", "NeptuneGloves.png");

    public static final Item armorRingIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing(itemID++), "armor.ring.iron", "IronRing.png");
    public static final Item armorRingGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing(itemID++), "armor.ring.gold", "GoldRing.png");
    public static final Item armorRingZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing(itemID++), "armor.ring.zanite", "ZaniteRing.png");
    public static final Item armorRingIce = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing(itemID++), "armor.ring.ice", "IceRing.png");

    public static final Item armorPendantIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant(itemID++, "/armor/Accessories.png"), "armor.pendant.iron", "IronPendant.png");
    public static final Item armorPendantGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant(itemID++, "/armor/GoldAccessories.png"), "armor.pendant.gold", "GoldPendant.png");
    public static final Item armorPendantZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant(itemID++, "/armor/ZaniteAccessories.png"), "armor.pendant.zanite", "ZanitePendant.png");
    public static final Item armorPendantIce = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant(itemID++, "/armor/IceAccessories.png"), "armor.pendant.ice", "IcePendant.png");

    public static final Item armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new ItemAccessoryIronBubble(itemID++), "armor.talisman.ironbubble", "IronBubble.png");
    public static final Item armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldenFeather(itemID++), "armor.talisman.goldenfeather", "GoldenFeather.png");
    public static final Item armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new ItemAccessoryRegenStone(itemID++), "armor.talisman.regenstone", "RegenerationStone.png");

    public static final Item armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new ItemAccessoryShield(itemID++), "armor.shield.repulsion", "RepulsionShield.png");

    public static final Item armorCapeSwet = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/AetherCape.png"), "armor.cape.swet", "AetherCape.png");
    public static final Item armorCapeInvisibility = ItemHelper.createItem(MOD_ID, new ItemAccessoryInvisibilityCloak(itemID++), "armor.cape.invisibility", "InvisibilityCloak.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/AgilityCape.png"), "armor.cape.agility", "AgilityCape.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/WhiteCape.png"), "armor.cape.white", "Cape.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/RedCape.png"), "armor.cape.red", "RedCape.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/YellowCape.png"), "armor.cape.yellow", "YellowCape.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape(itemID++, "/assets/aether/other/BlueCape.png"), "armor.cape.blue", "BlueCape.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.blue", itemID++, 20, false, 64), "food.gummy.blue", "BlueGummy.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.gold", itemID++, 20, false, 64), "food.gummy.gold", "GoldGummy.png");

    public static final Item cloudParachute = ItemHelper.createItem(MOD_ID, new Item(itemID++), "cloud.parachute", "CloudParachute.png").setMaxStackSize(1);
    public static final Item cloudParachuteGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "cloud.parachute.gold", "GoldenParachute.png").setMaxStackSize(1);

    public static final Item lifeshard = ItemHelper.createItem(MOD_ID, new ItemLifeShard(itemID++), "food.lifeshard", "LifeShard.png");
    public static final Item lanternAether = ItemHelper.createItem(MOD_ID, new ItemPlaceable("lantern.firefly.silver",itemID++, AetherBlocks.lanternAetherBlock), "lantern.firefly.silver", "lanternSilverItem.png");

    public static final Item devStick = ItemHelper.createItem(MOD_ID, new ItemDevStick(itemID++), "dev.stick", "Stick.png").setMaxStackSize(1);


    public void initializeItems(){}
}
