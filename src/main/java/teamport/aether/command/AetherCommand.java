package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;

public class AetherCommand {

    public static void registerClientCommands(){
        CommandManager.registerCommand(new CommandExtraHealth());
    }

    // I am unsure if this is correct way of doing this, cause I could not start server to check
    public static void initializeCommandServer(){
        CommandManager.registerCommand(new CommandExtraHealth());
    }
}
