package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.commands.CommandKill;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.MobUtil;

@Mixin(value = CommandKill.class, remap = false)
public abstract class CommandKillMixinFix {

    @WrapOperation(
        method = "lambda$register$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z")
    )
    private static boolean beforeKill(
        Entity instance, Entity attacker,
        int baseDamage, DamageType type, Operation<Boolean> original
    ) {
        if(instance instanceof Mob mob){
            return MobUtil.killMob(mob);
        }
        return original.call(instance, attacker, baseDamage, type);
    }
}
