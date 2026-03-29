package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;

@Environment(EnvType.SERVER)
@Mixin(value = PlayerList.class)
public abstract class PlayerListMixinRespawnWithExtraHealth {
    @Inject(method = "recreatePlayerEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;transferAllContents(Lnet/minecraft/core/player/inventory/container/ContainerInventory;)V"))
    private void keepExtraHealthServer(PlayerServer previousPlayer, int dimension, CallbackInfoReturnable<PlayerServer> cir, @Local(name = "newPlayer") final PlayerServer newPlayer) {
        HealthHelper.setExtraHealth(newPlayer, HealthHelper.getExtraHealth(previousPlayer));
        newPlayer.heal(newPlayer.getMaxHealth() + HealthHelper.getExtraHealth(previousPlayer));
    }
}
