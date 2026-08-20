package teamport.aether.mixins.mixin.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerFixArmorMixin {
    @ModifyArg(method = "damageEntity(ILnet/minecraft/core/util/helper/DamageType;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;damageArmor(I)V"), index = 0)
    private int forceMinArmorWearOnNegation(int amount) {
        return Math.max(amount, 1);
    }
}
