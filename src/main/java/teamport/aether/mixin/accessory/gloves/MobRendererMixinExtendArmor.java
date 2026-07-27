package teamport.aether.mixin.accessory.gloves;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRendererBipedArmored;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererBipedArmored.class, remap = false)
public abstract class MobRendererMixinExtendArmor<T extends Mob> {
    @ModifyReturnValue(method = "maxRenderLayer(Lnet/minecraft/core/entity/Mob;)I", at = @At("RETURN"))
    private int extendArmorLayers(int original, T entity) {
        // this renderer hates me
        return entity instanceof Player ? original + 4 : original;
    }
}
