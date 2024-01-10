package bta.aether.mixin;

import bta.aether.entity.EntitySentry;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.ExplosionCannonball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ExplosionCannonball.class, remap = false)
public class ExplosionCannonballMixin {

    @Redirect(method = "doExplosionA()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean explosionAHurt(Entity instance, Entity attacker, int baseDamage, DamageType type){
        return !(attacker instanceof EntitySentry) && instance.hurt(attacker, baseDamage, type);
    }
}
