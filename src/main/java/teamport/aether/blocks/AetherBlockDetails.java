package teamport.aether.blocks;

import net.minecraft.core.block.material.MaterialColor;

import static net.minecraft.core.block.BlockLogicMoss.stoneToMossMap;
import static net.minecraft.core.block.material.MaterialColor.registerManualBlockColor;

public class AetherBlockDetails {

    public void initializeBlockDetails() {

        stoneToMossMap.put(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_MOSSY);
        stoneToMossMap.put(AetherBlocks.COBBLE_HOLYSTONE, AetherBlocks.COBBLE_HOLYSTONE_MOSSY);

        registerManualBlockColor(AetherBlocks.PORTAL_AETHER, 0, MaterialColor.paintedLightblue);

        registerManualBlockColor(AetherBlocks.GRASS_AETHER, 0, MaterialColor.birchLeaves);
        registerManualBlockColor(AetherBlocks.DIRT_AETHER, 0, MaterialColor.permafrost);

        registerManualBlockColor(AetherBlocks.LEAVES_SKYROOT, 0, MaterialColor.paintedLime);
        registerManualBlockColor(AetherBlocks.LEAVES_OAK_GOLDEN, 0, MaterialColor.paintedYellow);

        registerManualBlockColor(AetherBlocks.PATH_DIRT_AETHER, 0, MaterialColor.permafrost);

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

        registerManualBlockColor(AetherBlocks.ORE_GRAVITITE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.ORE_ZANITE_HOLYSTONE, 0, MaterialColor.metal);
        registerManualBlockColor(AetherBlocks.ORE_AMBROSIUM_HOLYSTONE, 0, MaterialColor.metal);

        registerManualBlockColor(AetherBlocks.QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.GLASS_QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.TRAPDOOR_GLASS_QUICKSOIL, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.DOOR_GLASS_QUICKSOIL_BOTTOM, 0, MaterialColor.paintedYellow);
        registerManualBlockColor(AetherBlocks.DOOR_GLASS_QUICKSOIL_TOP, 0, MaterialColor.paintedYellow);

        registerManualBlockColor(AetherBlocks.BLOCK_AMBER, 0, MaterialColor.gold);
        registerManualBlockColor(AetherBlocks.BLOCK_AMBROSIUM, 0, MaterialColor.gold);
        registerManualBlockColor(AetherBlocks.BLOCK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.BLOCK_GRAVITITE, 0, MaterialColor.paintedPink);
        registerManualBlockColor(AetherBlocks.BRICK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.STAIRS_BRICK_ZANITE, 0, MaterialColor.paintedPurple);
        registerManualBlockColor(AetherBlocks.SLAB_BRICK_ZANITE, 0, MaterialColor.paintedPurple);


        registerManualBlockColor(AetherBlocks.CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LIGHT, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_STONE, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_LIGHT_LOCKED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.CARVED_STONE_TRAPPED, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.BRONZE_CHEST_DUNGEON, 0, MaterialColor.stone);
        registerManualBlockColor(AetherBlocks.BRONZE_CHEST_DUNGEON_LOCKED, 0, MaterialColor.stone);

        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LIGHT, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_ANGELIC, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_LIGHT_LOCKED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.CARVED_ANGELIC_TRAPPED, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.SILVER_CHEST_DUNGEON, 0, MaterialColor.grassScorched);
        registerManualBlockColor(AetherBlocks.SILVER_CHEST_DUNGEON_LOCKED, 0, MaterialColor.grassScorched);


        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LIGHT, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.STAIRS_CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.SLAB_CARVED_HELLFIRE, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LOCKED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.CARVED_HELLFIRE_LIGHT_LOCKED, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.GOLD_CHEST_DUNGEON, 0, MaterialColor.brick);
        registerManualBlockColor(AetherBlocks.GOLD_CHEST_DUNGEON_LOCKED, 0, MaterialColor.brick);

        registerManualBlockColor(AetherBlocks.PILLAR, 0, MaterialColor.quartz);
        registerManualBlockColor(AetherBlocks.PILLAR_CAPSTONE, 0, MaterialColor.quartz);
    }
}
