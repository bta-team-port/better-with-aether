package teamport.aether.mixin.entity.mob;

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
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;
import teamport.aether.mixin.accessors.EntityAccessor;

import java.util.Random;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinGravitite extends Entity {

    @Shadow
    public boolean isJumping;

    @Unique
    public boolean usedDoubleJump = false;

    @Unique
    public boolean isJumpingPrev = false;

    public MobMixinGravitite(@Nullable World world) {
        super(world);
    }

    @Inject(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isSprinting()Z"))
    public void aether$jump(CallbackInfo ci) {
        if (!((Mob)(Object) this instanceof Player)) {
            return;
        }
        Player player = (Player)(Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) == 4) {
            yd = 1.05;
            fallDistance = 0.0F;
        }
    }

    @Inject(method = "onLivingUpdate",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;moveStrafing:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
    public void aether$onLivingUpdate(CallbackInfo ci) {
        if (!((Mob)(Object) this instanceof Player)) {
            return;
        }
        Player player = (Player)(Object) this;

        if (noPhysics) {
            usedDoubleJump = true;
            return;
        }

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) < 4) return;
        if (!onGround && !isJumpingPrev && isJumping && !usedDoubleJump) {
            yd = 1.05;
            fallDistance = 0.0F;
            aether$spawnCloudParticles();
            usedDoubleJump = true;
        }
        if (onGround) {usedDoubleJump = false;}

        isJumpingPrev = isJumping;
    }


    @Unique
    public void aether$spawnCloudParticles() {
        float width = 1.0f;

        for (int i = 0; i < 20; ++i) {
            Random random = ((EntityAccessor)this).getRandom();
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            // TODO again what is the data field used for?
            world.spawnParticle(
                    "snowshovel",
                    x + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    y - bbHeight + (double) (random.nextFloat() * width),
                    z + (double) (random.nextFloat() * width * 2.0F) - (double) width,
                    dx, dy, dz, 2
            );
        }
    }

}
