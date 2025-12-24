package teamport.aether.mixin.accessory.cape;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUtil;

@Mixin(value = MobSlime.class, remap = false)
public class MobSlimeMixinSweatyAndInvisibility {
    @WrapOperation(method = "updateAI", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getClosestPlayerToEntity(Lnet/minecraft/core/entity/Entity;D)Lnet/minecraft/core/entity/player/Player;"))
    public Player hardToSpotInvisPlayer(World instance, Entity entity, double radius, Operation<Player> original){
        return PlayerUtil.getClosestPlayerToEntity(instance, entity, radius, PlayerUtil::isInvisible, PlayerUtil::isSwetty);
    }

    @Definition(id = "i", local = @Local(type = int.class, ordinal = 0))
    @Expression("i > 1")
    @ModifyExpressionValue(method = "playerTouch", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean canHurtPlayer(boolean original, @Local(argsOnly = true) Player player){
        return !original || !PlayerUtil.isSwetty(player);
    }
}
