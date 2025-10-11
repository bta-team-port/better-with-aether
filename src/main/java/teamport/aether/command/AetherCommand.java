package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;
import teamport.aether.world.generate.feature.*;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherGoldChest;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherSilverChest;
import teamport.aether.world.generate.feature.components.dungeon.bronze.*;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;

// TODO figure out how server-side commands are registered
// TODO do the same optimization as in AetherItems.init()
public class AetherCommand {

    static {
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

        registerWorldFeatureClass(BossRoom.class, "DebugMazeBoss");
        registerWorldFeatureClass(DisplayRoom.class, "DebugMazeDisplay");
        registerWorldFeatureClass(HallwayRoom.class, "DebugMazeHallway");
        registerWorldFeatureClass(JumpRoom.class, "DebugMazeJump");
        registerWorldFeatureClass(SpikerRoom.class, "DebugMazeSpiker");
        registerWorldFeatureClass(StairwellRoom.class, "DebugMazeWell");
        registerWorldFeatureClass(TallRoom.class, "DebugMazeTall");
        registerWorldFeatureClass(TreasureRoom.class, "DebugMazeTreasure");

        registerWorldFeatureClass(RotationBlockTest.class, "DebugRotationBlocks");
    }

    public static void registerClientCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
        CommandManager.registerCommand(new CommandCount());
    }

    // I am unsure if this is correct way of doing this, cause I could not start server to check
    public static void registerServerCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
    }
}
