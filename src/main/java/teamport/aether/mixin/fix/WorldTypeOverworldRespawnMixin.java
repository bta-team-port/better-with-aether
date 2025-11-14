package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldType.class, remap = false, priority = 0)
public abstract class WorldTypeOverworldRespawnMixin {
    @Inject(method = "getRespawnLocation", at = @At("HEAD"))
    private void respawnLocationOne(World world, CallbackInfo ci, @Share("attempts") LocalIntRef attempts) {
        attempts.set(0);
    }
    @WrapOperation(method = "getRespawnLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getTopBlock(II)I"))
    private int respawnLocationTwo(World instance, int x, int z, Operation<Integer> original, @Share("attempts") LocalIntRef attempts) {
        if  (attempts.get() >= 500) return 1;
        attempts.set(attempts.get() + 1);
        return original.call(instance, x, z);
    }
    @WrapOperation(method = "getRespawnLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/save/LevelData;setSpawnX(I)V"))
    private void respawnLocationThree(LevelData instance, int x, Operation<Void> original, @Share("attempts") LocalIntRef attempts) {
        if  (attempts.get() >= 500) return;
        original.call(instance, x);
    }
    @WrapOperation(method = "getRespawnLocation", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/save/LevelData;setSpawnZ(I)V"))
    private void respawnLocationFour(LevelData instance, int z, Operation<Void> original, @Share("attempts") LocalIntRef attempts) {
        if  (attempts.get() >= 500) return;
        original.call(instance, z);
    }
}
