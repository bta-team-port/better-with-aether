package bta.aether.block;

import bta.aether.AetherBlockTags;
import bta.aether.entity.EntitySentry;
import bta.aether.entity.EntityValk;
import bta.aether.item.AetherItems;
import bta.aether.item.ItemBlockAetherDouble;
import bta.aether.item.tool.base.ItemToolAetherAxe;
import bta.aether.item.tool.base.ItemToolAetherPickaxe;
import bta.aether.item.tool.base.ItemToolAetherShovel;
import bta.aether.world.AetherDimension;
import net.minecraft.client.render.block.model.*;
import net.minecraft.client.render.stitcher.TextureRegistry;
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
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.BlockBuilder;

import static bta.aether.AetherMod.MOD_ID;
import static net.minecraft.core.block.Block.fluidWaterFlowing;
import static net.minecraft.core.block.Block.glowstone;

public class AetherBlocks {

    public static int blockID = 10000;

    public static Block portalAether;

    public static Block dirtAether;
    public static Block grassAether;

    public static Block cobbleHolystone;
    public static Block slabCobbleHolystone;
    public static Block stairsCobbleHolystone;
    public static Block cobbleHolystoneMossy;

    public static Block icestone;

    public static Block aercloudWhite;
    public static Block aercloudBlue;
    public static Block aercloudGold;

    public static Block aerogel;

    public static Block enchanter;
    public static Block incubator;
    public static Block freezer;

    public static Block logSkyroot;
    public static Block logOakGolden;

    public static Block planksSkyroot;
    public static Block slabPlanksSkyroot;
    public static Block stairsPlanksSkyroot;
    public static Block fencePlanksSkyroot;
    public static Block fenceGatePlanksSkyroot;
    public static Block chestSkyroot;

    public static Block leavesSkyroot;
    public static Block leavesOakGolden;

    public static Block saplingSkyroot;
    public static Block saplingOakGolden;

    public static Block oreAmbrosiumHolystone;
    public static Block oreZaniteHolystone;
    public static Block oreGravititeHolystone;

    public static Block torchAmbrosium;

    public static Block blockAmbrosium;
    public static Block blockZanite;
    public static Block blockGravitite;

    public static Block stoneCarved;
    public static Block slabStoneCarved;
    public static Block stairsStoneCarved;
    public static Block stoneCarvedLight;

    public static Block stoneAngelic;
    public static Block slabStoneAngelic;
    public static Block stairsStoneAngelic;
    public static Block stoneAngelicLight;

    public static Block stoneHellfire;
    public static Block slabStoneHellfire;
    public static Block stairsStoneHellfire;
    public static Block stoneHellfireLight;

    public static Block pillar;
    public static Block pillarCapstone;

    public static Block quicksoil;
    public static Block glassQuicksoil;
    public static Block trapdoorGlassQuicksoil;

    public static Block tallgrassAether;

    public static Block flowerWhite;
    public static Block flowerPurple;

    public static Block chestMimic;

    public static Block chestDungeon;
    public static Block chestDungeonLocked;

    public static Block stoneCarvedLocked;
    public static Block stoneCarvedLightLocked;

    public static Block stoneAngelicLocked;
    public static Block stoneAngelicLightLocked;

    public static Block stoneHellfireLocked;
    public static Block stoneHellfireLightLocked;

    public static Block stoneCarvedTrap;
    public static Block stoneAngelicTrap;

    public static Block lanternFireflyAether;



