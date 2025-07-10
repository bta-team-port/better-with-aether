package teamport.aether.mixin.entity.mob;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixinNeptune extends Entity {
    public MobMixinNeptune(@Nullable World world) {
        super(world);
    }

    @Inject(
            method = "moveEntityWithHeading",
            at = @At(value = "FIELD", target = "Lnet/minecraft/core/entity/Mob;horizontalCollision:Z", ordinal = 0)
    )
    private void aether$changeGravity(float moveStrafing, float moveForward, CallbackInfo ci) {
        if (!((Mob)(Object) this instanceof Player)) {
            return;
        }
        Player player = (Player)(Object) this;
        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory,AetherArmorMaterial.NEPTUNE) < 4) {
            return;
        }
        yd += 0.02;
        yd -= 0.08;
        yd *= 0.98;
    }

    // TODO maybe target in some other way
    @ModifyConstant(
            method = "moveEntityWithHeading",
            constant = @Constant(floatValue = 0.02f),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/core/entity/Mob;isInWater()Z"
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/core/entity/Mob;horizontalCollision:Z"
                    )
            )
    )
    private float aether$changeMoveRelative(float constant) {
        if (!((Mob)(Object) this instanceof Player)) {
            return constant;
        }
        Player player = (Player)(Object) this;

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory,AetherArmorMaterial.NEPTUNE) < 4) {
            return constant;
        }
        return this.speed * 0.4f;
    }

    // TODO maybe target in some other way
    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(doubleValue = 0.04, ordinal = 0))
    private double aether$changeRisingSpeed(double constant) {
        if (!((Mob)(Object) this instanceof Player)) {
            return constant;
        }
        Player player = (Player)(Object) this;

        if (ContainerHelper.countArmorPiecesOfMaterial(player.inventory,AetherArmorMaterial.NEPTUNE) < 4) {
            return constant;
        }
        return 0.16;
    }




}
