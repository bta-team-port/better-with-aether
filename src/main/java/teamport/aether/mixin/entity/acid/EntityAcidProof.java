package teamport.aether.mixin.entity.acid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityAcidProof {


    @WrapOperation(method = "processAcidDamageAndParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    protected boolean acidDamage(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original){
        return original.call(instance, attacker, baseDamage, type);
    }
}
