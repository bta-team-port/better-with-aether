package teamport.aether.mixin.fix;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.command.commands.CommandHeal;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CommandHeal.class, priority = 0)
public abstract class CommandHealMixin {
    @Expression("? instanceof ?")
    @ModifyExpressionValue(method = "lambda$register$0", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean handleMobHeal(boolean original, @Local(name = "entity") Entity entity, @Local(name = "entitiesAffected") LocalIntRef entitiesAffected, @Local(name = "amount") int amount) {
        if (!original) return false;
        Mob mob = (Mob) entity;
        int maxHealth = mob.getMaxHealth();
        int before = mob.getHealth();
        mob.setHealthRaw(MathHelper.clamp(before + amount, 0, maxHealth));
        if (mob.getHealth() != before) entitiesAffected.set(entitiesAffected.get() + 1);
        return false;
    }
}
