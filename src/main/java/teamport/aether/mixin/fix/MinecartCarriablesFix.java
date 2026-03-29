package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlockTags;

@Mixin(value = EntityMinecart.class)
public abstract class MinecartCarriablesFix extends Entity {
    protected MinecartCarriablesFix(@Nullable World world) {
        super(world);
    }

    @Definition(id = "TileEntityChest", type = {TileEntityChest.class})
    @Expression("? instanceof TileEntityChest")
    @ModifyExpressionValue(method = "interact", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean doNotCarryOne(boolean original, @Local(name = "carriedBlock") CarriedBlock carriedBlock, @Share("shouldNotCarry") LocalBooleanRef shouldNotCarry) {
        if (carriedBlock == null) {
            shouldNotCarry.set(false);
            return original;
        }
        if (carriedBlock.block().hasTag(AetherBlockTags.AETHER_DOES_NOT_FIT_IN_MINECART)) {
            shouldNotCarry.set(true);
            return false;
        }
        shouldNotCarry.set(false);
        return original;
    }

    @ModifyReturnValue(method = "interact", at = @At("RETURN"))
    private boolean doNotCarryTwo(boolean original, @Share("shouldNotCarry") LocalBooleanRef shouldNotCarry) {
        if (shouldNotCarry.get()) return false;
        return original;
    }

    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;startRiding(Lnet/minecraft/core/world/IVehicle;)V"))
    private void doNotCarryThree(Player instance, IVehicle iVehicle, Operation<Void> original, @Share("shouldNotCarry") LocalBooleanRef shouldNotCarry) {
        if (shouldNotCarry.get()) return;
        original.call(instance, iVehicle);
    }
}
