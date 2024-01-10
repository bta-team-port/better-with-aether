package bta.aether.block;

import bta.aether.AetherBlockTags;
import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.block.ItemBlockSlab;
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
            .build(new BlockPortal("portal", blockID++, 3, glowstone.id, fluidWaterStill.id));

    public static final Block dirtAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.gravel", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS)
            .build(new Block("dirt", blockID++, Material.dirt));

    public static final Block grassAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.6f)
            .setSideTextures("GrassSide.png")
            .setTopTexture("GrassTop.png")
            .setBottomTexture("Dirt.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS)
            .build(new BlockAetherGrass("grass", blockID++, Material.grass));

    public static final Block holystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTextures("Holystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH)
            .build(new Block("holystone", blockID++, Material.stone));

    public static final Block slabHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Holystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(holystone, blockID++));

    public static final Block stairsHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockStairs(holystone, blockID++));

    public static final Block holystoneMossy = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTextures("MossyHolystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("holystone.mossy", blockID++, Material.stone));

    public static final Block icestone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("Icestone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.SKATEABLE)
            .build(new Block("icestone", blockID++, Material.stone));

    public static final Block aercloudWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("Aercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.white", blockID++, Material.cloth));
    public static final Block aercloudBlue = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("BlueAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBlue("aercloud.blue", blockID++, Material.cloth));
    public static final Block aercloudGold = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("GoldAercloud.png")
            .setTags(BlockTags.MINEABLE_BY_SWORD)
            .build(new BlockCloudBase("aercloud.gold", blockID++, Material.cloth));

    public static final Block aerogel = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(2000f)
            .setTextures("Aerogel.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAerogel("aerogel", blockID++, Material.stone));

    public static final Block enchanter = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("EnchanterSide.png")
            .setTopBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockEnchanter("enchanter", blockID++, Material.wood));
    public static final Block freezer = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("FreezerSide.png")
            .setTopTexture("FreezerTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockFreezer("freezer", blockID++, Material.wood));
    public static final Block incubator = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("IncubatorSide.png")
            .setTopTexture("IncubatorTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("incubator", blockID++, Material.wood));

    public static final Block logSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("SkyrootLogSide.png")
            .setTopBottomTexture("SkyrootLogTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("skyroot.log", blockID++));
    public static final Block logOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("GoldenOak.png")
            .setTopBottomTexture("GoldenOakTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockGoldenOakLog("goldenoak.log", blockID++));

    public static final Block planksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .build(new Block("planks.skyroot", blockID++, Material.wood));

    public static final Block slabPlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(planksSkyroot, blockID++));

    public static final Block stairsPlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_AXE)
            .build(new BlockStairs(planksSkyroot, blockID++));

    public static final Block fencePlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setBlockModel(new BlockModelRenderBlocks(11))
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
            .build(new BlockFence("fence.planks.skyroot", blockID++));

    public static final Block fenceGatePlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setBlockModel(new BlockModelRenderBlocks(18))
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .build(new BlockFenceGate("fencegate.planks.skyroot", blockID++));

    public static final Block leavesSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("SkyrootLeaves.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS)
            .build(new BlockLeavesBase("skyroot.leaves", blockID++, Material.leaves, true) {
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
            .build(new BlockLeavesBase("goldenoak.leaves", blockID++, Material.leaves, true) {
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
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingAetherSkyroot("skyroot.sapling", blockID++));
    public static final Block saplingOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("GoldenOakSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingAetherGoldenOak("goldenoak.sapling", blockID++));

    public static final Block oreAmbrosiumHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("AmbrosiumOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreAmbrosium("ore.ambrosium", blockID++, Material.stone));
    public static final Block oreZaniteHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("ZaniteOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreZanite("ore.zanite", blockID++, Material.stone));
    public static final Block oreGravititeHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("GravititeOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockOreGravitite("ore.gravitite", blockID++));

    public static final Block torchAmbrosium = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AmbrosiumTorch.png")
            .setBlockModel((new BlockModelRenderBlocks(2)))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAmbrosiumTorch("torch.ambrosium", blockID++));

    public static final Block gravititeEnchanted = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("GravititeBlockTop.png")
            .setSideTextures("GravititeBlockSide.png")
            .setBottomTexture("GravititeBlockBottom.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.gravitite", blockID++, Material.metal));
    public static final Block blockZanite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTopTexture("ZaniteBlockTop.png")
            .setSideTextures("ZaniteBlockSide.png")
            .setBottomTexture("ZaniteBlockBottom.png")
            .setBlockColor(new BlockColorDefault())
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("block.zanite", blockID++, Material.metal));

    public static final Block trap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockTrap("trap", blockID++, Material.stone));

    public static final Block chestMimic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopBottomTexture(9, 1)
            .setNorthTexture(11, 1)
            .setSideTextures(10, 1)
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("chest.mimic", blockID++, Material.wood));

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
            .build(new BlockChestTreasure("chest.treasure", blockID++, Material.stone));

    public static final Block stoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved", blockID++, Material.stone));

    public static final Block stoneCarvedTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("carved.trap", blockID++, Material.stone));

    public static final Block slabStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneCarved, blockID++));

    public static final Block stairsStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockStairs(stoneCarved, blockID++));
    public static final Block stoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic", blockID++, Material.stone));

    public static final Block stoneAngelicTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("angelic.trap", blockID++, Material.stone));

    public static final Block slabStoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneAngelic, blockID++));

    public static final Block stairsStoneCAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockStairs(stoneAngelic, blockID++));
    public static final Block stoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire", blockID++, Material.stone));

    public static final Block stoneHellfireTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("hellfire.trap", blockID++, Material.stone));

    public static final Block slabStoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneHellfire, blockID++));

    public static final Block stairsStoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockStairs(stoneHellfire, blockID++));

    public static final Block stoneCarvedLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("carved.light", blockID++, Material.stone));
    public static final Block stoneCarvedLightTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("carved.light.trap", blockID++, Material.stone));

    public static final Block stoneAngelicLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("angelic.light", blockID++, Material.stone));
    public static final Block stoneAngelicLightTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("angelic.light.trap", blockID++, Material.stone));

    public static final Block stoneHellfireLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new Block("hellfire.light", blockID++, Material.stone));
    public static final Block stoneHellfireLightTrap = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new Block("hellfire.light.trap", blockID++, Material.stone));

    public static final Block pillar = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar", blockID++, Material.stone));
    public static final Block pillarTop = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarCarved.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.top", blockID++, Material.stone));

    public static final Block quicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("Quicksoil.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL)
            .build(new BlockQuicksoil("quicksoil", blockID++, Material.sand));
    public static final Block glassQuicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("QuicksoilGlass.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockGlassAmbrosium("glass.quicksoil", blockID++));

    public static final Block trapdoorGlassQuicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("QuicksoilGlass.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockTrapdoorAmbrosium("trapdoor.glass.quicksoil", blockID++, Material.glass, false));

    public static final Block flowerWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("WhiteFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
            .build(new BlockAetherFlower("flower.white", blockID++));

    public static final Block flowerPurple = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("PurpleFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR)
            .build(new BlockAetherFlower("flower.purple", blockID++));

    public void initializeBlocks(){}
}
