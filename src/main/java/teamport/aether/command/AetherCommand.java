package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;

public class AetherCommand {

    public static void initializeCommandsClient(){
        CommandManager.registerCommand(new CommandGetHealth());
        CommandManager.registerCommand(new CommandSetHealth());
    }

    // I am unsure if this is correct way of doing this, cause I could not start server to check
    public static void initializeCommandsServer(){
        CommandManager.registerCommand(new CommandGetHealth());
        CommandManager.registerCommand(new CommandSetHealth());
    }
}
