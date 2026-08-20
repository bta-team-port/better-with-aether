package teamport.aether.mixins.mixin.armor.player.phoenix;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;

@Mixin(Player.class)
public abstract class PlayerMixinFireImmunity extends Mob {
    @Shadow
    @Final
    @NonNull
    public ContainerInventory inventory;

    protected PlayerMixinFireImmunity(@NonNull World world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (this.isInLava() || this.isInWater()) {
            return;
        }
        if (MixinHelper.fireResistanceCount(inventory) >= 3 && random.nextInt(6) == 0) {
            ParticleMaker.spawnFlameParticles(world, x, y, z, bbHeight, bbWidth);
        }

        if (MixinHelper.fireResistanceCount(inventory) >= 5 && random.nextInt(3) == 0) {
            ParticleMaker.spawnFlameParticles(world, x, y, z, bbHeight, bbWidth);
        }
    }
    @Definition(id = "fireImmune", field = "Lnet/minecraft/core/entity/player/Player;fireImmune:Z")
    @Expression("this.fireImmune")
    @ModifyExpressionValue(method = "lavaHurt", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean aether$lavaImmunity(boolean original) {
        if (MixinHelper.fireResistanceCount(inventory) >= 5) {
            MixinHelper.damageArmourWithEffect(4, (Player) (Object) this, x, y, z, bbHeight, bbWidth);
            return true;
        }
        return original;
    }
    @Definition(id = "fireImmune", field = "Lnet/minecraft/core/entity/player/Player;fireImmune:Z")
    @Expression("this.fireImmune")
    @ModifyExpressionValue(method = "fireHurt", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean aether$fireImmunity(boolean original) {
        if (MixinHelper.fireResistanceCount(inventory) >= 3) {
            MixinHelper.damageArmourWithEffect(4, (Player) (Object) this, x, y, z, bbHeight, bbWidth);
            return true;
        }
        return original;
    }
}
