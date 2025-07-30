package teamport.aether.mixin.accessory.trinket;


import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Player.class, remap = false)
abstract public class PlayerMixinPendantFallDamageModifier {

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;causeFallDamage(F)V"), argsOnly = true)
    public float modifyDistance(float value) {
        return value;
    }


}
