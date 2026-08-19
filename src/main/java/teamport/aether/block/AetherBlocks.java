package teamport.aether.block;

import net.minecraft.core.Global;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.block.ItemBlockPainted;
import net.minecraft.core.item.block.ItemBlockSlabPainted;
import net.minecraft.core.item.block.ItemBlockStairsPainted;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import org.jspecify.annotations.NonNull;
import teamport.aether.AetherMod;
import teamport.aether.block.dungeon.*;
import teamport.aether.block.machine.BlockLogicEnchanter;
import teamport.aether.block.machine.BlockLogicFreezer;
import teamport.aether.block.machine.BlockLogicIncubator;
import teamport.aether.block.skyroot.*;
import teamport.aether.block.terrain.*;
import teamport.aether.entity.monster.fireminion.MobFireMinion;
import teamport.aether.entity.monster.sentry.MobSentry;
import teamport.aether.entity.monster.valkyrie.MobValkyrie;
import teamport.aether.item.AetherItems;
import teamport.aether.world.AetherDimension;

import static net.minecraft.core.block.Blocks.register;
import static teamport.aether.AetherConfig.blockID;
import static teamport.aether.AetherMod.MOD_ID;

@SuppressWarnings({"java:S6539", "java:S1104", "java:S1444", "java:S3008", "unchecked", "java:S3878"})
public final class AetherBlocks {
    public static Block<BlockLogicPortalAether> PORTAL_AETHER;

    public static Block<?> GRASS_AETHER;
    public static Block<?> DIRT_AETHER;
    public static Block<?> PATH_DIRT_AETHER;

    public static Block<?> QUICKSOIL;

    public static Block<?> GLASS_QUICKSOIL;
    public static Block<?> TRAPDOOR_GLASS_QUICKSOIL;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_TOP;
    public static Block<BlockLogicDoorGlassQuicksoil> DOOR_GLASS_QUICKSOIL_BOTTOM;

    public static Block<?> TALLGRASS_AETHER;

    public static Block<?> DEADBUSH_AETHER;

    public static Block<BlockLogicFlowerStackable> FLOWER_PURPLE;
    public static Block<BlockLogicFlowerStackable> FLOWER_WHITE;

    public static Block<?> HOLYSTONE;
    public static Block<?> HOLYSTONE_MOSSY;
    public static Block<?> HOLYSTONE_POLISHED;
    public static Block<?> HOLYSTONE_CARVED;
    public static Block<?> SLAB_HOLYSTONE_POLISHED;

    public static Block<?> COBBLE_HOLYSTONE;
    public static Block<?> COBBLE_HOLYSTONE_MOSSY;
    public static Block<?> STAIRS_COBBLE_HOLYSTONE;
    public static Block<?> SLAB_COBBLE_HOLYSTONE;

    public static Block<?> BRICK_HOLYSTONE;
    public static Block<?> STAIRS_BRICK_HOLYSTONE;
    public static Block<?> SLAB_BRICK_HOLYSTONE;

    public static Block<?> PRESSURE_PLATE_HOLYSTONE;
    public static Block<?> PRESSURE_PLATE_COBBLE_HOLYSTONE;

    public static Block<?> BUTTON_HOLYSTONE;

    public static Block<?> STATUE_HOLYSTONE_LOWER;
    public static Block<?> STATUE_HOLYSTONE_UPPER;

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


    public static Block<?> LOG_SKYROOT;
    public static Block<?> LOG_OAK_GOLDEN;

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
    public static Block<BlockLogicPaintedSignSkyroot> SIGN_POST_PLANKS_SKYROOT_PAINTED;
    public static Block<BlockLogicPaintedSignSkyroot> SIGN_WALL_PLANKS_SKYROOT_PAINTED;

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
    public static Block<?> SLAB_BRICK_ZANITE;
    public static Block<?> STAIRS_BRICK_ZANITE;

    public static Block<?> BRICK_GRAVITITE;
    public static Block<?> SLAB_BRICK_GRAVITITE;
    public static Block<?> STAIRS_BRICK_GRAVITITE;

    public static Block<?> CARVED_STONE;
    public static Block<?> SLAB_CARVED_STONE;
    public static Block<?> STAIRS_CARVED_STONE;
    public static Block<?> CARVED_STONE_LIGHT;

    public static Block<?> CARVED_ANGELIC;
    public static Block<?> SLAB_CARVED_ANGELIC;
    public static Block<?> STAIRS_CARVED_ANGELIC;
    public static Block<?> CARVED_ANGELIC_LIGHT;

    public static Block<?> CARVED_HELLFIRE;
    public static Block<?> SLAB_CARVED_HELLFIRE;
    public static Block<?> STAIRS_CARVED_HELLFIRE;
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
    public static Block<?> CARVED_ANGELIC_TRAPPED;
    public static Block<?> CARVED_HELLFIRE_TRAPPED;

    public static Block<?> CARVED_STONE_TRAPPED_LOCKED;
    public static Block<?> CARVED_ANGELIC_TRAPPED_LOCKED;
    public static Block<?> CARVED_HELLFIRE_TRAPPED_LOCKED;

