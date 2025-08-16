package teamport.aether.mixin;

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

@Mixin(value = PlayerLocal.class, remap = false)
public abstract class HandleAetherRideableMixin extends Player {

    @Shadow public PlayerInput input;

    public HandleAetherRideableMixin(World world) {
        super(world);
    }

    @Inject(method = "handleSpecialVehicleControl", at = @At("HEAD"))
    public void handleAetherRideableControl(CallbackInfo ci) {
        if (vehicle instanceof AetherRideable) {
            ((AetherRideable) vehicle).controlEntity(input.moveForward, input.moveStrafe, isJumping, xRot, yRot);
        }
    }

}
