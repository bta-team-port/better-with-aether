package teamport.aether.command;

import net.minecraft.core.net.command.CommandManager;

// TODO figure out how server-side commands are registered
// TODO do the same optimization as in AetherItems.init()
public class AetherCommand {

    public static void registerClientCommands() {
        CommandManager.registerCommand(new CommandCount());
    }

    // I am unsure if this is a correct way of doing this, because I could not start server to check
    public static void registerServerCommands() {}
}
