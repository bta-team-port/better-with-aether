package bta.aether.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemFoodStackable;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.item.tool.ItemToolSword;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemHelper;

import static net.minecraft.core.block.Block.fluidWaterFlowing;
import static bta.aether.Aether.MOD_ID;

public class AetherItems {
    private static int itemID = 17000;

    public static final Item victorymedal = ItemHelper.createItem(MOD_ID, new Item(17000), "victorymedal", "VictoryMedal.png").setMaxStackSize(10);
    public static final Item keyBronze = ItemHelper.createItem(MOD_ID, new Item(17001), "key.bronze", "BronzeKey.png").setMaxStackSize(1);
    public static final Item keySilver = ItemHelper.createItem(MOD_ID, new Item(17002), "key.silver", "SilverKey.png").setMaxStackSize(1);
    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item(17003), "key.gold", "GoldKey.png").setMaxStackSize(1);

    public static final Item bookLore1 = ItemHelper.createItem(MOD_ID, new Item(17004), "book.lore1", "OverworldBook.png");
    public static final Item bookLore2 = ItemHelper.createItem(MOD_ID, new Item(17005), "book.lore2", "NetherBook.png");
    public static final Item bookLore3 = ItemHelper.createItem(MOD_ID, new Item(17006), "book.lore3", "AetherBook.png");
    public static final Item bookLore4 = ItemHelper.createItem(MOD_ID, new Item(17107), "book.lore4", "ParadiseBook.png");

    public static final Item eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item(17008), "egg.moa.blue", "BlueMoaEgg.png");
    public static final Item eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item(17009), "egg.moa.black", "BlackMoaEgg.png");
    public static final Item eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item(17010), "egg.moa.white", "WhiteMoaEgg.png");

    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new Item(18400), "record.blue", "BlueMusicDisk.png").setMaxStackSize(1);

    public static final Item amberGolden = ItemHelper.createItem(MOD_ID, new Item(17011), "goldenamber", "GoldenAmber.png");
    public static final Item petalAechor = ItemHelper.createItem(MOD_ID, new Item(17012), "aechorpetal", "AechorPetal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item(17013), "stick.skyroot", "Stick.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item(17014), "ammo.dart.gold", "DartGolden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item(17015), "ammo.dart.poison", "DartPoison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item(17016), "ammo.dart.enchanted", "DartEnchanted.png");

    public static final Item dartshooter = ItemHelper.createItem(MOD_ID, new ItemShooter(17018), "tool.dart.shooter", "DartShooter.png");
    public static final Item dartshooterPoison = ItemHelper.createItem(MOD_ID, new Item(17019), "tool.dart.shooter.poison", "DartShooterPoison.png");
    public static final Item dartshooterEnchanted = ItemHelper.createItem(MOD_ID, new Item(17020), "tool.dart.shooter.enchanted", "DartShooterEnchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("ambrosium", 17021, 1, false, 64), "ambrosium", "AmbrosiumShard.png");
    public static final Item gemZanite = ItemHelper.createItem(MOD_ID, new Item(17022), "zanite", "Zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty(17023), "bucket.skyroot", "Bucket.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(17024, fluidWaterFlowing), "bucket.skyroot.water", "BucketWater.png");
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(17025, null), "bucket.skyroot.milk", "BucketMilk.png");
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(17026, null), "bucket.skyroot.poison", "BucketPoison.png");
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(17027, null), "bucket.skyroot.remedy", "BucketRemedy.png");

    public static final Item healingstone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", 17028, 4, false), "food.healingstone", "HealingStone.png");

    public static final ToolMaterial toolskyroot = new ToolMaterial().setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0).setBlockHitDelay(0);
    public static final ToolMaterial toolholystone = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1).setBlockHitDelay(0);
    public static final ToolMaterial toolzanite = new ToolMaterial().setDurability(256).setEfficiency(6.0F, 8.0F).setMiningLevel(2).setBlockHitDelay(0);
    public static final ToolMaterial toolgravitite = new ToolMaterial().setDurability(1536).setEfficiency(8.0F, 25.0F).setMiningLevel(3).setBlockHitDelay(2);
    public static final ToolMaterial toolvalkyrie = new ToolMaterial().setDurability(1536).setEfficiency(10.0f, 35.0f).setMiningLevel(3).setBlockHitDelay(3);

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.skyroot",17029, toolskyroot), "tool.pickaxe.skyroot", "PickSkyroot.png");
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSkyrootShovel("tool.shovel.skyroot",17030, toolskyroot), "tool.shovel.skyroot", "ShovelSkyroot.png");
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.skyroot", 17031, toolskyroot), "tool.axe.skyroot", "AxeSkyroot.png");
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", 17032, toolskyroot), "tool.sword.skyroot", "SwordSkyroot.png");

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.holystone",17033, toolholystone), "tool.pickaxe.holystone", "PickHolystone.png");
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.holystone",17034, toolholystone), "tool.shovel.holystone", "ShovelHolystone.png");
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.holystone",17035, toolholystone), "tool.axe.holystone", "AxeHolystone.png");
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", 17036, toolholystone), "tool.sword.holystone", "SwordHolystone.png");

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.zanite", 17037, toolzanite), "tool.pickaxe.zanite", "PickZanite.png");
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.zanite", 17038, toolzanite), "tool.shovel.zanite", "ShovelZanite.png");
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.zanite", 17039, toolzanite), "tool.axe.zanite", "AxeZanite.png");
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", 17040, toolzanite), "tool.sword.zanite", "SwordZanite.png");

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.gravitite", 17041, toolgravitite), "tool.pickaxe.gravitite", "PickGravitite.png");
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.gravitite", 17042, toolgravitite), "tool.shovel.gravitite", "ShovelGravitite.png");
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.gravitite", 17043, toolgravitite), "tool.axe.gravitite", "AxeGravitite.png");
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", 17044, toolgravitite), "tool.sword.gravitite", "SwordGravitite.png");

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.valkyrie", 17045, toolvalkyrie), "tool.pickaxe.valkyrie", "ValkyriePickaxe.png");
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.valkyrie", 17046, toolvalkyrie), "tool.shovel.valkyrie", "ValkyrieShovel.png");
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.valkyrie", 17047, toolvalkyrie), "tool.axe.valkyrie", "ValkyrieAxe.png");
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", 17048, toolvalkyrie), "tool.sword.valkyrie", "Lance.png");

    //Armor
    public static final ArmorMaterial armorzanite = ArmorHelper.createArmorMaterial("Zanite", 200, 0.0f, 0.0f, 0.0f, 0.0f); // all zeros are intended, uses custom protection values
    public static final ArmorMaterial armorgravitite = ArmorHelper.createArmorMaterial("Gravitite", 800, 0.0f, 0.0f, 0.0f, 150.0f);
    public static final ArmorMaterial armorphoenix = ArmorHelper.createArmorMaterial("Phoenix", 800, 0.0f, 0.0f, 150.0f, 0.0f);
    public static final ArmorMaterial armorobsidian = ArmorHelper.createArmorMaterial("Obsidian", 1200, 0.0f, 150.0f, 50.0f, 0.0f);
    public static final ArmorMaterial armorneptune = ArmorHelper.createArmorMaterial("Neptune", 800, 150.0f, 0.0f, 0.0f, 0.0f);

    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Helmet", 17049, armorzanite, 0), "armor.helmet.zanite", "ZaniteHelmet.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Chestplate", 17050, armorzanite, 1), "armor.chestplate.zanite", "ZaniteChestplate.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Leggings", 17051, armorzanite, 2), "armor.leggings.zanite", "ZaniteLeggings.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Boots", 17052, armorzanite, 3), "armor.boots.zanite", "ZaniteBoots.png");
    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Helmet", 17053, armorgravitite, 0), "armor.helmet.gravitite", "GravititeHelmet.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Chestplate", 17054, armorgravitite, 1), "armor.chestplate.gravitite", "GravititeChestplate.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Leggings", 17055, armorgravitite, 2), "armor.leggings.gravitite", "GravititeLeggings.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Boots", 17056, armorgravitite, 3), "armor.boots.gravitite", "GravititeBoots.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Helmet", 17057, armorphoenix, 0), "armor.helmet.phoenix", "PhoenixHelmet.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Chestplate", 17058, armorphoenix, 1), "armor.chestplate.phoenix", "PhoenixChestplate.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Leggings", 17059, armorphoenix, 2), "armor.leggings.phoenix", "PhoenixLeggings.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Boots", 17060, armorphoenix, 3), "armor.boots.phoenix", "PhoenixBoots.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Helmet", 17061, armorobsidian, 0), "armor.helmet.obsidian", "ObsidianHelmet.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Chestplate", 17062, armorobsidian, 1), "armor.chestplate.obsidian", "ObsidianChestplate.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Leggings", 17063, armorobsidian, 2), "armor.leggings.obsidian", "ObsidianLeggings.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Boots", 17064, armorobsidian, 3), "armor.boots.obsidian", "ObsidianBoots.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Helmet", 17065,armorneptune, 0), "armor.helmet.neptune", "NeptuneHelmet.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Chestplate", 17066, armorneptune, 1), "armor.chestplate.neptune", "NeptuneChestplate.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Leggings", 17067, armorneptune, 2), "armor.leggings.neptune", "NeptuneLeggings.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Boots", 17068, armorneptune, 3), "armor.boots.neptune", "NeptuneBoots.png");

    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new Item(17069), "tool.sword.pig", "PigSlayer.png");
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new Item(17070), "tool.sword.vampire", "VampireBlade.png");

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new Item(17071), "tool.sword.flaming", "FlameSword.png");
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new Item(17072), "tool.sword.holy", "HolySword.png");
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new Item(17073), "tool.sword.lightning", "LightningSword.png");

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item(17074), "tool.staff.nature", "NatureStaff.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item(17075), "tool.staff.cloud", "CloudStaff.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife(17076), "tool.knife.lightning", "LightningKnife.png");
    public static final Item toolHammerMajonk = ItemHelper.createItem(MOD_ID, new Item(17077), "tool.hammer.majonk", "HammerNotch.png");
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow(17078), "tool.bow.phoenix", "PhoenixBow.png");

    public static final Item armorGlovesLeather = ItemHelper.createItem(MOD_ID, new Item(17079), "armor.gloves.leather", "LeatherGloves.png");
    public static final Item armorGlovesIron = ItemHelper.createItem(MOD_ID, new Item(17080), "armor.gloves.iron", "IronGloves.png");
    public static final Item armorGlovesGold = ItemHelper.createItem(MOD_ID, new Item(17081), "armor.gloves.gold", "GoldGloves.png");
    public static final Item armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new Item(17082), "armor.gloves.diamond", "DiamondGloves.png");
    public static final Item armorGlovesZanite = ItemHelper.createItem(MOD_ID, new Item(17083), "armor.gloves.zanite", "ZaniteGloves.png");
    public static final Item armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new Item(17084), "armor.gloves.gravitite", "GravititeGloves.png");
    public static final Item armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new Item(17085), "armor.gloves.phoenix", "PhoenixGloves.png");
    public static final Item armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new Item(17086), "armor.gloves.obsidian", "ObsidianGloves.png");
    public static final Item armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new Item(17087), "armor.gloves.neptune", "NeptuneGloves.png");

    public static final Item armorRingIron = ItemHelper.createItem(MOD_ID, new Item(17088), "armor.ring.iron", "IronRing.png");
    public static final Item armorRingGold = ItemHelper.createItem(MOD_ID, new Item(17089), "armor.ring.gold", "GoldRing.png");
    public static final Item armorRingZanite = ItemHelper.createItem(MOD_ID, new Item(17090), "armor.ring.zanite", "ZaniteRing.png");
    public static final Item armorRingIce = ItemHelper.createItem(MOD_ID, new Item(17091), "armor.ring.ice", "IceRing.png");

    public static final Item armorPendantIron = ItemHelper.createItem(MOD_ID, new Item(17092), "armor.pendant.iron", "IronPendant.png");
    public static final Item armorPendantGold = ItemHelper.createItem(MOD_ID, new Item(17093), "armor.pendant.gold", "GoldPendant.png");
    public static final Item armorPendantZanite = ItemHelper.createItem(MOD_ID, new Item(17094), "armor.pendant.zanite", "ZanitePendant.png");
    public static final Item armorPendantIce = ItemHelper.createItem(MOD_ID, new Item(17095), "armor.pendant.ice", "IcePendant.png");

    public static final Item armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new Item(17096), "armor.talisman.ironbubble", "IronBubble.png");
    public static final Item armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new Item(17097), "armor.talisman.goldenfeather", "GoldenFeather.png");
    public static final Item armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new Item(17098), "armor.talisman.regenstone", "RegenerationStone.png");

    public static final Item armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new Item(17099), "armor.shield.repulsion", "RepulsionShield.png");

    public static final Item armorCapeSwet = ItemHelper.createItem(MOD_ID, new Item(17100), "armor.cape.swet", "AetherCape.png");
    public static final Item armorCapeInvisibility = ItemHelper.createItem(MOD_ID, new Item(17101), "armor.cape.invisibility", "InvisibilityCloak.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new Item(17102), "armor.cape.agility", "AgilityCape.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new Item(17103), "armor.cape.white", "Cape.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new Item(17104), "armor.cape.red", "RedCape.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new Item(17105), "armor.cape.yellow", "YellowCape.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new Item(17106), "armor.cape.blue", "BlueCape.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.blue", 17107, 20, false, 64), "food.gummy.blue", "BlueGummy.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.gold", 17108, 20, false, 64), "food.gummy.gold", "GoldGummy.png");

    public static final Item cloudparachute = ItemHelper.createItem(MOD_ID, new Item(17109), "cloud.parachute", "CloudParachute.png");
    public static final Item cloudparachuteGold = ItemHelper.createItem(MOD_ID, new Item(17110), "cloud.parachute.gold", "GoldenParachute.png");

    public static final Item lifeshard = ItemHelper.createItem(MOD_ID, new Item(17111), "food.lifeshard", "LifeShard.png");

    public void initializeItems(){}
}
