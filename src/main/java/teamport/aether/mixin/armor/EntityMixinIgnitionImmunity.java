package teamport.aether.mixin.armor;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;

@Mixin(Entity.class)
public abstract class EntityMixinIgnitionImmunity {
    @ModifyReturnValue(method = "isInWaterOrRain", at = @At("RETURN"))
    private boolean cantCatchFire(boolean original) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof Player player && MixinHelper.fireResistanceCount(player.inventory) >= 3) return true;
        if (entity instanceof MobWolf wolf && MixinHelper.isImmuneToFire(wolf)) return true;
        return original;
    }
}
