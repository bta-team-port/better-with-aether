package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.projectiles.EntityEnchantedDart;
import bta.aether.entity.projectiles.EntityGoldenDart;
import bta.aether.entity.projectiles.EntityPoisonDart;
import bta.aether.entity.projectiles.EntityProjectileModular;
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
import net.minecraft.core.item.*;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.ItemHelper;

import static bta.aether.Aether.MOD_ID;
import static net.minecraft.core.block.Block.fluidWaterFlowing;

@SuppressWarnings({"unused", "unchecked"})
public class AetherItems {
    private static int itemID = 17000;

    // tags
    public static Tag<Item> aetherTool = Tag.of("aether_tool");
    public static Tag<Item> aetherdungeonKey = Tag.of("aether_key");
    public static Tag<Item> aetheregg = Tag.of("aether_egg");

    public static final Item victoryMedal = ItemHelper.createItem(MOD_ID, new Item("victorymedal", itemID++), "VictoryMedal.png").setMaxStackSize(10);

    // See BlockChestLocked.java before using any of these.
    public static final Item keyBronze = ItemHelper.createItem(MOD_ID, new Item("key.bronze", itemID++), "BronzeKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keySilver = ItemHelper.createItem(MOD_ID, new Item("key.silver", itemID++), "SilverKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item("key.gold", itemID++), "GoldKey.png").withTags(aetherdungeonKey).setMaxStackSize(1);

    public static final Item bookLoreOverworld = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.overworld", itemID++, Dimension.overworld.languageKey),  "OverworldBook.png");
    public static final Item bookLoreNether = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.nether", itemID++, Dimension.nether.languageKey),  "NetherBook.png");
    public static final Item bookLoreAether = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.aether", itemID++, AetherDimension.dimensionAether.languageKey),  "AetherBook.png");
    public static final Item bookLoreParadise = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.paradise", itemID++, Dimension.paradise.languageKey),  "paradise_book.png");

