package bta.aether.block;

import bta.aether.Aether;
import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;

import static net.minecraft.core.block.Block.fluidWaterStill;
import static net.minecraft.core.block.Block.glowstone;
import static bta.aether.Aether.MOD_ID;

public class AetherBlocks {

    private static int blockID = 1000;

    public static final Block portalAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("Portal.png")
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockPortal("portal.aether", 1000, 3, glowstone.id, fluidWaterStill.id));

    public static final Block dirtAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.gravel", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL)
            .build(new Block("dirt.aether", 1001, Material.dirt));

    public static final Block grassAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.6f)
            .setSideTextures("GrassSide.png")
            .setTopTexture("GrassTop.png")
            .setBottomTexture("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL)
            .build(new BlockAetherGrass("grass.aether", 1002, Material.grass));

    public static final Block holystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTextures("Holystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("holystone.aether", 1003, Material.stone));

    public static final Block holystoneMossy = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTextures("MossyHolystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("holystone.mossy.aether", 1004, Material.stone));

    public static final Block icestone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("Icestone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("icestone.aether", 1005, Material.stone));

    public static final Block aercloudWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("Aercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.white.aether", 1006, Material.cloth));
    public static final Block aercloudBlue = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("BlueAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBlue("aercloud.blue.aether", 1007, Material.cloth));
    public static final Block aercloudGold = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("GoldAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.gold.aether", 1008, Material.cloth));

    public static final Block aerogel = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(10.0f)
            .setTextures("Aerogel.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAerogel("aerogel.aether", 1009, Material.stone));

    public static final Block enchanter = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("EnchanterSide.png")
            .setTopBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockEnchanter("enchanter.aether", 1010, Material.wood));
    public static final Block freezer = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("FreezerSide.png")
            .setTopTexture("FreezerTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockFreezer("freezer.aether", 1011, Material.wood));
    public static final Block incubator = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("IncubatorSide.png")
            .setTopTexture("IncubatorTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("incubator.aether", 1012, Material.wood));

    public static final Block logSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("SkyrootLogSide.png")
            .setTopBottomTexture("SkyrootLogTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("skyroot.log.aether", 1013));
    public static final Block logOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("GoldenOak.png")
            .setTopBottomTexture("GoldenOakTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("goldenoak.log.aether", 1014));

    public static final Block planksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .build(new Block("skyroot.planks.aether", 1015, Material.wood));

    public static final Block leavesSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("SkyrootLeaves.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
            .build(new BlockLeavesBase("skyroot.leaves.aether", 1016, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return AetherBlocks.saplingSkyroot;
                }
            });
    public static final Block leavesOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("GoldenOakLeaves.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
            .build(new BlockLeavesBase("goldenoak.leaves.aether", 1017, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return AetherBlocks.saplingOakGolden;
                }
            });

    public static final Block saplingSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("SkyrootSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingSkyroot("skyroot.sapling.aether", 1018));
    public static final Block saplingOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("GoldenOakSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingGoldenOak("goldenoak.sapling.aether", 1019));

    public static final Block oreAmbrosiumHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("AmbrosiumOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreAmbrosium("ore.ambrosium.aether", 1020, Material.stone));
    public static final Block oreZaniteHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("ZaniteOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreZanite("ore.zanite.aether", 1021, Material.stone));
    public static final Block oreGravititeHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("GravititeOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreGravitite("ore.gravitite.aether", 1022));

    public static final Block torchAmbrosium = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AmbrosiumTorch.png")
            .setBlockModel((new BlockModelRenderBlocks(3)))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAmbrosiumTorch("torch.ambrosium.aether", 1023));

    public static final Block gravititeEnchanted = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("GravititeBlockTop.png")
            .setSideTextures("GravititeBlockSide.png")
            .setBottomTexture("GravititeBlockBottom.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.gravitite.aether", 1024, Material.metal));
    public static final Block blockZanite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("ZaniteBlockTop.png")
            .setSideTextures("ZaniteBlockSide.png")
            .setBottomTexture("ZaniteBlockBottom.png")
            .setBlockColor(new BlockColorDefault())
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.zanite.aether", 1025, Material.metal));

    public static final Block trap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("trap.aether", 1026, Material.stone));

    public static final Block chestMimic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopBottomTexture(9, 1)
            .setNorthTexture(11, 1)
            .setSideTextures(10, 1)
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("chest.mimic.aether", 1027, Material.wood));

    public static final Block chestTreasure = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTopBottomTexture(14, 3)
            .setNorthTexture("LockedChestFront.png")
            .setEastTexture("LockedChestSide.png")
            .setWestTexture("LockedChestSide.png")
            .setSouthTexture("LockedChestSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockChestTreasure("chest.treasure.aether", 1028, Material.stone));

    public static final Block stoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved.aether", 1029, Material.stone));
    public static final Block stoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic.aether", 1030, Material.stone));
    public static final Block stoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire.aether", 1031, Material.stone));

    public static final Block stoneCarvedLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved.light.aether", 1032, Material.stone));
    public static final Block stoneAngelicLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic.light.aether", 1033, Material.stone));
    public static final Block stoneHellfireLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire.light.aether", 1034, Material.stone));

    public static final Block pillar = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.aether", 1035, Material.stone));
    public static final Block pillarTop = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarCarved.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.top.aether", 1036, Material.stone));

    public static final Block quicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("Quicksoil.png")
            .setSlipperiness(1.2f)
            .setTags(BlockTags.MINEABLE_BY_SHOVEL)
            .build(new Block("quicksoil.aether", 1038, Material.sand));
    public static final Block glassQuicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("QuicksoilGlass.png")
            .setSlipperiness(1.1f)
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockGlassAmbrosium("glass.quicksoil.aether", 1037, Material.glass, false));

    public static final Block flowerWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("WhiteFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockAetherFlower("flower.white.aether", 1039));

    public static final Block flowerPurple = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("PurpleFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockAetherFlower("flower.purple.aether", 1040));

    public void initializeBlocks(){}
}
