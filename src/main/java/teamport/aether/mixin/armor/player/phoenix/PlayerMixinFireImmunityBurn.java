package teamport.aether.mixin.armor.player.phoenix;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.helper.MixinHelper;

@Mixin(value = Entity.class, remap = false)
public abstract class PlayerMixinFireImmunityBurn {
    @Shadow
    public double x;
    @Shadow
    public double y;
    @Shadow
    public double z;
    @Shadow
    public float bbHeight;
    @Shadow
    public float bbWidth;
    @Shadow
    public abstract boolean hurt(Entity attacker, int baseDamage, DamageType type);
    @WrapMethod(method = "burn")
    public void burn(int damage, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof Player)) {
            original.call(damage);
            return;
        }
        Player player = (Player) (Object) this;
        if (MixinHelper.fireResistanceCount(player.inventory) >= 3) {
            MixinHelper.damageArmourWithEffect(1, player, x, y, z, bbHeight, bbWidth);
            return;
        }
        original.call(damage);
    }
    @WrapMethod(method = "thunderHit")
    public void thunderHit(EntityLightning bolt, Operation<Void> original) {
        if (!((Entity) (Object) this instanceof Player)) {
            original.call(bolt);
            return;
        }
        Player player = (Player) (Object) this;
        if (MixinHelper.fireResistanceCount(player.inventory) >= 5) {
            // we only negate the burn but the player takes the lightning damage
            hurt(null, 5, DamageType.FIRE);
            MixinHelper.damageArmourWithEffect(1, player, x, y, z, bbHeight, bbWidth);
            return;
        }
        original.call(bolt);
    }
}