    public void initializeBlocks() {

        BlockBuilder stoneholy = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder clouds = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
                .setHardness(0.2f)
                .setResistance(0.2f);

        BlockBuilder stations = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
                .setHardness(2.5f)
                .setResistance(10.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT);

        BlockBuilder wood = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        BlockBuilder leaves = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.2f)
                .setResistance(0.2f)
                .setFlammability(60, 30)
                .setItemBlock(ItemBlockLeaves::new)
                .setVisualUpdateOnMetadata()
                .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_SWORD);

        BlockBuilder sapling = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setVisualUpdateOnMetadata()
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING)
                .setBlockModel(BlockModelCrossedSquares::new);

        BlockBuilder ores = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(3.0f)
                .setResistance(15.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder oreBlock = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.5f))
                .setHardness(3.0f)
                .setResistance(10.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder stone = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(1.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder stoneLocked = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(-1.0F)
                .setResistance((float)Integer.MAX_VALUE)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder flower = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setBlockModel(BlockModelCrossedSquares::new)
                .setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.AETHER_JAR_RENDERING);





        portalAether = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(-1.0f)
                .setResistance(-1.0f)
                .setTextures("aether:block/portal_aether")
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
                .build(new BlockPortalAether("portal", blockID++, 3, glowstone.id, fluidWaterFlowing.id));


        dirtAether = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.gravel", "step.gravel", 1.0f, 1.0f))
                .setHardness(0.2f)
                .setResistance(0.2f)
                .setTextures("aether:block/dirt_aether")
                .setItemBlock(ItemBlockAetherDouble::new)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN)
                .build(new BlockAetherDouble("dirt", blockID++, Material.dirt, ItemToolAetherShovel.class));

        grassAether = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.6f)
                .setSideTextures("aether:block/grass_side_aether")
                .setTopTexture("aether:block/grass_top_aether")
                .setBottomTexture("aether:block/dirt_aether")
                .setItemBlock(ItemBlockAetherDouble::new)
                .setTickOnLoad()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN)
                .build(new BlockAetherGrass("grass", blockID++, Material.grass, ItemToolAetherShovel.class));


        cobbleHolystone = stoneholy
                .setTextures("aether:block/holystone")
                .setItemBlock(ItemBlockAetherDouble::new)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockAetherDouble("holystone", blockID++, Material.stone, ItemToolAetherPickaxe.class));

        slabCobbleHolystone = stoneholy
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/holystone")
                .setItemBlock(ItemBlockSlab::new)
                .setBlockModel(BlockModelSlab::new)
                .build(new BlockSlab(cobbleHolystone, blockID++));

        stairsCobbleHolystone = stoneholy
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setBlockModel(BlockModelStairs::new)
                .build(new BlockStairs(cobbleHolystone, blockID++));

        cobbleHolystoneMossy = stoneholy
                .setTextures("aether:block/holystone_mossy")
                .setItemBlock(ItemBlockAetherDouble::new)
                .build(new BlockMoss("holystone.mossy", blockID++) {
                    public final Class<?> toolClass = ItemToolAetherPickaxe.class;
                    @Override
                    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, EntityPlayer player, Item item) {
                        if (toolClass.isInstance(item) && player.getGamemode().consumeBlocks() && meta == 0){
                            dropBlockWithCause(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, world.getBlockTileEntity(x, y, z));
                        }
                    }
                });


        icestone = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(3.0f)
                .setResistance(3.0f)
                .setTextures("aether:block/icestone")
                .setTickOnLoad()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build(new BlockIcestone("icestone", blockID++, Material.stone));


        aercloudWhite = clouds
                .setTextures("aether:block/aercloud_white")
                .build(new BlockCloudBase("aercloud.white", blockID++, Material.cloth));

        aercloudBlue = clouds
                .setTextures("aether:block/aercloud_blue")
                .build(new BlockCloudBlue("aercloud.blue", blockID++, Material.cloth));

        aercloudGold = clouds
                .setTextures("aether:block/aercloud_gold")
                .build(new BlockCloudBase("aercloud.gold", blockID++, Material.cloth));


        aerogel = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(1.0f)
                .setResistance(2000.0f)
                .setTextures("aether:block/aerogel")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockAerogel("aerogel", blockID++, Material.stone));


        enchanter = stations
                .setSideTextures("aether:block/incubator_side")
                .setNorthTexture("aether:block/enchanter_side")
                .setTopBottomTextures("aether:block/enchanter_top")
                .build(new BlockEnchanter("enchanter", blockID++, Material.wood));
        freezer = stations
                .setSideTextures("aether:block/freezer_side")
                .setTopTexture("aether:block/freezer_top")
                .setBottomTexture("aether:block/enchanter_top")
                .build(new BlockFreezer("freezer", blockID++, Material.wood));
        incubator = stations
                .setSideTextures("aether:block/incubator_side")
                .setTopTexture("aether:block/incubator_top")
                .setBottomTexture("aether:block/enchanter_top")
                .build(new BlockIncubator("incubator", blockID++, Material.wood));


        logSkyroot = wood
                .setBlockModel(block -> new BlockModelAxisAligned<>(block).withTextures("bonusblocks:block/log_skyroot_top", "bonusblocks:block/log_skyroot_side"))
                .setFlammability(5, 5)
                .setItemBlock(ItemBlockAetherDouble::new)
                .build(new BlockDoubleLog("skyroot.log", blockID++, ItemToolAetherAxe.class));

        logOakGolden = wood
                .setFlammability(5, 5)
                .setBlockModel(block -> new BlockModelAxisAligned<>(block).withTextures("bonusblocks:block/log_goldenoak_top", "bonusblocks:block/log_goldenoak_side"))
                .setItemBlock(ItemBlockAetherDouble::new)
                .build(new BlockGoldenOakLog("goldenoak.log", blockID++, ItemToolAetherAxe.class));


        planksSkyroot = wood
                .setTextures("aether:block/planks_skyroot")
                .setFlammability(20, 5)
                .build(new Block("planks.skyroot", blockID++, Material.wood));

        slabPlanksSkyroot = wood
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/planks_skyroot")
                .setFlammability(20, 5)
                .setItemBlock(ItemBlockSlab::new)
                .setBlockModel(BlockModelSlab::new)
                .build(new BlockSlab(planksSkyroot, blockID++));

        stairsPlanksSkyroot = wood
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setFlammability(20, 5)
                .setBlockModel(BlockModelStairs::new)
                .build(new BlockStairs(planksSkyroot, blockID++));

        fencePlanksSkyroot = wood
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/planks_skyroot")
                .setFlammability(20, 5)
                .setBlockModel(BlockModelFence::new)
                .build(new BlockFence("fence.planks.skyroot", blockID++));

        fenceGatePlanksSkyroot = wood
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/planks_skyroot")
                .setFlammability(20, 5)
                .setBlockModel(BlockModelFenceGate::new)
                .build(new BlockFenceGate("fencegate.planks.skyroot", blockID++));

        chestSkyroot = wood
                .setHardness(2.5f)
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setSideTextures("aether:block/chest_skyroot_side")
                .setTopBottomTextures("aether:block/chest_skyroot_top")
                .setNorthTexture("aether:block/chest_skyroot_front")
                .build(new BlockChestSkyroot("chest.skyroot", blockID++, Material.wood));


        leavesSkyroot = leaves
                .setSideTextures("aether:block/leaves_skyroot")
                .setBottomTexture("aether:block/leaves_skyroot_solid")
                .setTopBottomTextures("aether:block/leaves_skyroot")
                .build(new BlockLeavesBase("skyroot.leaves", blockID++, Material.leaves) {
                    @Override
                    public Block getSapling() {
                        return AetherBlocks.saplingSkyroot;
                    }
                });
        leavesOakGolden = leaves
                .setSideTextures("aether:block/leaves_goldenoak")
                .setBottomTexture("aether:block/leaves_goldenoak_solid")
                .setTopBottomTextures("aether:block/leaves_goldenoak")
                .build(new BlockLeavesBase("goldenoak.leaves", blockID++, Material.leaves) {
                    @Override
                    public Block getSapling() {
                        return AetherBlocks.saplingOakGolden;
                    }
                });


        saplingSkyroot = sapling
                .setTextures("aether:block/sapling_skyroot")
                .build(new BlockSaplingAetherSkyroot("skyroot.sapling", blockID++));
        saplingOakGolden = sapling
                .setTextures("aether:block/sapling_goldenoak")
                .build(new BlockSaplingAetherGoldenOak("goldenoak.sapling", blockID++));


        oreAmbrosiumHolystone = ores
                .setTextures("aether:block/ore_ambrosium_holystone")
                .build(new BlockOreAmbrosium("ore.ambrosium", blockID++, ItemToolAetherPickaxe.class));
        oreZaniteHolystone = ores
                .setTextures("aether:block/ore_zanite_holystone")
                .build(new BlockOreZanite("ore.zanite", blockID++, Material.stone));
        oreGravititeHolystone = ores
                .setTextures("aether:block/ore_gravitite_holystone")
                .build(new BlockOreGravitite("ore.gravitite", blockID++));


        torchAmbrosium = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(1.0f)
                .setResistance(1.0f)
                .setTextures("aether:block/torch_ambrosium")
                .setBlockModel(BlockModelTorch::new)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .build(new BlockAmbrosiumTorch("torch.ambrosium", blockID++)).withLightEmission(15);


        blockZanite = oreBlock
                .setTopTexture("aether:block/block_zanite_top")
                .setSideTextures("aether:block/block_zanite_side")
                .setBottomTexture("aether:block/block_zanite_bottom")
                .build(new Block("block.zanite", blockID++, Material.metal));
        blockAmbrosium = oreBlock
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setTextures("aether:block/block_ambrosium")
                .build(new Block("block.ambrosium", blockID++, Material.stone));
        blockGravitite = oreBlock
                .setTopTexture("aether:block/block_gravitite_top")
                .setSideTextures("aether:block/block_gravitite_side")
                .setBottomTexture("aether:block/block_gravitite_bottom")
                .build(new Block("block.gravitite", blockID++, Material.metal));


        //    brickZanite = oreBlock
