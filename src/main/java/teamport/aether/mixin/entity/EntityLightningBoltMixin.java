package teamport.aether.mixin.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLightning;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherMod;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityLightningBoltMixin {
    @Shadow
    public abstract boolean hurt(Entity attacker, int baseDamage, DamageType type);
    @Inject(method = "thunderHit", at = @At("HEAD"))
    private void thunderHit(EntityLightning bolt, CallbackInfo ci) {
        this.hurt(null, 5, AetherMod.LIGHTNING);
    }
}
