package bta.aether;

import bta.aether.world.BiomeAether;
import bta.aether.world.WorldTypeAetherDefault;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.BiomeRainforest;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.*;
import turniplabs.halplibe.mixin.accessors.CraftingManagerAccessor;

import java.util.Random;

import static net.minecraft.core.block.Block.*;


public class Aether implements ModInitializer {
    public static final String MOD_ID = "aether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Blocks
    public static final Block portalAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("Portal.png")
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockPortal("portal.aether", 1000, 3, glowstone.id, fluidWaterStill.id));

    public static final Block dirtAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.gravel", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.GROWS_FLOWERS, BlockTags.GROWS_SUGAR_CANE, BlockTags.GROWS_TREES)
            .build(new Block("dirt.aether", 1001, Material.dirt));

    public static final Block GrassAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.6f)
            .setResistance(0.6f)
            .setSideTextures("GrassSide.png")
            .setTopTexture("GrassTop.png")
            .setBottomTexture("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.GROWS_FLOWERS, BlockTags.GROWS_SUGAR_CANE, BlockTags.GROWS_TREES)
            .build(new Block("grass.aether", 1002, Material.grass));

    public static final Block HolystoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.8f)
            .setResistance(0.8f)
            .setTextures("Holystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("holystone.aether", 1003, Material.stone));

    public static final Block MossyHolystoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.8f)
            .setResistance(0.8f)
            .setTextures("MossyHolystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("holystone.mossy.aether", 1004, Material.stone));

    public static final Block IcestoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTextures("Icestone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("icestone.aether", 1005, Material.stone));

    public static final Block AercloudWhiteAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.1f)
            .setResistance(0.1f)
            .setTextures("Aercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.white.aether", 1006, Material.cloth));
    public static final Block AercloudBlueAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.1f)
            .setResistance(0.1f)
            .setTextures("BlueAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBlue("aercloud.blue.aether", 1007, Material.cloth));
    public static final Block AercloudGoldAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.1f)
            .setResistance(0.1f)
            .setTextures("GoldAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.gold.aether", 1008, Material.cloth));

    public static final Block AerogelAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(10.0f)
            .setTextures("Aerogel.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAerogel("aerogel.aether", 1009, Material.stone));

    public static final Block EnchanterAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setSideTextures("EnchanterSide.png")
            .setTopBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("enchanter.aether", 1010, Material.wood));
    public static final Block FreezerAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setSideTextures("FreezerSide.png")
            .setTopTexture("FreezerTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("freezer.aether", 1011, Material.wood));
    public static final Block IncubatorAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setSideTextures("IncubatorSide.png")
            .setTopTexture("IncubatorTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("incubator.aether", 1012, Material.wood));

    public static final Block SkyrootLogAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("SkyrootLogSide.png")
            .setTopBottomTexture("SkyrootLogTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("skyroot.log.aether", 1013));
    public static final Block GoldenOakLogAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("GoldenOak.png")
            .setTopBottomTexture("GoldenOakTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("goldenoak.log.aether", 1014));

    public static final Block SkyrootPlanksAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .build(new Block("skyroot.planks.aether", 1015, Material.wood));

    public static final Block SkyrootLeavesAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("SkyrootLeaves.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
            .build(new BlockLeavesBase("skyroot.leaves.aether", 1016, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return Aether.SkyrootSaplingAether;
                }
            });
    public static final Block GoldenOakLeavesAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("GoldenOakLeaves.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
            .build(new BlockLeavesBase("goldenoak.leaves.aether", 1017, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return Aether.GoldenOakSaplingAether;
                }
            });

    public static final Block SkyrootSaplingAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("SkyrootSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingSkyroot("skyroot.sapling.aether", 1018));
    public static final Block GoldenOakSaplingAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("GoldenOakSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingGoldenOak("goldenoak.sapling.aether", 1019));

    public static final Block AmbrosiumOreAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AmbrosiumOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("ore.ambrosium.aether", 1020, Material.stone));
    public static final Block ZaniteOreAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("ZaniteOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("ore.zanite.aether", 1021, Material.stone));
    public static final Block GravititeOreAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("GravititeOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("ore.gravitite.aether", 1022, Material.stone));

    public static final Block AmbrosiumTorchAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AmbrosiumTorch.png")
            .setBlockModel((new BlockModelRenderBlocks(3)))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAmbrosiumTorch("torch.ambrosium.aether", 1023));

    public static final Block EnchantedGravitite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("GravititeBlockTop.png")
            .setSideTextures("GravititeBlockSide.png")
            .setBottomTexture("GravititeBlockBottom.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.gravitite.aether", 1024, Material.metal));
    public static final Block ZaniteBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("ZaniteBlockTop.png")
            .setSideTextures("ZaniteBlockSide.png")
            .setBottomTexture("ZaniteBlockBottom.png")
            .setBlockColor(new BlockColorDefault())
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.zanite.aether", 1025, Material.metal));

    public static final Block TrapAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("trap.aether", 1026, Material.stone));

    public static final Block MimicChestAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopBottomTexture(9, 1)
            .setNorthTexture(11, 1)
            .setSideTextures(10, 1)
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("chest.mimic.aether", 1027, Material.wood));

    public static final Block TreasureChestAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTopBottomTexture(14, 3)
            .setNorthTexture("LockedChestFront.png")
            .setEastTexture("LockedChestSide.png")
            .setWestTexture("LockedChestSide.png")
            .setSouthTexture("LockedChestSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("chest.treasure.aether", 1028, Material.stone));

    public static final Block CarvedStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved.aether", 1029, Material.stone));
    public static final Block AngelicStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic.aether", 1030, Material.stone));
    public static final Block HellfireStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire.aether", 1031, Material.stone));

    public static final Block LightCarvedStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved.light.aether", 1032, Material.stone));
    public static final Block LightAngelicStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic.light.aether", 1033, Material.stone));
    public static final Block LightHellfireStoneAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire.light.aether", 1034, Material.stone));

    public static final Block PillarAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.aether", 1035, Material.stone));
    public static final Block PillarTopAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarCarved.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.top.aether", 1036, Material.stone));

    public static final Block QuickSoilAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("Quicksoil.png")
            .setSlipperiness(1.2f)
            .setTags(BlockTags.MINEABLE_BY_SHOVEL)
            .build(new Block("quicksoil.aether", 1038, Material.sand));
    public static final Block QuicksoilGlassAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("QuicksoilGlass.png")
            .setSlipperiness(1.1f)
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockGlassAmbrosium("glass.quicksoil.aether", 1037, Material.glass, true));

    public static final Block WhiteFlower = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("WhiteFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockFlower("flower.white.aether", 1039));

    public static final Block PurpleFlower = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("PurpleFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockFlower("flower.purple.aether", 1040));

    //Items
    public static final Item VictoryMedal = ItemHelper.createItem(MOD_ID, new Item(17000), "victorymedal", "VictoryMedal.png");
    public static final Item BronzeKey = ItemHelper.createItem(MOD_ID, new Item(17001), "key.bronze", "BronzeKey.png");
    public static final Item SilverKey = ItemHelper.createItem(MOD_ID, new Item(17002), "key.silver", "SilverKey.png");
    public static final Item GoldKey = ItemHelper.createItem(MOD_ID, new Item(17003), "key.gold", "GoldKey.png");

    public static final Item BookofLore1 = ItemHelper.createItem(MOD_ID, new Item(17004), "book.lore1", "OverworldBook.png");
    public static final Item BookofLore2 = ItemHelper.createItem(MOD_ID, new Item(17005), "book.lore2", "NetherBook.png");
    public static final Item BookofLore3 = ItemHelper.createItem(MOD_ID, new Item(17006), "book.lore3", "AetherBook.png");
    public static final Item BookofLore4 = ItemHelper.createItem(MOD_ID, new Item(17112), "book.lore4", "ParadiseBook.png");

    public static final Item BlueMoaEgg = ItemHelper.createItem(MOD_ID, new Item(17007), "egg.moa.blue", "BlueMoaEgg.png");
    public static final Item BlackMoaEgg = ItemHelper.createItem(MOD_ID, new Item(17008), "egg.moa.black", "BlackMoaEgg.png");
    public static final Item WhiteMoaEgg = ItemHelper.createItem(MOD_ID, new Item(17009), "egg.moa.white", "WhiteMoaEgg.png");

    public static final Item recordBlue = ItemHelper.createItem(MOD_ID, new Item(17010), "record.blue", "BlueMusicDisk.png");

    public static final Item goldenAmber = ItemHelper.createItem(MOD_ID, new Item(17011), "goldenamber", "GoldenAmber.png");
    public static final Item aechorPetal = ItemHelper.createItem(MOD_ID, new Item(17012), "aechorpetal", "AechorPetal.png");
    public static final Item stickSkyroot = ItemHelper.createItem(MOD_ID, new Item(17013), "stick.skyroot", "Stick.png");

    public static final Item dartGolden = ItemHelper.createItem(MOD_ID, new Item(17014), "ammo.dart.gold", "DartGolden.png");
    public static final Item dartPoison = ItemHelper.createItem(MOD_ID, new Item(17015), "ammo.dart.poison", "DartPoison.png");
    public static final Item dartEnchanted = ItemHelper.createItem(MOD_ID, new Item(17016), "ammo.dart.enchanted", "DartEnchanted.png");

    public static final Item dartShooter = ItemHelper.createItem(MOD_ID, new Item(17018), "tool.dart.shooter", "DartShooter.png");
    public static final Item dartShooterPoison = ItemHelper.createItem(MOD_ID, new Item(17019), "tool.dart.shooter.poison", "DartShooterPoison.png");
    public static final Item dartShooterEnchanted = ItemHelper.createItem(MOD_ID, new Item(17020), "tool.dart.shooter.enchanted", "DartShooterEnchanted.png");

    public static final Item ambrosium = ItemHelper.createItem(MOD_ID, new Item(17021), "ambrosium", "AmbrosiumShard.png");
    public static final Item zanitegem = ItemHelper.createItem(MOD_ID, new Item(17022), "zanite", "Zanite.png");

    public static final Item bucketSkyroot = ItemHelper.createItem(MOD_ID, new Item(17023), "bucket.skyroot", "Bucket.png");
    public static final Item bucketSkyrootWater = ItemHelper.createItem(MOD_ID, new Item(17024), "bucket.skyroot.water", "BucketWater.png");
    public static final Item bucketSkyrootMilk = ItemHelper.createItem(MOD_ID, new Item(17025), "bucket.skyroot.milk", "BucketMilk.png");
    public static final Item bucketSkyrootPoison = ItemHelper.createItem(MOD_ID, new Item(17026), "bucket.skyroot.poison", "BucketPoison.png");
    public static final Item bucketSkyrootRemedy = ItemHelper.createItem(MOD_ID, new Item(17027), "bucket.skyroot.remedy", "BucketRemedy.png");

    public static final Item healingStone = ItemHelper.createItem(MOD_ID, new Item(17028), "food.healingstone", "HealingStone.png");

    public static final Item toolPickaxeSkyroot = ItemHelper.createItem(MOD_ID, new Item(17029), "tool.pickaxe.skyroot", "PickSkyroot.png");
    public static final Item toolShovelSkyroot = ItemHelper.createItem(MOD_ID, new Item(17030), "tool.shovel.skyroot", "ShovelSkyroot.png");
    public static final Item toolAxeSkyroot = ItemHelper.createItem(MOD_ID, new Item(17031), "tool.axe.skyroot", "AxeSkyroot.png");
    public static final Item toolSwordSkyroot = ItemHelper.createItem(MOD_ID, new Item(17032), "tool.sword.skyroot", "SwordSkyroot.png");

    public static final Item toolPickaxeHolystone = ItemHelper.createItem(MOD_ID, new Item(17033), "tool.pickaxe.holystone", "PickHolystone.png");
    public static final Item toolShovelHolystone = ItemHelper.createItem(MOD_ID, new Item(17034), "tool.shovel.holystone", "ShovelHolystone.png");
    public static final Item toolAxeHolystone = ItemHelper.createItem(MOD_ID, new Item(17035), "tool.axe.holystone", "AxeHolystone.png");
    public static final Item toolSwordHolystone = ItemHelper.createItem(MOD_ID, new Item(17036), "tool.sword.holystone", "SwordHolystone.png");

    public static final Item toolPickaxeZanite = ItemHelper.createItem(MOD_ID, new Item(17037), "tool.pickaxe.zanite", "PickZanite.png");
    public static final Item toolShovelZanite = ItemHelper.createItem(MOD_ID, new Item(17038), "tool.shovel.zanite", "ShovelZanite.png");
    public static final Item toolAxeZanite = ItemHelper.createItem(MOD_ID, new Item(17039), "tool.axe.zanite", "AxeZanite.png");
    public static final Item toolSwordZanite = ItemHelper.createItem(MOD_ID, new Item(17040), "tool.sword.zanite", "SwordZanite.png");

    public static final Item toolPickaxeGravitite = ItemHelper.createItem(MOD_ID, new Item(17041), "tool.pickaxe.gravitite", "PickGravitite.png");
    public static final Item toolShovelGravitite = ItemHelper.createItem(MOD_ID, new Item(17042), "tool.shovel.gravitite", "ShovelGravitite.png");
    public static final Item toolAxeGravitite = ItemHelper.createItem(MOD_ID, new Item(17043), "tool.axe.gravitite", "AxeGravitite.png");
    public static final Item toolSwordGravitite = ItemHelper.createItem(MOD_ID, new Item(17044), "tool.sword.gravitite", "SwordGravitite.png");

    public static final Item toolPickaxeValkyrie = ItemHelper.createItem(MOD_ID, new Item(17045), "tool.pickaxe.valkyrie", "ValkyriePickaxe.png");
    public static final Item toolShovelValkyrie = ItemHelper.createItem(MOD_ID, new Item(17046), "tool.shovel.valkyrie", "ValkyrieShovel.png");
    public static final Item toolAxeValkyrie = ItemHelper.createItem(MOD_ID, new Item(17047), "tool.axe.valkyrie", "ValkyrieAxe.png");
    public static final Item toolSwordValkyrie = ItemHelper.createItem(MOD_ID, new Item(17048), "tool.sword.valkyrie", "Lance.png");

    //Armor
    public static final ArmorMaterial zanite = ArmorHelper.createArmorMaterial("Zanite_1", 200, 0.0f, 200.0f, 0.0f, 200.0f);
    public static final ArmorMaterial gravitite = ArmorHelper.createArmorMaterial("gravitite", 800, 0.0f, 200.0f, 0.0f, 200.0f);
    public static final ArmorMaterial phoenix = ArmorHelper.createArmorMaterial("phoenix", 800, 0.0f, 200.0f, 0.0f, 200.0f);
    public static final ArmorMaterial obsidian = ArmorHelper.createArmorMaterial("obsidian", 1200, 0.0f, 200.0f, 0.0f, 200.0f);
    public static final ArmorMaterial neptune = ArmorHelper.createArmorMaterial("neptune", 800, 0.0f, 200.0f, 0.0f, 200.0f);

    public static final Item armorHelmetZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Helmet", 17049, zanite, 0), "armor.helmet.zanite", "ZaniteHelmet.png");
    public static final Item armorChestplateZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Chestplate", 17050, zanite, 1), "armor.chestplate.zanite", "ZaniteChestplate.png");
    public static final Item armorLeggingsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Leggings", 17051, zanite, 2), "armor.leggings.zanite", "ZaniteLeggings.png");
    public static final Item armorBootsZanite = ItemHelper.createItem(MOD_ID, new ItemArmor("Zanite Boots", 17052, zanite, 3), "armor.boots.zanite", "ZaniteBoots.png");

    public static final Item armorHelmetGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Helmet", 17053, gravitite, 0), "armor.helmet.gravitite", "GravititeHelmet.png");
    public static final Item armorChestplateGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Chestplate", 17054, gravitite, 1), "armor.chestplate.gravitite", "GravititeChestplate.png");
    public static final Item armorLeggingsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Leggings", 17055, gravitite, 2), "armor.leggings.gravitite", "GravititeLeggings.png");
    public static final Item armorBootsGravitite = ItemHelper.createItem(MOD_ID, new ItemArmor("Gravitite Boots", 17056, gravitite, 3), "armor.boots.gravitite", "GravititeBoots.png");

    public static final Item armorHelmetPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Helmet", 17057, phoenix, 0), "armor.helmet.phoenix", "PhoenixHelmet.png");
    public static final Item armorChestplatePhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Chestplate", 17058, phoenix, 1), "armor.chestplate.phoenix", "PhoenixChestplate.png");
    public static final Item armorLeggingsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Leggings", 17059, phoenix, 2), "armor.leggings.phoenix", "PhoenixLeggings.png");
    public static final Item armorBootsPhoenix = ItemHelper.createItem(MOD_ID, new ItemArmor("Phoenix Boots", 17060, phoenix, 3), "armor.boots.phoenix", "PhoenixBoots.png");

    public static final Item armorHelmetObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Helmet", 17061, obsidian, 0), "armor.helmet.obsidian", "ObsidianHelmet.png");
    public static final Item armorChestplateObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Chestplate", 17062, obsidian, 1), "armor.chestplate.obsidian", "ObsidianChestplate.png");
    public static final Item armorLeggingsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Leggings", 17063, obsidian, 2), "armor.leggings.obsidian", "ObsidianLeggings.png");
    public static final Item armorBootsObsidian = ItemHelper.createItem(MOD_ID, new ItemArmor("Obsidian Boots", 17064, obsidian, 3), "armor.boots.obsidian", "ObsidianBoots.png");

    public static final Item armorHelmetNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Helmet", 17065, neptune, 0), "armor.helmet.neptune", "NeptuneHelmet.png");
    public static final Item armorChestplateNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Chestplate", 17066, neptune, 0), "armor.chestplate.neptune", "NeptuneChestplate.png");
    public static final Item armorLeggingsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Leggings", 17067, neptune, 0), "armor.leggings.neptune", "NeptuneLeggings.png");
    public static final Item armorBootsNeptune = ItemHelper.createItem(MOD_ID, new ItemArmor("Neptune Boots", 17068, neptune, 0), "armor.boots.neptune", "NeptuneBoots.png");

    public static final Item toolSwordPig = ItemHelper.createItem(MOD_ID, new Item(17069), "tool.sword.pig", "PigSlayer.png");
    public static final Item toolSwordVampire = ItemHelper.createItem(MOD_ID, new Item(17070), "tool.sword.vampire", "VampireBlade.png");

    public static final Item toolSwordFlaming = ItemHelper.createItem(MOD_ID, new Item(17071), "tool.sword.flaming", "FlameSword.png");
    public static final Item toolSwordHoly = ItemHelper.createItem(MOD_ID, new Item(17072), "tool.sword.holy", "HolySword.png");
    public static final Item toolSwordLightning = ItemHelper.createItem(MOD_ID, new Item(17073), "tool.sword.lightning", "LightningSword.png");

    public static final Item toolStaffNature = ItemHelper.createItem(MOD_ID, new Item(17074), "tool.staff.nature", "NatureStaff.png");
    public static final Item toolStaffCloud = ItemHelper.createItem(MOD_ID, new Item(17075), "tool.staff.cloud", "CloudStaff.png");

    public static final Item toolKnifeLightning = ItemHelper.createItem(MOD_ID, new Item(17076), "tool.knife.lightning", "LightningKnife.png");
    public static final Item toolHammerNotch = ItemHelper.createItem(MOD_ID, new Item(17077), "tool.hammer.notch", "HammerNotch.png");
    public static final Item toolBowPhoenix = ItemHelper.createItem(MOD_ID, new Item(17078), "tool.bow.phoenix", "PhoenixBow.png");

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
    public static final Item armorCapeInvisiblity = ItemHelper.createItem(MOD_ID, new Item(17101), "armor.cape.invisibility", "InvisibilityCloak.png");
    public static final Item armorCapeAgility = ItemHelper.createItem(MOD_ID, new Item(17102), "armor.cape.agility", "AgilityCape.png");

    public static final Item armorCapeWhite = ItemHelper.createItem(MOD_ID, new Item(17103), "armor.cape.white", "Cape.png");
    public static final Item armorCapeRed = ItemHelper.createItem(MOD_ID, new Item(17104), "armor.cape.red", "RedCape.png");
    public static final Item armorCapeYellow = ItemHelper.createItem(MOD_ID, new Item(17105), "armor.cape.yellow", "YellowCape.png");
    public static final Item armorCapeBlue = ItemHelper.createItem(MOD_ID, new Item(17106), "armor.cape.blue", "BlueCape.png");

    public static final Item foodGummyBlue = ItemHelper.createItem(MOD_ID, new Item(17107), "food.gummy.blue", "BlueGummy.png");
    public static final Item foodGummyGold = ItemHelper.createItem(MOD_ID, new Item(17108), "food.gummy.gold", "GoldGummy.png");

    public static final Item cloudParachute = ItemHelper.createItem(MOD_ID, new Item(17109), "cloud.parachute", "CloudParachute.png");
    public static final Item cloudParachuteGold = ItemHelper.createItem(MOD_ID, new Item(17110), "cloud.parachute.gold", "GoldenParachute.png");

    public static final Item lifeShard = ItemHelper.createItem(MOD_ID, new Item(17111), "food.lifeshard", "LifeShard.png");

    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether());
    static
    {
        biomeAether.topBlock = (short) GrassAether.id;
        biomeAether.fillerBlock = (short) dirtAether.id;
    }

    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));

    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, Aether.portalAether.id).setDefaultWorldType(worldTypeAether);
    static
    {
        Dimension.registerDimension(3, dimensionAether);
    }













    static {
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Aether initialized.");

        //Crafting Recipes Blocks

        RecipeHelper.Crafting.createRecipe(Block.workbench, 1, new Object[]{"PP", "PP", 'P' , Aether.SkyrootPlanksAether});
        RecipeHelper.Crafting.createRecipe(Aether.IncubatorAether, 1, new Object[]{"PPP", "PTP", "PPP", 'P' , Aether.SkyrootPlanksAether, 'T', Aether.AmbrosiumTorchAether});
        RecipeHelper.Crafting.createRecipe(Aether.FreezerAether, 1, new Object[]{"PPP", "PTP", "XXX", 'P' , Aether.SkyrootPlanksAether, 'T', Aether.IcestoneAether, 'X', Aether.SkyrootPlanksAether});
        RecipeHelper.Crafting.createRecipe(chestPlanksOak, 1, new Object[]{"PPP", "P P", "PPP", 'P' , Aether.SkyrootPlanksAether});

        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(Item.dye, 2, 7), new Object[]{"P", 'P' , Aether.WhiteFlower});
        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(Item.dye, 2, 5), new Object[]{"P", 'P' , Aether.PurpleFlower});

        RecipeHelper.Crafting.createRecipe(Aether.SkyrootPlanksAether, 4, new Object[]{"P", 'P' , Aether.SkyrootLogAether});
        ((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(new ItemStack(planksOakPainted, 4, 4), new Object[]{"P", 'P' , Aether.GoldenOakLogAether});

        RecipeHelper.Crafting.createRecipe(Aether.ZaniteBlock, 1, new Object[]{"PP ", "PP ", 'P' , Aether.zanitegem});

        RecipeHelper.Crafting.createRecipe(jukebox, 1, new Object[]{"PPP", "PGP", "PPP", 'P' , Aether.SkyrootPlanksAether, 'G', EnchantedGravitite});

        //Crafting Recipes Items

        RecipeHelper.Crafting.createRecipe(Aether.stickSkyroot, 4, new Object[]{" P ", " P ", 'P' , Aether.SkyrootPlanksAether});
        RecipeHelper.Crafting.createRecipe(Aether.AmbrosiumTorchAether, 2, new Object[]{" A ", " P ", 'P' , Aether.stickSkyroot, 'A', ambrosium});

        RecipeHelper.Crafting.createRecipe(Aether.dartGolden, 1, new Object[]{" G ", " P ", " F ", 'P' , Aether.stickSkyroot, 'G', Aether.goldenAmber, 'F', Item.featherChicken});
        RecipeHelper.Crafting.createRecipe(Aether.dartPoison, 8, new Object[]{"GGG", "GBG", "GGG", 'G' , Aether.dartGolden, 'B', Aether.bucketSkyrootPoison});

        RecipeHelper.Crafting.createRecipe(Aether.dartShooter, 1, new Object[]{"P  ", "P  ", "Z  ", 'P' , Aether.SkyrootPlanksAether, 'Z', zanitegem});


        RecipeHelper.Crafting.createRecipe(Aether.zanitegem, 4, new Object[]{"P", 'P' , Aether.ZaniteBlock});

        RecipeHelper.Crafting.createRecipe(Aether.toolStaffNature, 1, new Object[]{"Z ", "S ", 'S' , Aether.stickSkyroot, 'Z', zanitegem});

        RecipeHelper.Crafting.createRecipe(Aether.cloudParachute, 1, new Object[]{"PP ", "PP ", 'P' , Aether.AercloudWhiteAether});
        RecipeHelper.Crafting.createRecipe(Aether.cloudParachuteGold, 1, new Object[]{"PP ", "PP ", 'P' , Aether.AercloudGoldAether});

        RecipeHelper.Crafting.createRecipe(Item.saddle, 1, new Object[]{"LLL", "LSL", 'L' , Item.leather, 'S', Item.string});

        RecipeHelper.Crafting.createRecipe(Aether.armorCapeWhite, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , Block.wool});
        RecipeHelper.Crafting.createRecipe(Aether.armorCapeRed, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 14))});
        RecipeHelper.Crafting.createRecipe(Aether.armorCapeYellow, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 4))});
        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 11))});
        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 3))});
        RecipeHelper.Crafting.createRecipe(Aether.armorCapeBlue, 1, new Object[]{"WW ", "WW ", "WW ", 'W' , (new ItemStack(Block.wool, 1, 9))});

        RecipeHelper.Crafting.createRecipe(Aether.bucketSkyroot, 1, new Object[]{"P P", " P ", 'P' , Aether.SkyrootPlanksAether});

        RecipeHelper.Crafting.createRecipe(toolAxeSkyroot, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.SkyrootPlanksAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolPickaxeSkyroot, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.SkyrootPlanksAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolShovelSkyroot, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.SkyrootPlanksAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolSwordSkyroot, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.SkyrootPlanksAether, 'S', Aether.stickSkyroot});

        RecipeHelper.Crafting.createRecipe(toolAxeHolystone, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.HolystoneAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolPickaxeHolystone, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.HolystoneAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolShovelHolystone, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.HolystoneAether, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolSwordHolystone, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.HolystoneAether, 'S', Aether.stickSkyroot});

        RecipeHelper.Crafting.createRecipe(toolAxeZanite, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.zanitegem, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolPickaxeZanite, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.zanitegem, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolShovelZanite, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.zanitegem, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolSwordZanite, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.zanitegem, 'S', Aether.stickSkyroot});

        RecipeHelper.Crafting.createRecipe(toolAxeGravitite, 1, new Object[]{"PP ", "PS ", " S ", 'P' , Aether.EnchantedGravitite, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolPickaxeGravitite, 1, new Object[]{"PPP", " S ", " S ", 'P' , Aether.EnchantedGravitite, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolShovelGravitite, 1, new Object[]{" P ", " S ", " S ", 'P' , Aether.EnchantedGravitite, 'S', Aether.stickSkyroot});
        RecipeHelper.Crafting.createRecipe(toolSwordGravitite, 1, new Object[]{" P ", " P ", " S ", 'P' , Aether.EnchantedGravitite, 'S', Aether.stickSkyroot});


        //Furnace Recipes

        RecipeHelper.smeltingManager.addSmelting(Aether.SkyrootLogAether.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.smeltingManager.addSmelting(Aether.GoldenOakLogAether.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.blastingManager.addSmelting(Aether.SkyrootLogAether.id, new ItemStack(Item.coal, 1, 1));
        RecipeHelper.blastingManager.addSmelting(Aether.GoldenOakLogAether.id, new ItemStack(Item.coal, 1, 1));

        LookupFuelFurnace.instance.addFuelEntry(Aether.SkyrootLogAether.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(Aether.GoldenOakLogAether.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(Aether.SkyrootPlanksAether.id, 300);

    }
}
