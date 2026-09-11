package teamport.aether.mixin.entity.acid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.entity.interfaces.AetherMobOtherImmunities;

@Mixin(Mob.class)
public abstract class MobAcidProof extends EntityAcidProof {

    @Override
    protected boolean acidDamage(Entity instance, Entity attacker, int baseDamage, DamageType type, Operation<Boolean> original) {
        Mob mob = (Mob) (Object) this;
        if (!(mob instanceof AetherMobOtherImmunities immune) || immune.canTakeDamageFromAcid()) {
            return original.call(instance, attacker, baseDamage, type);
        }
        return false;
    }
}