//            .setTextures("aether:block/ZaniteBrick")
//            .build(new Block("brick.zanite", blockID++, Material.metal));
//    brickGravitite = oreBlock
//            .setTextures("aether:block/GravititeBrick")
//            .build(new Block("brick.gravitite", blockID++, Material.metal));


        // CARVED DUNGEON STONES
        stoneCarved = stone
                .setTextures("aether:block/carved")
                .build(new Block("carved", blockID++, Material.stone));

        slabStoneCarved = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/carved")
                .setItemBlock(ItemBlockSlab::new)
                .setBlockModel(BlockModelSlab::new)
                .build(new BlockSlab(stoneCarved, blockID++));

        stairsStoneCarved = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setBlockModel(BlockModelStairs::new)
                .build(new BlockStairs(stoneCarved, blockID++));


        // ANGELIC DUNGEON STONES
        stoneAngelic = stone
                .setTextures("aether:block/angelic")
                .build(new Block("angelic", blockID++, Material.stone));

        slabStoneAngelic = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/angelic")
                .setItemBlock(ItemBlockSlab::new)
                .setBlockModel(BlockModelSlab::new)
                .build(new BlockSlab(stoneAngelic, blockID++));

        stairsStoneAngelic = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setBlockModel(BlockModelStairs::new)
                .build(new BlockStairs(stoneAngelic, blockID++));


        // HELLFIRE DUNGEON STONES
        stoneHellfire = stone
                .setTextures("aether:block/hellfire")
                .setInfiniburn()
                .build(new Block("hellfire", blockID++, Material.stone));

        slabStoneHellfire = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setTextures("aether:block/hellfire")
                .setInfiniburn()
                .setItemBlock(ItemBlockSlab::new)
                .setBlockModel(BlockModelSlab::new)
                .build(new BlockSlab(stoneHellfire, blockID++));

        stairsStoneHellfire = stone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .setInfiniburn()
                .setBlockModel(BlockModelStairs::new)
                .build(new BlockStairs(stoneHellfire, blockID++));


        // LIGHT DUNGEON STONES
        stoneCarvedLight = stone
                .setLuminance(11)
                .setTextures("aether:block/carved_glow")
                .build(new Block("carved.light", blockID++, Material.stone));

        stoneAngelicLight = stone
                .setLuminance(11)
                .setTextures("aether:block/angelic_glow")
                .build(new Block("angelic.light", blockID++, Material.stone));

        stoneHellfireLight = stone
                .setLuminance(11)
                .setTextures("aether:block/hellfire_glow")
                .setInfiniburn()
                .build(new Block("hellfire.light", blockID++, Material.stone));


        pillar = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(1.0f)
                .setTopBottomTextures("aether:block/pillar_top")
                .setSideTextures("aether:block/pillar_side")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .setBlockModel(BlockModelAxisAligned::new)
                .build(new BlockAxisAligned("pillar", blockID++, Material.stone));

        pillarCapstone = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(1.0f)
                .setTopBottomTextures("aether:block/pillar_top")
                .setSideTextures("aether:block/pillar_side2")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .setBlockModel(BlockModelAxisAligned::new)
                .build(new BlockAxisAligned("pillar.top", blockID++, Material.stone));


        quicksoil = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.5f)
                .setTextures("aether:block/quicksoil")
                .setItemBlock(ItemBlockAetherDouble::new)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build(new BlockQuicksoil("quicksoil", blockID++, Material.sand, ItemToolAetherShovel.class));

        glassQuicksoil = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .setTextures("aether:block/glass_quicksoil")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .build(new BlockGlassAmbrosium("glass.quicksoil", blockID++));

        trapdoorGlassQuicksoil = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .setTextures("aether:block/glass_quicksoil")
                .setVisualUpdateOnMetadata()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .build(new BlockTrapdoorAmbrosium("trapdoor.glass.quicksoil", blockID++, Material.glass, false));


        tallgrassAether = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setBlockDrop(null)
                .setTextures("aether:block/tallgrass_aether")
                .setBlockModel(BlockModelCrossedSquares::new)
                .setTags(BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.BROKEN_BY_FLUIDS)
                .build(new BlockAetherTallGrass("grass.tall", blockID++){
                    @Override
                    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
                        return dropCause == EnumDropCause.SILK_TOUCH || dropCause == EnumDropCause.PICK_BLOCK ? new ItemStack[]{new ItemStack(this)} : null;
                    }
                });


        flowerWhite = flower
                .setTextures("aether:block/flower_white")
                .build(new BlockAetherFlower("flower.white", blockID++));

        flowerPurple = flower
                .setTextures("aether:block/flower_purple")
                .build(new BlockAetherFlower("flower.purple", blockID++));


        stoneCarvedTrap = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(-1.0f)
                .setResistance((float)Integer.MAX_VALUE)
                .setTextures("aether:block/carved")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, /*BlockTags.NOT_IN_CREATIVE_MENU,*/ BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockMobTrap("trap.carved", blockID++, Material.stone, stoneCarved.id, EntitySentry.class));

        stoneAngelicTrap = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(-1.0f)
                .setResistance(-1.0f)
                .setTextures("aether:block/angelic")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockMobTrap("trap.angelic", blockID++, Material.stone, stoneAngelic.id, EntityValk.class));


        chestMimic = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
                .setHardness(2.0f)
                .setResistance(2.0f)
                .setSideTextures("aether:block/chest_skyroot_side")
                .setTopBottomTextures("aether:block/chest_skyroot_top")
                .setNorthTexture("aether:block/chest_skyroot_front")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE /*BlockTags.NOT_IN_CREATIVE_MENU*/)
                .build(new BlockChestMimic("chest.mimic", blockID++, Material.wood));


        chestDungeon = new BlockBuilder(MOD_ID)
                .setHardness(4.0f)
                .setResistance(15.0f)
                .setImmovable()
                .setSideTextures("aether:block/chest_dungeon_side")
                .setTopBottomTextures("minecraft:block/furnace_stone_top")
                .setNorthTexture("aether:block/chest_dungeon_front")
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockChestLocked("chest.treasure", blockID++, Material.stone));

        chestDungeonLocked = new BlockBuilder(MOD_ID)
                .setHardness(-1.0F)
                .setResistance((float)Integer.MAX_VALUE)
                .setImmovable()
                .setSideTextures("aether:block/chest_dungeon_side")
                .setTopBottomTextures("minecraft:block/furnace_stone_top")
                .setNorthTexture("aether:block/chest_dungeon_front")
                .setTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build(new BlockChestLocked("chest.treasure.locked", blockID++, Material.stone));


        //LOCKED DUNGEON STONES
        stoneCarvedLocked = stoneLocked
                .setTextures("aether:block/carved")
                .build(new BlockDungeon("carved.locked", blockID++, Material.stone, stoneCarved.id));

        stoneAngelicLocked = stoneLocked
                .setTextures("aether:block/angelic")
                .build(new BlockDungeon("angelic.locked", blockID++, Material.stone, stoneAngelic.id));

        stoneHellfireLocked = stoneLocked
                .setTextures("aether:block/hellfire")
                .build(new BlockDungeon("hellfire.locked", blockID++, Material.stone, stoneHellfire.id));


        //LOCKED DUNGEON LIGHTS
        stoneCarvedLightLocked = stoneLocked
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setLuminance(7)
                .setTextures("aether:block/carved_glow")
                .build(new BlockDungeon("carved.light.locked", blockID++, Material.stone, stoneCarvedLight.id));

        stoneAngelicLightLocked = stoneLocked
                .setLuminance(7)
                .setTextures("aether:block/angelic_glow")
                .build(new BlockDungeon("angelic.light.locked", blockID++, Material.stone, stoneAngelicLight.id));

        stoneHellfireLightLocked = stoneLocked
                .setLuminance(7)
                .setTextures("aether:block/hellfire_glow")
                .build(new BlockDungeon("hellfire.light.locked", blockID++, Material.stone, stoneHellfireLight.id));

        lanternFireflyAether = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.GLASS)
                .setHardness(0.1f)
                .setBlockModel(BlockModelLantern::new)
                .setTextures("aether:block/lantern_firefly_silver")
                .setLuminance(14)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .build(new BlockLanternFirefly("lantern.firefly.silver", blockID++, null, () -> AetherItems.lanternAether))
                .withDisabledStats()
                .withDisabledNeighborNotifyOnMetadataChange();

    }

    public void initializeBlockDetails() {
        BlockMoss.mossToStoneMap.put(cobbleHolystone, cobbleHolystoneMossy);

        ItemToolAetherPickaxe.miningLevels.put(cobbleHolystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(slabCobbleHolystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(stairsCobbleHolystone, 0);
        ItemToolAetherPickaxe.miningLevels.put(cobbleHolystoneMossy, 0);
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
        ItemToolAetherPickaxe.miningLevels.put(pillarCapstone, 1);
        ItemToolAetherPickaxe.miningLevels.put(blockZanite, 1);
        ItemToolAetherPickaxe.miningLevels.put(oreZaniteHolystone, 1);


        ItemToolAetherPickaxe.miningLevels.put(blockGravitite, 2);
        ItemToolAetherPickaxe.miningLevels.put(oreGravititeHolystone, 2);

        ItemToolAetherPickaxe.miningLevels.put(aerogel, 3);

        //TextureRegistry.getTexture("aether:block/jar_dirt_aether"); // Loads the texture into halplibe at startup

        AetherDimension.getDimensionBlacklist(Dimension.nether).add(portalAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(grassAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(dirtAether.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(cobbleHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(cobbleHolystoneMossy.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stairsCobbleHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(slabCobbleHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(quicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(glassQuicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(trapdoorGlassQuicksoil.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreAmbrosiumHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreZaniteHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(oreGravititeHolystone.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(blockGravitite.id);
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
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneCarvedTrap.id);
        AetherDimension.getDimensionBlacklist(Dimension.nether).add(stoneAngelicTrap.id);
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
