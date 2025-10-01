package teamport.aether.mixin.player;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.helper.HealthHelper;

@Mixin(value = Player.class, remap = false)
public class PlayerExtraHealthMixin extends Mob {
    public PlayerExtraHealthMixin(@Nullable World world) {
        super(world);
    }

    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
    public void getMaxHealth(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() + HealthHelper.getExtraHealth(Player.class.cast(this)));
    }
}
