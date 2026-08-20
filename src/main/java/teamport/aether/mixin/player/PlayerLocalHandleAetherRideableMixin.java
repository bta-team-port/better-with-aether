package teamport.aether.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.AetherRideable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerLocal.class)
public abstract class PlayerLocalHandleAetherRideableMixin extends Player {
    protected PlayerLocalHandleAetherRideableMixin(World world) {
        super(world);
    }

    @Shadow
    public PlayerInput input;

    @Inject(method = "handleSpecialVehicleControl", at = @At("HEAD"))
    private void handleAetherRideableControl(CallbackInfo ci) {
        if (vehicle instanceof AetherRideable aetherRideable) {
            aetherRideable.controlEntity(input.moveForward, input.moveStrafe, isJumping, xRot, yRot);
        } else if (passenger instanceof AetherRideable aetherRideable) {
            aetherRideable.controlEntity(input.moveForward, input.moveStrafe, isJumping, xRot, yRot);
        }
    }
}
