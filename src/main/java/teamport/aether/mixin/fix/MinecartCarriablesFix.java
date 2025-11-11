package teamport.aether.mixin.fix;

import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static teamport.aether.blocks.AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART;

@Mixin(value = EntityMinecart.class, remap = false)
public abstract class MinecartCarriablesFix extends Entity {
    public MinecartCarriablesFix(@Nullable World world) {
        super(world);
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;getHeldObject()Lnet/minecraft/core/world/ICarriable;", ordinal = 1), cancellable = true)
    public void doNotCarry(Player player, CallbackInfoReturnable<Boolean> cir) {
        CarriedBlock carriedBlock = (CarriedBlock) player.getHeldObject();
        if (carriedBlock == null) return;

        if (carriedBlock.block().hasTag(AETHER_DOES_NOT_FIT_IN_MINECART)) {
            cir.setReturnValue(false);
        }
    }
}
