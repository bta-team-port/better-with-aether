package teamport.aether.mixin.armor.player.phoenix;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinFireImmunity extends Mob {
    protected PlayerMixinFireImmunity(@Nullable World world) {
        super(world);
    }
    @Shadow
    public ContainerInventory inventory;
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
