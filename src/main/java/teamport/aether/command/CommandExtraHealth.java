package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.util.CommandHelper;
import teamport.aether.helper.HealthHelper;

import java.util.List;
import java.util.stream.Collectors;

import static com.mojang.brigadier.builder.ArgumentBuilderLiteral.literal;

public class CommandExtraHealth implements CommandManager.CommandRegistry{

    /* @a, all players
    *  @e, all entity
    *  @p, nearest player
    *  @r, random
    *  @s, self
    * */

    static int add(CommandContext<?> c) {
        CommandSource source = (CommandSource) c.getSource();

        int amount = c.getArgument("amount", Integer.class);
        EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);

        List<? extends Player> entities;
        try {
            entities = entitySelector.get(source).stream()
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .collect(Collectors.toList());
        }

        catch (CommandSyntaxException e) {  throw new RuntimeException(e); }

        int max_health_added = 0;
        for (Player player: entities) {
            HealthHelper.addExtraHealth(player, amount);
        }

        if (entities.size() == 1) {
            source.sendTranslatableMessage(
                "command.aether.add.extra_health.success_single_entity",
                max_health_added,
                CommandHelper.getEntityName(entities.get(0))
            );
        }

        else source.sendTranslatableMessage("command.aether.add.extra_health.success_multiple_entities", max_health_added);
        return 0;
    }

    static int set(CommandContext<?> c) {
        CommandSource source = (CommandSource) c.getSource();

        int amount = c.getArgument("amount", Integer.class);
        amount = Math.min(20, amount);

        EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
        List<? extends Player> entities;
        try {
            entities = entitySelector.get(source).stream()
                    .filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .collect(Collectors.toList());
        }

        catch (CommandSyntaxException e) {  throw new RuntimeException(e); }

        for (Player player: entities){
            HealthHelper.setExtraHealth(player, amount);
        }

        if(entities.size() == 1) {
            source.sendTranslatableMessage(
                "command.aether.set.extra_health.success_single_entity",
                CommandHelper.getEntityName(entities.get(0)), amount
            );
        }

        else source.sendTranslatableMessage("command.aether.set.extra_health.success_multiple_entities", amount);
        return 0;
    }

    static int get(CommandContext<?> c) throws CommandSyntaxException {
        CommandSource source = (CommandSource) c.getSource();
        EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
        List<? extends Entity> entities = entitySelector.get(source);

        int total_health = 0;
        for (Entity player: entities){
            total_health += HealthHelper.getExtraHealth((Player) player);
        }

        if (entities.size() == 1) {
            source.sendTranslatableMessage("command.aether.get.extra_health.success_single_entity",
                    CommandHelper.getEntityName(entities.get(0)), total_health);
        }

        else source.sendTranslatableMessage("command.aether.get.extra_health.success_multiple_entities", total_health);
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        // You can't, it just IS this bad. :^)
        ArgumentBuilderLiteral<CommandSource> commandExtraHealth = (ArgumentBuilderLiteral)
            ((ArgumentBuilderLiteral) literal("aether:extraHealth")
            .requires(t -> ((CommandSource) t).hasAdmin()))
            .then(
                literal("add")
                .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer())
                .executes(CommandExtraHealth::add)))
            )
            .then(
                literal("get")
                .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                .executes(CommandExtraHealth::get))
            )
            .then(
                literal("set")
                .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer())
                .executes(CommandExtraHealth::set)))
            );

        commandDispatcher.register(commandExtraHealth);
    }
}
