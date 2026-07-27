package teamport.aether.mixin.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.server.entity.EntityTrackerEntryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.effect.api.IHasEffects;
import teamport.aether.net.message.EffectSyncNetworkMessage;
import turniplabs.halplibe.helper.network.NetworkHandler;

@Mixin(value = EntityTrackerEntryImpl.class, remap = false)
public class EntityTrackerEntryImplMixin {
    @Shadow
    public Entity trackedEntity;

    @Inject(
        method = "updatePlayerEntity",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/net/handler/PacketHandlerServer;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V",
            ordinal = 0,
            shift = At.Shift.AFTER
        )
    )
    private void aether$syncEffectsOnTrack(Player player, CallbackInfo ci) {
        IHasEffects<?> hasEffects = (IHasEffects<?>) trackedEntity;
        if (!hasEffects.getContainer().getEffects().isEmpty()) {
            NetworkHandler.sendToPlayer(player, new EffectSyncNetworkMessage(trackedEntity));
        }
    }
}
