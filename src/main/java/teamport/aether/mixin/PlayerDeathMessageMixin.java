package teamport.aether.mixin;


import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.AetherTranslatableDeathMessage;

import static net.minecraft.core.net.command.TextFormatting.RED;
import static net.minecraft.core.net.command.TextFormatting.RESET;


@Mixin(value = Player.class, remap = false)
public class PlayerDeathMessageMixin {
    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    public void sendAetherDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
        Player player = (Player) (Object) this;
        String displayNameFormated = RESET + player.getDisplayName() + RED;
        if(!(entityKilledBy instanceof AetherTranslatableDeathMessage)){
            return;
        }
        AetherTranslatableDeathMessage killer = (AetherTranslatableDeathMessage)entityKilledBy;
        cir.setReturnValue(killer.deathMessage(player));
    }
}
