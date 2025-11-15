package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicSpikes;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.AetherMobImmuneToSpikes;

@Mixin(value = BlockLogicSpikes.class, remap = false)
public abstract class SpikeImmunitiesMixin {
    @ModifyExpressionValue(method = "onEntityCollidedWithBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogicSpikes;isSpikesUp(I)Z"))
    private boolean monsterImmuneToSpikes(boolean original, World world, int x, int y, int z, Entity entity) {
        if (!(entity instanceof AetherMobImmuneToSpikes)) return original;
        AetherMobImmuneToSpikes immune = (AetherMobImmuneToSpikes) entity;
        if (immune.canTakeDamageFromSpikes()) return original;
        return false;
    }
}
