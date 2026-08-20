package teamport.aether.mixin.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;

@Environment(EnvType.SERVER)
@Mixin(PlayerList.class)
public abstract class PlayerListMixinRespawnWithExtraHealth {
    @Inject(method = "recreatePlayerEntity", at = @At("RETURN"), remap = false)
    private void keepExtraHealthServer(PlayerServer previousPlayer, int dimension, @NonNull CallbackInfoReturnable<PlayerServer> cir) {
        PlayerServer newPlayer = cir.getReturnValue();
        HealthHelper.setExtraHealth(newPlayer, HealthHelper.getExtraHealth(previousPlayer));
        newPlayer.heal(newPlayer.getMaxHealth());
    }
}
