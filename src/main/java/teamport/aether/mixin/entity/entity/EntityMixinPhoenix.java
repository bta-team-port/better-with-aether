package teamport.aether.mixin.entity.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.accessory.api.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Entity.class, remap = false)
public class EntityMixinPhoenix {

    @Inject(method = "isInWaterOrRain", at = @At("HEAD"), cancellable = true)
    public void aether$cantCatchFire(CallbackInfoReturnable<Boolean> cir){
        if(((Entity)(Object)this) instanceof Player){
            Player player = ((Player)(Object)this);
            if(ContainerHelper.countArmorPiecesOfMaterial(player.inventory, AetherArmorMaterial.phoenix) == 4){
                cir.setReturnValue(true);
            }
        }
    }

}
