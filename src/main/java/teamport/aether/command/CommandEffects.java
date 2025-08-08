package teamport.aether.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.util.CommandHelper;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.Effects;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import sunsetsatellite.catalyst.effects.command.argument.ArgumentTypeEffect;
import teamport.aether.effect.AetherEffects;

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
                .register((ArgumentBuilderLiteral) ((ArgumentBuilderLiteral) ((ArgumentBuilderLiteral)literal("aether:effect")
                        .then(literal("list")
                        .executes(c ->{
                            ((CommandSource)c.getSource()).sendMessage("Available effects:");

                            for(Effect effect : Effects.getInstance()) {
                                ((CommandSource)c.getSource()).sendMessage("- " + Effects.getInstance().getKey(effect));
                            }
                            return 1;
                        }))
                        .then(ArgumentBuilderLiteral.literal("remove")
                        .requires(t -> ((CommandSource) t).hasAdmin())
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                        .then(literal("all")
                        .executes(c ->{
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            int count = 0;
                            for(Entity entity: entities){
                                if (!(entity instanceof Mob))continue;
                                count++;
                                ((IHasEffects) entity).getContainer().removeAll();
                            }
                            if(count == 1){
                                source.sendTranslatableMessage("command.aether.effect.clear.success_single_entity",
                                        CommandHelper.getEntityName(entities.get(0)));
                                return 1;
                            }
                            source.sendTranslatableMessage("command.aether.effect.clear.multiple_single_entity", count);
                            return 1;
                        }))
                        .then(ArgumentBuilderRequired
                        .argument("name", ArgumentTypeEffect.effect())
                        .executes((ctx) -> {
                            Effect effect = ArgumentTypeEffect.getEffect(ctx, "name");
                            if (!(((CommandSource) ctx.getSource()).getSender() instanceof IHasEffects)) {
//                                throw INCOMPATIBLE_ENTITY.create();
                                return 0;
                            }
                            IHasEffects effects = (IHasEffects)((CommandSource)ctx.getSource()).getSender();
                            effects.getContainer().remove(effect);
                            return 1;
                        }))))
                        .requires(t -> ((CommandSource) t).hasAdmin())
                        .then(literal("add")
                        .then(ArgumentBuilderRequired.argument("name", ArgumentTypeEffect.effect())
                        .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                        .then(ArgumentBuilderRequired.argument("duration", ArgumentTypeInteger.integer())
                        .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer())
                        .executes((c) ->{
                            Effect effect = ArgumentTypeEffect.getEffect(c, "name");
                            int duration = ArgumentTypeInteger.getInteger(c, "duration");
                            int amount = ArgumentTypeInteger.getInteger(c, "amount");
                            CommandSource source = (CommandSource) c.getSource();
                            EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
                            List<? extends Entity> entities = entitySelector.get(source);
                            int count = 0;
                            for(Entity entity: entities){
                                if (!(entity instanceof Mob)) continue;
                                EffectStack stack = new EffectStack((IHasEffects) entity, effect, duration, amount);
                                if (AetherEffects.add((Mob)entity,stack)) count++;
                            }
                            if(count == 1){
                                source.sendTranslatableMessage("command.aether.effects.add.poison.success_single_entity",
                                        AetherEffects.poisonEffect.getName(), CommandHelper.getEntityName(entities.get(0)));
                                return 1;
                            }
                            source.sendTranslatableMessage("command.aether.effects.add.poison.success_multiple_entity",
                                    AetherEffects.poisonEffect.getName(), count);
                            return 1;
                        })))))))));
    }
}
