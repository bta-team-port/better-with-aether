package teamport.aether.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.AetherDeathMessage;

@Mixin(value = Player.class)
public abstract class PlayerDeathMessageMixin {
    @ModifyReturnValue(method = "getDeathMessage", at = @At("RETURN"))
    private String sendAetherDeathMessages(String original, Entity entityKilledBy) {
        Player player = (Player) (Object) this;
        if (!(entityKilledBy instanceof AetherDeathMessage)) return original;
        AetherDeathMessage killer = (AetherDeathMessage) entityKilledBy;
        return killer.deathMessage(player);
    }
}
