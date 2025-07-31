package teamport.aether.mixin.accessory.cape.invisibilitycape;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.entity.EnemyBoss;
import teamport.aether.items.IAetherAccessories;

@Mixin(value = World.class, remap = false)
public abstract class WorldMixinHardToSpot {
    @Shadow public abstract Player getClosestPlayer(double x, double y, double z, double radius);

    @Inject(method = "getClosestPlayerToEntity", at = @At("HEAD"), cancellable = true)
    public void hardToSeePlayer(Entity attacker, double radius, CallbackInfoReturnable<Player> cir){
        Player player = this.getClosestPlayer(attacker.x, attacker.y, attacker.z, radius);
        if (
                player == null
                || !((IAetherAccessories) player).aether$getInvisible()
                || attacker instanceof EnemyBoss
        ) {
            cir.setReturnValue(player);
        }
        cir.setReturnValue(this.getClosestPlayer(attacker.x, attacker.y, attacker.z, 2.0F));
    }

}
