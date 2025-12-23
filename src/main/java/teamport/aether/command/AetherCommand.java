package teamport.aether.command;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.net.command.CommandManager;

public class AetherCommand {

    public static void registerClientCommands() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()){
            CommandManager.registerCommand(new CommandCount());
        }
    }

    public static void registerServerCommands() {}
}
