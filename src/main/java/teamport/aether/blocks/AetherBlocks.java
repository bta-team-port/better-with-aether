package teamport.aether.blocks;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import teamport.aether.AetherMod;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.items.AetherItems;
import teamport.aether.world.AetherDimension;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import static teamport.aether.AetherConfig.blockID;
import static teamport.aether.AetherMod.MOD_ID;

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
    public static Block<BlockLogicSlab> SLAB_PLANKS_SKYROOT;
    public static Block<BlockLogicStairs> STAIRS_PLANKS_SKYROOT;
    public static Block<BlockLogicFence> FENCE_PLANKS_SKYROOT;
    public static Block<BlockLogicFenceGate> FENCEGATE_PLANKS_SKYROOT;
    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_BOTTOM;
    public static Block<BlockLogicDoor> DOOR_PLANKS_SKYROOT_TOP;
    public static Block<?> SIGN_POST_PLANKS_SKYROOT;
    public static Block<?> SIGN_WALL_PLANKS_SKYROOT;
    public static Block<BlockLogicTrapDoor> TRAPDOOR_PLANKS_SKYROOT;
    public static Block<BlockLogicChest> CHEST_PLANKS_SKYROOT;
    public static Block<BlockLogicButtonPlanks> BUTTON_PLANKS_SKYROOT;
    public static Block<?> PRESSURE_PLATE_PLANKS_SKYROOT;

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

    public static Block<?> CHEST_MIMIC;

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
    public static Block<?> CARVED_ANGELIC_TRAPPED;

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

        BlockBuilder wood = new BlockBuilder(MOD_ID)
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setFlammability(20, 5)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        BlockBuilder dungeonStoneLocked = stone
                .setTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU)
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
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.SHEEPS_FAVOURITE_BLOCK);

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
                .setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR);

        BlockBuilder clouds = new BlockBuilder(MOD_ID)
                .setBlockSound(new BlockSound("step.cloth", "step.cloth", 1.0f, 1.0f))
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
                .build("portal.aether", "portal_aether", blockID("PORTAL_AETHER"), b -> new BlockLogicPortalAether(b, AetherDimension.AETHER, Blocks.GLOWSTONE, Blocks.FLUID_WATER_FLOWING));

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
                .build("dirt.aether", "dirt_aether", blockID("DIRT_AETHER"), b -> new BlockLogicDirtAether(b));

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
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
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
                .build("flower.purple", "flower_purple", blockID("FLOWER_PURPLE"), (b) -> (BlockLogicFlowerAether) (new BlockLogicFlowerAether(b)).setKilledByWeather().setBonemealable());

        FLOWER_WHITE = flower
                .build("flower.white", "flower_white", blockID("FLOWER_WHITE"), (b) -> (BlockLogicFlowerAether) (new BlockLogicFlowerAether(b)).setKilledByWeather().setBonemealable());


        TALLGRASS_AETHER = flower
                .setTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEEPS_FAVOURITE_BLOCK, BlockTags.SHEARS_DO_SILK_TOUCH)
                .build("tallgrass.aether", "tallgrass_aether", blockID("TALLGRASS_AETHER"), (b) -> (BlockLogicTallGrassAether) (new BlockLogicTallGrassAether(b)).setKilledByWeather());


        PLANKS_SKYROOT = wood
                .build("planks.skyroot", "planks_skyroot", blockID("PLANKS_SKYROOT"), b -> new BlockLogic(b, Material.wood));
        SLAB_PLANKS_SKYROOT = slab
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
                .build("slab.planks.skyroot", "slab_planks_skyroot", blockID("SLAB_PLANKS_SKYROOT"), b -> new BlockLogicSlab(b, PLANKS_SKYROOT));
        STAIRS_PLANKS_SKYROOT = slab
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
                .build("stairs.planks.skyroot", "stairs_planks_skyroot", blockID("STAIRS_PLANKS_SKYROOT"), b -> new BlockLogicStairs(b, PLANKS_SKYROOT));
        FENCE_PLANKS_SKYROOT = slab
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
                .build("fence.planks.skyroot", "fence_planks_skyroot", blockID("FENCE_PLANKS_SKYROOT"), BlockLogicFenceSkyroot::new);
        FENCEGATE_PLANKS_SKYROOT = slab
                .setBlockSound(BlockSounds.WOOD)
                .setHardness(2.0f)
                .setResistance(5.0f)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT)
                .build("fencegate.planks.skyroot", "fencegate_planks_skyroot", blockID("FENCEGATE_PLANKS_SKYROOT"), BlockLogicFenceGateSkyroot::new);

        DOOR_PLANKS_SKYROOT_BOTTOM = wood
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .setHardness(3.0f)
                .<BlockLogicDoor>build("door.planks.skyroot.bottom", "door_planks_skyroot_bottom", blockID("DOOR_PLANKS_SKYROOT_BOTTOM"), b -> new BlockLogicDoor(b, Material.ice, false, false, () -> AetherItems.DOOR_SKYROOT))
                .setStatParent(() -> AetherItems.DOOR_SKYROOT);
        DOOR_PLANKS_SKYROOT_TOP = wood
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
                .setVisualUpdateOnMetadata()
                .setHardness(3.0f)
                .<BlockLogicDoor>build("door.planks.skyroot.top", "door_planks_skyroot_top", blockID("DOOR_PLANKS_SKYROOT_TOP"), b -> new BlockLogicDoor(b, Material.ice, true, false, () -> AetherItems.DOOR_SKYROOT))
                .setStatParent(() -> AetherItems.DOOR_SKYROOT);

