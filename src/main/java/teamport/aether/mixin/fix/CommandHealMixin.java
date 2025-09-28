package teamport.aether.mixin.fix;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeInteger;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.commands.CommandHeal;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = CommandHeal.class, remap = false, priority = 0)
public class CommandHealMixin {

    @Unique
    private static int heal(CommandContext<?> c) {
        EntitySelector entitySelector = c.getArgument("target", EntitySelector.class);
        int amount = c.getArgument("amount", Integer.class);

        List<? extends Mob> targets;
        try {
            targets = entitySelector
                .get((CommandSource) c.getSource())
                .stream()
                .filter(e -> e instanceof Mob)
                .map(e -> (Mob) e)
                .collect(Collectors.toList());
        }

        catch (CommandSyntaxException e) {  throw new RuntimeException(e); }

        int entitiesAffected = 0;
        for (Mob target : targets) {
            int maxHealth = target.getMaxHealth();
            int originalHealth = target.getHealth();

            target.setHealthRaw(MathHelper.clamp(target.getHealth() + amount, 0, maxHealth));

            if (target.getHealth() != originalHealth) ++entitiesAffected;
        }

        ((CommandSource)c.getSource()).sendTranslatableMessage("command.commands.heal.success_" + (entitiesAffected == 1 ? "single" : "multiple"), new Object[]{entitiesAffected});
        return entitiesAffected;
    }

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    public void register(CommandDispatcher<CommandSource> dispatcher, CallbackInfo ci) {
        ci.cancel();

        ArgumentBuilderLiteral<CommandSource> commandHeal = (ArgumentBuilderLiteral)
                ((ArgumentBuilderLiteral)ArgumentBuilderLiteral.literal("heal")
                .requires(c -> ((CommandSource) c).hasAdmin()))
                .then(ArgumentBuilderRequired.argument("target", ArgumentTypeEntity.entities())
                .then(ArgumentBuilderRequired.argument("amount", ArgumentTypeInteger.integer(0, 32768))
                .executes(CommandHealMixin::heal)));

        dispatcher.register(commandHeal);
    }
}
