package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.util.CommandHelper;

import java.util.List;

public class CommandGetHealth implements CommandManager.CommandRegistry {
    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
                (ArgumentBuilderLiteral)ArgumentBuilderLiteral.literal("aether:getHealth")
                        .executes(c -> {
                            CommandSource source = ((CommandSource) c.getSource());
                            EntitySelector entitySelector = (EntitySelector) c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);

                            for(Entity player : entities) {
                                source.sendTranslatableMessage("command.aether.get_max_health", CommandHelper.getEntityName(player));
                            }
                            return 1;
                        }
        ));
    }
}
