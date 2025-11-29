package teamport.aether.mixin.armor.player.neptune;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.player.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinSwimming extends Entity {
    protected MobMixinSwimming(@Nullable World world) {
        super(world);
    }
    @Inject(method = "moveEntityWithHeading", at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;horizontalCollision:Z", ordinal = 0, opcode = Opcodes.GETFIELD))
    private void aether$changeGravity(float moveStrafing, float moveForward, CallbackInfo ci) {
        if (!((Mob) (Object) this instanceof Player)) {
            return;
        }
        Player player = (Player) (Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 5) {
            return;
        }
        yd += 0.02;
        yd -= 0.08;
        yd *= 0.98;
    }
    @ModifyExpressionValue(method = "moveEntityWithHeading", at = @At(value = "CONSTANT", args = "floatValue=0.02F"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isInWater()Z"), to = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;horizontalCollision:Z", opcode = Opcodes.GETFIELD)))
    private float aether$changeMoveRelative(float constant) {
        if (!((Mob) (Object) this instanceof Player)) {
            return constant;
        }
        Player player = (Player) (Object) this;

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 5) {
            return constant;
        }
        return this.speed * 0.4f;
    }
    @ModifyExpressionValue(method = "onLivingUpdate", at = @At(value = "CONSTANT", args = "doubleValue=0.04", ordinal = 0))
    private double aether$changeRisingSpeed(double constant) {
        if (!((Mob) (Object) this instanceof Player)) {
            return constant;
        }
        Player player = (Player) (Object) this;

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.NEPTUNE) < 5) {
            return constant;
        }
        return 0.16;
    }
}