    public static Block<?> LANTERN_FIREFLY_SILVER;
    private static boolean hasInit;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeBlocks();
        }
    }

    public static @NonNull String blockKey(String string) {
        return MOD_ID + ":block/" + string;
    }

    public static void initializeBlocks() {

        PORTAL_AETHER = register("portal.aether", blockKey("portal_aether"), blockID("PORTAL_AETHER"),
            b -> new BlockLogicPortalAether(b, AetherDimension.getAether(), Blocks.GLOWSTONE, Blocks.FLUID_WATER_FLOWING))
            .withSound(BlockSounds.GLASS)
            .withHardness(-1.0F)
            .withLightEmission(15)
            .withOverrideColor(MaterialColor.paintedLightblue)
            .withDisabledStats()
            .withTags(new Tag[]{BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU});


        GRASS_AETHER = register("grass.aether", blockKey("grass_aether"), blockID("GRASS_AETHER"),
            b -> new BlockLogicGrassAether(b, DIRT_AETHER))
            .withSound(BlockSounds.GRASS)
            .withHardness(0.3f)
            .withBlastResistance(0.6f)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, AetherBlockTags.GROWS_AETHER_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS, AetherBlockTags.PASSIVE_MOBS_SPAWN);

        DIRT_AETHER = register("dirt.aether", blockKey("dirt_aether"), blockID("DIRT_AETHER"),
            BlockLogicDirtAether::new)
            .withSound(BlockSounds.GRAVEL)
            .withHardness(0.2f)
            .withBlastResistance(0.2f)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.FIREFLIES_CAN_SPAWN, AetherBlockTags.GROWS_AETHER_TREES, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE, AetherBlockTags.GROWS_AETHER_FLOWERS);

        PATH_DIRT_AETHER = register("path.dirt.aether", blockKey("path_dirt_aether"), blockID("PATH_DIRT_AETHER"),
            BlockLogicPathDirtAether::new)
            .withSound(BlockSounds.GRAVEL)
            .withHardness(0.2f)
            .withBlastResistance(0.2f)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL})
            .withLitInteriorSurface(true);


        HOLYSTONE = register("holystone", blockKey("holystone"), blockID("HOLYSTONE"),
            b -> new BlockLogicStone(b, COBBLE_HOLYSTONE, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.AETHER_TERRAIN_BLOCK, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        HOLYSTONE_MOSSY = register("holystone.mossy", blockKey("holystone_mossy"), blockID("HOLYSTONE_MOSSY"),
            b -> new BlockLogicMoss(b, HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, AetherBlockTags.GROWS_AETHER_FLOWERS);


        HOLYSTONE_POLISHED = register("holystone.polished", blockKey("holystone_polished"), blockID("HOLYSTONE_POLISHED"),
            b -> new BlockLogic(b, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        HOLYSTONE_CARVED = register("holystone.carved", blockKey("holystone_carved"), blockID("HOLYSTONE_CARVED"),
            b -> new BlockLogic(b, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withDisabledStats()
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);

        SLAB_HOLYSTONE_POLISHED = register("slab.holystone.carved", blockKey("slab_holystone_polished"), blockID("SLAB_HOLYSTONE_POLISHED"),
            b -> new BlockLogicSlab(b, HOLYSTONE_CARVED))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withBlastResistance(0.8F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        COBBLE_HOLYSTONE = register("cobble.holystone", blockKey("cobble_holystone"), blockID("COBBLE_HOLYSTONE"),
            b -> new BlockLogicDouble(b, AetherMaterials.HOLYSTONE, () -> Blocks.GRAVEL))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.AETHER_TERRAIN_BLOCK, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        COBBLE_HOLYSTONE_MOSSY = register("cobble.holystone.mossy", blockKey("cobble_holystone_mossy"), blockID("COBBLE_HOLYSTONE_MOSSY"),
            b -> new BlockLogicDouble(b, AetherMaterials.HOLYSTONE, () -> Blocks.GRAVEL))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.GROWS_AETHER_FLOWERS);

        STAIRS_COBBLE_HOLYSTONE = register("stairs.cobble.holystone", blockKey("stairs_cobble_holystone"), blockID("STAIRS_COBBLE_HOLYSTONE"),
            b -> new BlockLogicStairs(b, COBBLE_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        SLAB_COBBLE_HOLYSTONE = register("slab.cobble.holystone", blockKey("slab_cobble_holystone"), blockID("SLAB_COBBLE_HOLYSTONE"),
            b -> new BlockLogicSlab(b, COBBLE_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        BRICK_HOLYSTONE = register("brick.holystone", blockKey("brick_holystone"), blockID("BRICK_HOLYSTONE"),
            b -> new BlockLogic(b, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        STAIRS_BRICK_HOLYSTONE = register("stairs.brick.holystone", blockKey("stairs_brick_holystone"), blockID("STAIRS_BRICK_HOLYSTONE"),
            b -> new BlockLogicStairs(b, BRICK_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        SLAB_BRICK_HOLYSTONE = register("slab.brick.holystone", blockKey("slab_brick_holystone"), blockID("SLAB_BRICK_HOLYSTONE"),
            b -> new BlockLogicSlab(b, BRICK_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        PRESSURE_PLATE_HOLYSTONE = register("pressureplate.holystone", blockKey("pressure_plate_holystone"), blockID("PRESSURE_PLATE_HOLYSTONE"),
            b -> new BlockLogicPressurePlate<>(b, Mob.class, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PREVENT_MOB_SPAWNS);

        PRESSURE_PLATE_COBBLE_HOLYSTONE = register("pressureplate.cobble.holystone", blockKey("pressure_plate_cobble_holystone"), blockID("PRESSURE_PLATE_COBBLE_HOLYSTONE"),
            b -> new BlockLogicPressurePlate<>(b, Player.class, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PREVENT_MOB_SPAWNS);


        BUTTON_HOLYSTONE = register("button.holystone", blockKey("button_holystone"), blockID("BUTTON_HOLYSTONE"),
            BlockLogicButton::new)
            .withSound(BlockSounds.STONE)
            .withHardness(0.5F)
            .withTags(BlockTags.BROKEN_BY_FLUIDS, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PREVENT_MOB_SPAWNS);


        STATUE_HOLYSTONE_LOWER = register("statue.holystone.lower", blockKey("statue_holystone_lower"), blockID("STATUE_HOLYSTONE_LOWER"),
            block -> new BlockLogicStatue(block, AetherMaterials.HOLYSTONE, true, () -> AetherItems.STATUE_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .setStatParent(() -> AetherItems.STATUE_HOLYSTONE)
            .withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

        STATUE_HOLYSTONE_UPPER = register("statue.holystone.upper", blockKey("statue_holystone_upper"), blockID("STATUE_HOLYSTONE_UPPER"),
            block -> new BlockLogicStatue(block, AetherMaterials.HOLYSTONE, false, () -> AetherItems.STATUE_HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .setStatParent(() -> AetherItems.STATUE_HOLYSTONE)
            .withTags(BlockTags.MINEABLE_BY_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);


        ICESTONE = register("icestone", blockKey("icestone"), blockID("ICESTONE"),
            BlockLogicIceStone::new)
            .withSound(BlockSounds.GLASS)
            .withHardness(3.0F)
            .setTicking(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.SKATEABLE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE);


        QUICKSOIL = register("quicksoil", blockKey("quicksoil"), blockID("QUICKSOIL"),
            BlockLogicQuicksoil::new)
            .withSound(new BlockSound("step.sand", "step.gravel", 1.0F, 1.0F))
            .withHardness(0.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVES_CUT_THROUGH, BlockTags.CAVE_GEN_REPLACES_SURFACE);

        GLASS_QUICKSOIL = register("glass.quicksoil", blockKey("glass_quicksoil"), blockID("GLASS_QUICKSOIL"),
            BlockLogicGlassQuicksoil::new)
            .withSound(BlockSounds.GLASS)
            .withHardness(0.3F)
            .withLightEmission(7)
            .withLightBlock(0)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.EXTENDS_MOTION_SENSOR_RANGE);

        DOOR_GLASS_QUICKSOIL_BOTTOM = register("door.glass.quicksoil.bottom", blockKey("door_glass_quicksoil_bottom"), blockID("DOOR_GLASS_QUICKSOIL_BOTTOM"),
            block -> new BlockLogicDoorGlassQuicksoil(block, Materials.GLASS, false, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM))
            .withSound(BlockSounds.GLASS)
            .withHardness(0.3F)
            .withLightEmission(7)
            .withLightBlock(0)
            .setStatParent(() -> AetherItems.DOOR_GLASS_AMBROSIUM)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU});

        DOOR_GLASS_QUICKSOIL_TOP = register("door.glass.quicksoil.top", blockKey("door_glass_quicksoil_top"), blockID("DOOR_GLASS_QUICKSOIL_TOP"),
            block -> new BlockLogicDoorGlassQuicksoil(block, Materials.GLASS, true, false, () -> AetherItems.DOOR_GLASS_AMBROSIUM))
            .withSound(BlockSounds.GLASS)
            .withHardness(0.3F)
            .withLightEmission(7)
            .withLightBlock(0)
            .setStatParent(() -> AetherItems.DOOR_GLASS_AMBROSIUM)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU});

        TRAPDOOR_GLASS_QUICKSOIL = register("trapdoor.glass.quicksoil", blockKey("trapdoor_glass_quicksoil"), blockID("TRAPDOOR_GLASS_QUICKSOIL"),
            b -> new BlockLogicTrapDoorGlassQuicksoil(b, Materials.GLASS))
            .withSound(BlockSounds.GLASS)
            .withHardness(0.3F)
            .withLightEmission(7)
            .withLightBlock(0)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        FLOWER_PURPLE = register("flower.aether.purple", blockKey("flower_aether_purple"), blockID("FLOWER_AETHER_PURPLE"),
            b -> new BlockLogicFlowerAether(b)
                .setKilledByWeather()
                .setBonemealable())
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withOverrideColor(MaterialColor.paintedPurple)
            .withTags(new Tag[]{BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.SHEEPS_FAVOURITE_BLOCK, AetherBlockTags.PLANTABLE_IN_AETHER_JAR});

        FLOWER_WHITE = register("flower.aether.white", blockKey("flower_aether_white"), blockID("FLOWER_AETHER_WHITE"),
            b -> new BlockLogicFlowerAether(b)
                .setKilledByWeather()
                .setBonemealable())
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withOverrideColor(MaterialColor.paintedWhite)
            .withTags(new Tag[]{BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, BlockTags.SHEARS_DO_SILK_TOUCH, BlockTags.SHEEPS_FAVOURITE_BLOCK, AetherBlockTags.PLANTABLE_IN_AETHER_JAR});


        TALLGRASS_AETHER = register("tallgrass.aether", blockKey("tallgrass_aether"), blockID("TALLGRASS_AETHER"),
            b -> new BlockLogicTallGrassAether(b)
                .setKilledByWeather())
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withTags(BlockTags.MINEABLE_BY_SHEARS, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEEPS_FAVOURITE_BLOCK, BlockTags.SHEARS_DO_SILK_TOUCH);

        DEADBUSH_AETHER = register("deadbush.aether", blockKey("deadbush_aether"), blockID("DEADBUSH_AETHER"),
            b -> new BlockLogicDeadBushAether(b)
                .setKilledByWeather())
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH, AetherBlockTags.PLANTABLE_IN_AETHER_JAR);


        PLANKS_SKYROOT = register("planks.skyroot", blockKey("planks_skyroot"), blockID("PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableBlock(b, Materials.WOOD, () -> PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT);

        PLANKS_SKYROOT_PAINTED = register("planks.skyroot.painted", blockKey("planks_skyroot_painted"), blockID("PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedBlock(b, () -> PLANKS_SKYROOT))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, false))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT});


        SLAB_PLANKS_SKYROOT = register("slab.planks.skyroot", blockKey("slab_planks_skyroot"), blockID("SLAB_PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableSlab(b, PLANKS_SKYROOT, AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withLitInteriorSurface(true)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});

        SLAB_PLANKS_SKYROOT_PAINTED = register("slab.planks.skyroot.painted", blockKey("slab_planks_skyroot_painted"), blockID("SLAB_PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedSlab(b, PLANKS_SKYROOT, AetherBlocks.SLAB_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withLitInteriorSurface(true)
            .setBlockItem(ItemBlockSlabPainted::new)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});


        STAIRS_PLANKS_SKYROOT = register("stairs.planks.skyroot", blockKey("stairs_planks_skyroot"), blockID("STAIRS_PLANKS_SKYROOT"),
            block -> new BlockLogicPaintableStairs(block, PLANKS_SKYROOT, AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withLitInteriorSurface(true)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});

        STAIRS_PLANKS_SKYROOT_PAINTED = register("stairs.planks.skyroot.painted", blockKey("stairs_planks_skyroot_painted"), blockID("STAIRS_PLANKS_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedStairs(block, PLANKS_SKYROOT, AetherBlocks.STAIRS_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withLitInteriorSurface(true)
            .setBlockItem(ItemBlockStairsPainted::new)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});


        FENCE_PLANKS_SKYROOT = register("fence.planks.skyroot", blockKey("fence_planks_skyroot"), blockID("FENCE_PLANKS_SKYROOT"),
            block -> new BlockLogicPaintableFence(block, AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT, BlockTags.CAN_HANG_OFF});

        FENCE_PLANKS_SKYROOT_PAINTED = register("fence.planks.skyroot.painted", blockKey("fence_planks_skyroot_painted"), blockID("FENCE_PLANKS_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedFence(block, AetherBlocks.FENCE_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, false))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT, BlockTags.CAN_HANG_OFF});


        FENCEGATE_PLANKS_SKYROOT = register("fencegate.planks.skyroot", blockKey("fencegate_planks_skyroot"), blockID("FENCEGATE_PLANKS_SKYROOT"),
            block -> new BlockLogicPaintableFenceGate(block, AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT});

        FENCEGATE_PLANKS_SKYROOT_PAINTED = register("fencegate.planks.skyroot.painted", blockKey("fencegate_planks_skyroot_painted"), blockID("FENCEGATE_PLANKS_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedFenceGate(block, AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT});


        DOOR_PLANKS_SKYROOT_BOTTOM = register("door.planks.skyroot.bottom", blockKey("door_planks_skyroot_bottom"), blockID("DOOR_PLANKS_SKYROOT_BOTTOM"),
            block -> new BlockLogicPaintableDoor(block, Materials.WOOD, false, false, AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, () -> AetherItems.DOOR_SKYROOT))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .setStatParent(() -> AetherItems.DOOR_SKYROOT)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});

        DOOR_PLANKS_SKYROOT_TOP = register("door.planks.skyroot.top", blockKey("door_planks_skyroot_top"), blockID("DOOR_PLANKS_SKYROOT_TOP"),
            block -> new BlockLogicPaintableDoor(block, Materials.WOOD, true, false, AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, () -> AetherItems.DOOR_SKYROOT))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .setStatParent(() -> AetherItems.DOOR_SKYROOT)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});


        DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM = register("door.planks.skyroot.bottom.painted", blockKey("door_planks_skyroot_bottom_painted"), blockID("DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM"),
            block -> new BlockLogicPaintedDoor(block, Materials.WOOD, false, AetherBlocks.DOOR_PLANKS_SKYROOT_TOP.id(), AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM.id(), () -> AetherItems.DOOR_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .setStatParent(() -> AetherItems.DOOR_SKYROOT_PAINTED)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});

        DOOR_PLANKS_SKYROOT_PAINTED_TOP = register("door.planks.skyroot.top.painted", blockKey("door_planks_skyroot_top_painted"), blockID("DOOR_PLANKS_SKYROOT_PAINTED_TOP"),
            block -> new BlockLogicPaintedDoor(block, Materials.WOOD, true, AetherBlocks.DOOR_PLANKS_SKYROOT_TOP.id(), AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM.id(), () -> AetherItems.DOOR_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .setStatParent(() -> AetherItems.DOOR_SKYROOT_PAINTED)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});


        SIGN_POST_PLANKS_SKYROOT = register("sign.post.planks.skyroot", blockKey("sign_post_planks_skyroot"), blockID("SIGN_POST_PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableSignSkyroot(b, true))
            .withSound(BlockSounds.WOOD)
            .withHardness(1.0F)
            .setStatParent(() -> AetherItems.SIGN_SKYROOT)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU);

        SIGN_WALL_PLANKS_SKYROOT = register("sign.wall.planks.skyroot", blockKey("sign_wall_planks_skyroot"), blockID("SIGN_WALL_PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableSignSkyroot(b, false))
            .withSound(BlockSounds.WOOD)
            .withHardness(1.0F)
            .setStatParent(() -> AetherItems.SIGN_SKYROOT)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU);

        SIGN_POST_PLANKS_SKYROOT_PAINTED = register("sign.post.planks.skyroot.painted", blockKey("sign_post_planks_skyroot_painted"), blockID("SIGN_POST_PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedSignSkyroot(b, true))
            .withSound(BlockSounds.WOOD)
            .withHardness(1.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT_PAINTED)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});

        SIGN_WALL_PLANKS_SKYROOT_PAINTED = register("sign.wall.planks.skyroot.painted", blockKey("sign_wall_planks_skyroot_painted"), blockID("SIGN_WALL_PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedSignSkyroot(b, false))
            .withSound(BlockSounds.WOOD)
            .withHardness(1.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .setStatParent(() -> AetherItems.SIGN_SKYROOT_PAINTED)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.NOT_IN_CREATIVE_MENU});

        TRAPDOOR_PLANKS_SKYROOT = register("trapdoor.planks.skyroot", blockKey("trapdoor_planks_skyroot"), blockID("TRAPDOOR_PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableTrapDoor(b, Materials.WOOD, TRAPDOOR_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});

        TRAPDOOR_PLANKS_SKYROOT_PAINTED = register("trapdoor.planks.skyroot.painted", blockKey("trapdoor_planks_skyroot_painted"), blockID("TRAPDOOR_PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedTrapDoor(b, Materials.WOOD, TRAPDOOR_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(3.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE});


        CHEST_PLANKS_SKYROOT = register("chest.planks.skyroot", blockKey("chest_planks_skyroot"), blockID("CHEST_PLANKS_SKYROOT"),
            b -> new BlockLogicPaintableChest(b, Materials.WOOD, CHEST_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART});

        CHEST_PLANKS_SKYROOT_PAINTED = register("chest.planks.skyroot.painted", blockKey("chest_planks_skyroot_painted"), blockID("CHEST_PLANKS_SKYROOT_PAINTED"),
            b -> new BlockLogicPaintedChest(b, Materials.WOOD, CHEST_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART});


        BUTTON_PLANKS_SKYROOT = register("button.planks.skyroot", blockKey("button_planks_skyroot"), blockID("BUTTON_PLANKS_SKYROOT"),
            block -> new BlockLogicPaintableButton(block, BUTTON_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(0.5F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PREVENT_MOB_SPAWNS});

        BUTTON_PLANKS_SKYROOT_PAINTED = register("button.planks.skyroot.painted", blockKey("button_planks_skyroot_painted"), blockID("BUTTON_PLANKS_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedButton(block, BUTTON_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(0.5F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.BROKEN_BY_FLUIDS, BlockTags.PREVENT_MOB_SPAWNS});


        PRESSURE_PLATE_PLANKS_SKYROOT = register("pressure.plate.planks.skyroot", blockKey("pressure_plate_planks_skyroot"), blockID("PRESSURE_PLATE_PLANKS_SKYROOT"),
            block -> new BlockLogicPaintablePressurePlate<>(block, Entity.class, Materials.WOOD, PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(0.5F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.PREVENT_MOB_SPAWNS});

        PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED = register("pressure.plate.planks.skyroot.painted", blockKey("pressure_plate_planks_skyroot_painted"), blockID("PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedPressurePlate<>(block, Entity.class, Materials.WOOD, PRESSURE_PLATE_PLANKS_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(0.5F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.PREVENT_MOB_SPAWNS});


        LOG_SKYROOT = register("log.skyroot", blockKey("log_skyroot"), blockID("LOG_SKYROOT"),
            BlockLogicLogAether::new)
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withTags(BlockTags.FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_AXE);

        LOG_OAK_GOLDEN = register("log.oak.golden", blockKey("log_oak_golden"), blockID("LOG_OAK_GOLDEN"),
            BlockLogicGoldenLogAether::new)
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withTags(BlockTags.FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_AXE);


        LEAVES_SKYROOT = register("leaves.skyroot", blockKey("leaves_skyroot"), blockID("LEAVES_SKYROOT"),
            BlockLogicLeavesSkyroot::new)
            .withSound(BlockSounds.GRASS)
            .withHardness(0.2F)
            .withLightBlock(1)
            .withTags(BlockTags.MINEABLE_BY_AXE, AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, AetherBlockTags.MINEABLE_BY_AETHER_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);

        LEAVES_OAK_GOLDEN = register("leaves.oak.golden", blockKey("leaves_oak_golden"), blockID("LEAVES_OAK_GOLDEN"),
            BlockLogicLeavesOakGolden::new)
            .withSound(BlockSounds.GRASS)
            .withHardness(0.2F)
            .withLightBlock(1)
            .withTags(BlockTags.MINEABLE_BY_AXE, AetherBlockTags.MINEABLE_BY_AETHER_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, AetherBlockTags.MINEABLE_BY_AETHER_SWORD, BlockTags.MINEABLE_BY_SHEARS, BlockTags.SHEARS_DO_SILK_TOUCH);


        SAPLING_SKYROOT = register("sapling.skyroot", blockKey("sapling_skyroot"), blockID("SAPLING_SKYROOT"),
            BlockLogicSaplingSkyroot::new)
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.PLANTABLE_IN_AETHER_JAR);

        SAPLING_OAK_GOLDEN = register("sapling.oak.golden", blockKey("sapling_oak_golden"), blockID("SAPLING_OAK_GOLDEN"),
            BlockLogicSaplingOakGolden::new)
            .withSound(BlockSounds.GRASS)
            .withHardness(0.0F)
            .withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLANTABLE_IN_JAR, AetherBlockTags.PLANTABLE_IN_AETHER_JAR);


        AERCLOUD_WHITE = register("aercloud.white", blockKey("aercloud_white"), blockID("AERCLOUD_WHITE"),
            BlockLogicCloudBase::new)
            .withSound(BlockSounds.CLOTH)
            .withLightBlock(0)
            .withHardness(0.2f)
            .withBlastResistance(0.2f)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CAVES_CUT_THROUGH, BlockTags.PREVENT_MOB_SPAWNS);

        AERCLOUD_BLUE = register("aercloud.blue", blockKey("aercloud_blue"), blockID("AERCLOUD_BLUE"),
            BlockLogicCloudBlue::new)
            .withSound(BlockSounds.CLOTH)
            .withLightBlock(0)
            .withHardness(0.2f)
            .withBlastResistance(0.2f)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CAVES_CUT_THROUGH, BlockTags.PREVENT_MOB_SPAWNS);

        AERCLOUD_GOLD = register("aercloud.gold", blockKey("aercloud_gold"), blockID("AERCLOUD_GOLD"),
            BlockLogicCloudBase::new)
            .withSound(BlockSounds.CLOTH)
            .withLightBlock(0)
            .withHardness(0.2f)
            .withBlastResistance(0.2f)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_SHOVEL, BlockTags.CAVE_GEN_REPLACES_SURFACE, BlockTags.CAVES_CUT_THROUGH, BlockTags.PREVENT_MOB_SPAWNS);


        AEROGEL = register("aerogel", blockKey("aerogel"), blockID("AEROGEL"),
            b -> new BlockLogicTransparent(b, Materials.STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.0f)
            .withBlastResistance(2000.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);


        TORCH_AMBROSIUM = register("torch.ambrosium", blockKey("torch_ambrosium"), blockID("TORCH_AMBROSIUM"),
            BlockLogicTorchAmbrosium::new)
            .withSound(BlockSounds.WOOD)
            .withHardness(0.0F)
            .withLightEmission(0.9375F)
            .withTags(BlockTags.BROKEN_BY_FLUIDS);


        ENCHANTER_IDLE = register("enchanter.idle", blockKey("enchanter_idle"), blockID("ENCHANTER_IDLE"),
            b -> new BlockLogicEnchanter(b, false))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT);

        ENCHANTER_ACTIVE = register("enchanter.active", blockKey("enchanter_active"), blockID("ENCHANTER_ACTIVE"),
            b -> new BlockLogicEnchanter(b, true))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withLightEmission(13)
            .setStatParent(() -> ENCHANTER_IDLE)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);

        FREEZER_IDLE = register("freezer.idle", blockKey("freezer_idle"), blockID("FREEZER_IDLE"),
            b -> new BlockLogicFreezer(b, false))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT);

        FREEZER_ACTIVE = register("freezer.active", blockKey("freezer_active"), blockID("FREEZER_ACTIVE"),
            b -> new BlockLogicFreezer(b, true))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withLightEmission(13)
            .setStatParent(() -> FREEZER_IDLE)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);

        INCUBATOR_IDLE = register("incubator.idle", blockKey("incubator_idle"), blockID("INCUBATOR_IDLE"),
            b -> new BlockLogicIncubator(b, false))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT);

        INCUBATOR_ACTIVE = register("incubator.active", blockKey("incubator_active"), blockID("INCUBATOR_ACTIVE"),
            b -> new BlockLogicIncubator(b, true))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.5F)
            .withBlastResistance(10.0F)
            .withLightEmission(13)
            .setStatParent(() -> INCUBATOR_IDLE)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_AXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.FENCES_CONNECT, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);


        ORE_AMBROSIUM_HOLYSTONE = register("ore.ambrosium.holystone", blockKey("ore_ambrosium_holystone"), blockID("ORE_AMBROSIUM_HOLYSTONE"),
            b -> new BlockLogicOreAmbrosium(b, COBBLE_HOLYSTONE, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withBlastResistance(5.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        ORE_ZANITE_HOLYSTONE = register("ore.zanite.holystone", blockKey("ore_zanite_holystone"), blockID("ORE_ZANITE_HOLYSTONE"),
            b -> new BlockLogicOreZanite(b, COBBLE_HOLYSTONE, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withBlastResistance(5.0F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        ORE_GRAVITITE_HOLYSTONE = register("ore.gravitite.holystone", blockKey("ore_gravitite_holystone"), blockID("ORE_GRAVITITE_HOLYSTONE"),
            b -> new BlockLogicOreGravitite(b, COBBLE_HOLYSTONE, AetherMaterials.HOLYSTONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withBlastResistance(5.0F)
            .setTicking(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        BLOCK_AMBER = register("block.amber", blockKey("block_amber"), blockID("BLOCK_AMBER"),
            b -> new BlockLogicTransparent(b, Materials.STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BLOCK_AMBROSIUM = register("block.ambrosium", blockKey("block_ambrosium"), blockID("BLOCK_AMBROSIUM"),
            b -> new BlockLogic(b, Materials.STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BLOCK_ZANITE = register("block.zanite", blockKey("block_zanite"), blockID("BLOCK_ZANITE"),
            b -> new BlockLogic(b, AetherMaterials.ZANITE))
            .withSound(BlockSounds.CRYSTAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BLOCK_GRAVITITE = register("block.gravitite", blockKey("block_gravitite"), blockID("BLOCK_GRAVITITE"),
            b -> new BlockLogicBlockGravitite(b, AetherMaterials.GRAVITITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BRICK_ZANITE = register("brick.zanite", blockKey("brick_zanite"), blockID("BRICK_ZANITE"),
            b -> new BlockLogic(b, AetherMaterials.ZANITE))
            .withSound(BlockSounds.CRYSTAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        SLAB_BRICK_ZANITE = register("slab.brick.zanite", blockKey("slab_brick_zanite"), blockID("SLAB_BRICK_ZANITE"),
            b -> new BlockLogicSlab(b, BRICK_ZANITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withLitInteriorSurface(true)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        STAIRS_BRICK_ZANITE = register("stairs.brick.zanite", blockKey("stairs_brick_zanite"), blockID("STAIRS_BRICK_ZANITE"),
            b -> new BlockLogicStairs(b, BRICK_ZANITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withLitInteriorSurface(true)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        BRICK_GRAVITITE = register("brick.gravitite", blockKey("brick_gravitite"), blockID("BRICK_GRAVITITE"),
            b -> new BlockLogic(b, AetherMaterials.GRAVITITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        SLAB_BRICK_GRAVITITE = register("slab.brick.gravitite", blockKey("slab_brick_gravitite"), blockID("SLAB_BRICK_GRAVITITE"),
            b -> new BlockLogicSlab(b, BRICK_GRAVITITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withLitInteriorSurface(true)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);

        STAIRS_BRICK_GRAVITITE = register("stairs.brick.gravitite", blockKey("stairs_brick_gravitite"), blockID("STAIRS_BRICK_GRAVITITE"),
            b -> new BlockLogicStairs(b, BRICK_GRAVITITE))
            .withSound(BlockSounds.METAL)
            .withHardness(3.0F)
            .withBlastResistance(10.0F)
            .withLitInteriorSurface(true)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE);


        CARVED_STONE = register("carved.stone", blockKey("carved_stone"), blockID("CARVED_STONE"),
            b -> new BlockLogicDungeon(b, AetherMaterials.SENTRY))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        SLAB_CARVED_STONE = register("slab.carved.stone", blockKey("slab_carved_stone"), blockID("SLAB_CARVED_STONE"),
            b -> new BlockLogicSlab(b, CARVED_STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        STAIRS_CARVED_STONE = register("stairs.carved.stone", blockKey("stairs_carved_stone"), blockID("STAIRS_CARVED_STONE"),
            b -> new BlockLogicStairs(b, CARVED_STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        CARVED_STONE_LIGHT = register("carved.stone.light", blockKey("carved_stone_light"), blockID("CARVED_STONE_LIGHT"),
            b -> new BlockLogic(b, AetherMaterials.SENTRY))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLightEmission(10)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);


        CARVED_ANGELIC = register("carved.angelic", blockKey("carved_angelic"), blockID("CARVED_ANGELIC"),
            b -> new BlockLogicDungeon(b, AetherMaterials.ANGELIC))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        SLAB_CARVED_ANGELIC = register("slab.carved.angelic", blockKey("slab_carved_angelic"), blockID("SLAB_CARVED_ANGELIC"),
            b -> new BlockLogicSlab(b, CARVED_ANGELIC))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        STAIRS_CARVED_ANGELIC = register("stairs.carved.angelic", blockKey("stairs_carved_angelic"), blockID("STAIRS_CARVED_ANGELIC"),
            b -> new BlockLogicStairs(b, CARVED_ANGELIC))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        CARVED_ANGELIC_LIGHT = register("carved.angelic.light", blockKey("carved_angelic_light"), blockID("CARVED_ANGELIC_LIGHT"),
            b -> new BlockLogicDungeon(b, AetherMaterials.ANGELIC))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLightEmission(10)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);


        CARVED_HELLFIRE = register("carved.hellfire", blockKey("carved_hellfire"), blockID("CARVED_HELLFIRE"),
            b -> new BlockLogicDungeon(b, AetherMaterials.HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN);

        SLAB_CARVED_HELLFIRE = register("slab.carved.hellfire", blockKey("slab_carved_hellfire"), blockID("SLAB_CARVED_HELLFIRE"),
            b -> new BlockLogicSlab(b, CARVED_HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN);

        STAIRS_CARVED_HELLFIRE = register("stairs.carved.hellfire", blockKey("stairs_carved_hellfire"), blockID("STAIRS_CARVED_HELLFIRE"),
            b -> new BlockLogicStairs(b, CARVED_HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLitInteriorSurface(true)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN);

        CARVED_HELLFIRE_LIGHT = register("carved.hellfire.light", blockKey("carved_hellfire_light"), blockID("CARVED_HELLFIRE_LIGHT"),
            b -> new BlockLogicDungeon(b, AetherMaterials.HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withLightEmission(10)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN);


        PILLAR = register("pillar", blockKey("pillar"), blockID("PILLAR"),
            b -> new BlockLogicAxisAligned(b, Materials.STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);

        PILLAR_CAPSTONE = register("pillar.capstone", blockKey("pillar_capstone"), blockID("PILLAR_CAPSTONE"),
            b -> new BlockLogicAxisAligned(b, Materials.STONE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT);


        DOOR_DUNGEON_BRONZE = register("door.dungeon.bronze", blockKey("door_dungeon_bronze"), blockID("DOOR_DUNGEON_BRONZE"),
            b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_BRONZE))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(4)
            .withLitInteriorSurface(true)
            .setStatParent(() -> AetherItems.DOOR_DUNGEON_BRONZE)
            .withTags(BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.CHAINLINK_FENCES_CONNECT);

        DOOR_DUNGEON_SILVER = register("door.dungeon.silver", blockKey("door_dungeon_silver"), blockID("DOOR_DUNGEON_SILVER"),
            b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_SILVER))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(4)
            .withLitInteriorSurface(true)
            .setStatParent(() -> AetherItems.DOOR_DUNGEON_SILVER)
            .withTags(BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.CHAINLINK_FENCES_CONNECT);

        DOOR_DUNGEON_GOLD = register("door.dungeon.gold", blockKey("door_dungeon_gold"), blockID("DOOR_DUNGEON_GOLD"),
            b -> new BlockLogicDungeonDoor(b, () -> AetherItems.DOOR_DUNGEON_GOLD))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(4)
            .withLitInteriorSurface(true)
            .setStatParent(() -> AetherItems.DOOR_DUNGEON_GOLD)
            .withTags(BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.INFINITE_BURN);


        CHEST_DUNGEON_BRONZE = register("chest.dungeon.bronze", blockKey("chest_dungeon_bronze"), blockID("CHEST_DUNGEON_BRONZE"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), false, CHEST_DUNGEON_BRONZE))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);

        CHEST_DUNGEON_BRONZE_LOCKED = register("chest.dungeon.bronze.locked", blockKey("chest_dungeon_bronze_locked"), blockID("CHEST_DUNGEON_BRONZE_LOCKED"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_BRONZE.getDefaultStack(), true, CHEST_DUNGEON_BRONZE))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CHEST_DUNGEON_BRONZE)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);


        CHEST_DUNGEON_SILVER = register("chest.dungeon.silver", blockKey("chest_dungeon_silver"), blockID("CHEST_DUNGEON_SILVER"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), false, CHEST_DUNGEON_SILVER))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);

        CHEST_DUNGEON_SILVER_LOCKED = register("chest.dungeon.silver.locked", blockKey("chest_dungeon_silver_locked"), blockID("CHEST_DUNGEON_SILVER_LOCKED"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_SILVER.getDefaultStack(), true, CHEST_DUNGEON_SILVER))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CHEST_DUNGEON_SILVER)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART);


        CHEST_DUNGEON_GOLD = register("chest.dungeon.gold", blockKey("chest_dungeon_gold"), blockID("CHEST_DUNGEON_GOLD"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_GOLD.getDefaultStack(), false, CHEST_DUNGEON_GOLD))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, BlockTags.INFINITE_BURN);

        CHEST_DUNGEON_GOLD_LOCKED = register("chest.dungeon.gold.locked", blockKey("chest_dungeon_gold_locked"), blockID("CHEST_DUNGEON_GOLD_LOCKED"),
            b -> new BlockLogicChestLocked(b, AetherItems.KEY_GOLD.getDefaultStack(), true, CHEST_DUNGEON_GOLD))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CHEST_DUNGEON_GOLD)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, BlockTags.INFINITE_BURN);


        CARVED_STONE_LOCKED = register("carved.stone.locked", blockKey("carved_stone_locked"), blockID("CARVED_STONE_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.SENTRY, CARVED_STONE))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_STONE)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);

        CARVED_STONE_LIGHT_LOCKED = register("carved.stone.light.locked", blockKey("carved_stone_light_locked"), blockID("CARVED_STONE_LIGHT_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.SENTRY, CARVED_STONE_LIGHT))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(7)
            .setStatParent(() -> CARVED_STONE_LIGHT)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);


        CARVED_ANGELIC_LOCKED = register("carved.angelic.locked", blockKey("carved_angelic_locked"), blockID("CARVED_ANGELIC_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.ANGELIC, CARVED_ANGELIC))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_ANGELIC)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);

        CARVED_ANGELIC_LIGHT_LOCKED = register("carved.angelic.light.locked", blockKey("carved_angelic_light_locked"), blockID("CARVED_ANGELIC_LIGHT_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.ANGELIC, CARVED_ANGELIC_LIGHT))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(7)
            .setStatParent(() -> CARVED_ANGELIC_LIGHT)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);


        CARVED_HELLFIRE_LOCKED = register("carved.hellfire.locked", blockKey("carved_hellfire_locked"), blockID("CARVED_HELLFIRE_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.HELLFIRE, CARVED_HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_HELLFIRE)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.INFINITE_BURN);

        CARVED_HELLFIRE_LIGHT_LOCKED = register("carved.hellfire.light.locked", blockKey("carved_hellfire_light_locked"), blockID("CARVED_HELLFIRE_LIGHT_LOCKED"),
            b -> new BlockLogicLocked(b, AetherMaterials.HELLFIRE, CARVED_HELLFIRE_LIGHT))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .withLightEmission(7)
            .setStatParent(() -> CARVED_HELLFIRE_LIGHT)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.INFINITE_BURN);


        CARVED_STONE_TRAPPED = register("carved.stone.trapped", blockKey("carved_stone_trapped"), blockID("CARVED_STONE_TRAPPED"),
            b -> new BlockLogicTrapped(b, CARVED_STONE, CARVED_STONE, MobSentry.class, 30 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .setStatParent(() -> CARVED_STONE)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);

        CARVED_STONE_TRAPPED_LOCKED = register("carved.stone.trapped.locked", blockKey("carved_stone_trapped_locked"), blockID("CARVED_STONE_TRAPPED_LOCKED"),
            b -> new BlockLogicTrapped(b, CARVED_STONE, CARVED_STONE_TRAPPED, MobSentry.class, 30 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_STONE)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);


        CARVED_ANGELIC_TRAPPED = register("carved.angelic.trapped", blockKey("carved_angelic_trapped"), blockID("CARVED_ANGELIC_TRAPPED"),
            b -> new BlockLogicTrapped(b, CARVED_ANGELIC, CARVED_ANGELIC, MobValkyrie.class, 40 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .setStatParent(() -> CARVED_ANGELIC)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU);

        CARVED_ANGELIC_TRAPPED_LOCKED = register("carved.angelic.trapped.locked", blockKey("carved_angelic_trapped_locked"), blockID("CARVED_ANGELIC_TRAPPED_LOCKED"),
            b -> new BlockLogicTrapped(b, CARVED_ANGELIC, CARVED_ANGELIC_TRAPPED, MobValkyrie.class, 40 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_ANGELIC)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU);


        CARVED_HELLFIRE_TRAPPED = register("carved.hellfire.trapped", blockKey("carved_hellfire_trapped"), blockID("CARVED_HELLFIRE_TRAPPED"),
            b -> new BlockLogicTrapped(b, CARVED_HELLFIRE, CARVED_HELLFIRE, MobFireMinion.class, 30 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withHardness(1.5F)
            .setStatParent(() -> CARVED_HELLFIRE)
            .withTags(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.CHAINLINK_FENCES_CONNECT, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.INFINITE_BURN);

        CARVED_HELLFIRE_TRAPPED_LOCKED = register("carved.hellfire.trapped.locked", blockKey("carved_hellfire_trapped_locked"), blockID("CARVED_HELLFIRE_TRAPPED_LOCKED"),
            b -> new BlockLogicTrapped(b, CARVED_HELLFIRE, CARVED_HELLFIRE_TRAPPED, MobFireMinion.class, 30 * Global.TICKS_PER_SECOND))
            .withSound(BlockSounds.STONE)
            .withSetUnbreakable()
            .withBlastResistance(6000000.0F)
            .setStatParent(() -> CARVED_HELLFIRE)
            .withTags(BlockTags.CHAINLINK_FENCES_CONNECT, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.PISTON_CRUSHING, BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.INFINITE_BURN);


        CHEST_MIMIC_OAK = register("chest.mimic.oak", blockKey("chest_mimic_oak"), blockID("CHEST_MIMIC_OAK"),
            block -> new BlockLogicPaintableChestMimic(block, Materials.WOOD, AetherBlocks.CHEST_MIMIC_OAK_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, BlockTags.MINEABLE_BY_AXE});

        CHEST_MIMIC_OAK_PAINTED = register("chest.mimic.oak.painted", blockKey("chest_mimic_oak_painted"), blockID("CHEST_MIMIC_OAK_PAINTED"),
            block -> new BlockLogicPaintedChestMimic(block, Materials.WOOD, AetherBlocks.CHEST_MIMIC_OAK.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, BlockTags.MINEABLE_BY_AXE});

        CHEST_MIMIC_SKYROOT = register("chest.mimic.skyroot", blockKey("chest_mimic_skyroot"), blockID("CHEST_MIMIC_SKYROOT"),
            block -> new BlockLogicPaintableChestMimic(block, Materials.WOOD, AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, AetherBlockTags.MINEABLE_BY_AETHER_AXE});

        CHEST_MIMIC_SKYROOT_PAINTED = register("chest.mimic.skyroot.painted", blockKey("chest_mimic_skyroot_painted"), blockID("CHEST_MIMIC_SKYROOT_PAINTED"),
            block -> new BlockLogicPaintedChestMimic(block, Materials.WOOD, AetherBlocks.CHEST_MIMIC_SKYROOT.id()))
            .withSound(BlockSounds.WOOD)
            .withHardness(2.0F)
            .withBlastResistance(5.0F)
            .setBlockItem(b -> new ItemBlockPainted<>(b, true))
            .withTags(new Tag[]{BlockTags.FENCES_CONNECT, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART, AetherBlockTags.MINEABLE_BY_AETHER_AXE});

        CHEST_MIMIC_BRONZE = register("chest.mimic.bronze", blockKey("chest_mimic_bronze"), blockID("CHEST_MIMIC_BRONZE"),
            block -> new BlockLogicChestMimic(block, AetherMaterials.SENTRY))
            .withSound(BlockSounds.STONE)
            .withHardness(4.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART});

        CHEST_MIMIC_SILVER = register("chest.mimic.silver", blockKey("chest_mimic_silver"), blockID("CHEST_MIMIC_SILVER"),
            block -> new BlockLogicChestMimic(block, AetherMaterials.ANGELIC))
            .withSound(BlockSounds.STONE)
            .withHardness(4.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART});

        CHEST_MIMIC_GOLD = register("chest.mimic.gold", blockKey("chest_mimic_gold"), blockID("CHEST_MIMIC_GOLD"),
            block -> new BlockLogicChestMimic(block, AetherMaterials.HELLFIRE))
            .withSound(BlockSounds.STONE)
            .withHardness(4.0F)
            .withBlastResistance(5.0F)
            .withTags(new Tag[]{AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART});


        LANTERN_FIREFLY_SILVER = register("lantern.firefly.silver", blockKey("lantern_firefly_silver"), blockID("LANTERN_FIREFLY_SILVER"),
            b -> new BlockLogicLanternFirefly(b, AetherMod.SILVER, () -> AetherItems.LANTERN_FIREFLY_SILVER))
            .withSound(BlockSounds.GLASS)
            .withHardness(0.1F)
            .withLightEmission(0.9375F)
            .setStatParent(() -> AetherItems.LANTERN_FIREFLY_SILVER)
            .withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.MINEABLE_BY_PICKAXE, AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE, BlockTags.NOT_IN_CREATIVE_MENU);

        hasInit = false;
    }

}
