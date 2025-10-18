package teamport.aether.mixin.block;

import net.minecraft.core.block.BlockLogicSpikes;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.AetherMobImmuneToSpikes;

@Mixin(value = BlockLogicSpikes.class, remap = false)
public class SpikeImmunitiesMixin {

    @Inject(method = "onEntityCollidedWithBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogicSpikes;isSpikesUp(I)Z"), cancellable = true)
    public void monsterImmuneToSpikes(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof AetherMobImmuneToSpikes)) {
            return;
        }
        AetherMobImmuneToSpikes immune = (AetherMobImmuneToSpikes) entity;
        if (immune.canTakeDamageFromSpikes()) {
            return;
        }
        ci.cancel();
    }
}
