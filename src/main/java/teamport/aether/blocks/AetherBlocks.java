package teamport.aether.blocks;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.block.ItemBlockPainted;
import net.minecraft.core.item.block.ItemBlockSlabPainted;
import net.minecraft.core.item.block.ItemBlockStairsPainted;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import teamport.aether.AetherMod;
import teamport.aether.blocks.dungeon.*;
import teamport.aether.blocks.machine.BlockLogicEnchanter;
import teamport.aether.blocks.machine.BlockLogicFreezer;
import teamport.aether.blocks.machine.BlockLogicIncubator;
import teamport.aether.blocks.skyroot.*;
import teamport.aether.blocks.terrain.*;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import static teamport.aether.AetherConfig.blockID;
import static teamport.aether.AetherMod.MOD_ID;
@SuppressWarnings({"java:S6539", "java:S1104", "java:S1444", "java:S3008"})
public final class AetherBlocks implements BlockInitEntrypoint {

    public static Block<BlockLogicPortalAether> PORTAL_AETHER;

    public static Block<?> GRASS_AETHER;
    public static Block<?> DIRT_AETHER;
    public static Block<BlockLogicPathDirtAether> PATH_DIRT_AETHER;

    public static Block<?> QUICKSOIL;

    public static Block<?> GLASS_QUICKSOIL;
    public static Block<?> TRAPDOOR_GLASS_QUICKSOIL;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_TOP;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_BOTTOM;

    public static Block<BlockLogicTallGrassAether> TALLGRASS_AETHER;

    public static Block<BlockLogicFlowerStackable> FLOWER_PURPLE;
    public static Block<BlockLogicFlowerStackable> FLOWER_WHITE;

    public static Block<?> HOLYSTONE;
    public static Block<?> HOLYSTONE_MOSSY;
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

    public static Block<?> ENCHANTER_IDLE;
    public static Block<?> ENCHANTER_ACTIVE;
    public static Block<?> FREEZER_IDLE;
    public static Block<?> FREEZER_ACTIVE;
    public static Block<?> INCUBATOR_IDLE;
    public static Block<?> INCUBATOR_ACTIVE;


    public static Block<BlockLogicLog> LOG_SKYROOT;
    public static Block<BlockLogicLog> LOG_OAK_GOLDEN;

    public static Block<?> LEAVES_SKYROOT;
    public static Block<?> LEAVES_OAK_GOLDEN;

    public static Block<?> SAPLING_SKYROOT;
    public static Block<?> SAPLING_OAK_GOLDEN;

    public static Block<?> PLANKS_SKYROOT;
    public static Block<? extends IPainted> PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableSlab> SLAB_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedSlab> SLAB_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableStairs> STAIRS_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedStairs> STAIRS_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableFence> FENCE_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedFence> FENCE_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableFenceGate> FENCEGATE_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedFenceGate> FENCEGATE_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_BOTTOM;
    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_TOP;
    public static Block<BlockLogicPaintedDoor> DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM;
    public static Block<BlockLogicPaintedDoor> DOOR_PLANKS_SKYROOT_PAINTED_TOP;

    public static Block<?> SIGN_POST_PLANKS_SKYROOT;
    public static Block<?> SIGN_WALL_PLANKS_SKYROOT;
    public static Block<?> SIGN_POST_PLANKS_SKYROOT_PAINTED;
    public static Block<?> SIGN_WALL_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableTrapDoor> TRAPDOOR_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedTrapDoor> TRAPDOOR_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableChest> CHEST_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedChest> CHEST_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintableButton> BUTTON_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedButton> BUTTON_PLANKS_SKYROOT_PAINTED;

    public static Block<BlockLogicPaintablePressurePlate<Entity>> PRESSURE_PLATE_PLANKS_SKYROOT;
    public static Block<BlockLogicPaintedPressurePlate<Entity>> PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED;

    public static Block<?> ORE_AMBROSIUM_HOLYSTONE;

    public static Block<?> ORE_ZANITE_HOLYSTONE;

    public static Block<?> ORE_GRAVITITE_HOLYSTONE;

    public static Block<?> BLOCK_AMBER;
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

    public static Block<BlockLogicPaintableChestMimic> CHEST_MIMIC_OAK;
    public static Block<BlockLogicPaintedChestMimic> CHEST_MIMIC_OAK_PAINTED;
    public static Block<BlockLogicPaintableChestMimic> CHEST_MIMIC_SKYROOT;
    public static Block<BlockLogicPaintedChestMimic> CHEST_MIMIC_SKYROOT_PAINTED;
    public static Block<BlockLogicChestMimic> CHEST_MIMIC_BRONZE;
    public static Block<BlockLogicChestMimic> CHEST_MIMIC_SILVER;
    public static Block<BlockLogicChestMimic> CHEST_MIMIC_GOLD;

    public static Block<?> DOOR_DUNGEON_BRONZE;
    public static Block<?> CHEST_DUNGEON_BRONZE;
    public static Block<?> CHEST_DUNGEON_BRONZE_LOCKED;

    public static Block<?> DOOR_DUNGEON_SILVER;
    public static Block<?> CHEST_DUNGEON_SILVER;
    public static Block<?> CHEST_DUNGEON_SILVER_LOCKED;

    public static Block<?> DOOR_DUNGEON_GOLD;
    public static Block<?> CHEST_DUNGEON_GOLD;
    public static Block<?> CHEST_DUNGEON_GOLD_LOCKED;

