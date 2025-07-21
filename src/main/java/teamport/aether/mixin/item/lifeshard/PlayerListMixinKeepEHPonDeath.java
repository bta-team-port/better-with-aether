package teamport.aether.mixin.item.lifeshard;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.helper.HealthHelper;

@Mixin(value = PlayerList.class)
public class PlayerListMixinKeepEHPonDeath {

    @Inject(method = "recreatePlayerEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;transferAllContents(Lnet/minecraft/core/player/inventory/container/ContainerInventory;)V"), remap = false)
    public void keepExtraHealthServer(final PlayerServer previousPlayer, final int i, final CallbackInfoReturnable<PlayerServer> cir, @Local(name = "newPlayer") final PlayerServer newPlayer) {
        HealthHelper.setExtraHealth(newPlayer, HealthHelper.getExtraHealth(previousPlayer));
    }
}
