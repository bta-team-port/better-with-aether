package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;
import teamport.aether.world.generate.feature.*;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherBronzeChest;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherGoldChest;
import teamport.aether.world.generate.feature.chests.WorldFeatureAetherSilverChest;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherBronzeDungeon;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherGoldDungeon;
import teamport.aether.world.generate.feature.dungeon.WorldFeatureAetherSilverDungeon;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;

// TODO figure out how server-side commands are registered
// TODO do the same optimization as in AetherItems.init()
public class AetherCommand {

    static {
        registerWorldFeatureClass(WorldFeatureAetherBronzeDungeon.class);
        registerWorldFeatureClass(WorldFeatureAetherSilverDungeon.class);
        registerWorldFeatureClass(WorldFeatureAetherGoldDungeon.class);
        registerWorldFeatureClass(WorldFeatureAetherClouds.class);
        registerWorldFeatureClass(WorldFeatureAetherLiquid.class);
        registerWorldFeatureClass(WorldFeatureAetherOre.class);
        registerWorldFeatureClass(WorldFeatureAetherTree.class);
        registerWorldFeatureClass(WorldFeatureAetherTreeGoldenOak.class);
        registerWorldFeatureClass(WorldFeatureAetherQuicksoil.class);

        registerWorldFeatureClass(WorldFeatureAetherGoldChest.class);
        registerWorldFeatureClass(WorldFeatureAetherSilverChest.class);
        registerWorldFeatureClass(WorldFeatureAetherBronzeChest.class);
    }

    public static void registerClientCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
        CommandManager.registerCommand(new CommandEffects());
        CommandManager.registerCommand(new CommandCount());
    }

    // I am unsure if this is correct way of doing this, cause I could not start server to check
    public static void registerServerCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
        CommandManager.registerCommand(new CommandEffects());
    }
}
