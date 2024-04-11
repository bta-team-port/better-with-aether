package bta.aether.block;

import bta.aether.Aether;
import bta.aether.AetherBlockTags;
import bta.aether.item.ItemBlockAetherDouble;
import bta.aether.item.tool.base.ItemToolAetherAxe;
import bta.aether.item.tool.base.ItemToolAetherPickaxe;
import bta.aether.item.tool.base.ItemToolAetherShovel;
import bta.aether.world.AetherDimension;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlockLeaves;
import net.minecraft.core.item.block.ItemBlockSlab;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.TextureHelper;

import static bta.aether.Aether.MOD_ID;
import static net.minecraft.core.block.Block.fluidWaterFlowing;
import static net.minecraft.core.block.Block.glowstone;

public class AetherBlocks {

    private static int blockID = 1000;

    public static final Block portalAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("portal_aether.png")
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockPortalAether("portal", blockID++, 3, glowstone.id, fluidWaterFlowing.id));

    public static final Block dirtAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.gravel", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTextures("dirt_aether.png")
            .setItemBlock(ItemBlockAetherDouble::new)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN)
            .build(new BlockAetherDouble("dirt", blockID++, Material.dirt, ItemToolAetherShovel.class));

    public static final Block grassAether = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.6f)
            .setSideTextures("grass_side_aether.png")
            .setTopTexture("grass_top_aether.png")
            .setBottomTexture("dirt_aether.png")
            .setItemBlock(ItemBlockAetherDouble::new)
            .setTickOnLoad()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN)
            .build(new BlockAetherGrass("grass", blockID++, Material.grass, ItemToolAetherShovel.class));

    public static final BlockBuilder holyStone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

    public static final Block holystone = holyStone
            .setTextures("holystone.png")
            .setItemBlock(ItemBlockAetherDouble::new)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockAetherDouble("holystone", blockID++, Material.stone, ItemToolAetherPickaxe.class));
    public static final Block slabHolystone = holyStone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("holystone.png")
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(holystone, blockID++));
    public static final Block stairsHolystone = holyStone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .build(new BlockStairs(holystone, blockID++));
    public static final Block holystoneMossy = holyStone
            .setTextures("holystone_mossy.png")
            .setItemBlock(ItemBlockAetherDouble::new)
            .build(new BlockMoss("holystone.mossy", blockID++) {
                private final Class<?> toolClass = ItemToolAetherPickaxe.class;

                @Override
                public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item) {
                    if (toolClass.isInstance(item) && player.getGamemode().consumeBlocks() && meta == 0){
                        dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
                    }
                }
            });


    public static final Block icestone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(3.0f)
            .setTextures("icestone.png")
            .setTickOnLoad()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
            .build(new BlockIcestone("icestone", blockID++, Material.stone));


    public static final BlockBuilder clouds = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f);

    public static final Block aercloudWhite = clouds
            .setTextures("aercloud_white.png")
            .build(new BlockCloudBase("aercloud.white", blockID++, Material.cloth));
    public static final Block aercloudBlue = clouds
            .setTextures("aercloud_blue.png")
            .build(new BlockCloudBlue("aercloud.blue", blockID++, Material.cloth));
    public static final Block aercloudGold = clouds
            .setTextures("aercloud_gold.png")
            .build(new BlockCloudBase("aercloud.gold", blockID++, Material.cloth));

    public static final Block aerogel = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(2000.0f)
            .setTextures("aerogel.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockAerogel("aerogel", blockID++, Material.stone));


    public static final BlockBuilder stations = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.5f)
            .setResistance(10.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT);

    public static final Block enchanter = stations
            .setSideTextures("incubator_side.png")
            .setNorthTexture("enchanter_side.png")
            .setTopBottomTexture("enchanter_top.png")
            .build(new BlockEnchanter("enchanter", blockID++, Material.wood));
    public static final Block freezer = stations
            .setSideTextures("freezer_side.png")
            .setTopTexture("freezer_top.png")
            .setBottomTexture("enchanter_top.png")
            .build(new BlockFreezer("freezer", blockID++, Material.wood));
    public static final Block incubator = stations
            .setSideTextures("incubator_side.png")
            .setTopTexture("incubator_top.png")
            .setBottomTexture("enchanter_top.png")
            .build(new BlockIncubator("incubator", blockID++, Material.wood));

    public static final BlockBuilder wood = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

    public static final Block logSkyroot = wood
            .setSideTextures("log_skyroot_side.png")
            .setTopBottomTexture("log_skyroot_top.png")
            .setBlockModel(new BlockModelRenderBlocks(27))
            .setFlammability(5, 5)
            .setItemBlock(ItemBlockAetherDouble::new)
            .build(new BlockDoubleLog("skyroot.log", blockID++, ItemToolAetherAxe.class));
    public static final Block logOakGolden = wood
            .setSideTextures("log_goldenoak_side.png")
            .setTopBottomTexture("log_goldenoak_top.png")
            .setFlammability(5, 5)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .setItemBlock(ItemBlockAetherDouble::new)
            .build(new BlockGoldenOakLog("goldenoak.log", blockID++, ItemToolAetherAxe.class));

    public static final Block planksSkyroot = wood
            .setTextures("planks_skyroot.png")
            .setFlammability(20, 5)
            .build(new Block("planks.skyroot", blockID++, Material.wood));
    public static final Block slabPlanksSkyroot = wood
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("planks_skyroot.png")
            .setFlammability(20, 5)
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(planksSkyroot, blockID++));
    public static final Block stairsPlanksSkyroot = wood
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setFlammability(20, 5)
            .setBlockModel(new BlockModelRenderBlocks(10))
            .build(new BlockStairs(planksSkyroot, blockID++));
    public static final Block fencePlanksSkyroot = wood
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("planks_skyroot.png")
            .setFlammability(20, 5)
            .setBlockModel(new BlockModelRenderBlocks(11))
            .build(new BlockFence("fence.planks.skyroot", blockID++));
    public static final Block fenceGatePlanksSkyroot = wood
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("planks_skyroot.png")
            .setFlammability(20, 5)
            .setBlockModel(new BlockModelRenderBlocks(18))
            .build(new BlockFenceGate("fencegate.planks.skyroot", blockID++));
    public static final Block chestSkyroot = wood
            .setHardness(2.5f)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setSideTextures("chest_skyroot_side.png")
            .setTopBottomTexture("chest_skyroot_top.png")
            .setNorthTexture("chest_skyroot_front.png")
            .build(new BlockChestSkyroot("chest.skyroot", blockID++, Material.wood));



    public static final BlockBuilder leaves = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setFlammability(60, 30)
            .setItemBlock(ItemBlockLeaves::new)
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_SWORD);
    public static final Block leavesSkyroot = leaves
            .setSideTextures("leaves_skyroot.png")
            .setBottomTexture("leaves_skyroot_solid.png")
            .setTopBottomTexture("leaves_skyroot.png")
            .build(new BlockLeavesBase("skyroot.leaves", blockID++, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return AetherBlocks.saplingSkyroot;
                }
            });
    public static final Block leavesOakGolden = leaves
            .setSideTextures("leaves_goldenoak.png")
            .setBottomTexture("leaves_goldenoak_solid.png")
            .setTopBottomTexture("leaves_goldenoak.png")
            .build(new BlockLeavesBase("goldenoak.leaves", blockID++, Material.leaves, true) {
                @Override
                protected Block getSapling() {
                    return AetherBlocks.saplingOakGolden;
                }
            });

    public static final BlockBuilder sapling = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .setBlockModel((new BlockModelRenderBlocks(1)));
    public static final Block saplingSkyroot = sapling
            .setTextures("sapling_skyroot.png")
            .build(new BlockSaplingAetherSkyroot("skyroot.sapling", blockID++));
    public static final Block saplingOakGolden = sapling
            .setTextures("sapling_goldenoak.png")
            .build(new BlockSaplingAetherGoldenOak("goldenoak.sapling", blockID++));


    public static final BlockBuilder ores = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setResistance(15.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);
    public static final Block oreAmbrosiumHolystone = ores
            .setTextures("ore_ambrosium_holystone.png")
            .build(new BlockOreAmbrosium("ore.ambrosium", blockID++, ItemToolAetherPickaxe.class));
    public static final Block oreZaniteHolystone = ores
            .setTextures("ore_zanite_holystone.png.png")
            .build(new BlockOreZanite("ore.zanite", blockID++, Material.stone));
    public static final Block oreGravititeHolystone = ores
            .setTextures("ore_gravitite_holystone.png.png")
            .build(new BlockOreGravitite("ore.gravitite", blockID++));

    public static final Block torchAmbrosium = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(1.0f)
            .setTextures("torch_ambrosium.png")
            .setBlockModel((new BlockModelRenderBlocks(2)))
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build(new BlockAmbrosiumTorch("torch.ambrosium", blockID++)).withLightEmission(15);

    public static final BlockBuilder oreBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
            .setHardness(3.0f)
            .setResistance(10.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);
    public static final Block gravititeEnchanted = oreBlock
            .setTopTexture("block_gravitite_top.png")
            .setSideTextures("block_gravitite_side.png")
            .setBottomTexture("block_gravitite_bottom.png")
            .build(new Block("block.gravitite", blockID++, Material.metal));
    public static final Block blockZanite = oreBlock
            .setTopTexture("block_zanite_top.png")
            .setSideTextures("block_zanite_side.png")
            .setBottomTexture("block_zanite_bottom.png")
            .build(new Block("block.zanite", blockID++, Material.metal));
    public static final Block blockAmbrosium = oreBlock
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setTextures("block_ambrosium.png")
            .build(new Block("block.ambrosium", blockID++, Material.stone));

