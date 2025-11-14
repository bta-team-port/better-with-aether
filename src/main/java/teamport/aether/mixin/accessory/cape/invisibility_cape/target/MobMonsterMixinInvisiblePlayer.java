package teamport.aether.mixin.accessory.cape.invisibility_cape.target;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.items.accessory.AetherInvisibility;

@Mixin(value = MobMonster.class, remap = false)
public abstract class MobMonsterMixinInvisiblePlayer {
    /// All enemies that inherit this method have a harder time seeing the player. Since not all Enemies inherit this method, it is advised
    /// to look in the mixin for the exceptions.
    @WrapOperation(method = "findPlayerToAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;getClosestPlayerToEntity(Lnet/minecraft/core/entity/Entity;D)Lnet/minecraft/core/entity/player/Player;"))
    public Player hardToSpotInvisPlayer(World instance, Entity entity, double radius, Operation<Player> original) {
        MobMonster asThis = (MobMonster) (Object) this;
        Player player = original.call(instance, entity, radius);
        if (player == null || !asThis.canEntityBeSeen(player) || !player.getGamemode().areMobsHostile()) {
            return null;
        }
        if (player instanceof AetherInvisibility && !(asThis instanceof EnemyBoss)) {
            AetherInvisibility invPlayer = (AetherInvisibility) player;
            if (invPlayer.aether$isInvisible() && asThis.world != null) {
                Player newPlayer = asThis.world.getClosestPlayerToEntity(asThis, 2.0);
                if (newPlayer == null || !asThis.canEntityBeSeen(newPlayer) || !newPlayer.getGamemode().areMobsHostile()) {
                    return null;
                }
                return newPlayer;
            }
        }
        return player;
    }
}
