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
import net.minecraft.client.render.item.model.ItemModelBow;
import net.minecraft.client.render.item.model.ItemModelStandard;
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
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .setStackSize(1)
                .build(new Item("key.bronze", itemID("keyBronze")));

        keySilver = new ItemBuilder(MOD_ID)
                .setIcon("item/key_silver")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .setStackSize(1)
                .build(new Item("key.silver", itemID("keySilver")));

        keyGold = new ItemBuilder(MOD_ID)
                .setIcon("item/key_gold")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
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


        eggMoaBlue = new ItemBuilder(MOD_ID)
                .setIcon("item/moa_egg_blue")
                .build(new Item("egg.moa.blue", itemID("eggMoaBlue")).withTags(aetheregg));

        eggMoaWhite = new ItemBuilder(MOD_ID)
                .setIcon("item/moa_egg_white")
                .build(new Item("egg.moa.white", itemID("eggMoaWhite")).withTags(aetheregg));

        eggMoaBlack = new ItemBuilder(MOD_ID)
                .setIcon("item/moa_egg_black")
                .build(new Item("egg.moa.black", itemID("eggMoaBlack")).withTags(aetheregg));


        recordBlue = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_aether")
                .setStackSize(1)
                .build(new ItemRecord("record.blue", itemID("recordBlue"), "AetherTune", "Noisestorm"));

        recordSilver = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_wish")
                .setStackSize(1)
                .build(new ItemRecord("record.silver", itemID("recordSilver"), "AMorningWish", "Emile van Krieken"));

        recordPink = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/record_dawn")
                .setStackSize(1)
                .build(new ItemRecord("record.pink", itemID("recordPink"), "AscendingDawn", "Emile van Krieken"));

        amberGolden = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/amber")
                .build(new Item("goldenamber", itemID("amberGolden")));

        petalAechor = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/petal")
                .build(new Item("aechorpetal", itemID("petalAechor")));

        stickSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/stick_skyroot")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new Item("stick.skyroot", itemID("stickSkyroot")));


        dartGolden = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/dart_golden")
                .build(new Item("ammo.dart.gold", itemID("dartGolden")));

        dartPoison = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/dart_poison")
                .build(new Item("ammo.dart.poison", itemID("dartPoison")));

        dartEnchanted = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/dart_enchanted")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new Item("ammo.dart.enchanted", itemID("dartEnchanted")));

        dartShooter = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/shooter_gold")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemShooter("tool.dart.shooter", itemID("dartShooter"), AetherItems.dartGolden.id){
            @Override
            public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                return new EntityGoldenDart(world, entityPlayer, true);
            }
                });

        dartShooterPoison = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/shooter_poison")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemShooter("tool.dart.shooter.poison", itemID("dartShooterPoison"), AetherItems.dartPoison.id){
                    @Override
                    public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                        return new EntityPoisonDart(world, entityPlayer, true);
                    }
                });

        dartShooterEnchanted = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/shooter_enchanted")
                .build(new ItemShooter("tool.dart.shooter.enchanted", itemID("dartShooterEnchanted"), AetherItems.dartEnchanted.id){
                    @Override
                    public EntityProjectile getArrow(World world, EntityPlayer entityPlayer, Boolean belongToPlayer) {
                        return new EntityEnchantedDart(world, entityPlayer, true);
                    }
                });


        ambrosium = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/ambrosium")
                .build(new ItemFood("ambrosium", itemID("ambrosium"), 1, 1, false, 64));

        zanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/zanite")
                .build(new Item("zanite", itemID("zanite")));

        bucketSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot")
                .build(new ItemSkyrootBucketEmpty("bucket.skyroot", itemID("bucketSkyroot")));

        bucketSkyrootWater = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot_water")
                .build(new ItemSkyrootBucket("bucket.skyroot.water", itemID("bucketSkyrootWater"), fluidWaterFlowing, 0)).setContainerItem(bucketSkyroot);

        bucketSkyrootMilk = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot_milk")
                .build(new ItemSkyrootBucket("bucket.skyroot.milk", itemID("bucketSkyrootMilk"), null, 1)).setContainerItem(bucketSkyroot);

        bucketSkyrootPoison = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot_poison")
                .build(new ItemSkyrootBucket("bucket.skyroot.poison", itemID("bucketSkyrootPoison"), null, 2)).setContainerItem(bucketSkyroot);

        bucketSkyrootRemedy = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot_remedy")
                .build(new ItemSkyrootBucket("bucket.skyroot.remedy", itemID("bucketSkyrootRemedy"), null, 3)).setContainerItem(bucketSkyroot);

        bucketSkyrootIcecream = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/bucket_skyroot_icecream")
                .build(new ItemSkyrootBucketIceCream("bucket.skyroot.icecream", itemID("bucketSkyrootIcecream"), 10, 4)).setContainerItem(bucketSkyroot);


        healingStone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/food_stone")
                .build(new ItemFood("food.healingstone", itemID("healingStone"), 4, 16, false, 64));


        toolPickaxeSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_pickaxe_skyroot")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherPickaxe("tool.pickaxe.skyroot",itemID("toolPickaxeSkyroot"), AetherToolMaterial.TOOL_SKYROOT)).withTags(aetherTool);

        toolShovelSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shovel_skyroot")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherShovel("tool.shovel.skyroot",itemID("toolShovelSkyroot"), AetherToolMaterial.TOOL_SKYROOT)).withTags(aetherTool);

        toolAxeSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_axe_skyroot")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherAxe("tool.axe.skyroot", itemID("toolAxeSkyroot"), AetherToolMaterial.TOOL_SKYROOT)).withTags(aetherTool);

        toolSwordSkyroot = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_skyroot")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolSword("tool.sword.skyroot", itemID("toolSwordSkyroot"), AetherToolMaterial.TOOL_SKYROOT)).withTags(ItemTags.PREVENT_CREATIVE_MINING, aetherTool);


        toolPickaxeHolystone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_pickaxe_holystone")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherPickaxe("tool.pickaxe.holystone",itemID("toolPickaxeHolystone"), AetherToolMaterial.TOOL_HOLYSTONE)).withTags(aetherTool);

        toolShovelHolystone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shovel_holystone")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherShovel("tool.shovel.holystone",itemID("toolShovelHolystone"), AetherToolMaterial.TOOL_HOLYSTONE)).withTags(aetherTool);

        toolAxeHolystone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_axe_holystone")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherAxe("tool.axe.holystone",itemID("toolAxeHolystone"), AetherToolMaterial.TOOL_HOLYSTONE)).withTags(aetherTool);

        toolSwordHolystone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_holystone")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolSword("tool.sword.holystone", itemID("toolSwordHolystone"), AetherToolMaterial.TOOL_HOLYSTONE)).withTags(ItemTags.PREVENT_CREATIVE_MINING, aetherTool);

        toolPickaxeZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_pickaxe_zanite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolPickaxeZanite("tool.pickaxe.zanite", itemID("toolPickaxeZanite"), AetherToolMaterial.TOOL_ZANITE)).withTags(aetherTool);

        toolShovelZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shovel_zanite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolShovelZanite("tool.shovel.zanite", itemID("toolShovelZanite"), AetherToolMaterial.TOOL_ZANITE)).withTags(aetherTool);

        toolAxeZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_axe_zanite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAxeZanite("tool.axe.zanite", itemID("toolAxeZanite"), AetherToolMaterial.TOOL_ZANITE)).withTags(aetherTool);

        toolSwordZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_zanite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolSword("tool.sword.zanite", itemID("toolSwordZanite"), AetherToolMaterial.TOOL_ZANITE)).withTags(ItemTags.PREVENT_CREATIVE_MINING, aetherTool);

        toolPickaxeGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_pickaxe_gravitite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherPickaxe("tool.pickaxe.gravitite", itemID("toolPickaxeGravitite"), AetherToolMaterial.TOOL_GRAVITITE)).withTags(aetherTool);

        toolShovelGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shovel_gravitite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherShovel("tool.shovel.gravitite", itemID("toolShovelGravitite"), AetherToolMaterial.TOOL_GRAVITITE)).withTags(aetherTool);

        toolAxeGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_axe_gravitite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherAxe("tool.axe.gravitite", itemID("toolAxeGravitite"), AetherToolMaterial.TOOL_GRAVITITE)).withTags(aetherTool);

        toolSwordGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_gravitite")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolSword("tool.sword.gravitite", itemID("toolSwordGravitite"), AetherToolMaterial.TOOL_GRAVITITE)).withTags(ItemTags.PREVENT_CREATIVE_MINING, aetherTool);

        toolPickaxeValkyrie = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_pickaxe_valk")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherPickaxe("tool.pickaxe.valkyrie", itemID("toolPickaxeValkyrie"), AetherToolMaterial.TOOL_VALKYRIE)).withTags(aetherTool);

        toolShovelValkyrie = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shovel_valk")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherShovel("tool.shovel.valkyrie", itemID("toolShovelValkyrie"), AetherToolMaterial.TOOL_VALKYRIE)).withTags(aetherTool);

        toolAxeValkyrie = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_axe_valk")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolAetherAxe("tool.axe.valkyrie", itemID("toolAxeValkyrie"), AetherToolMaterial.TOOL_VALKYRIE)).withTags(aetherTool);

        toolSwordValkyrie = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_valk")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemToolSword("tool.sword.valkyrie", itemID("toolSwordValkyrie"), AetherToolMaterial.TOOL_VALKYRIE)).withTags(ItemTags.PREVENT_CREATIVE_MINING, aetherTool);


        // --- ARMOR ---
        armorHelmetZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_helmet_zanite")
                .build(new ItemArmor("armor.helmet.zanite", itemID("armorHelmetZanite"), AetherArmorMaterial.ZANITE, 0));

        armorChestplateZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_chestplate_zanite")
                .build(new ItemArmor("armor.chestplate.zanite", itemID("armorChestplateZanite"), AetherArmorMaterial.ZANITE, 1));

        armorLeggingsZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_leggings_zanite")
                .build(new ItemArmor("armor.leggings.zanite", itemID("armorLeggingsZanite"), AetherArmorMaterial.ZANITE, 2));

        armorBootsZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_boots_zanite")
                .build(new ItemArmor("armor.boots.zanite", itemID("armorBootsZanite"), AetherArmorMaterial.ZANITE, 3));


        armorHelmetGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_helmet_gravitite")
                .build(new ItemArmor("armor.helmet.gravitite", itemID("armorHelmetGravitite"), AetherArmorMaterial.GRAVITITE, 0));

        armorChestplateGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_chestplate_gravitite")
                .build(new ItemArmor("armor.chestplate.gravitite", itemID("armorChestplateGravitite"), AetherArmorMaterial.GRAVITITE, 1));

        armorLeggingsGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_leggings_gravitite")
                .build(new ItemArmor("armor.leggings.gravitite", itemID("armorLeggingsGravitite"), AetherArmorMaterial.GRAVITITE, 2));

        armorBootsGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_boots_gravitite")
                .build(new ItemArmor("armor.boots.gravitite", itemID("armorBootsGravitite"), AetherArmorMaterial.GRAVITITE, 3));


        armorHelmetPhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_helmet_phoenix")
                .build(new ItemArmor("armor.helmet.phoenix", itemID("armorHelmetPhoenix"), AetherArmorMaterial.PHOENIX, 0));

        armorChestplatePhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_chestplate_phoenix")
                .build(new ItemArmor("armor.chestplate.phoenix", itemID("armorChestplatePhoenix"), AetherArmorMaterial.PHOENIX, 1));

        armorLeggingsPhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_leggings_phoenix")
                .build(new ItemArmor("armor.leggings.phoenix", itemID("armorLeggingsPhoenix"), AetherArmorMaterial.PHOENIX, 2));

        armorBootsPhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_boots_phoenix")
                .build(new ItemArmor("armor.boots.phoenix", itemID("armorBootsPhoenix"), AetherArmorMaterial.PHOENIX, 3));


        armorHelmetObsidian = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_helmet_obsidian")
                .build(new ItemArmor("armor.helmet.obsidian", itemID("armorHelmetObsidian"), AetherArmorMaterial.OBSIDIAN, 0));

        armorChestplateObsidian = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_chestplate_obsidian")
                .build(new ItemArmor("armor.chestplate.obsidian", itemID("armorChestplateObsidian"), AetherArmorMaterial.OBSIDIAN, 1));

        armorLeggingsObsidian = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_leggings_obsidian")
                .build(new ItemArmor("armor.leggings.obsidian", itemID("armorLeggingsObsidian"), AetherArmorMaterial.OBSIDIAN, 2));

        armorBootsObsidian = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_boots_obsidian")
                .build(new ItemArmor("armor.boots.obsidian", itemID("armorBootsObsidian"), AetherArmorMaterial.OBSIDIAN, 3));


        armorHelmetNeptune = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_helmet_neptune")
                .build(new ItemArmor("armor.helmet.neptune", itemID("armorHelmetNeptune"), AetherArmorMaterial.NEPTUNE, 0));

        armorChestplateNeptune = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_chestplate_neptune")
                .build(new ItemArmor("armor.chestplate.neptune", itemID("armorChestplateNeptune"), AetherArmorMaterial.NEPTUNE, 1));

        armorLeggingsNeptune = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_leggings_neptune")
                .build(new ItemArmor("armor.leggings.neptune", itemID("armorLeggingsNeptune"), AetherArmorMaterial.NEPTUNE, 2));

        armorBootsNeptune = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_boots_neptune")
                .build(new ItemArmor("armor.boots.neptune", itemID("armorBootsNeptune"), AetherArmorMaterial.NEPTUNE, 3));


        toolSwordPig = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_knife_pig")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemPigSlayer("tool.sword.pig", itemID("toolSwordPig"))).withTags(ItemTags.PREVENT_CREATIVE_MINING);

        toolSwordVampire = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_vampire")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemVampireSword("tool.sword.vampire", itemID("toolSwordVampire"), ToolMaterial.diamond)).withTags(ItemTags.PREVENT_CREATIVE_MINING);


        toolSwordFlaming = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_element_fire")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemSwordFlaming("tool.sword.flaming", itemID("toolSwordFlaming"), AetherToolMaterial.SWORD_SPECIAL)).withTags(ItemTags.PREVENT_CREATIVE_MINING);

        toolSwordHoly = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_element_holy")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemSwordHoly("tool.sword.holy", itemID("toolSwordHoly"), AetherToolMaterial.SWORD_HOLY)).withTags(ItemTags.PREVENT_CREATIVE_MINING);

        toolSwordLightning = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_sword_element_lightning")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemSwordLightning("tool.sword.lightning", itemID("toolSwordLightning"), AetherToolMaterial.SWORD_SPECIAL)).withTags(ItemTags.PREVENT_CREATIVE_MINING);


        toolStaffNature = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/staff_nature")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new Item("tool.staff.nature", itemID("toolStaffNature")));

        toolStaffCloud = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/staff_cloud")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new Item("tool.staff.cloud", itemID("toolStaffCloud")));


        toolKnifeLightning = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_knife_lightning")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemLightningKnife("tool.knife.lightning", itemID("toolKnifeLightning"))).withTags(ItemTags.PREVENT_CREATIVE_MINING);

        toolHammerNotch = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_hammer_notch")
                .setItemModel((item) -> new ItemModelStandard(item, MOD_ID).setFull3D())
                .build(new ItemHammerNotch("tool.hammer.notch", itemID("toolHammerNotch"), ToolMaterial.diamond)).withTags(ItemTags.PREVENT_CREATIVE_MINING);

        toolBowPhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_bow_phoenix")
                .setItemModel((item) -> new ItemModelBow(item, MOD_ID))
                .build(new ItemPhoenixBow("tool.bow.phoenix", itemID("toolBowPhoenix")));


        armorGlovesLeather = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_leather")
                .build(new ItemAccessoryGloves("armor.gloves.leather", itemID("armorGlovesLeather"), "/assets/aether/armor/leather_pendant_and_gloves.png", ArmorMaterial.LEATHER));

        armorGlovesChain = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_chain")
                .build(new ItemAccessoryGloves("armor.gloves.chain", itemID("armorGlovesChain"), "/assets/aether/armor/chain_pendant_and_gloves.png", ArmorMaterial.CHAINMAIL));

        armorGlovesIron = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_iron")
                .build(new ItemAccessoryGloves("armor.gloves.iron", itemID("armorGlovesIron"), "/assets/aether/armor/Accessories.png", ArmorMaterial.IRON));

        armorGlovesGold = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_gold")
                .build(new ItemAccessoryGloves("armor.gloves.gold", itemID("armorGlovesGold"), "/assets/aether/armor/gold_pendant_and_gloves.png", ArmorMaterial.GOLD));

        armorGlovesDiamond = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_diamond")
                .build(new ItemAccessoryGloves("armor.gloves.diamond", itemID("armorGlovesDiamond"), "/assets/aether/armor/diamond_pendant_and_gloves.png", ArmorMaterial.DIAMOND));

        armorGlovesSteel = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_steel")
                .build(new ItemAccessoryGloves("armor.gloves.steel", itemID("armorGlovesSteel"), "/assets/aether/armor/steel_pendant_and_gloves.png", ArmorMaterial.STEEL));

        armorGlovesZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_zanite")
                .build(new ItemAccessoryGloves("armor.gloves.zanite", itemID("armorGlovesZanite"), "/assets/aether/armor/zanite_pendant_and_gloves.png", AetherArmorMaterial.ZANITE));

        armorGlovesGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_gravitite")
                .build(new ItemAccessoryGloves("armor.gloves.gravitite", itemID("armorGlovesGravitite"), "/assets/aether/armor/gravitite_pendant_and_gloves.png", AetherArmorMaterial.GRAVITITE));

        armorGlovesPhoenix = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_phoenix")
                .build(new ItemAccessoryGloves("armor.gloves.phoenix", itemID("armorGlovesPhoenix"), "/assets/aether/armor/Phoenix.png", AetherArmorMaterial.PHOENIX));

        armorGlovesObsidian = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_obsidian")
                .build(new ItemAccessoryGloves("armor.gloves.obsidian", itemID("armorGlovesObsidian"), "/assets/aether/armor/obsidian_pendant_and_gloves.png", AetherArmorMaterial.OBSIDIAN));

        armorGlovesNeptune = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_gloves_neptune")
                .build(new ItemAccessoryGloves("armor.gloves.neptune", itemID("armorGlovesNeptune"), "/assets/aether/armor/neptune_pendant_and_gloves.png", AetherArmorMaterial.NEPTUNE));


        //Rings
        armorRingIron = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_ring_iron")
                .build(new ItemAccessoryRing("armor.ring.iron", itemID("armorRingIron")));

        armorRingGold = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_ring_gold")
                .build(new ItemAccessoryGoldRing("armor.ring.gold", itemID("armorRingGold")));

        armorRingZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_ring_zanite")
                .build(new ItemAccessoryZaniteRing("armor.ring.zanite", itemID("armorRingZanite")));

        armorRingIce = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_ring_ice")
                .build(new ItemRingIce("armor.ring.ice", itemID("armorRingIce")));


        //Pendants
        armorPendantLeather = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_leather")
                .build(new ItemAccessoryPendant("armor.pendant.leather", itemID("armorPendantLeather"), "/assets/aether/armor/leather_pendant_and_gloves.png"));

        armorPendantChain = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_chain")
                .build(new ItemAccessoryPendant("armor.pendant.chain", itemID("armorPendantChain"), "/assets/aether/armor/chain_pendant_and_gloves.png"));

        armorPendantIron = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_iron")
                .build(new ItemAccessoryPendant("armor.pendant.iron", itemID("armorPendantIron"), "/assets/aether/armor/Accessories.png"));

        armorPendantGold = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_gold")
                .build(new ItemAccessoryGoldPendant("armor.pendant.gold", itemID("armorPendantGold"), "/assets/aether/armor/gold_pendant_and_gloves.png"));

        armorPendantDiamond = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_diamond")
                .build(new ItemAccessoryPendant("armor.pendant.diamond", itemID("armorPendantDiamond"), "/assets/aether/armor/diamond_pendant_and_gloves.png"));

        armorPendantSteel = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_steel")
                .build(new ItemAccessoryPendant("armor.pendant.steel", itemID("armorPendantSteel"), "/assets/aether/armor/steel_pendant_and_gloves.png"));

        armorPendantZanite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_zanite")
                .build(new ItemAccessoryZanitePendant("armor.pendant.zanite", itemID("armorPendantZanite"), "/assets/aether/armor/zanite_pendant_and_gloves.png"));

        armorPendantGravitite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_gravitite")
                .build(new ItemAccessoryZanitePendant("armor.pendant.gravitite", itemID("armorPendantGravitite"), "/assets/aether/armor/gravitite_pendant_and_gloves.png"));

        armorPendantIce = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/armor_pendant_ice")
                .build(new ItemPendantIce("armor.pendant.ice", itemID("armorPendantIce"), "/assets/aether/armor/ice_pendant_and_gloves.png"));

        //Talismans
        armorTalismanIronBubble = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/accessory_bubble")
                .build(new ItemAccessoryIronBubble("armor.talisman.ironbubble", itemID("armorTalismanIronBubble")));

        armorTalismanGoldenFeather = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/accessory_feather")
                .build(new ItemAccessoryGoldenFeather("armor.talisman.goldenfeather", itemID("armorTalismanGoldenFeather")));

        armorTalismanRegenStone = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/accessory_healing")
                .build(new ItemAccessoryRegenStone("armor.talisman.regenstone", itemID("armorTalismanRegenStone")));

        //Shields
        armorShieldRepulsion = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/tool_shield_repulsion")
                .build(new ItemAccessoryShield("armor.shield.repulsion", itemID("armorShieldRepulsion")));

        //Capes
        armorCapeSwet = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_swet")
                .build(new ItemAccessoryCape("armor.cape.swet", itemID("armorCapeSwet"), "/assets/aether/other/AetherCape.png"));

        armorCapeInvisibility = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_invis")
                .build(new ItemAccessoryInvisibilityCloak("armor.cape.invisibility", itemID("armorCapeInvisibility"), "/assets/aether/other/InvisCape.png"));

        armorCapeAgility = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_agility")
                .build(new ItemAccessoryCape("armor.cape.agility", itemID("armorCapeAgility"), "/assets/aether/other/AgilityCape.png"));


        armorCapeWhite = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_white")
                .build(new ItemAccessoryCape("armor.cape.white", itemID("armorCapeWhite"), "/assets/aether/other/WhiteCape.png"));

        armorCapeRed = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_red")
                .build(new ItemAccessoryCape("armor.cape.red", itemID("armorCapeRed"), "/assets/aether/other/RedCape.png"));

        armorCapeYellow = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_yellow")
                .build(new ItemAccessoryCape("armor.cape.yellow", itemID("armorCapeYellow"), "/assets/aether/other/YellowCape.png"));

        armorCapeBlue = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/cape_blue")
                .build(new ItemAccessoryCape("armor.cape.blue", itemID("armorCapeBlue"), "/assets/aether/other/BlueCape.png"));

        //Gummy
        foodGummyBlue = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/food_swet_blue")
                .build(new ItemFood("food.gummy.blue", itemID("foodGummyBlue"), 20, 12, false, 64));

        foodGummyGold = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/food_swet_gold")
                .build(new ItemFood("food.gummy.gold", itemID("foodGummyGold"), 40, 6, false, 64));

        //Parachutes
        cloudParachute = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/parachute")
                .build(new Item("cloud.parachute", itemID("cloudParachute"))).setMaxStackSize(1);

        cloudParachuteGold = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/parachute_gold")
                .build(new Item("cloud.parachute.gold", itemID("cloudParachuteGold"))).setMaxStackSize(1);

        //Lifeshard
        lifeshard = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/food_lifeshard")
                .build(new ItemLifeShard("food.lifeshard", itemID("lifeshard")));

        //Firefly Lantern
        lanternAether = new ItemBuilder(MOD_ID)
                .setIcon(MOD_ID + ":item/lantern_firefly_silver")
                .build(new ItemPlaceable("lantern.firefly.silver", itemID("lanternAether"), AetherBlocks.lanternFireflyAether));

    }

}