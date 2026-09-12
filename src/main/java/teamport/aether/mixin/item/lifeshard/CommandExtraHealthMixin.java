package teamport.aether.mixin.item.lifeshard;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sunsetsatellite.catalyst.effects.command.CommandExtraHealth;
import sunsetsatellite.catalyst.effects.helper.HealthHelper;
import teamport.aether.entity.player.ILifeShard;
import teamport.aether.net.message.AetherSyncLifeShardMessage;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(CommandExtraHealth.class)
public abstract class CommandExtraHealthMixin {

    @WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Lsunsetsatellite/catalyst/effects/helper/HealthHelper;addExtraHealth(Lnet/minecraft/core/entity/player/Player;I)V"))
    private static void addAdjustLifeShardUsed(Player player, int amount, Operation<Void> original) {
        original.call(player, amount);
        if (amount >= 0 ) {
            return;
        }
        byte lifeShardUsed = (byte) (((ILifeShard) player).better_with_aether$getLifeShardUsed());
        if(amount >= lifeShardUsed){
            lifeShardUsed = 0;
        }else{
            lifeShardUsed -= (byte) amount;
        }
        if (EnvironmentHelper.isMultiplayerServer()) {
            NetworkHandler.sendToPlayer(player, new AetherSyncLifeShardMessage(lifeShardUsed));
        }
    }


    @WrapOperation(method = "set", at = @At(value = "INVOKE", target = "Lsunsetsatellite/catalyst/effects/helper/HealthHelper;setExtraHealth(Lnet/minecraft/core/entity/player/Player;I)V"))
    private static void setAdjustLifeShardUsed(Player player, int amount, Operation<Void> original) {
        int extraHealth = HealthHelper.getExtraHealth(player);
        original.call(player, amount);
        byte lifeShardUsed = (byte) (((ILifeShard) player).better_with_aether$getLifeShardUsed());
        if (amount <= 20) {
            lifeShardUsed = 0;
        }else{
            int diff = extraHealth - amount;
            if(diff >= lifeShardUsed){
                lifeShardUsed = 0;
            }
            lifeShardUsed -= (byte) diff;
        }
        if (EnvironmentHelper.isMultiplayerServer()) {
            NetworkHandler.sendToPlayer(player, new AetherSyncLifeShardMessage(lifeShardUsed));
        }
    }

}
