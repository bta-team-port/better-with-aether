package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.BlockLogicCactus;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.AetherMobOtherImmunities;

@Mixin(value = BlockLogicCactus.class)
public abstract class CactusSpikeImmunitiesMixin {
    @WrapOperation(method = "onEntityCollidedWithBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean monsterImmuneToSpikes(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original) {
        if (!(instance instanceof AetherMobOtherImmunities)) return original.call(instance, attacker, baseDamage, type);
        AetherMobOtherImmunities immune = (AetherMobOtherImmunities) instance;
        if (immune.canTakeDamageFromCactus()) return original.call(instance, attacker, baseDamage, type);
        return false;
    }
}
