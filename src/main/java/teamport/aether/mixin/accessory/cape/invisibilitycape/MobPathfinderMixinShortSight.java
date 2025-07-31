package teamport.aether.mixin.accessory.cape.invisibilitycape;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.EnemyBoss;
import teamport.aether.items.IAetherAccessories;

@Mixin(value = MobPathfinder.class, remap = false)
public class MobPathfinderMixinShortSight {
    @WrapOperation(
            method = "updateAI",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/world/World;getPathToEntity(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/entity/Entity;F)Lnet/minecraft/core/world/pathfinder/Path;"
            )
    )
    public Path shortSightPath(World instance, Entity attacker, Entity victim, float sightRadius, Operation<Path> original){
        if (
                !(victim instanceof Player)
                || !((IAetherAccessories)((Player) victim)).aether$getInvisible()
                || victim instanceof EnemyBoss
        ) {
            return original.call(instance, attacker, victim, sightRadius);
        }
        return instance.getPathToEntity(attacker, victim, 4.0F);
    }
}
