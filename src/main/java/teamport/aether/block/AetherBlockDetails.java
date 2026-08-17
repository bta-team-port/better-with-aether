package teamport.aether.block;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.crafting.LookupFuelFurnace;
import teamport.aether.block.terrain.BlockLogicIceStone;
import teamport.aether.block.terrain.BlockLogicOreAmbrosium;
import teamport.aether.block.terrain.BlockLogicOreGravitite;
import teamport.aether.block.terrain.BlockLogicOreZanite;
import teamport.aether.item.AetherItems;

import static net.minecraft.core.block.BlockLogicFire.setFlammable;
import static net.minecraft.core.block.BlockLogicMoss.MOSS_TO_NO_MOSS_MAP;
import static net.minecraft.core.block.BlockLogicMoss.NO_MOSS_TO_MOSS_MAP;
import static net.minecraft.core.block.BlockLogicNote.Instrument.CELESTA;
import static net.minecraft.core.block.BlockLogicNote.Instrument.WOOD_BLOCK;
import static net.minecraft.core.block.material.MaterialColor.registerManualBlockColor;
import static teamport.aether.AetherMod.*;

public class AetherBlockDetails {

    public static void initializeBlockDetails() {
        registerBlockInstruments();
        registerNewFurnaceFuel();
        registerFlammableBlocks();
        registerMapColors();
        registerMossMap();
        BlockLogicIceStone.initFreezeMap();

        BlockLogicOreAmbrosium.variantMap.put(AetherBlocks.HOLYSTONE.id(), AetherBlocks.ORE_AMBROSIUM_HOLYSTONE.id());
        BlockLogicOreZanite.variantMap.put(AetherBlocks.HOLYSTONE.id(), AetherBlocks.ORE_ZANITE_HOLYSTONE.id());
        BlockLogicOreGravitite.variantMap.put(AetherBlocks.HOLYSTONE.id(), AetherBlocks.ORE_GRAVITITE_HOLYSTONE.id());
    }

