package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Mob.class, remap = false)
public abstract class PlayerMixinPendantFallDamageModifier {
    @WrapOperation(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Ljava/lang/Math;ceil(D)D"))
    public double modifyDamageTaken(double damage, Operation<Double> original) {
        Mob mob = (Mob) (Object) this;
        if (mob instanceof Player) {
            Player player = (Player) mob;
            return Math.ceil(damage - ContainerHelper.countAccessoriesOfMaterial(player.inventory, AetherArmorMaterial.GRAVITITE) * 2);
        }
        return original.call(damage);
    }
}
