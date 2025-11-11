package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.AetherRideable;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class AetherRideableNetworkMessage implements NetworkMessage {
    float moveForward, moveStrafe;
    float xRot, yRot;
    boolean isJumping;

    public AetherRideableNetworkMessage() {
    }

    public AetherRideableNetworkMessage(float moveForward, float moveStrafe, boolean isJumping, float xRot, float yRot) {
        this.moveForward = moveForward;
        this.moveStrafe = moveStrafe;
        this.isJumping = isJumping;
        this.xRot = xRot;
        this.yRot = yRot;
    }

    @Override
    public void encodeToUniversalPacket(@NonNull UniversalPacket packet) {
        packet.writeDouble(moveForward);
        packet.writeDouble(moveStrafe);
        packet.writeBoolean(isJumping);
        packet.writeDouble(xRot);
        packet.writeDouble(yRot);
    }

    @Override
    public void decodeFromUniversalPacket(@NonNull UniversalPacket packet) {
        this.moveForward = (float) packet.readDouble();
        this.moveStrafe = (float) packet.readDouble();
        this.isJumping = packet.readBoolean();
        this.xRot = (float) packet.readDouble();
        this.yRot = (float) packet.readDouble();
    }

    @Override
    public void handle(NetworkContext networkContext) {
        if (EnvironmentHelper.isServerEnvironment()) {
            Player player = networkContext.player;

            if (player.vehicle instanceof AetherRideable) {
                AetherRideable vehicle = (AetherRideable) player.vehicle;
                vehicle.controlEntity(moveForward, moveStrafe, isJumping, xRot, yRot);
            }

            else if (player.passenger instanceof AetherRideable) {
                AetherRideable vehicle = (AetherRideable) player.passenger;
                vehicle.controlEntity(moveForward, moveStrafe, isJumping, xRot, yRot);
            }
        }
    }
}
