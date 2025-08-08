package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.util.CommandHelper;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.effect.AetherEffects;
import teamport.aether.helper.HealthHelper;

import java.util.List;

import static com.mojang.brigadier.builder.ArgumentBuilderLiteral.literal;

// for now, TODO figure out what that warning mean
@SuppressWarnings("unchecked")
public class CommandEffects implements CommandManager.CommandRegistry{

    /* @a, all players
    *  @e, all entity
    *  @p, nearest player
    *  @r, random
    *  @s, self
    * */

    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher
                .register((ArgumentBuilderLiteral)literal("aether:effect").requires(t -> ((CommandSource) t).hasAdmin())
                        .then(literal("add")
                        .then(literal("poison")
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            for(Entity entity: entities){
                                if(entity instanceof Mob && entity instanceof IHasEffects){
                                    AetherEffects.add((IHasEffects)entity,AetherEffects.poisonEffect, AetherEffects.poisonEffect.getMaxStack());
                                }
                            }
                            if(entities.size() == 1){
                                source.sendTranslatableMessage("command.aether.effects.add.poison.success_single_entity",
                                        AetherEffects.poisonEffect.getName(), CommandHelper.getEntityName(entities.get(0)));
                            }else{
                                source.sendTranslatableMessage("command.aether.effects.add.poison.success_multiple_entity",
                                        AetherEffects.poisonEffect.getName(), entities.size());
                            }
                        return 0;
                        }))))
                        .then(literal("add")
                        .then(literal("remedy")
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            for(Entity entity: entities){
                                if(entity instanceof Mob && entity instanceof IHasEffects){
                                    AetherEffects.add((IHasEffects)entity,AetherEffects.remedyEffect, AetherEffects.remedyEffect.getMaxStack());
                                }
                            }
                            if(entities.size() == 1){
                                source.sendTranslatableMessage("command.aether.effects.add.remedy.success_single_entity",
                                        AetherEffects.remedyEffect.getName(), CommandHelper.getEntityName(entities.get(0)));
                            }else{
                                source.sendTranslatableMessage("command.aether.effects.add.remedy.success_multiple_entity",
                                        AetherEffects.remedyEffect.getName(), entities.size());
                            }
                            return 0;
                        }))))
                        .then(ArgumentBuilderLiteral.literal("clear")
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            for(Entity entity: entities){
                                if(entity instanceof Mob && entity instanceof IHasEffects){
                                    ((IHasEffects) entity).getContainer().removeAll();
                                }
                            }
                            if(entities.size() == 1){
                                source.sendTranslatableMessage("command.aether.effect.clear.success_single_entity",
                                       CommandHelper.getEntityName(entities.get(0)));
                            }else{
                                source.sendTranslatableMessage("command.aether.effect.clear.multiple_single_entity",
                                        entities.size());
                            }
                            return 0;
                        }))));
    }
}
