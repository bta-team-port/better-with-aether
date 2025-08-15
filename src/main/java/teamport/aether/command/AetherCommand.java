package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;
import teamport.aether.world.generate.feature.*;

import static net.minecraft.core.net.command.util.CommandHelper.registerWorldFeatureClass;

// TODO figure out how server-side commands are registered
// TODO do the same optimization as in AetherItems.init()
public class AetherCommand {

    static {
        registerWorldFeatureClass(WorldFeatureAetherDungeonBronze.class);
        registerWorldFeatureClass(WorldFeatureAetherDungeonSilver.class);
        registerWorldFeatureClass(WorldFeatureAetherDungeonGold.class);
        registerWorldFeatureClass(WorldFeatureAetherClouds.class);
        registerWorldFeatureClass(WorldFeatureAetherLiquid.class);
        registerWorldFeatureClass(WorldFeatureAetherOre.class);
        registerWorldFeatureClass(WorldFeatureAetherTree.class);
        registerWorldFeatureClass(WorldFeatureAetherTreeGoldenOak.class);
        registerWorldFeatureClass(WorldFeatureAetherQuicksoil.class);
    }

    public static void registerClientCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
        CommandManager.registerCommand(new CommandEffects());
    }

    // I am unsure if this is correct way of doing this, cause I could not start server to check
    public static void registerServerCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
        CommandManager.registerCommand(new CommandEffects());
    }
}