    public static Block<?> CARVED_STONE_LOCKED;
    public static Block<?> CARVED_ANGELIC_LOCKED;
    public static Block<?> CARVED_HELLFIRE_LOCKED;

    public static Block<?> CARVED_STONE_LIGHT_LOCKED;
    public static Block<?> CARVED_ANGELIC_LIGHT_LOCKED;
    public static Block<?> CARVED_HELLFIRE_LIGHT_LOCKED;

    public static Block<?> CARVED_STONE_TRAPPED;

    public static Block<?> CARVED_ANGELIC_TRAPPED_LOCKED;

    public static Block<?> LANTERN_FIREFLY_SILVER;
    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeBlocks();
        }
    }

    public static void initializeBlocks() {

        BlockBuilder stone = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        BlockBuilder hellfire = stone
            .setHardness(1.5F)
            .setInfiniburn();

        BlockBuilder wood = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setFlammability(20, 5)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        BlockBuilder dungeonStoneLocked = stone
            .setTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.PISTON_CRUSHING)
            .setImmovable()
            .setHardness(-1.0F)
            .setResistance(999999999F);

        BlockBuilder oreBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.METAL)
            .setHardness(3.0f)
            .setResistance(10.0f)
            .setTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BlockBuilder flower = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.SHEEPS_FAVOURITE_BLOCK, AetherBlockTags.PLANTABLE_IN_AETHER_JAR);

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
            .setLightOpacity(1)
            .setTickOnLoad()
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.MINEABLE_BY_AXE, AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, AetherBlockTags.MINEABLE_BY_AETHER_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);

        BlockBuilder sapling = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setResistance(0.0f)
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.PLANTABLE_IN_AETHER_JAR);

        BlockBuilder clouds = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CAVES_CUT_THROUGH, BlockTags.PREVENT_MOB_SPAWNS)
            .setLightOpacity(1)
            .setHardness(0.2f)
            .setResistance(0.2f);

        BlockBuilder slab = new BlockBuilder(MOD_ID)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata();

        BlockBuilder ores = stone
            .setHardness(1.5f)
            .setResistance(5.0f);

        BlockBuilder station = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.5f)
            .setResistance(10.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT);

        // Blocks

        PORTAL_AETHER = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GLASS)
            .setHardness(-1.0f)
            .setResistance(-1.0f)
            .setLuminance(15)
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("portal.aether", "portal_aether", blockID("PORTAL_AETHER"), b -> new BlockLogicPortalAether(b, AetherDimension.getAether(), Blocks.GLOWSTONE, Blocks.FLUID_WATER_FLOWING));

        ///  M: GRASS
        GRASS_AETHER = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.grass", "step.grass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setResistance(0.6f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, AetherBlockTags.GROWS_AETHER_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN)
            .build("grass.aether", "grass_aether", blockID("GRASS_AETHER"), b -> new BlockLogicGrassAether(b, DIRT_AETHER));

        ///  M: DIRT
        DIRT_AETHER = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GRAVEL)
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, AetherBlockTags.GROWS_AETHER_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS)
            .build("dirt.aether", "dirt_aether", blockID("DIRT_AETHER"), BlockLogicDirtAether::new);

        ///  M: DIRT
        PATH_DIRT_AETHER = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GRAVEL)
            .setHardness(0.2f)
            .setResistance(0.2f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL)
            .setUseInternalLight()
            .build("path.dirt.aether", "path_dirt_aether", blockID("PATH_DIRT_AETHER"), BlockLogicPathDirtAether::new);


        /// M: MARBEL
        HOLYSTONE = stone
            .build("holystone", "holystone", blockID("HOLYSTONE"), b -> new BlockLogicStone(b, COBBLE_HOLYSTONE, Material.marble));

        /// M: MOSS
        HOLYSTONE_MOSSY = stone
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.GROWS_AETHER_FLOWERS)
            .build("holystone.mossy", "holystone_mossy", blockID("HOLYSTONE_MOSSY"), b -> new BlockLogicMoss(b, HOLYSTONE));

        /// M: MARBEL
        HOLYSTONE_POLISHED = stone
            .build("holystone.polished", "holystone_polished", blockID("HOLYSTONE_POLISHED"), b -> new BlockLogic(b, Material.marble));

        /// M: MARBEL
        HOLYSTONE_CARVED = stone
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("holystone.carved", "holystone_carved", blockID("HOLYSTONE_CARVED"), b -> new BlockLogic(b, Material.marble)).withDisabledStats();

        SLAB_HOLYSTONE_POLISHED = slab
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setResistance(0.8f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("slab.holystone.carved", "slab_holystone_polished", blockID("SLAB_HOLYSTONE_POLISHED"), b -> new BlockLogicSlab(b, HOLYSTONE_CARVED));

        /// M: MARBEL
        COBBLE_HOLYSTONE = stone
            .build("cobble.holystone", "cobble_holystone", blockID("COBBLE_HOLYSTONE"), b -> new BlockLogicDouble(b, Material.marble, () -> Blocks.GRAVEL));

        /// M: MARBEL
        COBBLE_HOLYSTONE_MOSSY = stone
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.GROWS_AETHER_FLOWERS)
            .build("cobble.holystone.mossy", "cobble_holystone_mossy", blockID("COBBLE_HOLYSTONE_MOSSY"), b -> new BlockLogicDouble(b, Material.marble, () -> Blocks.GRAVEL));

        STAIRS_COBBLE_HOLYSTONE = slab
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("stairs.cobble.holystone", "stairs_cobble_holystone", blockID("STAIRS_COBBLE_HOLYSTONE"), b -> new BlockLogicStairs(b, COBBLE_HOLYSTONE));

        SLAB_COBBLE_HOLYSTONE = slab
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("slab.cobble.holystone", "slab_cobble_holystone", blockID("SLAB_COBBLE_HOLYSTONE"), b -> new BlockLogicSlab(b, COBBLE_HOLYSTONE));


        BRICK_HOLYSTONE = stone
            .build("brick.holystone", "brick_holystone", blockID("BRICK_HOLYSTONE"), b -> new BlockLogic(b, Material.marble));

        STAIRS_BRICK_HOLYSTONE = slab
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("stairs.brick.holystone", "stairs_brick_holystone", blockID("STAIRS_BRICK_HOLYSTONE"), b -> new BlockLogicStairs(b, BRICK_HOLYSTONE));

        SLAB_BRICK_HOLYSTONE = slab
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("slab.brick.holystone", "slab_brick_holystone", blockID("SLAB_BRICK_HOLYSTONE"), b -> new BlockLogicSlab(b, BRICK_HOLYSTONE));


        /// M: STONE
        ICESTONE = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(3.0f)
            .setTickOnLoad()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
            .build("icestone", "icestone", blockID("ICESTONE"), BlockLogicIceStone::new);


        QUICKSOIL = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.sand", "step.gravel", 1.0f, 1.0f))
            .setHardness(0.5f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE)
            .build("quicksoil", "quicksoil", blockID("QUICKSOIL"), BlockLogicQuicksoil::new);


        /// M: DIRT
        GLASS_QUICKSOIL = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.EXTENDS_MOTION_SENSOR_RANGE)
            .build("glass.quicksoil", "glass_quicksoil", blockID("GLASS_QUICKSOIL"), BlockLogicGlassQuicksoil::new);


        DOOR_GLASS_QUICKSOIL_BOTTOM = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .<BlockLogicDoorGlassQuicksoil>build("door.glass.quicksoil.bottom", "door_glass_quicksoil_bottom", blockID("DOOR_GLASS_QUICKSOIL_BOTTOM"),
                block -> new BlockLogicDoorGlassQuicksoil(block, Material.glass, false, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM))
            .setStatParent(() -> AetherItems.DOOR_GLASS_AMBROSIUM);

        DOOR_GLASS_QUICKSOIL_TOP = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .<BlockLogicDoorGlassQuicksoil>build("door.glass.quicksoil.top", "door_glass_quicksoil_top", blockID("DOOR_GLASS_QUICKSOIL_TOP"),
                block -> new BlockLogicDoorGlassQuicksoil(block, Material.glass, true, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM))
            .setStatParent(() -> AetherItems.DOOR_GLASS_AMBROSIUM);

        TRAPDOOR_GLASS_QUICKSOIL = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "random.glass", 1.0f, 1.0f))
            .setHardness(0.3f)
            .setLuminance(7)
            .setLightOpacity(0)
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("trapdoor.glass.quicksoil", "trapdoor_glass_quicksoil", blockID("TRAPDOOR_GLASS_QUICKSOIL"), b -> new BlockLogicTrapDoorGlassQuicksoil(b, Material.glass));

        ///  M: PLANT
        FLOWER_PURPLE = flower
            .build("flower.purple", "flower_purple", blockID("FLOWER_PURPLE"), b -> (BlockLogicFlowerAether) (new BlockLogicFlowerAether(b)).setKilledByWeather().setBonemealable());

        FLOWER_WHITE = flower
            .build("flower.white", "flower_white", blockID("FLOWER_WHITE"), b -> (BlockLogicFlowerAether) (new BlockLogicFlowerAether(b)).setKilledByWeather().setBonemealable());


        TALLGRASS_AETHER = flower
            .setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEEPS_FAVOURITE_BLOCK, BlockTags.SHEARS_DO_SILK_TOUCH)
            .build("tallgrass.aether", "tallgrass_aether", blockID("TALLGRASS_AETHER"), b -> (BlockLogicTallGrassAether) (new BlockLogicTallGrassAether(b)).setKilledByWeather());


        PLANKS_SKYROOT = wood
            .build("planks.skyroot", "planks_skyroot", blockID("PLANKS_SKYROOT"), b -> new BlockLogicPaintableBlock(b, Material.wood, () -> PLANKS_SKYROOT_PAINTED));

        PLANKS_SKYROOT_PAINTED = wood
            .setBlockItem(b -> new ItemBlockPainted<>(b, false))
            .build("planks.skyroot.painted", "planks_skyroot_painted", blockID("PLANKS_SKYROOT_PAINTED"), b -> new BlockLogicPaintedBlock(b, () -> PLANKS_SKYROOT));

        SLAB_PLANKS_SKYROOT = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .build(
                "slab.planks.skyroot",
                "slab_planks_skyroot",
                blockID("SLAB_PLANKS_SKYROOT"),
                b -> new BlockLogicPaintableSlab(b, PLANKS_SKYROOT, AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED));
        SLAB_PLANKS_SKYROOT_PAINTED = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .setBlockItem(ItemBlockSlabPainted::new)
            .build(
                "slab.planks.skyroot.painted",
                "slab_planks_skyroot_painted",
                blockID("SLAB_PLANKS_SKYROOT_PAINTED"),
                b -> new BlockLogicPaintedSlab(b, PLANKS_SKYROOT, AetherBlocks.SLAB_PLANKS_SKYROOT.id()));


        STAIRS_PLANKS_SKYROOT = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .build(
                "stairs.planks.skyroot",
                "stairs_planks_skyroot",
                blockID("STAIRS_PLANKS_SKYROOT")
                , block -> new BlockLogicPaintableStairs(
                    block, PLANKS_SKYROOT, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED
                )
            );
        STAIRS_PLANKS_SKYROOT_PAINTED = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .setBlockItem(ItemBlockStairsPainted::new)
            .build(
                "stairs.planks.skyroot.painted",
                "stairs_planks_skyroot_painted",
                blockID("STAIRS_PLANKS_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedStairs(
                    block, PLANKS_SKYROOT, AetherBlocks.STAIRS_PLANKS_SKYROOT.id()
                )
            );


        FENCE_PLANKS_SKYROOT = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT, BlockTags.CAN_HANG_OFF)
            .build(
                "fence.planks.skyroot",
                "fence_planks_skyroot",
                blockID("FENCE_PLANKS_SKYROOT"),
                block -> new BlockLogicPaintableFence(
                    block, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED
                )
            );


        FENCE_PLANKS_SKYROOT_PAINTED = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setBlockItem(b -> new ItemBlockPainted<>(b, false))
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT,BlockTags.CAN_HANG_OFF)
            .build(
                "fence.planks.skyroot.painted",
                "fence_planks_skyroot_painted",
                blockID("FENCE_PLANKS_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedFence(
                    block, AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()
                )
            );

        FENCEGATE_PLANKS_SKYROOT = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
            .build(
                "fencegate.planks.skyroot",
                "fencegate_planks_skyroot",
                blockID("FENCEGATE_PLANKS_SKYROOT"),
                block -> new BlockLogicPaintableFenceGate(
                    block, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED
                )
            );

        FENCEGATE_PLANKS_SKYROOT_PAINTED = slab
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build(
                "fencegate.planks.skyroot.painted",
                "fencegate_planks_skyroot_painted",
                blockID("FENCEGATE_PLANKS_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedFenceGate(
                    block, AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()
                )
            );

        DOOR_PLANKS_SKYROOT_BOTTOM = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .setHardness(3.0f)
            .<BlockLogicDoor>build(
                "door.planks.skyroot.bottom",
                "door_planks_skyroot_bottom",
                blockID("DOOR_PLANKS_SKYROOT_BOTTOM"),
                block -> new BlockLogicPaintableDoor(
                    block, Material.clay,
                    false, false,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM,
                    () -> AetherItems.DOOR_SKYROOT)
            )
            .setStatParent(() -> AetherItems.DOOR_SKYROOT);

        DOOR_PLANKS_SKYROOT_TOP = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .setHardness(3.0f)
            .<BlockLogicDoor>build(
                "door.planks.skyroot.top",
                "door_planks_skyroot_top",
                blockID("DOOR_PLANKS_SKYROOT_TOP"),
                block -> new BlockLogicPaintableDoor(
                    block, Material.clay,
                    true, false,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM,
                    () -> AetherItems.DOOR_SKYROOT
                )
            )
            .setStatParent(() -> AetherItems.DOOR_SKYROOT);

        DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .setHardness(3.0f)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .<BlockLogicPaintedDoor>build(
                "door.planks.skyroot.bottom.painted",
                "door_planks_skyroot_bottom_painted",
                blockID("DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM"),
                block -> new BlockLogicPaintedDoor(
                    block,
                    Material.wood,
                    false,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_TOP.id(),
                    AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM.id(),
                    () -> AetherItems.DOOR_SKYROOT_PAINTED
                )
            )
            .setStatParent(() -> AetherItems.DOOR_SKYROOT_PAINTED);

        DOOR_PLANKS_SKYROOT_PAINTED_TOP = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setVisualUpdateOnMetadata()
            .setHardness(3.0f)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .<BlockLogicPaintedDoor>build(
                "door.planks.skyroot.top.painted",
                "door_planks_skyroot_top_painted",
                blockID("DOOR_PLANKS_SKYROOT_PAINTED_TOP"),
                block -> new BlockLogicPaintedDoor(
                    block,
                    Material.wood,
                    true,
                    AetherBlocks.DOOR_PLANKS_SKYROOT_TOP.id(),
                    AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM.id(),
                    () -> AetherItems.DOOR_SKYROOT_PAINTED
                )
            )
            .setStatParent(() -> AetherItems.DOOR_SKYROOT_PAINTED);

        SIGN_POST_PLANKS_SKYROOT = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setHardness(1.0f)
            .setVisualUpdateOnMetadata()
            .build("sign.post.planks.skyroot", "sign_post_planks_skyroot", blockID("SIGN_POST_PLANKS_SKYROOT"), b -> new BlockLogicPaintableSignSkyroot(b, true))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT);

        SIGN_WALL_PLANKS_SKYROOT = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setHardness(1.0f)
            .setVisualUpdateOnMetadata()
            .build("sign.wall.planks.skyroot", "sign_wall_planks_skyroot", blockID("SIGN_WALL_PLANKS_SKYROOT"), b -> new BlockLogicPaintableSignSkyroot(b, false))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT);

        SIGN_POST_PLANKS_SKYROOT_PAINTED = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setHardness(1.0f)
            .setVisualUpdateOnMetadata()
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build("sign.post.planks.skyroot.painted", "sign_post_planks_skyroot_painted", blockID("SIGN_POST_PLANKS_SKYROOT_PAINTED"),
                b -> new BlockLogicPaintedSignSkyroot(b, true))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT_PAINTED);

        SIGN_WALL_PLANKS_SKYROOT_PAINTED = wood
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
            .setHardness(1.0f)
            .setVisualUpdateOnMetadata()
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build("sign.wall.planks.skyroot.painted", "sign_wall_planks_skyroot_painted", blockID("SIGN_WALL_PLANKS_SKYROOT_PAINTED"),
                b -> new BlockLogicPaintedSignSkyroot(b, false))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT_PAINTED);

        TRAPDOOR_PLANKS_SKYROOT = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .build("trapdoor.planks.skyroot", "trapdoor_planks_skyroot", blockID("TRAPDOOR_PLANKS_SKYROOT"), b -> new BlockLogicPaintableTrapDoor(b, Material.wood, TRAPDOOR_PLANKS_SKYROOT_PAINTED));

        TRAPDOOR_PLANKS_SKYROOT_PAINTED = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build(
                "trapdoor.planks.skyroot.painted",
                "trapdoor_planks_skyroot_painted",
                blockID("TRAPDOOR_PLANKS_SKYROOT_PAINTED"),
                b -> new BlockLogicPaintedTrapDoor(b, Material.wood, TRAPDOOR_PLANKS_SKYROOT.id()));

        CHEST_PLANKS_SKYROOT = wood
            .setVisualUpdateOnMetadata()
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build(
                "chest.planks.skyroot",
                "chest_planks_skyroot",
                blockID("CHEST_PLANKS_SKYROOT"),
                b -> new BlockLogicPaintableChest(b, Material.wood, CHEST_PLANKS_SKYROOT_PAINTED));

        CHEST_PLANKS_SKYROOT_PAINTED = wood
            .setVisualUpdateOnMetadata()
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build("chest.planks.skyroot.painted",
                "chest_planks_skyroot_painted",
                blockID("CHEST_PLANKS_SKYROOT_PAINTED"),
                b -> new BlockLogicPaintedChest(b, Material.wood, CHEST_PLANKS_SKYROOT.id()));


        BUTTON_PLANKS_SKYROOT = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
            .build(
                "button.planks.skyroot",
                "button_planks_skyroot",
                blockID("BUTTON_PLANKS_SKYROOT"),
                block -> new BlockLogicPaintableButton(
                    block, BUTTON_PLANKS_SKYROOT_PAINTED
                )
            );

        BUTTON_PLANKS_SKYROOT_PAINTED = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build(
                "button.planks.skyroot.painted",
                "button_planks_skyroot_painted",
                blockID("BUTTON_PLANKS_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedButton(
                    block, BUTTON_PLANKS_SKYROOT.id()
                )
            );

        PRESSURE_PLATE_PLANKS_SKYROOT = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
            .build(
                "pressure.plate.planks.skyroot",
                "pressure_plate_planks_skyroot",
                blockID("PRESSURE_PLATE_PLANKS_SKYROOT"),
                block -> new BlockLogicPaintablePressurePlate<>(
                    block, Entity.class, Material.wood,
                    PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED
                )
            );


        PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED = wood
            .setVisualUpdateOnMetadata()
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .build(
                "pressure.plate.planks.skyroot.painted",
                "pressure_plate_planks_skyroot_painted",
                blockID("PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedPressurePlate<>(
                    block, Entity.class, Material.wood,
                    PRESSURE_PLATE_PLANKS_SKYROOT.id()
                )
            );


        LOG_SKYROOT = log
            .build("log.skyroot", "log_skyroot", blockID("LOG_SKYROOT"), BlockLogicLogAether::new);

        LOG_OAK_GOLDEN = log
            .build("log.oak.golden", "log_oak_golden", blockID("LOG_OAK_GOLDEN"), BlockLogicGoldenLogAether::new);


        LEAVES_SKYROOT = leaves
            .build("leaves.skyroot", "leaves_skyroot", blockID("LEAVES_SKYROOT"), block -> new BlockLogicLeavesSkyroot(block, Material.leaves, SAPLING_SKYROOT));

        LEAVES_OAK_GOLDEN = leaves
            .build("leaves.oak.golden", "leaves_oak_golden", blockID("LEAVES_OAK_GOLDEN"), BlockLogicLeavesOakGolden::new);


        SAPLING_SKYROOT = sapling
            .build("sapling.skyroot", "sapling_skyroot", blockID("SAPLING_SKYROOT"), BlockLogicSaplingSkyroot::new);

        SAPLING_OAK_GOLDEN = sapling
            .build("sapling.oak.golden", "sapling_oak_golden", blockID("SAPLING_OAK_GOLDEN"), BlockLogicSaplingOakGolden::new);

        ///  M: CLOTH
        AERCLOUD_WHITE = clouds
            .build("aercloud.white", "aercloud_white", blockID("AERCLOUD_WHITE"), BlockLogicCloudBase::new);
        AERCLOUD_BLUE = clouds
            .build("aercloud.blue", "aercloud_blue", blockID("AERCLOUD_BLUE"), BlockLogicCloudBlue::new);
        AERCLOUD_GOLD = clouds
            .build("aercloud.gold", "aercloud_gold", blockID("AERCLOUD_GOLD"), BlockLogicCloudBase::new);

        AEROGEL = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(1.0f)
            .setResistance(2000.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT)
            .build("aerogel", "aerogel", blockID("AEROGEL"), b -> new BlockLogicTransparent(b, Material.stone));


        TORCH_AMBROSIUM = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.stone", "step.stone", 1.0f, 1.0f))
            .setHardness(0.0f)
            .setUseInternalLight()
            .setLuminance(15)
            .build("torch.ambrosium", "torch_ambrosium", blockID("TORCH_AMBROSIUM"), BlockLogicTorchAmbrosium::new)
            .withTags(BlockTags.BROKEN_BY_FLUIDS);


        ENCHANTER_IDLE = station
            .build("enchanter.idle", "enchanter_idle", blockID("ENCHANTER_IDLE"), b -> new BlockLogicEnchanter(b, false));

        ENCHANTER_ACTIVE = station
            .setLuminance(13)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("enchanter.active", "enchanter_active", blockID("ENCHANTER_ACTIVE"), b -> new BlockLogicEnchanter(b, true)).setStatParent(() -> ENCHANTER_IDLE);

        FREEZER_IDLE = station
            .build("freezer.idle", "freezer_idle", blockID("FREEZER_IDLE"), b -> new BlockLogicFreezer(b, false));

        FREEZER_ACTIVE = station
            .setLuminance(13)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("freezer.active", "freezer_active", blockID("FREEZER_ACTIVE"), b -> new BlockLogicFreezer(b, true)).setStatParent(() -> FREEZER_IDLE);

        INCUBATOR_IDLE = station
            .build("incubator.idle", "incubator_idle", blockID("INCUBATOR_IDLE"), b -> new BlockLogicIncubator(b, false));

        INCUBATOR_ACTIVE = station
            .setLuminance(13)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("incubator.active", "incubator_active", blockID("INCUBATOR_ACTIVE"), b -> new BlockLogicIncubator(b, true)).setStatParent(() -> INCUBATOR_IDLE);

        ///  M:STONE
        ORE_AMBROSIUM_HOLYSTONE = ores
            .setBlockSound(BlockSounds.STONE)
            .build("ore.ambrosium.holystone", "ore_ambrosium_holystone", blockID("ORE_AMBROSIUM_HOLYSTONE"), b -> new BlockLogicOreAmbrosium(b, COBBLE_HOLYSTONE, Material.stone));
        ORE_ZANITE_HOLYSTONE = ores
            .build("ore.zanite.holystone", "ore_zanite_holystone", blockID("ORE_ZANITE_HOLYSTONE"), b -> new BlockLogicOreZanite(b, COBBLE_HOLYSTONE, Material.stone));
        ORE_GRAVITITE_HOLYSTONE = ores
            .setTicking(true)
            .setTickOnLoad()
            .build("ore.gravitite.holystone", "ore_gravitite_holystone", blockID("ORE_GRAVITITE_HOLYSTONE"), b -> new BlockLogicOreGravitite(b, COBBLE_HOLYSTONE, Material.stone));

        BLOCK_AMBER = oreBlock
            .setBlockSound(BlockSounds.STONE)
            .build("block.amber", "block_amber", blockID("BLOCK_AMBER"), b -> new BlockLogicTransparent(b, Material.stone));
        BLOCK_AMBROSIUM = oreBlock
            .setBlockSound(BlockSounds.STONE)
            .build("block.ambrosium", "block_ambrosium", blockID("BLOCK_AMBROSIUM"), b -> new BlockLogic(b, Material.stone));
        BLOCK_ZANITE = oreBlock
            .build("block.zanite", "block_zanite", blockID("BLOCK_ZANITE"), b -> new BlockLogic(b, Material.metal));
        BLOCK_GRAVITITE = oreBlock
            .build("block.gravitite", "block_gravitite", blockID("BLOCK_GRAVITITE"), b -> new BlockLogicBlockGravitite(b, Material.metal));

        BRICK_ZANITE = oreBlock
            .build("brick.zanite", "brick_zanite", blockID("BRICK_ZANITE"), b -> new BlockLogic(b, Material.metal));

        SLAB_BRICK_ZANITE = oreBlock
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("slab.brick.zanite", "slab_brick_zanite", blockID("SLAB_BRICK_ZANITE"), b -> new BlockLogicSlab(b, BRICK_ZANITE));
        STAIRS_BRICK_ZANITE = oreBlock
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("stairs.brick.zanite", "stairs_brick_zanite", blockID("STAIRS_BRICK_ZANITE"), b -> new BlockLogicStairs(b, BRICK_ZANITE));

        CARVED_STONE = stone
            .setHardness(1.5F)
            .build("carved.stone", "carved_stone", blockID("CARVED_STONE"), b -> new BlockLogicDungeon(b, Material.stone));
        SLAB_CARVED_STONE = stone
            .setHardness(1.5F)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("slab.carved.stone", "slab_carved_stone", blockID("SLAB_CARVED_STONE"), b -> new BlockLogicSlab(b, CARVED_STONE));
        STAIRS_CARVED_STONE = stone
            .setHardness(1.5F)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("stairs.carved.stone", "stairs_carved_stone", blockID("STAIRS_CARVED_STONE"), b -> new BlockLogicStairs(b, CARVED_STONE));
        CARVED_STONE_LIGHT = stone
            .setLuminance(10)
            .build("carved.stone.light", "carved_stone_light", blockID("CARVED_STONE_LIGHT"), b -> new BlockLogic(b, Material.stone));


        CARVED_ANGELIC = stone
            .setHardness(1.5F)
            .build("carved.angelic", "carved_angelic", blockID("CARVED_ANGELIC"), b -> new BlockLogicDungeon(b, Material.stone));
        SLAB_CARVED_ANGELIC = stone
            .setHardness(1.5F)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("slab.carved.angelic", "slab_carved_angelic", blockID("SLAB_CARVED_ANGELIC"), b -> new BlockLogicSlab(b, CARVED_ANGELIC));
        STAIRS_CARVED_ANGELIC = stone
            .setHardness(1.5F)
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("stairs.carved.angelic", "stairs_carved_angelic", blockID("STAIRS_CARVED_ANGELIC"), b -> new BlockLogicStairs(b, CARVED_ANGELIC));
        CARVED_ANGELIC_LIGHT = stone
            .setHardness(1.5F)
            .setLuminance(10)
            .build("carved.angelic.light", "carved_angelic_light", blockID("CARVED_ANGELIC_LIGHT"), b -> new BlockLogicDungeon(b, Material.stone));


        CARVED_HELLFIRE = hellfire
            .build("carved.hellfire", "carved_hellfire", blockID("CARVED_HELLFIRE"), b -> new BlockLogicDungeon(b, Material.stone));
        SLAB_CARVED_HELLFIRE = hellfire
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("slab.carved.hellfire", "slab_carved_hellfire", blockID("SLAB_CARVED_HELLFIRE"), b -> new BlockLogicSlab(b, CARVED_HELLFIRE));
        STAIRS_CARVED_HELLFIRE = hellfire
            .setUseInternalLight()
            .setVisualUpdateOnMetadata()
            .build("stairs.carved.hellfire", "stairs_carved_hellfire", blockID("STAIRS_CARVED_HELLFIRE"), b -> new BlockLogicStairs(b, CARVED_HELLFIRE));
        CARVED_HELLFIRE_LIGHT = hellfire
            .setLuminance(10)
            .build("carved.hellfire.light", "carved_hellfire_light", blockID("CARVED_HELLFIRE_LIGHT"), b -> new BlockLogicDungeon(b, Material.stone));


        PILLAR = stone
            .setHardness(1.5F)
            .build("pillar", "pillar", blockID("PILLAR"), b -> new BlockLogicAxisAligned(b, Material.stone));

        PILLAR_CAPSTONE = stone
            .setHardness(1.5F)
            .build("pillar.capstone", "pillar_capstone", blockID("PILLAR_CAPSTONE"), b -> new BlockLogicAxisAligned(b, Material.stone));


        DOOR_DUNGEON_BRONZE = dungeonStoneLocked
            .setLightOpacity(1)
            .setUseInternalLight()
            .build("door.dungeon.bronze", "door_dungeon_bronze", blockID("DOOR_DUNGEON_BRONZE"), b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_BRONZE)).setStatParent(() -> AetherItems.DOOR_DUNGEON_BRONZE);

        DOOR_DUNGEON_SILVER = dungeonStoneLocked
            .setLightOpacity(1)
            .setUseInternalLight()
            .build("door.dungeon.silver", "door_dungeon_silver", blockID("DOOR_DUNGEON_SILVER"), b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_SILVER)).setStatParent(() -> AetherItems.DOOR_DUNGEON_SILVER);

        DOOR_DUNGEON_GOLD = dungeonStoneLocked
            .setLightOpacity(1)
            .setUseInternalLight()
            .build("door.dungeon.gold", "door_dungeon_gold", blockID("DOOR_DUNGEON_GOLD"), b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_GOLD)).setStatParent(() -> AetherItems.DOOR_DUNGEON_GOLD);


        CHEST_DUNGEON_BRONZE = stone
            .setHardness(1.5F)
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.bronze", "chest_dungeon_bronze", blockID("CHEST_DUNGEON_BRONZE"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), false, CHEST_DUNGEON_BRONZE));

        CHEST_DUNGEON_BRONZE_LOCKED = dungeonStoneLocked
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.bronze.locked", "chest_dungeon_bronze_locked", blockID("CHEST_DUNGEON_BRONZE_LOCKED"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), true, CHEST_DUNGEON_BRONZE)).withDisabledStats();

        CHEST_DUNGEON_SILVER = stone
            .setHardness(1.5F)
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.silver", "chest_dungeon_silver", blockID("CHEST_DUNGEON_SILVER"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), false, CHEST_DUNGEON_SILVER));

        CHEST_DUNGEON_SILVER_LOCKED = dungeonStoneLocked
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.silver.locked", "chest_dungeon_silver_locked", blockID("CHEST_DUNGEON_SILVER_LOCKED"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), true, CHEST_DUNGEON_SILVER)).withDisabledStats();

        CHEST_DUNGEON_GOLD = stone
            .setHardness(1.5F)
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.gold", "chest_dungeon_gold", blockID("CHEST_DUNGEON_GOLD"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_GOLD.getDefaultStack(), false, CHEST_DUNGEON_GOLD));

        CHEST_DUNGEON_GOLD_LOCKED = dungeonStoneLocked
            .addTags(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)
            .build("chest.dungeon.gold.locked", "chest_dungeon_gold_locked", blockID("CHEST_DUNGEON_GOLD_LOCKED"),
                b -> new BlockLogicChestLocked(b, AetherItems.KEY_GOLD.getDefaultStack(), true, CHEST_DUNGEON_GOLD)).withDisabledStats();


        ///  M: STONE
        CARVED_STONE_LOCKED = dungeonStoneLocked
            .build("carved.stone.locked", "carved_stone_locked", blockID("CARVED_STONE_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_STONE)).withDisabledStats();
        CARVED_STONE_LIGHT_LOCKED = dungeonStoneLocked
            .setLuminance(7)
            .build("carved.stone.light.locked", "carved_stone_light_locked", blockID("CARVED_STONE_LIGHT_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_STONE_LIGHT)).withDisabledStats();


        CARVED_ANGELIC_LOCKED = dungeonStoneLocked
            .build("carved.angelic.locked", "carved_angelic_locked", blockID("CARVED_ANGELIC_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_ANGELIC)).withDisabledStats();
        CARVED_ANGELIC_LIGHT_LOCKED = dungeonStoneLocked
            .setLuminance(7)
            .build("carved.angelic.light.locked", "carved_angelic_light_locked", blockID("CARVED_ANGELIC_LIGHT_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_ANGELIC_LIGHT)).withDisabledStats();


        CARVED_HELLFIRE_LOCKED = dungeonStoneLocked
            .setInfiniburn()
            .build("carved.hellfire.locked", "carved_hellfire_locked", blockID("CARVED_HELLFIRE_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_HELLFIRE)).withDisabledStats();

        CARVED_HELLFIRE_LIGHT_LOCKED = dungeonStoneLocked
            .setLuminance(7)
            .setInfiniburn()
            .build("carved.hellfire.light.locked", "carved_hellfire_light_locked", blockID("CARVED_HELLFIRE_LIGHT_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_HELLFIRE_LIGHT)).withDisabledStats();

        CARVED_STONE_TRAPPED = stone
            .setHardness(1.5F)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
            .build("carved.stone.trapped", "carved_stone_trapped", blockID("CARVED_STONE_TRAPPED"),
                b -> new BlockLogicTrapped(b, CARVED_STONE, CARVED_STONE, MobSentry.class, 2))
            .withDisabledStats();

        CARVED_ANGELIC_TRAPPED_LOCKED = dungeonStoneLocked
            .build("carved.angelic.trapped.locked", "carved_angelic_trapped_locked", blockID("CARVED_ANGELIC_TRAPPED_LOCKED"),
                b -> new BlockLogicTrapped(b, CARVED_ANGELIC, CARVED_ANGELIC, MobValkyrie.class, 4)
            ).withDisabledStats();


        BlockBuilder mimic = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.WOOD)
            .setHardness(2.0f)
            .setResistance(5.0f)
            .setFlammability(20, 5)
            .setTags(BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);


        CHEST_MIMIC_OAK = mimic
            .addTags(BlockTags.MINEABLE_BY_AXE)
            .build(
                "chest.mimic.oak",
                "chest_mimic_oak",
                blockID("CHEST_MIMIC_OAK"),
                block -> new BlockLogicPaintableChestMimic(block, Material.wood, AetherBlocks.CHEST_MIMIC_OAK_PAINTED)
            );
        CHEST_MIMIC_OAK_PAINTED = mimic
            .addTags(BlockTags.MINEABLE_BY_AXE)
            .build(
                "chest.mimic.oak.painted",
                "chest_mimic_oak_painted",
                blockID("CHEST_MIMIC_OAK_PAINTED"),
                block -> new BlockLogicPaintedChestMimic(block, Material.wood, AetherBlocks.CHEST_MIMIC_OAK.id())
            );

        CHEST_MIMIC_SKYROOT = mimic
            .addTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .build(
                "chest.mimic.skyroot",
                "chest_mimic_skyroot",
                blockID("CHEST_MIMIC_SKYROOT"),
                block -> new BlockLogicPaintableChestMimic(block, Material.wood, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED)
            );
        CHEST_MIMIC_SKYROOT_PAINTED = mimic
            .addTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
            .build(
                "chest.mimic.skyroot.painted",
                "chest_mimic_skyroot_painted",
                blockID("CHEST_MIMIC_SKYROOT_PAINTED"),
                block -> new BlockLogicPaintedChestMimic(block, Material.wood, AetherBlocks.CHEST_MIMIC_SKYROOT.id()))
        ;

        BlockBuilder mimicBoss = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.STONE)
            .setHardness(4.0f)
            .setResistance(5.0f)
            .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);


        CHEST_MIMIC_BRONZE = mimicBoss.build("chest.mimic.bronze", "chest_mimic_bronze", blockID("CHEST_MIMIC_BRONZE"), block -> new BlockLogicChestMimic(block, Material.marble));
        CHEST_MIMIC_SILVER = mimicBoss.build("chest.mimic.silver", "chest_mimic_silver", blockID("CHEST_MIMIC_SILVER"), block -> new BlockLogicChestMimic(block, Material.marble));
        CHEST_MIMIC_GOLD = mimicBoss.build("chest.mimic.gold", "chest_mimic_gold", blockID("CHEST_MIMIC_GOLD"), block -> new BlockLogicChestMimic(block, Material.marble));


        LANTERN_FIREFLY_SILVER = new BlockBuilder(MOD_ID)
            .setBlockSound(BlockSounds.GLASS)
            .setHardness(0.1f)
            .setResistance(0.1f)
            .setUseInternalLight()
            .setLuminance(14)
            .setVisualUpdateOnMetadata()
            .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_PICKAXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
            .build("lantern.firefly.silver", "lantern_firefly_silver", blockID("LANTERN_FIREFLY_SILVER"), b -> new BlockLogicLanternFirefly(b, AetherMod.SILVER, () -> AetherItems.LANTERN_FIREFLY_SILVER))
            .setStatParent(() -> AetherItems.LANTERN_FIREFLY_SILVER);
    }

    @Override
    public void afterBlockInit() {
        init();
        AetherDimension.init();
        PORTAL_AETHER.getLogic().targetDimension = AetherDimension.getAether();

    }
}
