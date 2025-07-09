package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;

import java.util.List;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.helpers.EntitySelector;

import net.minecraft.core.net.command.util.CommandHelper;
import teamport.aether.accessory.api.HealthHelper;

public class CommandSetHealth implements CommandManager.CommandRegistry {
    // TODO limit selection to one player or extends to allow multiple
    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher
                .register((ArgumentBuilderLiteral) ((ArgumentBuilderLiteral) ArgumentBuilderLiteral.literal("aether:setExtraHealth")
                .requires(t -> ((CommandSource) t).hasAdmin()))
                .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.username())
                .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer(0, 32768)).executes((c) -> {
                            CommandSource source = (CommandSource) c.getSource();
                            int amount = (Integer) c.getArgument("amount", Integer.class);
                            EntitySelector entitySelector = (EntitySelector) c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);

                            for(Entity player : entities) {
                                HealthHelper.setExtraHealth((Player) player, amount);
                                int actualAmount = HealthHelper.getExtraHealth((Player) player);
                                source.sendTranslatableMessage("command.aether.set_extra_health", new Object[]{CommandHelper.getEntityName(player), actualAmount});
                            }
                            return 1;
                        }))));
    }
}
