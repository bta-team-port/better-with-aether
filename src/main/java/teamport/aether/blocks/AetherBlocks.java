package teamport.aether.blocks;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobChicken;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import net.minecraft.core.world.Dimension;
import teamport.aether.items.AetherItems;
import turniplabs.halplibe.helper.BlockBuilder;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherBlocks {


    public static Block<BlockLogicPortal> PORTAL_AETHER;

    public static Block<?> GRASS_AETHER;
    public static Block<?> DIRT_AETHER;

    public static Block<?> QUICKSOIL;

    public static Block<?> GLASS_QUICKSOIL;
    public static Block<?> TRAPDOOR_GLASS_QUICKSOIL;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_TOP;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_BOTTOM;

    public static Block<?> TALLGRASS_AETHER;

    public static Block<BlockLogicFlowerStackable> FLOWER_PURPLE;
    public static Block<BlockLogicFlowerStackable> FLOWER_WHITE;

    public static Block<?> HOLYSTONE;
    public static Block<?> HOLYSTONE_POLISHED;
    public static Block<?> HOLYSTONE_CARVED;
    public static Block<BlockLogicSlab> SLAB_HOLYSTONE_POLISHED;

    public static Block<?> COBBLE_HOLYSTONE;
    public static Block<?> COBBLE_HOLYSTONE_MOSSY;
    public static Block<BlockLogicStairs> STAIRS_COBBLE_HOLYSTONE;
    public static Block<BlockLogicSlab> SLAB_COBBLE_HOLYSTONE;

    public static Block<?> BRICK_HOLYSTONE;
    public static Block<BlockLogicStairs> STAIRS_BRICK_HOLYSTONE;
    public static Block<BlockLogicSlab> SLAB_BRICK_HOLYSTONE;

    public static Block<?> ICESTONE;

    public static Block<?> AERCLOUD_WHITE;
    public static Block<?> AERCLOUD_BLUE;
    public static Block<?> AERCLOUD_GOLD;

    public static Block<?> AEROGEL;

    public static Block<?> TORCH_AMBROSIUM;

    public static Block<BlockLogicLog> LOG_SKYROOT;
    public static Block<BlockLogicLog> LOG_OAK_GOLDEN;

    public static Block<?> LEAVES_SKYROOT;
    public static Block<?> LEAVES_OAK_GOLDEN;

    public static Block<?> SAPLING_SKYROOT;
    public static Block<?> SAPLING_OAK_GOLDEN;

    public static Block<?> PLANKS_SKYROOT;
    public static Block<BlockLogicSlab> SLAB_PLANKS_SKYROOT;
    public static Block<BlockLogicStairs> STAIRS_PLANKS_SKYROOT;
    public static Block<BlockLogicFence> FENCE_PLANKS_SKYROOT;
    public static Block<BlockLogicFenceGate> FENCEGATE_PLANKS_SKYROOT;
    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_BOTTOM;
    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_TOP;
    public static Block<BlockLogicTrapDoor> TRAPDOOR_PLANKS_SKYROOT;
    public static Block<BlockLogicChest> CHEST_PLANKS_SKYROOT;
    public static Block<BlockLogicButtonPlanks> BUTTON_PLANKS_SKYROOT;
    public static Block<?> PRESSURE_PLATE_PLANKS_SKYROOT;

    public static Block<?> ORE_AMBROSIUM_HOLYSTONE;

    public static Block<?> ORE_ZANITE_HOLYSTONE;

    public static Block<?> ORE_GRAVITITE_HOLYSTONE;

    public static Block<?> BLOCK_AMBROSIUM;
    public static Block<?> BLOCK_ZANITE;
    public static Block<?> BLOCK_GRAVITITE;

    public static Block<?> BRICK_ZANITE;
    public static Block<BlockLogicSlab> SLAB_BRICK_ZANITE;
    public static Block<BlockLogicStairs> STAIRS_BRICK_ZANITE;

    public static Block<?> CARVED_STONE;
    public static Block<BlockLogicSlab> SLAB_CARVED_STONE;
    public static Block<BlockLogicStairs> STAIRS_CARVED_STONE;
    public static Block<?> CARVED_STONE_LIGHT;

    public static Block<?> CARVED_ANGELIC;
    public static Block<BlockLogicSlab> SLAB_CARVED_ANGELIC;
    public static Block<BlockLogicStairs> STAIRS_CARVED_ANGELIC;
    public static Block<?> CARVED_ANGELIC_LIGHT;

    public static Block<?> CARVED_HELLFIRE;
    public static Block<BlockLogicSlab> SLAB_CARVED_HELLFIRE;
    public static Block<BlockLogicStairs> STAIRS_CARVED_HELLFIRE;
    public static Block<?> CARVED_HELLFIRE_LIGHT;

    public static Block<?> PILLAR;
    public static Block<?> PILLAR_CAPSTONE;

    public static Block<?> CHEST_MIMIC;

    public static Block<?> CHEST_DUNGEON;
    public static Block<?> CHEST_DUNGEON_LOCKED;

    public static Block<?> CARVED_STONE_LOCKED;
    public static Block<?> CARVED_ANGELIC_LOCKED;
    public static Block<?> CARVED_HELLFIRE_LOCKED;

    public static Block<?> CARVED_STONE_LIGHT_LOCKED;
    public static Block<?> CARVED_ANGELIC_LIGHT_LOCKED;
    public static Block<?> CARVED_HELLFIRE_LIGHT_LOCKED;

    public static Block<?> CARVED_STONE_TRAPPED;
    public static Block<?> CARVED_ANGELIC_TRAPPED;

    public static Block<?> LANTERN_FIREFLY_SILVER;

//    public static Block<?> ;

    public void initializeBlocks() {

        BlockBuilder stone = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder wood = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setFlammability(20, 5)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        BlockBuilder dungeonStone = stone
                .setResistance(1.0f);

        BlockBuilder dungeonStoneLocked = stone
                .setTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setHardness(-1.0f)
                .setResistance(-1.0f);

        BlockBuilder oreBlock = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.METAL)
                .setHardness(3.0f)
                .setResistance(10.0f)
                .setTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BlockBuilder flower = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR);

        BlockBuilder log = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0F)
                .setResistance(1.0f)
                .setFlammability(5, 5)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        BlockBuilder leaves = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.2F)
                .setResistance(0.2F)
                .setFlammability(30, 60)
                .setTickOnLoad()
                .setVisualUpdateOnMetadata()
                .setTags(BlockTags.MINEABLE_BY_AXE, AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);

        BlockBuilder sapling = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.0f)
                .setResistance(0.0f)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR);

        BlockBuilder clouds = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
                .setHardness(0.2f)
                .setResistance(0.2f);

        BlockBuilder slab = new BlockBuilder(MOD_ID)
                .setUseInternalLight()
                .setVisualUpdateOnMetadata();

        BlockBuilder stairs = new BlockBuilder(MOD_ID)
                .setUseInternalLight()
                .setVisualUpdateOnMetadata();

        BlockBuilder ores = stone
                .setHardness(3.0f)
                .setResistance(15.0f);


        PORTAL_AETHER = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.GLASS)
                .setHardness(-1.0f)
                .setResistance(-1.0f)
                .setLuminance(15)
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
                .build("portal.aether", 10000, b -> new BlockLogicPortal(b, Dimension.OVERWORLD, Blocks.GLOWSTONE, Blocks.FLUID_WATER_FLOWING));


        GRASS_AETHER = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.6f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build("grass", 10001, b -> new BlockLogicGrassAether(b, DIRT_AETHER));

        DIRT_AETHER = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.GRAVEL)
                .setHardness(0.2f)
                .setResistance(0.2f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, BlockTags.GROWS_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build("dirt", 10002, b -> new BlockLogic(b, Material.dirt));


        HOLYSTONE = stone
                .build("holystone", 10050, b -> new BlockLogicStone(b, COBBLE_HOLYSTONE, Material.marble));

        HOLYSTONE_POLISHED = stone
                .build("holystone.polished", 10051, b -> new BlockLogic(b, Material.marble));

        HOLYSTONE_CARVED = stone
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
                .build("holystone.carved", 10052, b -> new BlockLogic(b, Material.marble));

        SLAB_HOLYSTONE_POLISHED = slab
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .build("slab.holystone.carved", 10053, b -> new BlockLogicSlab(b, HOLYSTONE_CARVED));


        COBBLE_HOLYSTONE = stone
                .build("cobble.holystone", 10055, b -> new BlockLogicCobble(b, Material.marble, () -> Blocks.GRAVEL));

        COBBLE_HOLYSTONE_MOSSY = stone
                .build("cobble.holystone.mossy", 10056, b -> new BlockLogicCobble(b, Material.marble, () -> Blocks.GRAVEL));

        STAIRS_COBBLE_HOLYSTONE = stairs
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .build("stairs.cobble.holystone", 10057, b -> new BlockLogicStairs(b, COBBLE_HOLYSTONE));

        SLAB_COBBLE_HOLYSTONE = slab
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .build("slab.cobble.holystone", 10058, b -> new BlockLogicSlab(b, COBBLE_HOLYSTONE));


        BRICK_HOLYSTONE = stone
                .build("brick.holystone", 10060, b -> new BlockLogic(b, Material.marble));

        STAIRS_BRICK_HOLYSTONE = stairs
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .build("stairs.brick.holystone", 10061, b -> new BlockLogicStairs(b, BRICK_HOLYSTONE));

        SLAB_BRICK_HOLYSTONE = slab
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.8f)
                .build("slab.brick.holystone", 10062, b -> new BlockLogicSlab(b, BRICK_HOLYSTONE));


        ICESTONE = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(3.0f)
                .setResistance(3.0f)
                .setTickOnLoad()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build("icestone", 10070, BlockLogicIceStone::new);


        QUICKSOIL = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
                .setHardness(0.5f)
                .setResistance(0.5f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
                .build("quicksoil", 10030, BlockLogicQuicksoil::new);


        GLASS_QUICKSOIL = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .build("glass.quicksoil", 10031, BlockLogicGlassQuicksoil::new);


        DOOR_GLASS_QUICKSOIL_BOTTOM = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .build("door.glass.quicksoil.bottom", 10032, block -> new BlockLogicDoorGlassQuicksoil(block, Material.glass, false, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM));

        DOOR_GLASS_QUICKSOIL_TOP = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .build("door.glass.quicksoil.top", 10033, block -> new BlockLogicDoorGlassQuicksoil(block, Material.glass, true, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM));

        TRAPDOOR_GLASS_QUICKSOIL = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
                .setHardness(0.3f)
                .setResistance(0.3f)
                .setLuminance(7)
                .setLightOpacity(0)
                .setVisualUpdateOnMetadata()
                .build("trapdoor.glass.quicksoil", 10034, b -> new BlockLogicTrapDoorGlassQuicksoil(b, Material.glass));


        FLOWER_PURPLE = flower
                .build("flower.purple", 10020, BlockLogicFlowerStackable::new);

        FLOWER_WHITE = flower
                .build("flower.white", 10021, BlockLogicFlowerStackable::new);


        PLANKS_SKYROOT = wood
                .build("planks.skyroot", 10040, b -> new BlockLogic(b, Material.wood));
        SLAB_PLANKS_SKYROOT = slab
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .build("slab.planks.skyroot", 10041, b -> new BlockLogicSlab(b, PLANKS_SKYROOT));
        STAIRS_PLANKS_SKYROOT = stairs
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .build("stairs.planks.skyroot", 10042, b -> new BlockLogicStairs(b, PLANKS_SKYROOT));
        FENCE_PLANKS_SKYROOT = stairs
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
                .build("fence.planks.skyroot", 10043, BlockLogicFence::new);
        FENCEGATE_PLANKS_SKYROOT = stairs
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
                .build("fencegate.planks.skyroot", 10044, BlockLogicFenceGate::new);

        DOOR_PLANKS_SKYROOT_BOTTOM = wood
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .build("door.planks.skyroot.bottom", "door_planks_skyroot_bottom", 10045, b -> new BlockLogicDoor(b, Material.wood, false, false, () -> AetherItems.DOOR_SKYROOT));
        DOOR_PLANKS_SKYROOT_TOP = wood
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .build("door.planks.skyroot.top", "door_planks_skyroot_top", 10046, b -> new BlockLogicDoor(b, Material.wood, true, false, () -> AetherItems.DOOR_SKYROOT));

        TRAPDOOR_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .build("trapdoor.planks.skyroot", 10047, b -> new BlockLogicTrapDoor(b, Material.wood));

        CHEST_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .build("chest.planks.skyroot", 10048, b -> new BlockLogicChest(b, Material.wood));

        BUTTON_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .build("button.planks.skyroot", 10049, BlockLogicButtonPlanks::new);

        PRESSURE_PLATE_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .build("pressure.plate.planks.skyroot", 10039, b -> new BlockLogicPressurePlate<>(b, Entity.class, Material.wood));


        LOG_SKYROOT = log
                .build("log.skyroot", 10010, BlockLogicLog::new);

        LOG_OAK_GOLDEN = log
                .build("log.oak.golden", 10011, BlockLogicLog::new);


        LEAVES_SKYROOT = leaves
                .build("leaves.skyroot", 10012, block -> new BlockLogicLeavesBase(block, Material.leaves, SAPLING_SKYROOT));

        LEAVES_OAK_GOLDEN = leaves
                .build("leaves.oak.golden", 10013, block -> new BlockLogicLeavesBase(block, Material.leaves, SAPLING_OAK_GOLDEN));


        SAPLING_SKYROOT = sapling
                .build("sapling.skyroot", 10014, BlockLogicSaplingSkyroot::new);

        SAPLING_OAK_GOLDEN = sapling
                .build("sapling.oak.golden", 10015, BlockLogicSaplingOakGolden::new);


        AERCLOUD_WHITE = clouds
                .build("aercloud.white", 10080, BlockLogicCloudBase::new);
        AERCLOUD_BLUE = clouds
                .build("aercloud.blue", 10081, BlockLogicCloudBlue::new);
        AERCLOUD_GOLD = clouds
                .build("aercloud.gold", 10082, BlockLogicCloudBase::new);

        AEROGEL = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(1.0f)
                .setResistance(2000.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
                .build("aerogel", 10083, b -> new BlockLogicTransparent(b, Material.stone));


        TORCH_AMBROSIUM = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
                .setHardness(1.0f)
                .setResistance(1.0f)
                .setUseInternalLight()
                .setLuminance(15)
                .setTicking(true)
                .setTickOnLoad()
                .build("torch.ambrosium", 10005, BlockLogicTorchAmbrosium::new);


        ORE_AMBROSIUM_HOLYSTONE = ores
                .setBlockSound(BlockSounds.STONE)
                .build("ore.ambrosium.holystone", 10085, b -> new BlockLogicOreAmbrosium(b, COBBLE_HOLYSTONE, Material.stone));
        ORE_ZANITE_HOLYSTONE = ores
                .build("ore.zanite.holystone", 10086, b -> new BlockLogicOreZanite(b, COBBLE_HOLYSTONE, Material.stone));
        ORE_GRAVITITE_HOLYSTONE = ores
                .build("ore.gravitite.holystone", 10087, b -> new BlockLogicOreGravitite(b, COBBLE_HOLYSTONE, Material.stone));

        BLOCK_AMBROSIUM = oreBlock
                .setBlockSound(BlockSounds.STONE)
                .build("block.ambrosium", 10090, b -> new BlockLogic(b, Material.stone));
        BLOCK_ZANITE = oreBlock
                .build("block.zanite", 10091, b -> new BlockLogic(b, Material.metal));
        BLOCK_GRAVITITE = oreBlock
                .build("block.gravitite", 10092, b -> new BlockLogic(b, Material.metal));

        BRICK_ZANITE = oreBlock
                .build("brick.zanite", 10093, b -> new BlockLogic(b, Material.metal));

        SLAB_BRICK_ZANITE = oreBlock
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("slab.brick.zanite", 10094, b -> new BlockLogicSlab(b, BRICK_ZANITE));
        STAIRS_BRICK_ZANITE = oreBlock
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("stairs.brick.zanite", 10095, b -> new BlockLogicStairs(b, BRICK_ZANITE));

        CARVED_STONE = dungeonStone
                .build("carved.stone", 10100, b -> new BlockLogic(b, Material.stone));
        SLAB_CARVED_STONE = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("slab.carved.stone", 10101, b -> new BlockLogicSlab(b, CARVED_STONE));
        STAIRS_CARVED_STONE = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("stairs.carved.stone", 10102, b -> new BlockLogicStairs(b, CARVED_STONE));
        CARVED_STONE_LIGHT = dungeonStone
                .setLuminance(11)
                .build("carved.stone.light", 10103, b -> new BlockLogic(b, Material.stone));


        CARVED_ANGELIC = dungeonStone
                .build("carved.angelic", 10104, b -> new BlockLogic(b, Material.stone));
        SLAB_CARVED_ANGELIC = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("slab.carved.angelic", 10105, b -> new BlockLogicSlab(b, CARVED_ANGELIC));
        STAIRS_CARVED_ANGELIC = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("stairs.carved.angelic", 10106, b -> new BlockLogicStairs(b, CARVED_ANGELIC));
        CARVED_ANGELIC_LIGHT = dungeonStone
                .setLuminance(11)
                .build("carved.angelic.light", 10107, b -> new BlockLogic(b, Material.stone));


        CARVED_HELLFIRE = dungeonStone
                .build("carved.hellfire", 10108, b -> new BlockLogic(b, Material.stone));
        SLAB_CARVED_HELLFIRE = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("slab.carved.hellfire", 10109, b -> new BlockLogicSlab(b, CARVED_HELLFIRE));
        STAIRS_CARVED_HELLFIRE = dungeonStone
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("stairs.carved.hellfire", 10110, b -> new BlockLogicStairs(b, CARVED_HELLFIRE));
        CARVED_HELLFIRE_LIGHT = dungeonStone
                .setLuminance(11)
                .build("carved.hellfire.light", 10111, b -> new BlockLogic(b, Material.stone));


        PILLAR = dungeonStone
                .build("pillar", 10120, b -> new BlockLogicAxisAligned(b, Material.stone));
        PILLAR_CAPSTONE = dungeonStone
                .build("pillar.capstone", 10121, b -> new BlockLogicAxisAligned(b, Material.stone));


        CHEST_DUNGEON = dungeonStone
                .build("chest.dungeon", 10140, BlockLogicChestDungeon::new);

        CHEST_DUNGEON_LOCKED = dungeonStoneLocked
                .build("chest.dungeon.locked", 10141, BlockLogicChestDungeon::new);


        CARVED_STONE_LOCKED = dungeonStoneLocked
                .build("carved.stone.locked", 10130, b -> new BlockLogic(b, Material.stone));
        CARVED_STONE_LIGHT_LOCKED = dungeonStoneLocked
                .setLuminance(11)
                .build("carved.stone.light.locked", 10131, b -> new BlockLogic(b, Material.stone));


        CARVED_ANGELIC_LOCKED = dungeonStoneLocked
                .build("carved.angelic.locked", 10132, b -> new BlockLogic(b, Material.stone));
        CARVED_ANGELIC_LIGHT_LOCKED = dungeonStoneLocked
                .setLuminance(11)
                .build("carved.angelic.light.locked", 10133, b -> new BlockLogic(b, Material.stone));


        CARVED_HELLFIRE_LOCKED = dungeonStoneLocked
                .build("carved.hellfire.locked", 10134, b -> new BlockLogic(b, Material.stone));
        CARVED_HELLFIRE_LIGHT_LOCKED = dungeonStoneLocked
                .setLuminance(11)
                .build("carved.hellfire.light.locked", 10135, b -> new BlockLogic(b, Material.stone));


        CARVED_STONE_TRAPPED = dungeonStoneLocked
                .build("carved.stone.trapped", 10136, b -> new BlockLogicTrapped(b, CARVED_STONE.id(), MobChicken.class));

        CARVED_ANGELIC_TRAPPED = dungeonStoneLocked
                .build("carved.angelic.trapped", 10137, b -> new BlockLogicTrapped(b, CARVED_ANGELIC.id(), MobCow.class));


        CHEST_MIMIC = wood
                .build("chest.mimic", 10138, BlockLogicChestMimic::new);


        LANTERN_FIREFLY_SILVER = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.GLASS)
                .setHardness(0.1f)
                .setResistance(0.1f)
                .setUseInternalLight()
                .setLuminance(14)
                .setVisualUpdateOnMetadata()
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_PICKAXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                .build("lantern.firefly.silver", 10150, b -> new BlockLogicLanternFirefly(b, MobFireflyCluster.FireflyColor.BLUE, () -> AetherItems.LANTERN_FIREFLY_SILVER));
    }


}
