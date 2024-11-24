package bta.aether.item;

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

    public static final Item victoryMedal = ItemHelper.createItem(MOD_ID, new Item("victorymedal", itemID++), "medal.png").setMaxStackSize(10);

    // See BlockChestLocked.java before using any of these.
    public static final Item keyBronze = ItemHelper.createItem(MOD_ID, new Item("key.bronze", itemID++), "key_bronze.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keySilver = ItemHelper.createItem(MOD_ID, new Item("key.silver", itemID++), "key_silver.png").withTags(aetherdungeonKey).setMaxStackSize(1);
    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item("key.gold", itemID++), "key_gold.png").withTags(aetherdungeonKey).setMaxStackSize(1);

    public static final Item bookLoreOverworld = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.overworld", itemID++, Dimension.overworld.languageKey),  "book_overworld.png");
    public static final Item bookLoreNether = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.nether", itemID++, Dimension.nether.languageKey),  "book_nether.png");
    public static final Item bookLoreAether = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.aether", itemID++, AetherDimension.dimensionAether.languageKey),  "book_aether.png");
    public static final Item bookLoreParadise = ItemHelper.createItem(MOD_ID, new ItemLoreBook("book.lore.paradise", itemID++, Dimension.paradise.languageKey),  "book_paradise.png");

    public static final Item eggMoaBlue = ItemHelper.createItem(MOD_ID, new Item("egg.moa.blue", itemID++), "moa_egg_blue.png").withTags(aetheregg);
    public static final Item eggMoaWhite = ItemHelper.createItem(MOD_ID, new Item("egg.moa.white", itemID++), "moa_egg_white.png").withTags(aetheregg);
    public static final Item eggMoaBlack = ItemHelper.createItem(MOD_ID, new Item("egg.moa.black", itemID++), "moa_egg_black.png").withTags(aetheregg);

    // Record names apparently have to match the sound file. -Cookie
    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.blue", 18500, "aether_tune", "Noisestorm"), "record_aether.png").setMaxStackSize(1);
    public static final Item recordSilver = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.silver", 18501, "a_morning_wish", "Emile van Krieken"), "record_wish.png").setMaxStackSize(1);
    public static final Item recordPink = ItemHelper.createItem(MOD_ID, new ItemRecordAccessor("record.pink", 18502, "ascending_dawn", "Emile van Krieken"), "record_dawn.png").setMaxStackSize(1);

    public static final Item amberGolden = ItemHelper.createItem(MOD_ID, new Item("goldenamber", itemID++), "amber.png");
    public static final Item petalAechor = ItemHelper.createItem(MOD_ID, new Item("aechorpetal", itemID++), "petal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item("stick.skyroot", itemID++), "stick_skyroot.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.gold", itemID++),"dart_golden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.poison", itemID++), "dart_poison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item("ammo.dart.enchanted",  itemID++), "dart_enchanted.png");

    public static final Item dartShooter = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter", itemID++, AetherItems.dartGolden.id){
        @Override
        public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
            return new EntityGoldenDart(world, entityPlayer, true);
        }
    }, "shooter_gold.png");
    public static final Item dartShooterPoison = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.poison", itemID++, AetherItems.dartPoison.id){
        @Override
        public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
            return new EntityPoisonDart(world, entityPlayer, true);
        }
    }, "shooter_poison.png");
    public static final Item dartShooterEnchanted = ItemHelper.createItem(MOD_ID, new ItemShooter("tool.dart.shooter.enchanted", itemID++, AetherItems.dartEnchanted.id){
            @Override
            public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityEnchantedDart(world, entityPlayer, true);
            }
        }, "shooter_enchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("ambrosium", itemID++, 1, false, 64), "ambrosium.png");
    public static final Item gemZanite = ItemHelper.createItem(MOD_ID, new Item("zanite", itemID++), "zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketEmpty("bucket.skyroot", itemID++), "bucket_skyroot.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.water", itemID++, fluidWaterFlowing, 0), "bucket_skyroot_water.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.milk", itemID++, null, 1), "bucket_skyroot_milk.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.poison", itemID++, null, 2), "bucket_skyroot_poison.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucket("bucket.skyroot.remedy", itemID++, null, 3), "bucket_skyroot_remedy.png").setContainerItem(bucketSkyroot);
    public static final Item bucketSkyrootIcecream = ItemHelper.createItem(MOD_ID, new ItemSkyrootBucketIceCream("bucket.skyroot.icecream", itemID++, 10), "bucket_skyroot_icecream.png").setContainerItem(bucketSkyroot);

    public static final Item healingStone = ItemHelper.createItem(MOD_ID, new ItemFood("food.healingstone", itemID++, 4, false), "food_stone.png");

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_pickaxe_skyroot.png").withTags(aetherTool);
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.skyroot",itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_shovel_skyroot.png").withTags(aetherTool);
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_axe_skyroot.png").withTags(aetherTool);
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.skyroot", itemID++, AetherToolMaterial.TOOL_SKYROOT), "tool_sword_skyroot.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_pickaxe_holystone.png").withTags(aetherTool);
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_shovel_holystone.png").withTags(aetherTool);
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.holystone",itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_axe_holystone.png").withTags(aetherTool);
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.holystone", itemID++, AetherToolMaterial.TOOL_HOLYSTONE), "tool_sword_holystone.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_pickaxe_zanite.png").withTags(aetherTool);
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new ItemToolShovelZanite("tool.shovel.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_shovel_zanite.png").withTags(aetherTool);
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new ItemToolAxeZanite("tool.axe.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_axe_zanite.png").withTags(aetherTool);
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.zanite", itemID++, AetherToolMaterial.TOOL_ZANITE), "tool_sword_zanite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_pickaxe_gravitite.png").withTags(aetherTool);
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_shovel_gravitite.png").withTags(aetherTool);
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_axe_gravitite.png").withTags(aetherTool);
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.gravitite", itemID++, AetherToolMaterial.TOOL_GRAVITITE), "tool_sword_gravitite.png").withTags(ItemTags.preventCreativeMining, aetherTool);

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherPickaxe("tool.pickaxe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_pickaxe_valk.png").withTags(aetherTool);
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherShovel("tool.shovel.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_shovel_valk.png").withTags(aetherTool);
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolAetherAxe("tool.axe.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_axe_valk.png").withTags(aetherTool);
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.valkyrie", itemID++, AetherToolMaterial.TOOL_VALKYRIE), "tool_sword_valk.png").withTags(ItemTags.preventCreativeMining, aetherTool);


    // --- ARMOR ---
    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.zanite", itemID++, AetherArmorMaterial.ZANITE, 0), "armor_helmet_zanite.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.zanite", itemID++, AetherArmorMaterial.ZANITE, 1), "armor_chestplate_zanite.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.zanite", itemID++, AetherArmorMaterial.ZANITE, 2), "armor_leggings_zanite.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.zanite", itemID++, AetherArmorMaterial.ZANITE, 3), "armor_boots_zanite.png");

    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 0), "armor_helmet_gravitite.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 1), "armor_chestplate_gravitite.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 2), "armor_leggings_gravitite.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.gravitite", itemID++, AetherArmorMaterial.GRAVITITE, 3), "armor_boots_gravitite.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 0), "armor_helmet_phoenix.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 1), "armor_chestplate_phoenix.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 2), "armor_leggings_phoenix.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.phoenix", itemID++, AetherArmorMaterial.PHOENIX, 3), "armor_boots_phoenix.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 0), "armor_helmet_obsidian.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 1), "armor_chestplate_obsidian.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 2), "armor_leggings_obsidian.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.obsidian", itemID++, AetherArmorMaterial.OBSIDIAN, 3), "armor_boots_obsidian.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.helmet.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 0), "armor_helmet_neptune.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.chestplate.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 1), "armor_chestplate_neptune.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.leggings.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 2), "armor_leggings_neptune.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("armor.boots.neptune", itemID++, AetherArmorMaterial.NEPTUNE, 3), "armor_boots_neptune.png");


    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new ItemPigSlayer("tool.sword.pig", itemID++), "tool_knife_pig.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new ItemVampireSword("tool.sword.vampire", itemID++, ToolMaterial.diamond), "tool_sword_vampire.png").withTags(ItemTags.preventCreativeMining);

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new ItemSwordFlaming("tool.sword.flaming", itemID++, AetherToolMaterial.SWORD_SPECIAL), "tool_sword_element_fire.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new ItemSwordHoly("tool.sword.holy", itemID++, AetherToolMaterial.SWORD_HOLY), "tool_sword_element_holy.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new ItemSwordLightning("tool.sword.lightning", itemID++, AetherToolMaterial.SWORD_SPECIAL), "tool_sword_element_lightning.png").withTags(ItemTags.preventCreativeMining);

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item("tool.staff.nature", itemID++), "staff_nature.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item("tool.staff.cloud", itemID++), "staff_cloud.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new ItemLightningKnife("tool.knife.lightning", itemID++), "tool_knife_lightning.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolHammerNotch = ItemHelper.createItem(MOD_ID, new ItemHammerNotch("tool.hammer.notch", itemID++, ToolMaterial.diamond), "tool_hammer_notch.png").withTags(ItemTags.preventCreativeMining);
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new ItemPhoenixBow("tool.bow.phoenix", itemID++), "tool_bow_phoenix.png");

    public static final Item armorGlovesLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.leather", itemID++, "/assets/aether/armor/leather_pendant_and_gloves.png", ArmorMaterial.LEATHER), "armor_gloves_leather.png");
    public static final Item armorGlovesChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.chain", itemID++, "/assets/aether/armor/chain_pendant_and_gloves.png", ArmorMaterial.CHAINMAIL), "armor_gloves_chain.png");
    public static final Item armorGlovesIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.iron", itemID++, "/assets/aether/armor/Accessories.png", ArmorMaterial.IRON), "armor_gloves_iron.png");
    public static final Item armorGlovesGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gold", itemID++, "/assets/aether/armor/gold_pendant_and_gloves.png", ArmorMaterial.GOLD), "armor_gloves_gold.png");
    public static final Item armorGlovesDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.diamond", itemID++, "/assets/aether/armor/diamond_pendant_and_gloves.png", ArmorMaterial.DIAMOND), "armor_gloves_diamond.png");
    public static final Item armorGlovesSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.steel", itemID++, "/assets/aether/armor/steel_pendant_and_gloves.png", ArmorMaterial.STEEL), "armor_gloves_steel.png");
    public static final Item armorGlovesZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.zanite", itemID++, "/assets/aether/armor/zanite_pendant_and_gloves.png", AetherArmorMaterial.ZANITE), "armor_gloves_zanite.png");
    public static final Item armorGlovesGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.gravitite", itemID++, "/assets/aether/armor/gravitite_pendant_and_gloves.png", AetherArmorMaterial.GRAVITITE), "armor_gloves_gravitite.png");
    public static final Item armorGlovesPhoenix = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.phoenix", itemID++, "/assets/aether/armor/Phoenix.png", AetherArmorMaterial.PHOENIX), "armor_gloves_phoenix.png");
    public static final Item armorGlovesObsidian = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.obsidian", itemID++, "/assets/aether/armor/obsidian_pendant_and_gloves.png", AetherArmorMaterial.OBSIDIAN), "armor_gloves_obsidian.png");
    public static final Item armorGlovesNeptune = ItemHelper.createItem(MOD_ID, new ItemAccessoryGloves("armor.gloves.neptune", itemID++, "/assets/aether/armor/neptune_pendant_and_gloves.png", AetherArmorMaterial.NEPTUNE), "armor_gloves_neptune.png");

    public static final Item armorRingIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryRing("armor.ring.iron", itemID++), "armor_ring_iron.png");
    public static final Item armorRingGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldRing("armor.ring.gold", itemID++), "armor_ring_gold.png");
    public static final Item armorRingZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZaniteRing("armor.ring.zanite", itemID++), "armor_ring_zanite.png");
    public static final Item armorRingIce = ItemHelper.createItem(MOD_ID, new ItemRingIce("armor.ring.ice", itemID++), "armor_ring_ice.png");

    public static final Item armorPendantLeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.leather", itemID++, "/assets/aether/armor/leather_pendant_and_gloves.png"), "armor_pendant_leather.png");
    public static final Item armorPendantChain = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.chain", itemID++, "/assets/aether/armor/chain_pendant_and_gloves.png"), "armor_pendant_chain.png");
    public static final Item armorPendantIron = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.iron", itemID++, "/assets/aether/armor/Accessories.png"), "armor_pendant_iron.png");
    public static final Item armorPendantGold = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldPendant("armor.pendant.gold", itemID++, "/assets/aether/armor/gold_pendant_and_gloves.png"), "armor_pendant_gold.png");
    public static final Item armorPendantDiamond = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.diamond", itemID++, "/assets/aether/armor/diamond_pendant_and_gloves.png"), "armor_pendant_diamond.png");
    public static final Item armorPendantSteel = ItemHelper.createItem(MOD_ID, new ItemAccessoryPendant("armor.pendant.steel", itemID++, "/assets/aether/armor/steel_pendant_and_gloves.png"), "armor_pendant_steel.png");
    public static final Item armorPendantZanite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZanitePendant("armor.pendant.zanite", itemID++, "/assets/aether/armor/zanite_pendant_and_gloves.png"), "armor_pendant_zanite.png");
    public static final Item armorPendantGravitite = ItemHelper.createItem(MOD_ID, new ItemAccessoryZanitePendant("armor.pendant.gravitite", itemID++, "/assets/aether/armor/gravitite_pendant_and_gloves.png"), "armor_pendant_gravitite.png");
    public static final Item armorPendantIce = ItemHelper.createItem(MOD_ID, new ItemPendantIce("armor.pendant.ice", itemID++, "/assets/aether/armor/ice_pendant_and_gloves.png"), "armor_pendant_ice.png");

    public static final Item armorTalismanIronBubble = ItemHelper.createItem(MOD_ID, new ItemAccessoryIronBubble("armor.talisman.ironbubble", itemID++), "accessory_bubble.png");
    public static final Item armorTalismanGoldenFeather = ItemHelper.createItem(MOD_ID, new ItemAccessoryGoldenFeather("armor.talisman.goldenfeather", itemID++), "accessory_feather.png");
    public static final Item armorTalismanRegenStone = ItemHelper.createItem(MOD_ID, new ItemAccessoryRegenStone("armor.talisman.regenstone", itemID++), "accessory_healing.png");

    public static final Item armorShieldRepulsion = ItemHelper.createItem(MOD_ID, new ItemAccessoryShield("armor.shield.repulsion", itemID++), "tool_shield_repulsion.png");

    public static final Item armorCapeSwet = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.swet", itemID++, "/assets/aether/other/AetherCape.png"), "cape_swet.png");
    public static final Item armorCloakInvisibility = ItemHelper.createItem(MOD_ID, new ItemAccessoryInvisibilityCloak("armor.cape.invisibility", itemID++, "/assets/aether/other/InvisCape.png"), "cape_invis.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.agility", itemID++, "/assets/aether/other/AgilityCape.png"), "cape_agility.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.white", itemID++, "/assets/aether/other/WhiteCape.png"), "cape_white.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.red", itemID++, "/assets/aether/other/RedCape.png"), "cape_red.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.yellow", itemID++, "/assets/aether/other/YellowCape.png"), "cape_yellow.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new ItemAccessoryCape("armor.cape.blue", itemID++, "/assets/aether/other/BlueCape.png"), "cape_blue.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.blue", itemID++, 20, false, 64), "food_swet_blue.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.gummy.gold", itemID++, 40, false, 64), "food_swet_gold.png");

    public static final Item cloudParachute = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute", itemID++), "parachute.png").setMaxStackSize(1);
    public static final Item cloudParachuteGold = ItemHelper.createItem(MOD_ID, new Item("cloud.parachute.gold", itemID++), "parachute_gold.png").setMaxStackSize(1);

    public static final Item lifeshard = ItemHelper.createItem(MOD_ID, new ItemLifeShard("food.lifeshard", itemID++), "food_lifeshard.png");
    public static final Item lanternAether = ItemHelper.createItem(MOD_ID, new ItemPlaceable("lantern.firefly.silver", itemID++, AetherBlocks.lanternAetherBlock), "lantern_firefly_silver.png");

    public static final Item devStick = ItemHelper.createItem(MOD_ID, new ItemDevStick("dev.stick", itemID++), "stick_skyroot.png").setMaxStackSize(1);


    public void initializeItems(){}
}