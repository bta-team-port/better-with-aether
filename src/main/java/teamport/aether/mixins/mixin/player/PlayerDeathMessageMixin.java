package teamport.aether.mixins.mixin.player;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mob.class)
public abstract class PlayerDeathMessageMixin {
    @Redirect(
        method = "onDeath",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/world/World;sendGlobalMessageTranslated(Lnet/minecraft/core/net/command/TextFormatting$Base;Ljava/lang/String;[Ljava/lang/String;)V"
        )
    )
    private void sendAetherDeathMessages(World world, TextFormatting.Base format, String key, String[] args, Entity entityKilledBy) {
        world.sendGlobalMessageTranslated(format, key, args);
    }
}