    public static final Item eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item("egg.moa.blue", itemID++), "BlueMoaEgg.png").withTags(aetheregg);
    public static final Item eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item("egg.moa.black", itemID++), "BlackMoaEgg.png").withTags(aetheregg);
    public static final Item eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item("egg.moa.white", itemID++), "WhiteMoaEgg.png").withTags(aetheregg);

    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.blue", 18400, "AetherTune", "Noisestorm"), "BlueMusicDisk.png").setMaxStackSize(1);
    public static final Item recordSilver = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.silver", 18401, "AMorningWish", "Emile van Krieken"), "silver_music_disk.png").setMaxStackSize(1);
    public static final Item recordPink = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.pink", 18402, "AscendingDawn", "Emile van Krieken"), "pink_music_disk.png").setMaxStackSize(1);

    public static final Item amberGolden = ItemHelper.createItem(MOD_ID, new Item("goldenamber", itemID++), "GoldenAmber.png");
    public static final Item petalAechor = ItemHelper.createItem(MOD_ID, new Item("aechorpetal", itemID++), "AechorPetal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item("stick.skyroot", itemID++), "Stick.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.gold", itemID++),"DartGolden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.poison", itemID++), "DartPoison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.enchanted",  itemID++), "DartEnchanted.png");

    public static final Item dartShooter = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter", itemID++, AetherItems.dartGolden.id){
        @Override
        public EntityProjectileModular getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
            return new EntityGoldenDart(world, entityPlayer, true);
        }
    }, "DartShooter.png");
    public static final Item dartShooterPoison = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.poison", itemID++, AetherItems.dartPoison.id){
        @Override
        public EntityProjectileModular getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
            return new EntityPoisonDart(world, entityPlayer, true);
        }
    }, "DartShooterPoison.png");
    public static final Item dartShooterEnchanted = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.enchanted", itemID++, AetherItems.dartEnchanted.id){
            @Override
            public EntityProjectileModular getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityEnchantedDart(world, entityPlayer, true);
            }
        }, "DartShooterEnchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("ambrosium", itemID++, 1, false, 64), "AmbrosiumShard.png");
    public static final Item gemZanite = ItemHelper.createItem(MOD_ID, new Item("zanite", itemID++), "Zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty("bucket.skyroot", itemID++), "Bucket.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.water", itemID++, fluidWaterFlowing, 0), "BucketWater.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.milk", itemID++, null, 1), "BucketMilk.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.poison", itemID++, null, 2), "BucketPoison.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.remedy", itemID++, null, 3), "BucketRemedy.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootIcecream = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketIceCream("bucket.skyroot.icecream", itemID++, 10), "skyroot_icecream_bucket.png").setContainerItem(bucketSkyroot);

    public static final Item healingStone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", itemID++, 4, false), "HealingStone.png");

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "PickSkyroot.png").withTags(aetherTool);
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "ShovelSkyroot.png").withTags(aetherTool);
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "AxeSkyroot.png").withTags(aetherTool);
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "SwordSkyroot.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "PickHolystone.png").withTags(aetherTool);
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "ShovelHolystone.png").withTags(aetherTool);
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "AxeHolystone.png").withTags(aetherTool);
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "SwordHolystone.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "PickZanite.png").withTags(aetherTool);
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovelZanite("tool.shovel.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "ShovelZanite.png").withTags(aetherTool);
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxeZanite("tool.axe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "AxeZanite.png").withTags(aetherTool);
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "SwordZanite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "PickGravitite.png").withTags(aetherTool);
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "ShovelGravitite.png").withTags(aetherTool);
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "AxeGravitite.png").withTags(aetherTool);
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "SwordGravitite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "ValkyriePickaxe.png").withTags(aetherTool);
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "ValkyrieShovel.png").withTags(aetherTool);
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "ValkyrieAxe.png").withTags(aetherTool);
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "Lance.png").withTags(ItemTags.preventCreativeMining, aetherTool);


    // --- ARMOR ---
    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.zanite", itemID++, AetherArmorMaterial.ZANITE, 0), "ZaniteHelmet.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.zanite", itemID++, AetherArmorMaterial.ZANITE, 1), "ZaniteChestplate.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.zanite", itemID++, AetherArmorMaterial.ZANITE, 2), "ZaniteLeggings.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.zanite", itemID++, AetherArmorMaterial.ZANITE, 3), "ZaniteBoots.png");
    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 0), "GravititeHelmet.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 1), "GravititeChestplate.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 2), "GravititeLeggings.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 3), "GravititeBoots.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 0), "PhoenixHelmet.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 1), "PhoenixChestplate.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 2), "PhoenixLeggings.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 3), "PhoenixBoots.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 0), "ObsidianHelmet.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 1), "ObsidianChestplate.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 2), "ObsidianLeggings.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 3), "ObsidianBoots.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 0), "NeptuneHelmet.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 1), "NeptuneChestplate.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 2), "NeptuneLeggings.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 3), "NeptuneBoots.png");


    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new ItemPigSlayer("tool.sword.pig", itemID++), "PigSlayer.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new ItemVampireSword("tool.sword.vampire", itemID++, ToolMaterial.diamond), "VampireBlade.png").withTags(ItemTags.preventCreativeMining);

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new ItemSwordFlaming("tool.sword.flaming", itemID++, AetherToolMaterial.SWORD_SPECIAL), "FlameSword.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new ItemSwordHoly("tool.sword.holy", itemID++, AetherToolMaterial.SWORD_HOLY), "HolySword.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new ItemSwordLightning("tool.sword.lightning", itemID++, AetherToolMaterial.SWORD_SPECIAL), "LightningSword.png").withTags(ItemTags.preventCreativeMining);

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item("tool.staff.nature", itemID++), "NatureStaff.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item("tool.staff.cloud", itemID++), "CloudStaff.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife("tool.knife.lightning", itemID++), "LightningKnife.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolHammerNotch = ItemHelper.createItem(MOD_ID, new ItemHammerNotch("tool.hammer.notch", itemID++, ToolMaterial.diamond), "HammerNotch.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow("tool.bow.phoenix", itemID++), "PhoenixBow.png");

    public static final Item armorGlovesLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.leather", itemID++, "/Jar/armor/leather_pendant_and_gloves.png", ArmorMaterial.LEATHER), "LeatherGloves.png");
    public static final Item armorGlovesChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.chain", itemID++, "/Jar/armor/chain_pendant_and_gloves.png", ArmorMaterial.CHAINMAIL), "GloveChain.png");
    public static final Item armorGlovesIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.iron", itemID++, "/Jar/armor/Accessories.png", ArmorMaterial.IRON), "IronGloves.png");
    public static final Item armorGlovesGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gold", itemID++, "/Jar/armor/gold_pendant_and_gloves.png", ArmorMaterial.GOLD), "GoldGloves.png");
    public static final Item armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.diamond", itemID++, "/Jar/armor/diamond_pendant_and_gloves.png", ArmorMaterial.DIAMOND), "DiamondGloves.png");
    public static final Item armorGlovesSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.steel", itemID++, "/Jar/armor/steel_pendant_and_gloves.png", ArmorMaterial.STEEL), "steel_gloves.png");
    public static final Item armorGlovesZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.zanite", itemID++, "/Jar/armor/zanite_pendant_and_gloves.png", AetherArmorMaterial.ZANITE), "ZaniteGloves.png");
    public static final Item armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gravitite", itemID++, "/Jar/armor/gravitite_pendant_and_gloves.png", AetherArmorMaterial.GRAVITITE), "GravititeGloves.png");
    public static final Item armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.phoenix", itemID++, "/Jar/armor/Phoenix.png", AetherArmorMaterial.PHOENIX), "PhoenixGloves.png");
    public static final Item armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.obsidian", itemID++, "/Jar/armor/obsidian_pendant_and_gloves.png", AetherArmorMaterial.OBSIDIAN), "ObsidianGloves.png");
    public static final Item armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.neptune", itemID++, "/Jar/armor/neptune_pendant_and_gloves.png", AetherArmorMaterial.NEPTUNE), "NeptuneGloves.png");

    public static final Item armorRingIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing("armor.ring.iron", itemID++), "IronRing.png");
    public static final Item armorRingGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldRing("armor.ring.gold", itemID++), "GoldRing.png");
    public static final Item armorRingZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZaniteRing("armor.ring.zanite", itemID++), "ZaniteRing.png");
    public static final Item armorRingIce = ItemHelper.createItem(MOD_ID, new ItemRingIce("armor.ring.ice", itemID++), "IceRing.png");

    public static final Item armorPendantIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.iron", itemID++, "/Jar/armor/Accessories.png"), "IronPendant.png");
    public static final Item armorPendantGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldPendant("armor.pendant.gold", itemID++, "/Jar/armor/gold_pendant_and_gloves.png"), "GoldPendant.png");
    public static final Item armorPendantZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZanitePendant("armor.pendant.zanite", itemID++, "/Jar/armor/zanite_pendant_and_gloves.png"), "ZanitePendant.png");
    public static final Item armorPendantIce = ItemHelper.createItem(MOD_ID, new ItemPendantIce("armor.pendant.ice", itemID++, "/Jar/armor/ice_pendant_and_gloves.png"), "IcePendant.png");

    public static final Item armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new ItemAccessoryIronBubble("armor.talisman.ironbubble", itemID++), "IronBubble.png");
    public static final Item armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldenFeather("armor.talisman.goldenfeather", itemID++), "GoldenFeather.png");
    public static final Item armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new ItemAccessoryRegenStone("armor.talisman.regenstone", itemID++), "RegenerationStone.png");

    public static final Item armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new ItemAccessoryShield("armor.shield.repulsion", itemID++), "RepulsionShield.png");

    public static final Item armorCapeSwet = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.swet", itemID++, "/Jar/aether/other/AetherCape.png"), "AetherCape.png");
    public static final Item armorCloakInvisibility = ItemHelper.createItem(MOD_ID, new ItemAccessoryInvisibilityCloak("armor.cape.invisibility", itemID++, "/Jar/aether/other/InvisCape.png"), "InvisibilityCloak.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.agility", itemID++, "/Jar/aether/other/AgilityCape.png"), "AgilityCape.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.white", itemID++, "/Jar/aether/other/WhiteCape.png"), "Cape.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.red", itemID++, "/Jar/aether/other/RedCape.png"), "RedCape.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.yellow", itemID++, "/Jar/aether/other/YellowCape.png"), "YellowCape.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.blue", itemID++, "/Jar/aether/other/BlueCape.png"), "BlueCape.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.blue", itemID++, 20, false, 64), "BlueGummy.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.gold", itemID++, 40, false, 64), "GoldGummy.png");

    public static final Item cloudParachute = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute", itemID++), "CloudParachute.png").setMaxStackSize(1);
    public static final Item cloudParachuteGold = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute.gold", itemID++), "GoldenParachute.png").setMaxStackSize(1);

    public static final Item lifeshard = ItemHelper.createItem(MOD_ID, new ItemLifeShard("food.lifeshard", itemID++), "LifeShard.png");
    public static final Item lanternAether = ItemHelper.createItem(MOD_ID, new ItemPlaceable("lantern.firefly.silver", itemID++, AetherBlocks.lanternAetherBlock), "silver_lantern.png");

    public static final Item devStick = ItemHelper.createItem(MOD_ID, new ItemDevStick("dev.stick", itemID++), "Stick.png").setMaxStackSize(1);


    public void initializeItems(){}
}