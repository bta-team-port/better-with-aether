package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.util.CommandHelper;
import teamport.aether.accessory.api.HealthHelper;

import java.util.List;

import static com.mojang.brigadier.builder.ArgumentBuilderLiteral.*;

// for now, TODO figure out what that warning mean
@SuppressWarnings("unchecked")
public class CommandExtraHealth implements CommandManager.CommandRegistry{

    /* @a, all players
    *  @e, all entity
    *  @p, nearest player
    *  @r, random
    *  @s, self
    * */

    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher
                .register((ArgumentBuilderLiteral)literal("aether:extraHealth").requires(t -> ((CommandSource) t).hasAdmin())
                        .then(ArgumentBuilderLiteral.literal("add")
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                        .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            int amount = c.getArgument("amount", Integer.class);
                            EntitySelector entitySelector = (EntitySelector) c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            int max_health_added = 0;
                            for(Entity player: entities){
                                int current_extra_health = HealthHelper.getExtraHealth((Player) player);
                                HealthHelper.addExtraHealth((Player) player, amount);
                                int new_extra_health = HealthHelper.getExtraHealth((Player) player);
                                max_health_added = Math.max(new_extra_health - current_extra_health, max_health_added);
                            }

                            if(entities.size() == 1) {
                                source.sendTranslatableMessage("command.aether.add.extra_health.success_single_entity",
                                        new Object[]{max_health_added, CommandHelper.getEntityName((Entity) entities.get(0))});
                            }else{
                                source.sendTranslatableMessage("command.aether.add.extra_health.success_multiple_entities", max_health_added);
                            }
                            return 0;
                        }))))
                        .then((ArgumentBuilderLiteral.literal("set"))
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                        .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            int amount = c.getArgument("amount", Integer.class);
                            amount = Math.min(20, amount);
                            EntitySelector entitySelector = (EntitySelector) c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            for(Entity player: entities){
                                HealthHelper.setExtraHealth((Player) player, amount);
                            }

                            if(entities.size() == 1) {
                                source.sendTranslatableMessage("command.aether.set.extra_health.success_single_entity",
                                        new Object[]{CommandHelper.getEntityName((Entity) entities.get(0)), amount});
                            }else{
                                source.sendTranslatableMessage("command.aether.set.extra_health.success_multiple_entities", amount);
                            }
                            return 0;
                        }))))
                        .then(ArgumentBuilderLiteral.literal("get")
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.usernames())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = (EntitySelector) c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            int total_health = 0;
                            for(Entity player: entities){
                                total_health += HealthHelper.getExtraHealth((Player) player);
                            }
                            if(entities.size() == 1) {
                                source.sendTranslatableMessage("command.aether.get.extra_health.success_single_entity",
                                        new Object[]{CommandHelper.getEntityName((Entity) entities.get(0)), total_health});
                            }else{
                                source.sendTranslatableMessage("command.aether.get.extra_health.success_multiple_entities", total_health);
                            }
                            return 0;
                        }))));
    }
}
