package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.block.BlockLogicSpikes;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.AetherMobOtherImmunities;

@Mixin(value = BlockLogicSpikes.class)
public abstract class SpikeImmunitiesMixin {
    @ModifyExpressionValue(method = "onEntityCollision(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/BlockLogicSpikes;isSpikesUp(I)Z"))
    private boolean monsterImmuneToSpikes(boolean original, World world, TilePosc pos, Entity entity) {
        if (!(entity instanceof AetherMobOtherImmunities)) return original;
        AetherMobOtherImmunities immune = (AetherMobOtherImmunities) entity;
        if (immune.canTakeDamageFromSpikes()) return original;
        return false;
    }
}
