package teamport.aether.mixin.armor.player.gravitite;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.api.ContainerHelper;
import teamport.aether.api.ParticalHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinJump extends Entity {

    @Shadow
    public boolean isJumping;

    @Unique
    public boolean usedDoubleJump = false;

    @Unique
    public boolean isJumpingPrev = false;

    public MobMixinJump(@Nullable World world) {
        super(world);
    }

    @Inject(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isSprinting()Z"))
    public void aether$jump(CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) == 4) {
            yd = 1.05;
            fallDistance = 0.0F;
        }
    }

    @Inject(method = "onLivingUpdate",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;moveStrafing:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
    public void aether$onLivingUpdate(CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;

        if (noPhysics) {
            usedDoubleJump = true;
            return;
        }

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) < 4) return;
        if (!onGround && !isJumpingPrev && isJumping && !usedDoubleJump) {
            yd = 1.05;
            fallDistance = 0.0F;
            ParticalHelper.spawnCloudParticles(world, x, y, z, bbHeight);
            usedDoubleJump = true;
        }
        if (onGround) {
            usedDoubleJump = false;
        }

        isJumpingPrev = isJumping;
    }
}