//    public static final Block brickZanite = oreBlock
//            .setTextures("ZaniteBrick.png")
//            .build(new Block("brick.zanite", blockID++, Material.metal));
//    public static final Block brickGravitite = oreBlock
//            .setTextures("GravititeBrick.png")
//            .build(new Block("brick.gravitite", blockID++, Material.metal));


    public static final BlockBuilder stone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);
    // CARVED DUNGEON STONES
    public static final Block stoneCarved = stone
            .setTextures("carved.png")
            .build(new Block("carved", blockID++, Material.stone));
    public static final Block slabStoneCarved = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("carved.png")
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneCarved, blockID++));
    public static final Block stairsStoneCarved = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .build(new BlockStairs(stoneCarved, blockID++));
    // ANGELIC DUNGEON STONES
    public static final Block stoneAngelic = stone
            .setTextures("angelic.png")
            .build(new Block("angelic", blockID++, Material.stone));
    public static final Block slabStoneAngelic = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("angelic.png")
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneAngelic, blockID++));
    public static final Block stairsStoneAngelic = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .build(new BlockStairs(stoneAngelic, blockID++));
    // HELLFIRE DUNGEON STONES
    public static final Block stoneHellfire = stone
            .setTextures("hellfire.png")
            .setInfiniburn()
            .build(new Block("hellfire", blockID++, Material.stone));
    public static final Block slabStoneHellfire = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setTextures("hellfire.png")
            .setInfiniburn()
            .setItemBlock(ItemBlockSlab::new)
            .build(new BlockSlab(stoneHellfire, blockID++));
    public static final Block stairsStoneHellfire = stone
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .setInfiniburn()
            .setBlockModel(new BlockModelRenderBlocks(10))
            .build(new BlockStairs(stoneHellfire, blockID++));
    // LIGHT DUNGEON STONES
    public static final Block stoneCarvedLight = stone
            .setLuminance(11)
            .setTextures("carved_glow.png")
            .build(new Block("carved.light", blockID++, Material.stone));
    public static final Block stoneAngelicLight = stone
            .setLuminance(11)
            .setTextures("angelic_glow.png")
            .build(new Block("angelic.light", blockID++, Material.stone));
    public static final Block stoneHellfireLight = stone
            .setLuminance(11)
            .setTextures("hellfire_glow.png")
            .setInfiniburn()
            .build(new Block("hellfire.light", blockID++, Material.stone));

    public static final Block pillar = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("pillar_top.png")
            .setSideTextures("pillar_side.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar", blockID++, Material.stone));
    public static final Block pillarTop = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(1.0f)
            .setTopBottomTexture("pillar_top.png")
            .setSideTextures("pillar_side2.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .setBlockModel(new BlockModelRenderBlocks(27))
            .build(new BlockAxisAligned("pillar.top", blockID++, Material.stone));

    public static final Block quicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.5f)
            .setTextures("quicksoil.png")
            .setItemBlock(ItemBlockAetherDouble::new)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
            .build(new BlockQuicksoil("quicksoil", blockID++, Material.sand, ItemToolAetherShovel.class));
    public static final Block glassQuicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("glass_quicksoil.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build(new BlockGlassAmbrosium("glass.quicksoil", blockID++));
    public static final Block trapdoorGlassQuicksoil = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTextures("glass_quicksoil.png")
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build(new BlockTrapdoorAmbrosium("trapdoor.glass.quicksoil", blockID++, Material.glass, false));

    public static final Block aetherTallGrass = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setBlockDrop(null)
            .setTextures("tallgrass_aether.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.BROKEN_BY_FLUIDS)
            .build(new BlockAetherTallGrass("grass.tall", blockID++){
                @Override
                public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
                    return dropCause == EnumDropCause.SILK_TOUCH || dropCause == EnumDropCause.PICK_BLOCK ? new ItemStack[]{new ItemStack(this)} : null;
                }
            });
    public static final Block flowerWhite = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("flower_white.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .build(new BlockAetherFlower("flower.white", blockID++));

    public static final Block flowerPurple = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTextures("flower_purple.png")
            .setBlockModel(new BlockModelRenderBlocks(1))
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
            .build(new BlockAetherFlower("flower.purple", blockID++));

    public static final Block trapStoneCarved = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("carved.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockCarvedTrap("trap.carved", blockID++, Material.stone));

    public static final Block trapStoneAngelic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setTextures("angelic.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockAngelicTrap("trap.angelic", blockID++, Material.stone));

    public static final Block chestMimic = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopBottomTexture("SkyrootChestTop.png")
            .setSideTextures("SkyrootChestSide.png")
            .setNorthTexture("SkyrootChestFront.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE /*BlockTags.NOT_IN_CREATIVE_MENU*/)
            .build(new BlockChestMimic("chest.mimic", blockID++, Material.wood));

    public static final Block dungeonChestLocked = new BlockBuilder(MOD_ID)
            .setHardness(-1.0F)
            .setResistance((float)Integer.MAX_VALUE)
            .setImmovable()
            .setSideTextures("chest_dungeon_side.png")
            .setTopBottomTexture(14, 3)
            .setNorthTexture("chest_dungeon_front.png")
            .setTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockChestLocked("chest.treasure.locked", blockID++, Material.stone));

    public static final Block dungeonChest = new BlockBuilder(MOD_ID)
            .setHardness(4.0f)
            .setResistance(15.0f)
            .setImmovable()
            .setSideTextures("chest_dungeon_side.png")
            .setTopBottomTexture(14, 3)
            .setNorthTexture("chest_dungeon_front.png")
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build(new BlockChestLocked("chest.treasure", blockID++, Material.stone));

    //LOCKED DUNGEON STONES
    public static final BlockBuilder stoneLocked = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(-1.0F)
            .setResistance((float)Integer.MAX_VALUE)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT);
    public static final Block stoneCarvedLocked = stoneLocked
            .setTextures("carved.png")
            .build(new BlockDungeon("carved.locked", blockID++, Material.stone, stoneCarved.id));
    public static final Block stoneAngelicLocked = stoneLocked
            .setTextures("angelic.png")
            .build(new BlockDungeon("angelic.locked", blockID++, Material.stone, stoneAngelic.id));
    public static final Block stoneHellfireLocked = stoneLocked
            .setTextures("hellfire.png")
            .build(new BlockDungeon("hellfire.locked", blockID++, Material.stone, stoneHellfire.id));

//LOCKED DUNGEON LIGHTS
    public static final Block stoneCarvedLightLocked = stoneLocked
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setLuminance(7)
            .setTextures("carved_glow.png")
            .build(new BlockDungeon("carved.light.locked", blockID++, Material.stone, stoneCarvedLight.id));
    public static final Block stoneAngelicLightLocked = stoneLocked
            .setLuminance(7)
            .setTextures("angelic_glow.png")
            .build(new BlockDungeon("angelic.light.locked", blockID++, Material.stone, stoneAngelicLight.id));
    public static final Block stoneHellfireLightLocked = stoneLocked
            .setLuminance(7)
            .setTextures("hellfire_glow.png")
            .build(new BlockDungeon("hellfire.light.locked", blockID++, Material.stone, stoneHellfireLight.id));

    public static final Block lanternAetherBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GLASS)
            .setHardness(0.1f)
            .setBlockModel(new BlockModelRenderBlocks(26))
            .setTextures("lantern_firefly_silver.png")
            .setLuminance(14)
            .setTags(BlockTags.BROKEN_BY_FLUIDS, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .build(new BlockLanternFirefly("lantern.firefly.silver", blockID++, null))
            .withDisabledStats()
            .withDisabledNeighborNotifyOnMetadataChange();

    public void initializeBlocks(){
        BlockMoss.mossToStoneMap.put(holystone, holystoneMossy);

        ItemToolAetherPickaxe.miningLevels.put(holystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(slabHolystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(stairsHolystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(holystoneMossy, 0);
        ItemToolAetherPickaxe.miningLevels.put(oreAmbrosiumHolystone, 0);

        ItemToolAetherPickaxe.miningLevels.put(icestone, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneCarved, 1);
        ItemToolAetherPickaxe.miningLevels.put(stairsStoneCarved, 1);
        ItemToolAetherPickaxe.miningLevels.put(slabStoneCarved, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneCarvedLight, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneAngelic, 1);
        ItemToolAetherPickaxe.miningLevels.put(stairsStoneAngelic, 1);
        ItemToolAetherPickaxe.miningLevels.put(slabStoneAngelic, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneAngelicLight, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneHellfire, 1);
        ItemToolAetherPickaxe.miningLevels.put(slabStoneHellfire, 1);
        ItemToolAetherPickaxe.miningLevels.put(stairsStoneHellfire, 1);
        ItemToolAetherPickaxe.miningLevels.put(stoneHellfireLight, 1);
        ItemToolAetherPickaxe.miningLevels.put(pillar, 1);
        ItemToolAetherPickaxe.miningLevels.put(pillarTop, 1);
        ItemToolAetherPickaxe.miningLevels.put(blockZanite, 1);
        ItemToolAetherPickaxe.miningLevels.put(oreZaniteHolystone, 1);


        ItemToolAetherPickaxe.miningLevels.put(gravititeEnchanted, 2);
        ItemToolAetherPickaxe.miningLevels.put(oreGravititeHolystone, 2);

        ItemToolAetherPickaxe.miningLevels.put(aerogel, 3);

        TextureHelper.getOrCreateBlockTextureIndex(Aether.MOD_ID, "jar_dirt_aether.png"); // Loads the texture into halplibe at startup

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
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(fencePlanksSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(fenceGatePlanksSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(chestSkyroot.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(chestMimic.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(trapStoneCarved.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(trapStoneAngelic.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneCarvedLocked.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneAngelicLocked.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneHellfireLocked.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneCarvedLightLocked.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneAngelicLightLocked.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneHellfireLightLocked.id);
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
