package teamport.aether.net.message;

import net.minecraft.core.entity.player.Player;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.interfaces.AetherRideable;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class AetherRideableNetworkMessage implements NetworkMessage {
    private float moveForward;
    private float moveStrafe;
    private float xRot;
    private float yRot;
    private boolean isJumping;

    public AetherRideableNetworkMessage() {}

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
        if (EnvironmentHelper.isMultiplayerServer()) {
            Player player = networkContext.player;

            if (player.vehicle instanceof AetherRideable vehicle) {
                vehicle.controlEntity(moveForward, moveStrafe, isJumping, xRot, yRot);
            }

            else if (player.passenger instanceof AetherRideable vehicle) {
                vehicle.controlEntity(moveForward, moveStrafe, isJumping, xRot, yRot);
            }
        }
    }
}
