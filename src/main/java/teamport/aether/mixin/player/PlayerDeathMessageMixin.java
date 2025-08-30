package teamport.aether.mixin.player;


import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.AetherDeathMessage;


@Mixin(value = Player.class, remap = false)
public class PlayerDeathMessageMixin {
    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    public void sendAetherDeathMessages(Entity entityKilledBy, CallbackInfoReturnable<String> cir) {
        Player player = (Player) (Object) this;
        if(!(entityKilledBy instanceof AetherDeathMessage)){
            return;
        }
        AetherDeathMessage killer = (AetherDeathMessage)entityKilledBy;
        cir.setReturnValue(killer.deathMessage(player));
    }
}
