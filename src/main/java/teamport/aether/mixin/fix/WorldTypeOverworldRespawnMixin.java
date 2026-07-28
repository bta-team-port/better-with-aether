package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldType.class, remap = false, priority = 0)
public abstract class WorldTypeOverworldRespawnMixin {
    @Inject(method = "getRespawnLocation(Lnet/minecraft/core/world/World;)V", at = @At("HEAD"))
    private void respawnLocationOne(World world, CallbackInfo ci, @Share("attempts") LocalIntRef attempts) {
        attempts.set(0);
    }
    @WrapOperation(method = "getRespawnLocation(Lnet/minecraft/core/world/World;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getTopBlock(II)I"))
    private int respawnLocationTwo(World instance, int x, int z, Operation<Integer> original, @Share("attempts") LocalIntRef attempts) {
        if  (attempts.get() >= 500) return 1;
        attempts.set(attempts.get() + 1);
        return original.call(instance, x, z);
    }
    @WrapOperation(method = "getRespawnLocation(Lnet/minecraft/core/world/World;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/pos/TilePos;set(III)Lnet/minecraft/core/world/pos/TilePos;"))
    private TilePos respawnLocationThree(TilePos instance, int x, int y, int z, Operation<TilePos> original, @Share("attempts") LocalIntRef attempts) {
        if  (attempts.get() >= 500) return instance;
        return original.call(instance, x, y, z);
    }
}
