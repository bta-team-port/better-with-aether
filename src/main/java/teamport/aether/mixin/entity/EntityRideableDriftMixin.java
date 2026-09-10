package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.world.IVehicle;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.animal.MobAetherAnimalRideable;

@Mixin(Entity.class)
public abstract class EntityRideableDriftMixin {

    @Shadow
    @Nullable
    public IVehicle vehicle;

    @Definition(id = "MobPig", type = MobPig.class)
    @Expression("? instanceof MobPig")
    @ModifyExpressionValue(method = "rideTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean treatAetherMountsAsPigForRiderRotation(boolean original) {
        return original || this.vehicle instanceof MobAetherAnimalRideable;
    }

}
