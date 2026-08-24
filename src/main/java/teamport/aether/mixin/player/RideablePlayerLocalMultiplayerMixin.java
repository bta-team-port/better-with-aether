package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerLocalMultiplayer;
import net.minecraft.core.net.packet.PacketVehicleControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerLocalMultiplayer.class)
public abstract class RideablePlayerLocalMultiplayerMixin {

    @Inject(method = "sendSpecialVehiclePacket", at = @At("HEAD"))
    private void sendAetherVehiclePacket(CallbackInfo ci) {
        PlayerLocalMultiplayer player = (PlayerLocalMultiplayer) (Object) this;

        if (player.vehicle instanceof MobAetherAnimalRideable mount) {
            double trueY = mount.bb.minY + (double) mount.heightOffset;
            player.sendQueue.addToSendQueue(new PacketVehicleControl(mount.id, mount.x, trueY, mount.z, mount.yRot, mount.consumePendingFallDistance()));
        }
    }
}
