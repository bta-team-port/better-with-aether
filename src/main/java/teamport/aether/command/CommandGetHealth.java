package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.util.CommandHelper;
import teamport.aether.accessory.api.HealthHelper;


public class CommandGetHealth implements CommandManager.CommandRegistry {
    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                (ArgumentBuilderLiteral)ArgumentBuilderLiteral.literal("aether:getHealth")
                        .executes(c -> {
                            CommandSource source = ((CommandSource) c.getSource());
                            Player player = source.getSender();
                            source.sendTranslatableMessage("command.aether.get_max_health", CommandHelper.getEntityName(player), HealthHelper.getExtraHealth(player));
                            return 1;
                        }
        ));
    }
}
