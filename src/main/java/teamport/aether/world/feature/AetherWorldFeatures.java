package teamport.aether.world.feature;

import net.fabricmc.loader.api.FabricLoader;
import teamport.aether.world.feature.chest.WorldFeatureAetherBronzeChest;
import teamport.aether.world.feature.chest.WorldFeatureAetherGoldChest;
import teamport.aether.world.feature.chest.WorldFeatureAetherSilverChest;
import teamport.aether.world.feature.dungeon.bronze.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.feature.dungeon.bronze.component.*;
import teamport.aether.world.feature.dungeon.gold.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.feature.dungeon.silver.WorldFeatureAetherSilverDungeon;
import teamport.aether.world.feature.terrain.*;
import teamport.aether.world.feature.util.RotationBlockTest;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;

public class AetherWorldFeatures {
    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            initializeWorldFeatures();
        }

    }

    private static void initializeWorldFeatures() {
        registerWorldFeatureClass(WorldFeatureAetherBronzeDungeon.class, "Maze");
        registerWorldFeatureClass(WorldFeatureAetherSilverDungeon.class, "Temple");
        registerWorldFeatureClass(WorldFeatureAetherGoldDungeon.class, "Sanctuary");
        registerWorldFeatureClass(WorldFeatureAetherClouds.class, "Clouds");
        registerWorldFeatureClass(WorldFeatureAetherLiquid.class, "AetherLakes");
        registerWorldFeatureClass(WorldFeatureAetherOre.class, "AetherOre");
        registerWorldFeatureClass(WorldFeatureAetherTree.class, "TreeSkyroot");
        registerWorldFeatureClass(WorldFeatureAetherTreeGoldenOak.class, "TreeGoldenSkyroot");
        registerWorldFeatureClass(WorldFeatureAetherQuicksoil.class, "Quicksoil");

        registerWorldFeatureClass(WorldFeatureAetherGoldChest.class, "SanctuaryTreasure");
        registerWorldFeatureClass(WorldFeatureAetherSilverChest.class, "TempleTreasure");
        registerWorldFeatureClass(WorldFeatureAetherBronzeChest.class, "MazeTreasure");

        /// for testing bronze dungeon rooms
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            registerWorldFeatureClass(BossRoom.class, "DebugMazeBoss");
            registerWorldFeatureClass(StorageRoom.class, "DebugMazeDisplay");
            registerWorldFeatureClass(HallwayRoom.class, "DebugMazeHallway");
            registerWorldFeatureClass(JumpRoom.class, "DebugMazeJump");
            registerWorldFeatureClass(SpikerRoom.class, "DebugMazeSpiker");
            registerWorldFeatureClass(StairwellRoom.class, "DebugMazeWell");
            registerWorldFeatureClass(TallRoom.class, "DebugMazeTall");
            registerWorldFeatureClass(TreasureChestRoom.class, "DebugMazeTreasureChest");
            registerWorldFeatureClass(TresureOreRoom.class, "DebugMazeTreasureOre");
            registerWorldFeatureClass(StorageRoom.class, "DebugMazeStorage");
            registerWorldFeatureClass(DisplayRoom.class, "DebugDisplay");
            registerWorldFeatureClass(RotationBlockTest.class, "DebugRotationBlocks");
        }
    }
}
