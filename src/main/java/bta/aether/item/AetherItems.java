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

    public static final Item victorymedal = ItemHelper.createItem(MOD_ID, new Item(itemID++), "victorymedal", "VictoryMedal.png").setMaxStackSize(10);
    public static final Item keyBronze = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.bronze", "BronzeKey.png").setMaxStackSize(1);
    public static final Item keySilver = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.silver", "SilverKey.png").setMaxStackSize(1);
    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "key.gold", "GoldKey.png").setMaxStackSize(1);

    public static final Item bookLore1 = ItemHelper.createItem(MOD_ID, new Item(itemID++), "book.lore1", "OverworldBook.png");
    public static final Item bookLore2 = ItemHelper.createItem(MOD_ID, new Item(itemID++), "book.lore2", "NetherBook.png");
    public static final Item bookLore3 = ItemHelper.createItem(MOD_ID, new Item(itemID++), "book.lore3", "AetherBook.png");
    public static final Item bookLore4 = ItemHelper.createItem(MOD_ID, new Item(itemID++), "book.lore4", "ParadiseBook.png");

    public static final Item eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.blue", "BlueMoaEgg.png");
    public static final Item eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.black", "BlackMoaEgg.png");
    public static final Item eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "egg.moa.white", "WhiteMoaEgg.png");

    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new Item(18400), "record.blue", "BlueMusicDisk.png").setMaxStackSize(1);

    public static final Item amberGolden = ItemHelper.createItem(MOD_ID, new Item(itemID++), "goldenamber", "GoldenAmber.png");
    public static final Item petalAechor = ItemHelper.createItem(MOD_ID, new Item(itemID++), "aechorpetal", "AechorPetal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item(itemID++), "stick.skyroot", "Stick.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.gold", "DartGolden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.poison", "DartPoison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item(itemID++), "ammo.dart.enchanted", "DartEnchanted.png");

    public static final Item dartshooter = ItemHelper.createItem(MOD_ID, new ItemShooter(itemID++), "tool.dart.shooter", "DartShooter.png");
    public static final Item dartshooterPoison = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.dart.shooter.poison", "DartShooterPoison.png");
    public static final Item dartshooterEnchanted = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.dart.shooter.enchanted", "DartShooterEnchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("ambrosium", itemID++, 1, false, 64), "ambrosium", "AmbrosiumShard.png");
    public static final Item gemZanite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "zanite", "Zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty(itemID++), "bucket.skyroot", "Bucket.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, fluidWaterFlowing, 0), "bucket.skyroot.water", "BucketWater.png");
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 1), "bucket.skyroot.milk", "BucketMilk.png");
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 2), "bucket.skyroot.poison", "BucketPoison.png");
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket(itemID++, null, 3), "bucket.skyroot.remedy", "BucketRemedy.png");

    public static final Item healingstone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", itemID++, 4, false), "food.healingstone", "HealingStone.png");

    public static final ToolMaterial toolskyroot = new ToolMaterial().setDurability(64).setEfficiency(2.0F, 4.0f).setMiningLevel(0).setBlockHitDelay(0);
    public static final ToolMaterial toolholystone = new ToolMaterial().setDurability(128).setEfficiency(4.0F, 6.0F).setMiningLevel(1).setBlockHitDelay(0);
    public static final ToolMaterial toolzanite = new ToolMaterial().setDurability(256).setEfficiency(6.0F, 8.0F).setMiningLevel(2).setBlockHitDelay(0);
    public static final ToolMaterial toolgravitite = new ToolMaterial().setDurability(1536).setEfficiency(8.0F, 25.0F).setMiningLevel(3).setBlockHitDelay(2);
    public static final ToolMaterial toolvalkyrie = new ToolMaterial().setDurability(1536).setEfficiency(10.0f, 35.0f).setMiningLevel(3).setBlockHitDelay(3);

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.skyroot",itemID++, toolskyroot), "tool.pickaxe.skyroot", "PickSkyroot.png");
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSkyrootShovel("tool.shovel.skyroot",itemID++, toolskyroot), "tool.shovel.skyroot", "ShovelSkyroot.png");
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.skyroot", itemID++, toolskyroot), "tool.axe.skyroot", "AxeSkyroot.png");
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", itemID++, toolskyroot), "tool.sword.skyroot", "SwordSkyroot.png");

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.holystone",itemID++, toolholystone), "tool.pickaxe.holystone", "PickHolystone.png");
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.holystone",itemID++, toolholystone), "tool.shovel.holystone", "ShovelHolystone.png");
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.holystone",itemID++, toolholystone), "tool.axe.holystone", "AxeHolystone.png");
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", itemID++, toolholystone), "tool.sword.holystone", "SwordHolystone.png");

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.zanite", itemID++, toolzanite), "tool.pickaxe.zanite", "PickZanite.png");
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.zanite", itemID++, toolzanite), "tool.shovel.zanite", "ShovelZanite.png");
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.zanite", itemID++, toolzanite), "tool.axe.zanite", "AxeZanite.png");
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", itemID++, toolzanite), "tool.sword.zanite", "SwordZanite.png");

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.gravitite", itemID++, toolgravitite), "tool.pickaxe.gravitite", "PickGravitite.png");
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.gravitite", itemID++, toolgravitite), "tool.shovel.gravitite", "ShovelGravitite.png");
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.gravitite", itemID++, toolgravitite), "tool.axe.gravitite", "AxeGravitite.png");
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", itemID++, toolgravitite), "tool.sword.gravitite", "SwordGravitite.png");

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.valkyrie", itemID++, toolvalkyrie), "tool.pickaxe.valkyrie", "ValkyriePickaxe.png");
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.valkyrie", itemID++, toolvalkyrie), "tool.shovel.valkyrie", "ValkyrieShovel.png");
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.valkyrie", itemID++, toolvalkyrie), "tool.axe.valkyrie", "ValkyrieAxe.png");
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", itemID++, toolvalkyrie), "tool.sword.valkyrie", "Lance.png");

    //Armor
    public static final ArmorMaterial armorzanite = ArmorHelper.createArmorMaterial("Zanite", 200, 0.0f, 0.0f, 0.0f, 0.0f); // all zeros are intended, uses custom protection values
    public static final ArmorMaterial armorgravitite = ArmorHelper.createArmorMaterial("Gravitite", 800, 0.0f, 0.0f, 0.0f, 150.0f);
    public static final ArmorMaterial armorphoenix = ArmorHelper.createArmorMaterial("Phoenix", 800, 0.0f, 0.0f, 150.0f, 0.0f);
    public static final ArmorMaterial armorobsidian = ArmorHelper.createArmorMaterial("Obsidian", 1200, 0.0f, 150.0f, 50.0f, 0.0f);
    public static final ArmorMaterial armorneptune = ArmorHelper.createArmorMaterial("Neptune", 800, 150.0f, 0.0f, 0.0f, 0.0f);

    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Helmet", itemID++, armorzanite, 0), "armor.helmet.zanite", "ZaniteHelmet.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Chestplate", itemID++, armorzanite, 1), "armor.chestplate.zanite", "ZaniteChestplate.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Leggings", itemID++, armorzanite, 2), "armor.leggings.zanite", "ZaniteLeggings.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Boots", itemID++, armorzanite, 3), "armor.boots.zanite", "ZaniteBoots.png");
    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Helmet", itemID++, armorgravitite, 0), "armor.helmet.gravitite", "GravititeHelmet.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Chestplate", itemID++, armorgravitite, 1), "armor.chestplate.gravitite", "GravititeChestplate.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Leggings", itemID++, armorgravitite, 2), "armor.leggings.gravitite", "GravititeLeggings.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Boots", itemID++, armorgravitite, 3), "armor.boots.gravitite", "GravititeBoots.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Helmet", itemID++, armorphoenix, 0), "armor.helmet.phoenix", "PhoenixHelmet.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Chestplate", itemID++, armorphoenix, 1), "armor.chestplate.phoenix", "PhoenixChestplate.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Leggings", itemID++, armorphoenix, 2), "armor.leggings.phoenix", "PhoenixLeggings.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Boots", itemID++, armorphoenix, 3), "armor.boots.phoenix", "PhoenixBoots.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Helmet", itemID++, armorobsidian, 0), "armor.helmet.obsidian", "ObsidianHelmet.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Chestplate", itemID++, armorobsidian, 1), "armor.chestplate.obsidian", "ObsidianChestplate.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Leggings", itemID++, armorobsidian, 2), "armor.leggings.obsidian", "ObsidianLeggings.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Boots", itemID++, armorobsidian, 3), "armor.boots.obsidian", "ObsidianBoots.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Helmet", itemID++,armorneptune, 0), "armor.helmet.neptune", "NeptuneHelmet.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Chestplate", itemID++, armorneptune, 1), "armor.chestplate.neptune", "NeptuneChestplate.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Leggings", itemID++, armorneptune, 2), "armor.leggings.neptune", "NeptuneLeggings.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Boots", itemID++, armorneptune, 3), "armor.boots.neptune", "NeptuneBoots.png");

    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new ItemPigSlayer(itemID++), "tool.sword.pig", "PigSlayer.png");
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.sword.vampire", "VampireBlade.png");

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.sword.flaming", "FlameSword.png");
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.sword.holy", "HolySword.png");
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.sword.lightning", "LightningSword.png");

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.staff.nature", "NatureStaff.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.staff.cloud", "CloudStaff.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife(itemID++), "tool.knife.lightning", "LightningKnife.png");
    public static final Item toolHammerMajonk = ItemHelper.createItem(MOD_ID, new Item(itemID++), "tool.hammer.majonk", "HammerNotch.png");
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow(itemID++), "tool.bow.phoenix", "PhoenixBow.png");

    public static final Item armorGlovesLeather = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.leather", "LeatherGloves.png");
    public static final Item armorGlovesIron = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.iron", "IronGloves.png");
    public static final Item armorGlovesGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.gold", "GoldGloves.png");
    public static final Item armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.diamond", "DiamondGloves.png");
    public static final Item armorGlovesZanite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.zanite", "ZaniteGloves.png");
    public static final Item armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.gravitite", "GravititeGloves.png");
    public static final Item armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.phoenix", "PhoenixGloves.png");
    public static final Item armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.obsidian", "ObsidianGloves.png");
    public static final Item armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.gloves.neptune", "NeptuneGloves.png");

    public static final Item armorRingIron = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.ring.iron", "IronRing.png");
    public static final Item armorRingGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.ring.gold", "GoldRing.png");
    public static final Item armorRingZanite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.ring.zanite", "ZaniteRing.png");
    public static final Item armorRingIce = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.ring.ice", "IceRing.png");

    public static final Item armorPendantIron = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.pendant.iron", "IronPendant.png");
    public static final Item armorPendantGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.pendant.gold", "GoldPendant.png");
    public static final Item armorPendantZanite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.pendant.zanite", "ZanitePendant.png");
    public static final Item armorPendantIce = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.pendant.ice", "IcePendant.png");

    public static final Item armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.talisman.ironbubble", "IronBubble.png");
    public static final Item armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.talisman.goldenfeather", "GoldenFeather.png");
    public static final Item armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.talisman.regenstone", "RegenerationStone.png");

    public static final Item armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.shield.repulsion", "RepulsionShield.png");

    public static final Item armorCapeSwet = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.swet", "AetherCape.png");
    public static final Item armorCapeInvisibility = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.invisibility", "InvisibilityCloak.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.agility", "AgilityCape.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.white", "Cape.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.red", "RedCape.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.yellow", "YellowCape.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new Item(itemID++), "armor.cape.blue", "BlueCape.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.blue", 17107, 20, false, 64), "food.gummy.blue", "BlueGummy.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.gold", 17108, 20, false, 64), "food.gummy.gold", "GoldGummy.png");

    public static final Item cloudparachute = ItemHelper.createItem(MOD_ID, new Item(itemID++), "cloud.parachute", "CloudParachute.png");
    public static final Item cloudparachuteGold = ItemHelper.createItem(MOD_ID, new Item(itemID++), "cloud.parachute.gold", "GoldenParachute.png");

    public static final Item lifeshard = ItemHelper.createItem(MOD_ID, new Item(itemID++), "food.lifeshard", "LifeShard.png");

    public void initializeItems(){}
}