//        SIGN_POST_PLANKS_SKYROOT = wood
//                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
//                .setHardness(1.0f)
//                .setVisualUpdateOnMetadata()
//                .build("sign.post.planks.skyroot", "sign_post_planks_skyroot", blockID("SIGN_POST_PLANKS_SKYROOT"), b -> new BlockLogicSignSkyroot(b, true))
//                .setStatParent(() -> AetherItems.SIGN_SKYROOT);
//        SIGN_WALL_PLANKS_SKYROOT = wood
//                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU)
//                .setHardness(1.0f)
//                .setVisualUpdateOnMetadata()
//                .build("sign.wall.planks.skyroot", "sign_wall_planks_skyroot", blockID("SIGN_WALL_PLANKS_SKYROOT"), b -> new BlockLogicSignSkyroot(b, false))
//                .setStatParent(() -> AetherItems.SIGN_SKYROOT);

        TRAPDOOR_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE)
                .build("trapdoor.planks.skyroot", "trapdoor_planks_skyroot", blockID("TRAPDOOR_PLANKS_SKYROOT"), b -> new BlockLogicTrapDoor(b, Material.cloth));

        CHEST_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .build("chest.planks.skyroot", "chest_planks_skyroot", blockID("CHEST_PLANKS_SKYROOT"), b -> new BlockLogicChestSkyroot(b, Material.wood));

        BUTTON_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
                .build("button.planks.skyroot", "button_planks_skyroot", blockID("BUTTON_PLANKS_SKYROOT"), BlockLogicButtonPlanks::new);

        PRESSURE_PLATE_PLANKS_SKYROOT = wood
                .setVisualUpdateOnMetadata()
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS)
                .build("pressure.plate.planks.skyroot", "pressure_plate_planks_skyroot", blockID("PRESSURE_PLATE_PLANKS_SKYROOT"), b -> new BlockLogicPressurePlate<>(b, Entity.class, Material.wood));


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


        CARVED_HELLFIRE = stone
                .setHardness(1.5F)
                .build("carved.hellfire", "carved_hellfire", blockID("CARVED_HELLFIRE"), b -> new BlockLogicDungeon(b, Material.stone));
        SLAB_CARVED_HELLFIRE = stone
                .setHardness(1.5F)
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("slab.carved.hellfire", "slab_carved_hellfire", blockID("SLAB_CARVED_HELLFIRE"), b -> new BlockLogicSlab(b, CARVED_HELLFIRE));
        STAIRS_CARVED_HELLFIRE = stone
                .setHardness(1.5F)
                .setUseInternalLight()
                .setVisualUpdateOnMetadata()
                .build("stairs.carved.hellfire", "stairs_carved_hellfire", blockID("STAIRS_CARVED_HELLFIRE"), b -> new BlockLogicStairs(b, CARVED_HELLFIRE));
        CARVED_HELLFIRE_LIGHT = stone
                .setHardness(1.5F)
                .setLuminance(10)
                .build("carved.hellfire.light", "carved_hellfire_light", blockID("CARVED_HELLFIRE_LIGHT"), b -> new BlockLogicDungeon(b, Material.stone));


        PILLAR = stone
                .setHardness(1.5F)
                .build("pillar", "pillar", blockID("PILLAR"), b -> new BlockLogicAxisAligned(b, Material.stone));

        PILLAR_CAPSTONE = stone
                .setHardness(1.5F)
                .build("pillar.capstone", "pillar_capstone", blockID("PILLAR_CAPSTONE"), b -> new BlockLogicAxisAligned(b, Material.stone));

        DOOR_DUNGEON_GOLD = dungeonStoneLocked
                .setLightOpacity(1)
                .setUseInternalLight()
                .build("door.dungeon.gold", "door_dungeon_gold", blockID("DOOR_DUNGEON_GOLD"), BlockLogicDungeonDoor::new);

        DOOR_DUNGEON_SILVER = dungeonStoneLocked
                .setLightOpacity(1)
                .setUseInternalLight()
                .build("door.dungeon.silver", "door_dungeon_silver", blockID("DOOR_DUNGEON_SILVER"), BlockLogicDungeonDoor::new);

        DOOR_DUNGEON_BRONZE = dungeonStoneLocked
                .setLightOpacity(1)
                .setUseInternalLight()
                .build("door.dungeon.bronze", "door_dungeon_bronze", blockID("DOOR_DUNGEON_BRONZE"), BlockLogicDungeonDoor::new);

        CHEST_DUNGEON_BRONZE = stone
                .setHardness(1.5F)
                .build("chest.dungeon.bronze", "chest_dungeon_bronze", blockID("CHEST_DUNGEON_BRONZE"),
                        b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), false, CHEST_DUNGEON_BRONZE));

        CHEST_DUNGEON_BRONZE_LOCKED = dungeonStoneLocked
                .build("chest.dungeon.bronze.locked", "chest_dungeon_bronze_locked", blockID("CHEST_DUNGEON_BRONZE_LOCKED"),
                        b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), true, CHEST_DUNGEON_BRONZE)).withDisabledStats();

        CHEST_DUNGEON_SILVER = stone
                .setHardness(1.5F)
                .build("chest.dungeon.silver", "chest_dungeon_silver", blockID("CHEST_DUNGEON_SILVER"),
                        b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), false, CHEST_DUNGEON_SILVER));

        CHEST_DUNGEON_SILVER_LOCKED = dungeonStoneLocked
                .build("chest.dungeon.silver.locked", "chest_dungeon_silver_locked", blockID("CHEST_DUNGEON_SILVER_LOCKED"),
                        b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), true, CHEST_DUNGEON_SILVER)).withDisabledStats();

        CHEST_DUNGEON_GOLD = stone
                .setHardness(1.5F)
                .build("chest.dungeon.gold", "chest_dungeon_gold", blockID("CHEST_DUNGEON_GOLD"),
                        b -> new BlockLogicChestLocked(b, AetherItems.KEY_GOLD.getDefaultStack(), false, CHEST_DUNGEON_GOLD));

        CHEST_DUNGEON_GOLD_LOCKED = dungeonStoneLocked
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
                .build("carved.hellfire.locked", "carved_hellfire_locked", blockID("CARVED_HELLFIRE_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_HELLFIRE)).withDisabledStats();

        CARVED_HELLFIRE_LIGHT_LOCKED = dungeonStoneLocked
                .setLuminance(7)
                .build("carved.hellfire.light.locked", "carved_hellfire_light_locked", blockID("CARVED_HELLFIRE_LIGHT_LOCKED"), b -> new BlockLogicLocked(b, Material.stone, CARVED_HELLFIRE_LIGHT)).withDisabledStats();

        CARVED_STONE_TRAPPED = stone
                .setHardness(1.5F)
                .setTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU)
                .build("carved.stone.trapped", "carved_stone_trapped", blockID("CARVED_STONE_TRAPPED"),
                        b -> new BlockLogicTrapped(b, CARVED_STONE, CARVED_STONE, MobSentry.class))
                .withDisabledStats();

        CARVED_ANGELIC_TRAPPED = dungeonStoneLocked
                .build("carved.angelic.trapped", "carved_angelic_trapped", blockID("CARVED_ANGELIC_TRAPPED"),
                        b -> new BlockLogicTrapped(b, CARVED_ANGELIC_LOCKED, CARVED_ANGELIC, MobValkyrie.class)
                ).withDisabledStats();


        CHEST_MIMIC = wood
                .build("chest.mimic", "chest_mimic", blockID("CHEST_MIMIC"), BlockLogicChestMimic::new);


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

        new AetherBlockDetails().initializeBlockDetails();
    }

    @Override
    public void afterBlockInit() {
        init();
        AetherDimension.init();
        PORTAL_AETHER.getLogic().targetDimension = AetherDimension.AETHER;

    }
}
