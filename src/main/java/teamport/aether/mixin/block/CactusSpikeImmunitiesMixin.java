package teamport.aether.mixin.block;

import net.minecraft.core.block.BlockLogicCactus;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.AetherImmuneMob;

@Mixin(value = BlockLogicCactus.class, remap = false)
public class CactusSpikeImmunitiesMixin {

    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    public void monsterImmuneToSpikes(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof AetherImmuneMob)) {
            return;
        }
        AetherImmuneMob immune = (AetherImmuneMob) entity;
        if (immune.canTakeDamageFromCactus()) {
            return;
        }
        ci.cancel();
    }
}
