package teamport.aether.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import teamport.aether.entity.AetherDeathMessage;

@Mixin(value = Mob.class, remap = false)
public abstract class PlayerDeathMessageMixin {
    @Redirect(
        method = "onDeath",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V"
        )
    )
    private void sendAetherDeathMessages(World world, TextFormatting.Base formatting, String key, String[] substitutions, Entity entityKilledBy) {
        if (!((Object) this instanceof Player) || !(entityKilledBy instanceof AetherDeathMessage)) {
            world.sendGlobalMessageTranslated(formatting, key, substitutions);
            return;
        }

        Player player = (Player) (Object) this;
        AetherDeathMessage killer = (AetherDeathMessage) entityKilledBy;
        world.sendGlobalMessage(killer.deathMessage(player));
    }
}
