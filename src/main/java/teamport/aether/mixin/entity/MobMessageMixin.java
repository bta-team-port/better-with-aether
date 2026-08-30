package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.IVehicle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.entity.PreVehicle;

@Mixin(Mob.class)
public abstract class MobMessageMixin implements PreVehicle {
    @Unique
    protected IVehicle prevVehicle = null;

    @WrapOperation(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/IVehicle;ejectRider()Lnet/minecraft/core/entity/Entity;"))
    private Entity captureEjectedEntity(IVehicle instance, Operation<Entity> original){
        this.prevVehicle = instance;
        return original.call(instance);
    }

    @Override
    public IVehicle better_with_aether$preVehicle() {
        return prevVehicle;
    }

    @Override
    public void better_with_aether$resestVehicle() {
        this.prevVehicle = null;
    }
}