    public static void registerNewFurnaceFuel() {
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.STICK_SKYROOT.id, 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.STAIRS_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SLAB_PLANKS_SKYROOT.id(), 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED.id(), 150);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCEGATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.PRESSURE_PLATE_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.SIGN_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.SIGN_SKYROOT_PAINTED.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.DOOR_SKYROOT.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.DOOR_SKYROOT_PAINTED.id, 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.BUTTON_PLANKS_SKYROOT.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.BUTTON_PLANKS_SKYROOT_PAINTED.id(), 75);

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.DEADBUSH_AETHER.id(), 100);

        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_OAK.id(), 75);
        LookupFuelFurnace.instance.addFuelEntry(Blocks.BUTTON_PLANKS_PAINTED.id(), 75);

        LookupFuelFurnace.instance.addFuelEntry(AetherItems.BUCKET_SKYROOT.id, 300);

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.LOG_OAK_GOLDEN.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_SKYROOT.id(), 100);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.SAPLING_OAK_GOLDEN.id(), 100);

        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_OAK.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), 300);
        LookupFuelFurnace.instance.addFuelEntry(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), 300);

        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_PICKAXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SWORD_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_AXE_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOVEL_SKYROOT.id, 500);
        LookupFuelFurnace.instance.addFuelEntry(AetherItems.TOOL_SHOOTER.id, 300);
    }

    public static void registerFlammableBlocks() {
        setFlammable(AetherBlocks.PLANKS_SKYROOT, 5, 20);
        setFlammable(AetherBlocks.PLANKS_SKYROOT_PAINTED, 5, 20);
        setFlammable(AetherBlocks.FENCE_PLANKS_SKYROOT, 5, 20);
        setFlammable(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED, 5, 20);
        setFlammable(AetherBlocks.SLAB_PLANKS_SKYROOT, 5, 20);
        setFlammable(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED, 5, 20);
        setFlammable(AetherBlocks.STAIRS_PLANKS_SKYROOT, 5, 20);
        setFlammable(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED, 5, 20);

        setFlammable(AetherBlocks.LOG_SKYROOT, 15, 10);
        setFlammable(AetherBlocks.LOG_OAK_GOLDEN, 15, 10);

        setFlammable(AetherBlocks.LEAVES_SKYROOT, 30, 60);
        setFlammable(AetherBlocks.LEAVES_OAK_GOLDEN, 30, 60);

        setFlammable(AetherBlocks.HOLYSTONE_MOSSY, 100, 30);
    }

    public static void registerBlockInstruments() {
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_WHITE.id(), FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_BLUE.id(), FLUTE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.AERCLOUD_GOLD.id(), FLUTE);

        // we do have a lot, a lot, of chests.
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_PLANKS_SKYROOT.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED.id(), CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_OAK.id(), WOOD_BLOCK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_OAK_PAINTED.id(), WOOD_BLOCK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_SKYROOT.id(), CLICK);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED.id(), CLICK);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_BRONZE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_BRONZE.id(), TRANCE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_SILVER.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_SILVER.id(), BELL);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_GOLD.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CHEST_MIMIC_GOLD.id(), ORGAN);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BRICK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_BRICK_ZANITE.id(), MUSICBOX);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_BRICK_ZANITE.id(), MUSICBOX);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_AMBER.id(), SAXOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.QUICKSOIL.id(), SITAR);
        BLOCK_INSTRUMENTS.put(AetherBlocks.GLASS_QUICKSOIL.id(), SITAR);

        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_GRAVITITE.id(), XYLOPHONE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BRICK_GRAVITITE.id(), XYLOPHONE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_BRICK_GRAVITITE.id(), XYLOPHONE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_BRICK_GRAVITITE.id(), XYLOPHONE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_HELLFIRE.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LOCKED.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED.id(), ORGAN);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_TRAPPED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_HELLFIRE_TRAPPED_LOCKED.id(), BELL);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_ANGELIC.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LOCKED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_TRAPPED.id(), BELL);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED.id(), BELL);

        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.SLAB_CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.STAIRS_CARVED_STONE.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LOCKED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_LIGHT_LOCKED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_TRAPPED.id(), TRANCE);
        BLOCK_INSTRUMENTS.put(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED.id(), TRANCE);

        BLOCK_INSTRUMENTS.put(AetherBlocks.ICESTONE.id(), CELESTA);
        BLOCK_INSTRUMENTS.put(AetherBlocks.BLOCK_AMBROSIUM.id(), TRUMPET);
    }

    public static void registerMossMap() {
        NO_MOSS_TO_MOSS_MAP.put(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_MOSSY);
        NO_MOSS_TO_MOSS_MAP.put(AetherBlocks.COBBLE_HOLYSTONE, AetherBlocks.COBBLE_HOLYSTONE_MOSSY);
        MOSS_TO_NO_MOSS_MAP.put(AetherBlocks.HOLYSTONE_MOSSY, AetherBlocks.HOLYSTONE);
        MOSS_TO_NO_MOSS_MAP.put(AetherBlocks.COBBLE_HOLYSTONE_MOSSY, AetherBlocks.COBBLE_HOLYSTONE);
    }

    public static void registerMapColors() {
        registerManualBlockColor(AetherBlocks.PORTAL_AETHER, 0, MaterialColor.paintedLightblue);

        registerManualBlockColor(AetherBlocks.GRASS_AETHER, 0, MaterialColor.birchLeaves);
        registerManualBlockColor(AetherBlocks.DIRT_AETHER, 0, MaterialColor.permafrost);

        registerManualBlockColor(AetherBlocks.LEAVES_SKYROOT, 0, MaterialColor.paintedLime);
        registerManualBlockColor(AetherBlocks.LEAVES_OAK_GOLDEN, 0, MaterialColor.paintedYellow);

        registerManualBlockColor(AetherBlocks.TALLGRASS_AETHER, 0, MaterialColor.birchLeaves);
        registerManualBlockColor(AetherBlocks.FLOWER_PURPLE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.FLOWER_WHITE, 0, MaterialColor.paintedWhite);

        registerManualBlockColor(AetherBlocks.SAPLING_SKYROOT, 0, MaterialColor.paintedLime);
        registerManualBlockColor(AetherBlocks.SAPLING_OAK_GOLDEN, 0, MaterialColor.paintedYellow);

        registerManualBlockColor(AetherBlocks.DEADBUSH_AETHER, 0, MaterialColor.paintedGrey);

        registerManualBlockColor(AetherBlocks.PATH_DIRT_AETHER, 0, MaterialColor.permafrost);

        registerManualBlockColor(AetherBlocks.DOOR_PLANKS_SKYROOT_BOTTOM, 0, MaterialColor.wood);
        registerManualBlockColor(AetherBlocks.DOOR_PLANKS_SKYROOT_TOP, 0, MaterialColor.wood);

        registerManualBlockColor(AetherBlocks.HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.HOLYSTONE_MOSSY, 0, MaterialColor.birchLeaves);

        registerManualBlockColor(AetherBlocks.HOLYSTONE_POLISHED, 0, MaterialColor.iron);
        registerManualBlockColor(AetherBlocks.HOLYSTONE_CARVED, 0, MaterialColor.iron);
        registerManualBlockColor(AetherBlocks.SLAB_HOLYSTONE_POLISHED, 0, MaterialColor.iron);

        registerManualBlockColor(AetherBlocks.COBBLE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.COBBLE_HOLYSTONE_MOSSY, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.SLAB_COBBLE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.STAIRS_COBBLE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.BRICK_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.STAIRS_BRICK_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.SLAB_BRICK_HOLYSTONE, 0, MaterialColor.metal);

        registerManualBlockColor(AetherBlocks.ICESTONE, 0, MaterialColor.clay);
        registerManualBlockColor(AetherBlocks.AEROGEL, 0, MaterialColor.clay);

        registerManualBlockColor(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.ORE_ZANITE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, 0, MaterialColor.metal);

        registerManualBlockColor(AetherBlocks.QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.GLASS_QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP, 0, MaterialColor.paintedYellow);

        registerManualBlockColor(AetherBlocks.BLOCK_AMBER, 0, MaterialColor.dirtScorched);
        registerManualBlockColor(AetherBlocks.BLOCK_AMBROSIUM, 0, MaterialColor.gold);
        registerManualBlockColor(AetherBlocks.BLOCK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.BLOCK_GRAVITITE, 0, MaterialColor.paintedPink);
        registerManualBlockColor(AetherBlocks.BRICK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.STAIRS_BRICK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.SLAB_BRICK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.BRICK_GRAVITITE, 0, MaterialColor.paintedPink);
        registerManualBlockColor(AetherBlocks.STAIRS_BRICK_GRAVITITE, 0, MaterialColor.paintedPink);
        registerManualBlockColor(AetherBlocks.SLAB_BRICK_GRAVITITE, 0, MaterialColor.paintedPink);


        registerManualBlockColor(AetherBlocks.CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LIGHT, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LIGHT_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_TRAPPED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_TRAPPED_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_BRONZE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_BRONZE_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CHEST_MIMIC_BRONZE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.DOOR_DUNGEON_BRONZE, 0, MaterialColor.stone);

        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LIGHT, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_SILVER, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_SILVER_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CHEST_MIMIC_SILVER, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.DOOR_DUNGEON_SILVER, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_TRAPPED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_TRAPPED_LOCKED, 0, MaterialColor.grassScorched);

        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LIGHT, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LOCKED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_GOLD, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CHEST_DUNGEON_GOLD_LOCKED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CHEST_MIMIC_GOLD, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.DOOR_DUNGEON_GOLD, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_TRAPPED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_TRAPPED_LOCKED, 0, MaterialColor.brick);

        registerManualBlockColor(AetherBlocks.PILLAR, 0, MaterialColor.quartz);
        registerManualBlockColor(AetherBlocks.PILLAR_CAPSTONE, 0, MaterialColor.quartz);

        for (int i = 0; i < 256; ++i) {
            int colorIndex = i % 16;
            registerManualBlockColor(AetherBlocks.PLANKS_SKYROOT_PAINTED, i, PAINTED_COLORS[colorIndex]);
            registerManualBlockColor(AetherBlocks.FENCE_PLANKS_SKYROOT_PAINTED, i, PAINTED_COLORS[colorIndex]);
        }

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int meta = i << 4 | j;
                registerManualBlockColor(AetherBlocks.STAIRS_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);
                registerManualBlockColor(AetherBlocks.SLAB_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.TRAPDOOR_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.FENCEGATE_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.SIGN_POST_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);
                registerManualBlockColor(AetherBlocks.SIGN_WALL_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.CHEST_PLANKS_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.CHEST_MIMIC_SKYROOT_PAINTED, meta, PAINTED_COLORS[i]);
                registerManualBlockColor(AetherBlocks.CHEST_MIMIC_OAK_PAINTED, meta, PAINTED_COLORS[i]);

                registerManualBlockColor(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_BOTTOM, meta, PAINTED_COLORS[i]);
                registerManualBlockColor(AetherBlocks.DOOR_PLANKS_SKYROOT_PAINTED_TOP, meta, PAINTED_COLORS[i]);
            }
        }
    }

    private static final MaterialColor[] PAINTED_COLORS = {
        MaterialColor.paintedWhite,
        MaterialColor.paintedOrange,
        MaterialColor.paintedMagenta,
        MaterialColor.paintedLightblue,
        MaterialColor.paintedYellow,
        MaterialColor.paintedLime,
        MaterialColor.paintedPink,
        MaterialColor.paintedGrey,
        MaterialColor.paintedSilver,
        MaterialColor.paintedCyan,
        MaterialColor.paintedPurple,
        MaterialColor.paintedBlue,
        MaterialColor.paintedBrown,
        MaterialColor.paintedGreen,
        MaterialColor.paintedRed,
        MaterialColor.paintedBlack
    };
}
