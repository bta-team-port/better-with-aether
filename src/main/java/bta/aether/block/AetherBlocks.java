package bta.aether.block;

import bta.aether.Aether;
import bta.aether.AetherBlockTags;
import bta.aether.world.AetherDimension;
import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.enums.EnumFireflyColor;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlockSlab;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.TextureHelper;

import static bta.aether.Aether.MOD_ID;
import static net.minecraft.core.block.Block.fluidWaterStill;
import static net.minecraft.core.block.Block.glowstone;

public class AetherBlocks {

    private static int blockID = 1000;

    public static final Block portalAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("Portal.png")
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockPortalAether("portal", blockID++, 3, glowstone.id, fluidWaterStill.id));

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
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("holystone", blockID++, Material.stone));

    public static final Block slabHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Holystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(holystone, blockID++));

    public static final Block stairsHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockStairs(holystone, blockID++));

    public static final Block holystoneMossy = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTextures("MossyHolystone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("holystone.mossy", blockID++, Material.stone));

    public static final Block icestone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("Icestone.png")
            .setTickOnLoad()
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
            .build(new BlockIcestone("icestone", blockID++, Material.stone));

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
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockAerogel("aerogel", blockID++, Material.stone));

    public static final Block enchanter = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("EnchanterSide.png")
            .setTopBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.FENCES_CONNECT)
            .build(new BlockEnchanter("enchanter", blockID++, Material.wood));
    public static final Block freezer = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("FreezerSide.png")
            .setTopTexture("FreezerTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.FENCES_CONNECT)
            .build(new BlockFreezer("freezer", blockID++, Material.wood));
    public static final Block incubator = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("IncubatorSide.png")
            .setTopTexture("IncubatorTop.png")
            .setBottomTexture("EnchanterTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.FENCES_CONNECT)
            .build(new BlockIncubator("incubator", blockID++, Material.wood));

    public static final Block logSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("SkyrootLogSide.png")
            .setTopBottomTexture("SkyrootLogTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockLog("skyroot.log", blockID++));
    public static final Block logOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setSideTextures("GoldenOak.png")
            .setTopBottomTexture("GoldenOakTop.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockGoldenOakLog("goldenoak.log", blockID++));

    public static final Block planksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .build(new Block("planks.skyroot", blockID++, Material.wood));

    public static final Block slabPlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(planksSkyroot, blockID++));

    public static final Block stairsPlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .build(new BlockStairs(planksSkyroot, blockID++));

    public static final Block fencePlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setBlockModel(new BlockModelRenderBlocks(11))
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
            .build(new BlockFence("fence.planks.skyroot", blockID++));

    public static final Block fenceGatePlanksSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("Plank.png")
            .setBlockModel(new BlockModelRenderBlocks(18))
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .build(new BlockFenceGate("fencegate.planks.skyroot", blockID++));

    public static final Block chestSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0F)
            .setResistance(5.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setSideTextures("SkyrootChestSide.png")
            .setTopBottomTexture("SkyrootChestTop.png")
            .setNorthTexture("SkyrootChestFront.png")
            .setTags(BlockTags.MINEABLE_BY_AXE, BlockTags.FENCES_CONNECT)
            .build(new BlockChestSkyroot("chest.skyroot", blockID++, Material.wood));

    public static final Block leavesSkyroot = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setSideTextures("SkyrootLeaves.png")
            .setBottomTexture("SkyrootLeavesFast.png")
            .setTopBottomTexture("SkyrootLeaves.png")
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
            .setSideTextures("GoldenOakLeaves.png")
            .setBottomTexture("GoldenOakLeavesFast.png")
            .setTopBottomTexture("GoldenOakLeaves.png")
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
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingAetherSkyroot("skyroot.sapling", blockID++));
    public static final Block saplingOakGolden = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("GoldenOakSapling.png")
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .setBlockModel((new BlockModelRenderBlocks(1)))
            .build(new BlockSaplingAetherGoldenOak("goldenoak.sapling", blockID++));

    public static final Block oreAmbrosiumHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("AmbrosiumOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockOreAmbrosium("ore.ambrosium", blockID++, Material.stone));
    public static final Block oreZaniteHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("ZaniteOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockOreZanite("ore.zanite", blockID++, Material.stone));
    public static final Block oreGravititeHolystone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTextures("GravititeOre.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockOreGravitite("ore.gravitite", blockID++));

    public static final Block torchAmbrosium = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("AmbrosiumTorch.png")
            .setBlockModel((new BlockModelRenderBlocks(2)))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE)
            .build(new BlockAmbrosiumTorch("torch.ambrosium", blockID++)).withLightEmission(15);

    public static final Block gravititeEnchanted = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(3.0f)
            .setResistance(10.0f)
            .setTopTexture("GravititeBlockTop.png")
            .setSideTextures("GravititeBlockSide.png")
            .setBottomTexture("GravititeBlockBottom.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("block.gravitite", blockID++, Material.metal));
    public static final Block blockZanite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(10.0f)
            .setTopTexture("ZaniteBlockTop.png")
            .setSideTextures("ZaniteBlockSide.png")
            .setBottomTexture("ZaniteBlockBottom.png")
            .setBlockColor(new BlockColorDefault())
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("block.zanite", blockID++, Material.stone));

    public static final Block blockAmbrosium = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(3.0f)
            .setResistance(10.0f)
            .setTextures("ambrosium_block.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("block.ambrosium", blockID++, Material.metal));

    public static final Block stoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("carved", blockID++, Material.stone));

    public static final Block slabStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneCarved, blockID++));

    public static final Block stairsStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockStairs(stoneCarved, blockID++));
    public static final Block stoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("angelic", blockID++, Material.stone));

    public static final Block slabStoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneAngelic, blockID++));

    public static final Block stairsStoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockStairs(stoneAngelic, blockID++));
    public static final Block stoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("hellfire", blockID++, Material.stone));

    public static final Block slabStoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneHellfire, blockID++));

    public static final Block stairsStoneHellfire = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockStairs(stoneHellfire, blockID++));

    public static final Block stoneCarvedLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("carved.light", blockID++, Material.stone));

    public static final Block stoneAngelicLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("angelic.light", blockID++, Material.stone));

    public static final Block stoneHellfireLight = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setLuminance(7)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new Block("hellfire.light", blockID++, Material.stone));

    public static final Block pillar = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarSide.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar", blockID++, Material.stone));
    public static final Block pillarTop = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("PillarTop.png")
            .setSideTextures("PillarCarved.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.top", blockID++, Material.stone));

    public static final Block quicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("Quicksoil.png")
            .setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
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

    public static final Block aetherTallGrass = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setBlockDrop(null)
            .setTextures("AetherTallGrass.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockAetherFlower("grass.tall", blockID++){
                @Override
                public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
                    return dropCause == EnumDropCause.SILK_TOUCH || dropCause == EnumDropCause.PICK_BLOCK ? new ItemStack[]{new ItemStack(this)} : null;
                }
            });

    public static final Block flowerWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("WhiteFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .build(new BlockAetherFlower("flower.white", blockID++));

    public static final Block flowerPurple = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("PurpleFlower.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .build(new BlockAetherFlower("flower.purple", blockID++));

    public static final Block trapStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockCarvedTrap("trap.carved", blockID++, Material.stone));

    public static final Block trapStoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockAngelicTrap("trap.angelic", blockID++, Material.stone));

    public static final Block chestMimic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopBottomTexture(9, 1)
            .setSideTextures(10, 1)
            .setNorthTexture(11, 1)
            .setTags(BlockTags.MINEABLE_BY_AXE /*BlockTags.NOT_IN_CREATIVE_MENU*/)
            .build(new BlockChestMimic("chest.mimic", blockID++, Material.wood));

    public static final Block dungeonChestLocked = new BlockBuilder(MOD_ID)
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setImmovable()
            .setSideTextures("LockedChestSide.png")
            .setTopBottomTexture(14, 3)
            .setNorthTexture("LockedChestFront.png")
            .setTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockChestLocked("chest.treasure.locked", blockID++, Material.stone));

    public static final Block dungeonChest = new BlockBuilder(MOD_ID)
            .setHardness(4.0f)
            .setResistance(15.0f)
            .setImmovable()
            .setSideTextures("LockedChestSide.png")
            .setTopBottomTexture(14, 3)
            .setNorthTexture("LockedChestFront.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockChestLocked("chest.treasure", blockID++, Material.stone));

    public static final Block stoneCarvedLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setTextures("CarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("carved.locked", blockID++, Material.stone, stoneCarved.id));

    public static final Block stoneAngelicLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setTextures("AngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("angelic.locked", blockID++, Material.stone, stoneAngelic.id));

    public static final Block stoneHellfireLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setTextures("HellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("hellfire.locked", blockID++, Material.stone, stoneHellfire.id));

    public static final Block stoneCarvedLightLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setLuminance(7)
            .setTextures("LightCarvedStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("carved.light.locked", blockID++, Material.stone, stoneCarvedLight.id));

    public static final Block stoneAngelicLightLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance(-1.0F)
            .setLuminance(7)
            .setTextures("LightAngelicStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("angelic.light.locked", blockID++, Material.stone, stoneAngelicLight.id));

    public static final Block stoneHellfireLightLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setLuminance(7)
            .setTextures("LightHellfireStone.png")
            .setTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU,BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockDungeon("hellfire.light.locked", blockID++, Material.stone, stoneHellfireLight.id));
    public static final Block lanternAetherBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GLASS)
            .setHardness(0.1f)
            .setBlockModel(new BlockModelRenderBlocks(26))
            .setTextures("silver_lantern.png")
            .build(new BlockLanternFirefly("lantern.firefly.silver", blockID++, EnumFireflyColor.BLUE/*EnumFireflyColor.SILVER*/))
            .withLightEmission(0.9375f)
            .withDisabledStats()
            .withDisabledNeighborNotifyOnMetadataChange()
            .withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

    public void initializeBlocks(){
        TextureHelper.getOrCreateBlockTextureIndex(Aether.MOD_ID, "jar_aether.png"); // Loads the texture into halplibe at startup
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(portalAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(grassAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(dirtAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(holystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(holystoneMossy.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stairsHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(slabHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(quicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(glassQuicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(trapdoorGlassQuicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreAmbrosiumHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreZaniteHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreGravititeHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(gravititeEnchanted.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(blockZanite.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(blockAmbrosium.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(logOakGolden.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(leavesOakGolden.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(saplingOakGolden.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(logSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(leavesSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(saplingSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(aercloudBlue.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(aercloudGold.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(aercloudWhite.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(planksSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(slabPlanksSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stairsPlanksSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(flowerPurple.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(flowerWhite.id);

        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.fire.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.fluidLavaFlowing.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.fluidLavaStill.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.torchCoal.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.pumpkinCarvedActive.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.netherrack.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.netherrackIgneous.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.soulsand.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.portalNether.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.oreNethercoalNetherrack.id);
        AetherDimension.getDimensionBlacklist(AetherDimension.dimensionAether).add(Block.blockNetherCoal.id);
    }
}
