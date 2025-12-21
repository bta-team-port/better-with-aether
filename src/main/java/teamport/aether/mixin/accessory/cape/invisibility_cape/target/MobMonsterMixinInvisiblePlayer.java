package teamport.aether.mixin.accessory.cape.invisibility_cape.target;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.player.PlayerUntil;

@Mixin(value = MobMonster.class, remap = false)
public abstract class MobMonsterMixinInvisiblePlayer {
    /// All enemies that inherit this method have a harder time seeing the player. Since not all Enemies inherit this method, it is advised
    /// to look in the mixin for the exceptions.
    @WrapOperation(method = "findPlayerToAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getClosestPlayerToEntity(Lnet/minecraft/core/entity/Entity;D)Lnet/minecraft/core/entity/player/Player;"))
    private Player hardToSpotInvisPlayer(World instance, Entity entity, double radius, Operation<Player> original) {
        return PlayerUntil.getClosestPlayerToEntity(instance, (MobMonster) (Object) this, radius);
    }
}
