package teamport.aether.mixin.armor.player.gravitite;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherArmorMaterial;

@Mixin(Mob.class)
public abstract class MobMixinJump extends Entity {

    @Shadow
    public abstract boolean isJumping();

    protected MobMixinJump(@NonNull World world) {
        super(world);
    }

    @Unique
    private boolean usedDoubleJump = false;
    @Unique
    private boolean isJumpingPrev = false;

    @Inject(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isSprinting()Z"))
    private void aether$jump(CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;
        if (PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) >= 5) {
            yd = 1.05;
            fallDistance = 0.0F;
        }
    }

    @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;moveStrafing:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
    private void aether$onLivingUpdate(CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;
        if (hasNoPhysics()) {
            usedDoubleJump = true;
            return;
        }
        if (PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) < 5) return;
        if (!onGround && !isJumpingPrev && isJumping() && !usedDoubleJump) {
            yd = 1.05;
            fallDistance = 0.0F;
            ParticleMaker.spawnCloudParticles(world, x, y, z, bbHeight);
            usedDoubleJump = true;
        }
        if (onGround) {
            usedDoubleJump = false;
        }
        isJumpingPrev = isJumping();
    }

    @WrapOperation(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z"))
    private boolean negateFallDamage(Mob instance, Entity attacker, int damage, DamageType type, Operation<Boolean> original) {
        if (!(instance instanceof Player player)) {
            return original.call(instance, attacker, damage, type);
        }
        if (PlayerUtil.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) < 5) {
            return original.call(instance, attacker, damage, type);
        }
        damage = damage - 13;
        player.damageArmor(damage > 0 ? damage : 1);
        return false;
    }
}
